
const isHeartGroup = function () {
    const xhr = new XMLHttpRequest()
    xhr.open('GET','/group/hearts/'+groupidx)
    xhr.send()
    xhr.onload = function () {
        if (xhr.status === 200 || xhr.status === 201) {
            const num = JSON.parse(xhr.response)
            isHeart(num)
        } else {
            console.error('오류1', xhr.status)
            console.error('오류2', xhr.response)
        }
    }
}

const isHeart = function (num) {
    const heartimg = document.getElementById('heart')
    if (num == 1) {
        heartimg.src = '/assets/img/fullheart.png'
    } else {
        heartimg.src = '/assets/img/emptyheart.png'
    }

}
function isLogin() {
    if (userid == 'null'){
        alert('로그인이 필요한 기능입니다.로그인 하시겠습니까?')
        return
    }
    else {
        return
    }

}


function heartonoff() {
    isLogin()
    const xhr = new XMLHttpRequest()
    xhr.open('GET','/group/heartsonoff/'+groupidx)
    xhr.send()
    xhr.onload = function () {
        if (xhr.status === 200 || xhr.status === 201) {
            const num = JSON.parse(xhr.response)
            isHeart(num)
        } else {
            console.error('오류1', xhr.status)
            console.error('오류2', xhr.response)
        }
    }

}

document.getElementById('heartonoff').addEventListener('click',heartonoff)



isHeartGroup()