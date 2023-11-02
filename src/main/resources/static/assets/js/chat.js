var stompClient = null;
var roomnum = document.querySelector('#roomnum').value
var sender = document.getElementById('user-id').value
function connect(){
    var socket = new SockJS('/ws/chat');
    stompClient = Stomp.over(socket);

    stompClient.connect({}, function(frame) {
        console.log('Connected:' + frame);
        //chatroom+roomnum 구독
        stompClient.subscribe('/sub/chatroom/'+roomnum, function (response){

            //전달 내용 받기
            var content = JSON.parse(response.body)
            /////////////////
            var chatBox = document.querySelector('#chat-box')

            // 내 메시지와 다른 사람의 메시지에 따라 적절한 클래스 및 스타일 적용
            var messageClass = (content.cm_sender === sender) ? 'chatmy' : 'chatothers';
            // 새로운 채팅 메시지를 생성
            var newMessage = '<div class="' + messageClass + '">' +
                '<div class="icon" + >' + content.cm_sender + '</div>' +
                '<div class="textbox" +>' + content.content + '</div>' +
                '</div>';

            // chat-box의 innerHTML에 새로운 메시지 추가
            chatBox.innerHTML += newMessage;
            chatBox.scrollTop=chatBox.scrollHeight;

        })
            //입장 메세지
            stompClient.send('/pub/chatroom/enter', {}, JSON.stringify({cm_roomnum: roomnum, cm_sender: sender}))

        //메세지 보내기 함수
        document.getElementById("send-btn").addEventListener('click',e => {
                performAction();
        })

        document.getElementById("msg").addEventListener('keydown', e => {
                if (e.key === "Enter")
                    performAction();
            })


        function performAction(){
            var msg = document.getElementById("msg")

            //메세지 내용 보내기
            stompClient.send('/pub/chatroom/message',{},JSON.stringify({cm_roomnum: roomnum, cm_sender:sender ,content: msg.value}))
            msg.value = '';

        }
    })//connect 됐을때 함수 끝

}//connect 함수 끝

function disconnect(){
    if (stompClient!==null){
        stompClient.send('/pub/chatroom/leave',{},JSON.stringify({cm_roomnum: roomnum, cm_sender: sender}))
    stompClient.disconnect()
    }



}
//나갈때 함수