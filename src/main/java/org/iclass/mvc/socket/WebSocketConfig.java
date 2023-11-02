package org.iclass.mvc.socket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer{

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(alertHandler(), "/alarm")
				// ** url이  /alarm 인 요청은 alertHandler 객체가 처리함. **
		.setAllowedOrigins("http://localhost:8086");	//url은 소켓통신을 사용할 서버주소
		// /alarm 소켓통신의 endpoint
		
	}

	
	@Bean    //bean(객체로 생성됨.)
	public AlertHandler alertHandler() {

		return new AlertHandler();
	}
}
