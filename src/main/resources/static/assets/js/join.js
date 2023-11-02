//clear 버튼 동작입니다. -> input 요소 초기화
let regEmail = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/;
let regPassword =/^[a-zA-Z\\d`~!@#$%^&*()-_=+]{8,24}$/
let regId = /^[a-z0-9]{4,20}$/;
const clear = function(){
    document.querySelector('#userid').value=''
    document.querySelector('#username').value=''
    document.querySelector('#userpassword').value=''
    document.querySelector('#useremail').value=''
    document.querySelector('#default').innerHTML =
        `<div class="card-header" id="messageBot">나는 메세지 봇입니다.</div>`
    document.querySelector('#idMessage').innerHTML = `<span class="text-sm"></span>`
}

let isValid = false
let isemailValid = false

async function requestEmail(useremail){
    const result = await axios.get(`/api/user/email/${useremail}`)
    return result.data
}
const emailcheck = function () {
    console.log('이메일검사')
    let isValidemail = false
    const emails = document.querySelector('#useremail').value
    if(!emails){
        return
    }
    requestEmail(emails).then(data =>{
        isemailValid = !data.exist
        if (isemailValid){
            document.querySelector('#emailMessage > span').innerHTML
                    ='사용 가능한 이메일 입니다.'
            document.querySelector('#emailMessage > span').style.color = 'green'
        }else{
            document.querySelector('#emailMessage > span ').innerHTML
                ='사용 불가능한 이메일입니다 다른 이메일을 사용해주세요.'
            document.querySelector('#emailMessage > span ').style.color = 'red'
        }
    })
        .catch(e => console.error("teamproject - ",e))
}
document.querySelector('#useremail').addEventListener('keyup',emailcheck)

async function requestIdCheck(userid){
    const result = await axios.get(`/api/user/check/${userid}`)
    return result.data
}

const  idcheck = function (){
    console.log("아아아")
    let isValidId = false
    const id = document.querySelector('#userid').value
    if(!id){
        //alert('아이디를 입력하세요')
        return
    }
    requestIdCheck(id).then(data =>{
        isValid = !data.exist   //result.exist 는 true 또는 false를 리턴
        if (isValid){
            document.querySelector('#idMessage > span').innerHTML
                = '사용 가능한 아이디 입니다.'
            document.querySelector('#idMessage > span').style.color = 'green'
        }else {
            document.querySelector('#idMessage > span').innerHTML
                = '사용 불가능한 아이디 입니다 다른 아이디를 사용해주세요.'
            document.querySelector('#idMessage > span').style.color = 'red'
        }
    })
        .catch(e => console.error("teamproject - ",e))
}
document.querySelector('#userid').addEventListener('keyup',idcheck)


function validateEmail() {
    const email = document.getElementById('useremail').value;
    const emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;

    if (!emailPattern.test(email)) {
       // alert('올바른 이메일 주소 형식이 아닙니다. 예: user@example.com');
        return false;
    }

    return true;
}

function validatePassword() {
    const password = document.getElementById('userpassword').value;
    const regPassword = /^[a-zA-Z\d`~!@#$%^&*()-_=+]{8,24}$/;

    if (!regPassword.test(password)) {
       // alert('올바른 비밀번호 유형이 아닙니다.');
        return false;
    }

    return true;
}

function validateId() {
    const id = document.getElementById('userid').value;
    const regId = /^[a-z0-9]{4,20}$/;

    if (!regId.test(id)) {
      //  alert('올바른 아이디 유형이 아닙니다.');
        return false;
    }

    return true;
}

function validateForm() {
    const isEmailValid = validateEmail();
    const isPasswordValid = validatePassword();
    const isIdValid = validateId();

    if (isEmailValid && isPasswordValid && isIdValid) {
        // 유효성 검사 통과, 폼 제출 가능
        alert("회원가입 성공");
        return true;

    } else {
        if (isEmailValid ==false){
            alert('올바른 이메일 유형이 아닙니다.')
        }
        if(isPasswordValid==false){
            alert('올바른 패스워드 유형이 아닙니다')
        }
        if(isIdValid==false){
            alert('올바른 아이디 유형이 아닙니다')
        }
        // 유효성 검사 실패, 폼 제출 막기
        return  false;
    }
}

function userupdateForm() {
    const isEmailValid = validateEmail();
    const isPasswordValid = validatePassword();
    const isIdValid = validateId();

    if (isEmailValid && isPasswordValid && isIdValid) {
        // 유효성 검사 통과, 폼 제출 가능
        //alert("회원가입 성공");
        return true;

    } else {
        if (isEmailValid ==false){
            alert('올바른 이메일 유형이 아닙니다.')
        }
        if(isPasswordValid==false){
            alert('올바른 패스워드 유형이 아닙니다')
        }
        if(isIdValid==false){
            alert('올바른 아이디 유형이 아닙니다')
        }
        // 유효성 검사 실패, 폼 제출 막기
        return  false;
    }
}



// HTML 회원가입 버튼과 연결
//document.querySelector('.btn-primary').addEventListener('click', validateForm);












