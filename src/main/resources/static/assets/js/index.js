const selectAll = function () {
    const xhr = new XMLHttpRequest()
    xhr.open('GET','/all')
    xhr.send()
    xhr.onload=function (){
        if(xhr.status===200 || xhr.status===201){
            const list = JSON.parse(xhr.response)
            console.log(list)
            makeList(list)
        } else {
            console.error('오류1',xhr.status)
            console.error('오류2',xhr.response)
        }
    }
}

const selectcate = function (cate) {
    const xhr = new XMLHttpRequest()
    xhr.open('GET', '/groups/' + cate)
    xhr.send()
    xhr.onload = function () {
        if (xhr.status === 200 || xhr.status === 201) {
            const list = JSON.parse(xhr.response)
            makeList(list)
        } else {
            console.error('오류1', xhr.status)
            console.error('오류2', xhr.response)
        }
    }
}





const makeList = function (list) {
    document.getElementById('grouplist').innerHTML =''
    if(list.length > 4){
        for (let i = 0; i <4 ; i ++){
            const $temp = `<div style="height: 500px; margin-bottom: 100px;" class="col-lg-6 col-12 item ${list[i].groupcate}">
        <a class="text-decoration-none" href="/group/groupdetail?groupidx=${list[i].groupidx}">
        <div class="card mb-2 bg-white card-showcase">
        <div class="card-body text-center">
        <h4 class="card-title fw-bold mb-0" style="color: #7aa1d2">${list[i].groupname}</h4>
        <div class="card-text text-dark">${list[i].groupcate}</div>
        </div><img class="card-img-bottom" style="height: 450px;" src="/uploads/${list[i].grouppic}" alt="Feature" />
        </div>
        </a></div>`;

            document.getElementById('grouplist').innerHTML += $temp;
        }
    } else {
        for (let i = 0; i <list.length ; i ++){
            const $temp = `<div style="height: 500px; margin-bottom: 100px;" class="col-lg-6 col-12 item ${list[i].groupcate}">
        <a class="text-decoration-none" href="/group/groupdetail?groupidx=${list[i].groupidx}">
        <div class="card mb-2 bg-white card-showcase">
        <div class="card-body text-center">
        <h4 class="card-title fw-bold mb-0" style="color: #7aa1d2">${list[i].groupname}</h4>
        <div class="card-text text-dark">${list[i].groupcate}</div>
        </div><img class="card-img-bottom" style="height: 450px;" src="/uploads/${list[i].grouppic}" alt="Feature" />
        </div>
        </a></div>`;

            document.getElementById('grouplist').innerHTML += $temp;
        }
    }

    document.getElementById('grouplist').innerHTML +=
        '<button type="button" class="btn btn-primary" style="width: 800px; margin: 0px auto;" onclick="goList()">더보기</button>'


}

function goList() {
    location.href='/group/grouplist'
}

/* 기본 실행 함수*/
const xh = new XMLHttpRequest()
xh.open('GET','/all')
xh.send()
xh.onload=function (){
    if(xh.status===200 || xh.status===201){
        const list = JSON.parse(xh.response)
        console.log(list)
        makeList(list)
    } else {
        console.error('오류1',xh.status)
        console.error('오류2',xh.response)
    }
}
