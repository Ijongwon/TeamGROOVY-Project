package org.iclass.mvc.socket;

import lombok.extern.slf4j.Slf4j;
import org.iclass.mvc.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//WebSocketHandler 의 자식 클래스 TextWebSocketHandler 를 상속받은 클래스 정의하기
@Slf4j
public class AlertHandler extends TextWebSocketHandler {
// 서버는 다수의 클라이언트가 보낸 메세지를  받게 되고 이를 처리할 핸들러가 필요
// 텍스트 기반의 통신을 구현해볼 것 이므로 'TextWebSocketHandler'를 상속받아서 작성한다.

	//데이터 송수신이 발생했을 때 처리 할 내용을 정해본다.
	//1. 새로운 클라이언트와 소켓이 연결되면 *모든 접속자들에게* 'id ... 님이 접속하였습니다. ' 라고 보낸다.
	private List<WebSocketSession> wsslist = new ArrayList<>();				//1-a.접속자들의 세션을 저장할 리스트

	//2. 좋아요 클릭하면 *글 작성자에게만* 메세지 보내기
	private Map<String,WebSocketSession> wsmap = new HashMap<>();

	//좋아요 처리에 필요한 서비스 클래스 자동 주입
	private PostService service;
	@Autowired			//socket config 클래스에서 기본생성자 사용하므로 setter 주입
	public void setService(PostService service) {
		this.service = service;
	}

	//소켓 연결이 이루어 진 후에 할일 처리 - 새로운 클라이언트와의 연결이 발생하면 실행되는 메소드
		@Override   //지금까지 세션은 http프로토콜 세션(HttpSession), 웹소켓은 ws프로토콜 전달받는 세션은 소켓세션
		public void afterConnectionEstablished(WebSocketSession session) throws Exception {
			log.info("새로운 클라이언트 : {}",session);
			wsslist.add(session);				//1-b. 세션 저장
			
		}

		//소켓 연결이 해제 된 후에 할일 처리
		@Override
		public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
				log.info("접속 해제 클라이언트 : {}",session);  
				//1-f. 접속이 해제되면 wsslist 에서 삭제
				wsslist.remove(session);
		}	
	
		//데이터를 수신하면 처리하는 핸들러 메소드 session은 누가 보냈는지, message는 보낸 내용이 무엇인지
		//js(클라이언트)가 보내면 서버는 받은 데이터를 이 메소드가 처리한다.
	   @Override
		protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
			log.info("데이터를 보낸 클라이언트 : {}",session );
			log.info("받은 메시지 : {}",message.getPayload());

			//1-c. 1번을 실행하기 위해 접속자들은 id값을 보낸다. 문자열 형식으로 정하자. 클라이언트가 "open/접속자id" 보내면 이 메소드에서 받아 처리 할수 있다.
			//											ㄴ 자바스크립트에서 연결이 시작되면 보낼 메시지 작성필요 1-d
			String content = message.getPayload();		//2-e. 변수명을 이해하기 쉬운 이름으로 변경(받은 메시지 변수)
			String senderId=null;
			if(content.startsWith("open")) {			//2-e. 변수명을 이해하기 쉬운 이름으로 변경(받은 메시지 변수)
				senderId = content.substring(5);		//  클라이언트가 보낸 open/접속자id 에서 / 기호 뒤의 접속자 id만 가져오기
				for(WebSocketSession ws : wsslist)		// 모든 접속자에게 보내기
					ws.sendMessage(new TextMessage(senderId +" 님이 접속하였습니다."));
					//ws에게 메시지 보내기: 보낼 메시지 타입은 TextMessage

				//map에 사용자id를 key, 소켓세션을 value 로 저장하기 - 좋아요에서 필요
				wsmap.put(senderId,session);

			} //if end

		   if(content.startsWith("close")) {			//2-e. 변수명을 이해하기 쉬운 이름으로 변경(받은 메시지 변수)
			   senderId = content.substring(6);		//  클라이언트가 보낸 close/접속자id 에서 / 기호 뒤의 접속자 id만 가져오기
			   for(WebSocketSession ws : wsslist)		// 모든 접속자에게 보내기
				   ws.sendMessage(new TextMessage(senderId +" 님이 로그아웃 하였습니다."));
			   //ws에게 메시지 보내기: 보낼 메시지 타입은 TextMessage

		   } //if end

		   if(content.startsWith("likes")) {
			   String data = content.substring(6);
			   Map<String,String> response = service.processLikeCount(data);

			   log.info(">>>> 서비스 응답 userid : {}",response.get("userid"));
			   log.info(">>>> 서비스 응답 result : {}",response.get("result"));

		   //응답 받은 userid 가 로그인 되어 있으면 1:1 메세지 보내기 -> map 에서 session 찾기
		   WebSocketSession receiver = wsmap.get(response.get("userid"));
		   if (receiver!=null)
			   receiver.sendMessage(new TextMessage(response.get("result")));
		   }	//좋아요 if 끝
			
	   } //handleTextMessage end
	  
	   
}
