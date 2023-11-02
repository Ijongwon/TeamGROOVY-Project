const myModal = document.getElementById('exampleModal')
const myInput = document.getElementById('myInput')
const schDateInput = document.querySelector('#schDateInput');
let post = ''

const calbtn = function (postidx) {
    const hello = document.forms[2]
    hello.post_idx.value = postidx
    // const post_idx = postidx;
    const xhr = new XMLHttpRequest()
    xhr.open('GET', `/board/commentList/` + postidx)
    // xhr.open('GET', `/board/commentList/` + post_idx)
    xhr.send()
    xhr.onload = function () {
        if (xhr.status === 200 || xhr.status === 201) {
            const list = JSON.parse(xhr.response)
            const scElement = document.getElementById('sc');
            scElement.innerHTML = ''

            if (list.length === 0) {
                scElement.innerHTML = '<li><p style="padding: 98px;">댓글을 입력 하세요.</p></li>'
            } else {
                list.forEach(item => {
                    if(likeuser == item.likeuser) {
                        const $context =

                            `
                            <li class="my-2 ps-3 d-flex justify-content-between border-start border-4 border-primary">
                                <span>${item.likeuser}: ${item.comment_content}</span>
                                <button class="btn btn-sm border-0 rounded-pill" id="${item.cmt_idx}" value="${item.cmt_idx}" onclick="deleted(${item.cmt_idx},${postidx})" >
                                    <i class="bi bi-x fs-5 link-primary"></i>
                                </button>
                            </li>
        
                                `
                        scElement.innerHTML += $context;
                    }
                    else {
                        const $context =
                            `
                        <li class="my-2 ps-3 d-flex border-start border-4 border-primary">
                            <span>${item.likeuser}: ${item.comment_content}</span>
                        </li>
                        `
                        console.log($context)
                        scElement.innerHTML += $context;
                    }

                })

            }

        } else {
            console.error('오류1', xhr.status)
            console.error('오류2', xhr.response)
        }
    }
}

const deleted = function(cmtidx,postidx){

    const xhr = new XMLHttpRequest()
    xhr.open('DELETE','/board/comment/' + cmtidx)
    xhr.send()
    xhr.onload = function (){
        if(xhr.status === 200 || xhr.status === 201){
            const result = JSON.parse(xhr.response)
            alert('삭제되었습니다.')
            calbtn(postidx)
            window.location = '/board/feedDetail?post_idx=' + postidx  //삭제 버튼 누르면 feedDetail로 되돌아감
        } else {
            console.error('오류1',xhr.status, xhr.response)
            console.error('오류2',xhr.response)

        }
    }
}