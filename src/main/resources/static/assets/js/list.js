let nowcate = ''

const isCate = function () {
    console.log('isCate')
    if (nowcate == '') {
        selectAllSearch()
    } else{
        selectcate(nowcate)
    }
}

const selectAll = function () {
    console.log('selectAll')
    nowcate = ''
    const xhr = new XMLHttpRequest()
    xhr.open('GET','/group/all')
    xhr.send()
    xhr.onload=function (){
        if(xhr.status===200 || xhr.status===201){
            const list = JSON.parse(xhr.response)
            console.log(list)
            makeList(list)
            console.log(nowcate)
        } else {
            console.error('오류1',xhr.status)
            console.error('오류2',xhr.response)
        }
    }
}

const selectAllSearch = function () {
    nowcate = ''
    console.log('selectAllSearch')
    const xhr = new XMLHttpRequest()
    const key = document.forms[0].keyword.value
    console.log(key)
    xhr.open('GET','/group/all/'+key)
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
    console.log('selectcate')
    nowcate = cate
    let key = document.forms[0].keyword.value
    if (key ==''){
        key = 'null'
    }
    console.log('selectcate key'+key)
    const xhr = new XMLHttpRequest()
    xhr.open('GET', '/group/groups/' + cate+'/'+key)
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

    list.forEach(item => {
        const $temp = `<div style="height: 500px; margin-bottom: 100px;" class="col-lg-6 col-12 item ${item.groupcate}">
        <a class="text-decoration-none" href="/group/groupdetail?groupidx=${item.groupidx}">
        <div class="card mb-2 bg-white card-showcase">
        <div class="card-body text-center">
        <h4 class="card-title fw-bold mb-0" style="color: #7aa1d2">${item.groupname}</h4>
        <div class="card-text text-dark">${item.groupcate}</div>
        </div><img class="card-img-bottom" style="height: 450px;" src="/uploads/${item.grouppic}" alt="Feature" />
        </div>
        </a></div>`;

        document.getElementById('grouplist').innerHTML += $temp;
    })
}

/* 기본 실행 함수*/
const xh = new XMLHttpRequest()
xh.open('GET','/group/all')
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
