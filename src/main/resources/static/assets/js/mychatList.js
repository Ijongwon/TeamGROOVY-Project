const mychatList = function () {
    const xhr = new XMLHttpRequest()
    xhr.open('GET','/chat/mychatList/'+chuserid)
    xhr.send()
    xhr.onload = function (){
        if(xhr.status===200 || xhr.status === 201) {
            const list = JSON.parse(xhr.response)
            makechatList(list)
        } else {
            console.error('오류1',xhr.status)
            console.error('오류2',xhr.response)
        }

    }
}
const makechatList = function (list) {

    document.getElementById('accordionExample').innerHTML = ''
    list.forEach(item => {
        const $for = `
                            <div class="accordion-item" id="text">
                                <h2 class="accordion-header">
                                    <button id="btn" class="accordion-button collapsed" type="button" data-bs-toggle="collapse"
                                            data-bs-target="${item.groupidx}" aria-expanded="true"
                                            aria-controls="${item.groupidx}" onclick="groupchatList('${item.groupidx}')">
                                            ${item.groupname}
                                     </button>
                                </h2>
                            </div>
                            <div id="${item.groupidx}" class="accordion-collapse collapse">
                            </div>
                            `

        document.getElementById('accordionExample').innerHTML += $for
    })


}
mychatList();


const groupchatList = function (groupidx) {
    console.log(groupidx)
    const chatlist = groupidx

    console.log(chatlist)
    const xhr = new XMLHttpRequest()
    xhr.open('GET','/chat/groupchatList/'+groupidx)
    xhr.send()
    xhr.onload = function (){
        if(xhr.status===200 || xhr.status === 201) {
            const list = JSON.parse(xhr.response)
            console.log(list)
            document.getElementById(chatlist).innerHTML =''
            document.getElementById(chatlist).classList.toggle('show')
            list.forEach(item => {
               let $content = null
                    $content = `
                <div>
                <div class="accordion-body" style="padding: 0px;">
                    <ol class="list-group">
                        <li class="list-group-item d-flex justify-content-between align-items-start">
                            <div class="ms-2 me-auto">
                                <div>
                                    <a href="chatDetails?roomnum=${item.roomnum}">${item.roomname}</a>
                                </div>
                            </div>
                        </li>
                    </ol>
                    </div>
                </div>
            </div>
            `
                    document.getElementById(chatlist).innerHTML += $content

            })
            if(list.length==0){
                    $content = `
                     <div>
                     <div class="accordion-body" style="padding: 0px;">
                        <ol class="list-group">
                            <li class="list-group-item d-flex justify-content-between align-items-start">
                                 <div class="ms-2 me-auto">
                                    <div class="fw-bold">
                                        <p style="color: #7eacf3">참여중인 채팅방이 없습니다.</p>
                                    </div>
                                 </div>
                            </li>
                        </ol>
                     </div>
                     </div>
                 </div>
                    `
                    document.getElementById(chatlist).innerHTML += $content
            }


        } else {
            console.error('오류1',xhr.status)
            console.error('오류2',xhr.response)
        }

    }

}




