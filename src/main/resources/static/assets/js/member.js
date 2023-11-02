
const groupmembers = function () {
    const xhr = new XMLHttpRequest()
    xhr.open('GET','/group/members/'+groupidx)
    xhr.send()
    xhr.onload = function () {
        if (xhr.status === 200 || xhr.status === 201) {
            const list = JSON.parse(xhr.response)

            makeMemberList(list)
        } else {
            console.error('오류1', xhr.status)
            console.error('오류2', xhr.response)
        }
    }

}

document.getElementById('memlist').addEventListener('click',groupmembers)
const groupadmembers = function () {
    const xhr = new XMLHttpRequest()
    xhr.open('GET','/group/admembers/'+groupidx)
    xhr.send()
    xhr.onload = function () {
        if (xhr.status === 200 || xhr.status === 201) {
            const list = JSON.parse(xhr.response)
            makeAdMemberList(list)
        } else {
            console.error('오류1', xhr.status)
            console.error('오류2', xhr.response)
        }
    }

}
document.getElementById('adlist').addEventListener('click',groupadmembers)

const modal = document.getElementById('modalWrap');
const closeBtn = document.querySelector('.closeBtn');
function clicks(num) {
    console.log('클릭')
    console.log(num)
    closeBtn.onclick = function() {
        modal.style.display = 'none';
        groupmembers()
    }
    if(num == 1) {
        document.getElementById('modaltext').innerHTML
            = `<p>진짜 그룹에서 추방하시겠습니까?</p>
                    <button class="btn btn-primary" onclick="kick()">네</button>
                    `
        modal.style.display = 'block'
    } else {
        document.getElementById('modaltext').innerHTML
            = `<p>${id}님께 방장을 위임하시겠습니까?</p>
                    <button class="btn btn-primary" onclick="assign()">네</button>
                    `
        modal.style.display = 'block'
    }

}

function kick() {
    const xhr = new XMLHttpRequest()
    xhr.open('GET','/group/deleteMember/'+groupidx+'/'+id)
    xhr.send()
    xhr.onload = function () {
        if (xhr.status === 200 || xhr.status === 201) {
            document.getElementById('modaltext').innerHTML = ''
            document.getElementById('modaltext').innerHTML =
                `
        <p>추방 완료</p>
        `
            groupmembers()
        } else {
            console.error('오류1', xhr.status)
            console.error('오류2', xhr.response)
        }
    }

}

function assign() {
    const xhr = new XMLHttpRequest()
    xhr.open('GET','/group/updateGroupMng/'+groupidx+'/'+id)
    xhr.send()
    xhr.onload = function () {
        if (xhr.status === 200 || xhr.status === 201) {
            document.getElementById('modaltext').innerHTML = ''
            document.getElementById('modaltext').innerHTML =
                `
        <p>${id}님께 위임이 완료되었습니다.</p>
        `
            location.href = 'groupdetail?groupidx='+ groupidx  +''
        } else {
            console.error('오류1', xhr.status)
            console.error('오류2', xhr.response)
        }
    }
}

const userprofile = function (profile,userid) {
    id = userid
    console.log(profile)
    document.getElementById('profileimg').innerHTML=''
    const $profile = `<img style="width: 350px; height: 350px;"
src="/uploads/${profile}">`
    document.getElementById('profileimg').innerHTML += $profile

    const xhr = new XMLHttpRequest()
        xhr.open('GET','/group/memberdetail/'+groupidx+'/'+userid)
        xhr.send()
        xhr.onload = function () {
            if (xhr.status === 200 || xhr.status === 201) {
                const dto = JSON.parse(xhr.response)
                    document.getElementById('userinfo').innerHTML = ''
                if (userid == groupmng) {
                    const $content =
                        `<div>
                <p style="margin-top: 0px;">${dto.userid}</p>
                <p>${dto.groupjoindate}</p>
                <p>${dto.userintro}</p>
                <p>방장</p>
            </div>`
                    document.getElementById('userinfo').innerHTML = $content
                } else {
                    const $content =
                        `<div>
                <p style="margin-top: 0px;">${dto.userid}</p>
                <p>${dto.groupjoindate}</p>
                <p>${dto.userintro}</p>
                    <div class="fncbtn">
                    <button class="btn btn-primary" type="button" onclick="clicks(1)">추방하기</button>
                    <button class="btn btn-primary" type="button" onclick="clicks(2)">방장위임</button>
                </div>
            </div>`
                    document.getElementById('userinfo').innerHTML = $content


                }


            } else {
                console.error('오류1', xhr.status)
                console.error('오류2', xhr.response)
            }
        }

}

const aduserprofile = function (profile,userid) {
    console.log(profile)
    document.getElementById('profileimg').innerHTML=''
    const $profile = `<img style="width: 350px; height: 350px;"
src="/uploads/${profile}">`
    document.getElementById('profileimg').innerHTML += $profile

    const xhr = new XMLHttpRequest()
    xhr.open('GET','/group/admember/'+groupidx+'/'+userid)
    xhr.send()
    xhr.onload = function () {
        if (xhr.status === 200 || xhr.status === 201) {
            const dto = JSON.parse(xhr.response)
            document.getElementById('userinfo').innerHTML = ''
            const $content =
                `<div>
                <p style="margin-top: 0px;">${dto.userid}</p>
                <p>${dto.username}</p>
                <p>${dto.userage}</p>
                <p>${dto.usergender}</p>
                <p>${dto.adanswer}</p>
                <div class="fncbtn">
                <button class="btn btn-primary" onclick="adtof('${dto.userid}','수락')">수락</button>
                <button class="btn btn-primary" onclick="adtof('${dto.userid}','거절')">거절</button>
            </div>
            </div>`

            document.getElementById('userinfo').innerHTML = $content


        } else {
            console.error('오류1', xhr.status)
            console.error('오류2', xhr.response)
        }
    }

}

const makeMemberList = function (list) {
document.getElementById('memberList').innerHTML = ''
    list.forEach(item => {
        if(item.userid == groupmng) {
            const $memberList =
                `<div class="members" style="margin: 10px; border: 1px solid black;">
              <div class="img">
                 <img class="profile" style="width: 100px; height: 100px;" src="/uploads/${item.userprofile}"
                 onclick="userprofile('${item.userprofile}','${item.userid}')">
             </div>
            <div class="info">
                <p style="font-weight: bold">${item.userid}<img style="width: 30px; height: 30px;" src="/assets/img/crown.png"></p>
                <p>${item.groupjoindate}</p>
            </div>
        </div>`

            document.getElementById('memberList').innerHTML += $memberList

        } else {

            const $memberList =
                `<div class="members" style="margin: 10px; border: 1px solid black;">
              <div class="img">
                 <img class="profile" style="width: 100px; height: 100px;" src="/uploads/${item.userprofile}"
                 onclick="userprofile('${item.userprofile}','${item.userid}')">
             </div>
            <div class="info">
                <p style="font-weight: bold">${item.userid}</p>
                <p>${item.groupjoindate}</p>
            </div>
        </div>`

            document.getElementById('memberList').innerHTML += $memberList
        }

    })
}

const makeAdMemberList = function (list) {
    document.getElementById('memberList').innerHTML = ''
    list.forEach(item => {
        const $memberList =
            `<div class="members" style="margin: 10px; border: 1px solid black;">
              <div class="img">
                 <img style="width: 100px; height: 100px;" src="/uploads/${item.userprofile}"
                 onclick="aduserprofile('${item.userprofile}','${item.userid}')">
             </div>
            <div class="info">
                <p style="font-weight: bold">${item.userid}</p>
                <p>${item.adanswer}</p>
            </div>
        </div>`

            document.getElementById('memberList').innerHTML += $memberList

    })
}

function adtof(userid,tof) {
    const xhr = new XMLHttpRequest()
    xhr.open('GET','/group/admissiontof/'+groupidx+'/'+userid+'/'+tof)
    xhr.send()
    xhr.onload = function () {
        if (xhr.status === 200 || xhr.status === 201) {
            const list = JSON.parse(xhr.response)
            makeAdMemberList(list)
            document.getElementById('profileimg').innerHTML=''
            document.getElementById('userinfo').innerHTML = ''
        } else {
            console.error('오류1', xhr.status)
            console.error('오류2', xhr.response)
        }
    }

}

groupmembers()


