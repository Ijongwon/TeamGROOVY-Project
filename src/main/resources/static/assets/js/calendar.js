
const calbtn = function () {
    console.log(typeof (schDateInput.value))

    const xhr = new XMLHttpRequest()
    xhr.open('GET', `/user/sch/` + schDateInput.value +"/"+ idx)
    xhr.send()
    xhr.onload = function () {
        if (xhr.status === 200 || xhr.status === 201) {
            const list = JSON.parse(xhr.response)
            const scElement = document.getElementById('sc');

            scElement.innerHTML = '';

            if (list.length === 0) {
                scElement.innerHTML = '<li><p style="padding: 98px;">당신의 일정을 추가해주세요.</p></li>';
            } else {
                list.forEach(item => {
                    const $context =
                        `
                        <li class="my-2 ps-3 d-flex justify-content-between border-start border-4 border-primary"> 
                            <span>
                                <a href="" class="selectOne link-dark text-decoration-none" data-bs-target="#exampleModal2" 
                                    data-bs-toggle="modal" data-hidden-value="${item.schidx}">
                                    <span class="fw-bold">${item.schtitle}</span>
                                    <br>
                                    <span style="white-space: pre-line;">${item.schcontents}</span> 
                                </a>
                            </span>
                            <button class="delete btn btn-sm border-0 rounded-pill" data-id="${item.schidx}">
                                <i class="bi bi-x fs-5 link-primary"></i>
                            </button>
                        </li>
                        `
                    scElement.innerHTML += $context;
                });

                // 모든 삭제 버튼에 이벤트 리스너 추가
                document.querySelectorAll('.delete').forEach(deleteButton => {
                    deleteButton.addEventListener('click', deleted);
                });

                document.querySelectorAll('.selectOne').forEach(selectButton => {
                    selectButton.addEventListener('click', selectOne);
                });

                // document.querySelector('#selectOne').addEventListener('click',selectOne)
            }
        } else {
            console.error('오류1', xhr.status);
            console.error('오류2', xhr.response);
        }
    }
}


const deleted = function () {

    // 현재 클릭한 버튼의 data-id 속성 가져오기
    const schidxdelete = this.getAttribute('data-id');

    // 사용자에게 확인 메시지 표시
    const isConfirmed = confirm('삭제하시겠습니까?');

    // 사용자가 "확인"을 클릭한 경우에만 삭제 수행
    if (isConfirmed) {
        const xhr = new XMLHttpRequest();

        xhr.open('DELETE', '/user/Schedule/' + schidxdelete);
        xhr.send();

        xhr.onload = function () {
            if (xhr.status === 200 || xhr.status === 201) {
                const result = JSON.parse(xhr.response);

                alert('삭제되었습니다.');
                calbtn();
            } else {
                console.error('오류1', xhr.status, xhr.response);
                console.error('오류2', xhr.response);
            }
        }
    }
}

const selectOne =function(){

    const selectOne = this.getAttribute('data-hidden-value');
    const xhr = new XMLHttpRequest()

    xhr.open('GET','/user/Schedule/' + selectOne)
    xhr.send()

    xhr.onload=function(){
        console.log('*** xhr response:', xhr.response)
        if(xhr.status === 200 || xhr.status ===201){
            const schedule = JSON.parse(xhr.response)   // xhr.response 는 json 문자열

            document.getElementById('title').value = schedule.schtitle;
            document.getElementById('contents').value = schedule.schcontents;
            document.getElementById('idx').value = schedule.schidx;

            console.log("get /user/Schedule/", schedule)

        }else {
            console.error('오류1',xhr.status, xhr.response)
            console.error('오류2',xhr.response)
        }
    }
}

const changeMany = function (){
    const idx =document.querySelector('#idx').value

    const xhr = new XMLHttpRequest()
    xhr.open('PATCH','/user/Schedule/')
    xhr.setRequestHeader("Content-Type","application/json")

    const title = document.querySelector('#title').value
    const contents = document.querySelector('#contents').value
    const userid = document.querySelector('#userid').value

    var msg = ""

    const schedule = {schtitle:title, schcontents:contents, schidx:idx, userid:userid}

    if(!title){
        alert('제목 입력은 필수입니다.')
        return
    }

    const json = JSON.stringify(schedule)
    console.log(schedule)
    console.log(json)
    xhr.send(json)

    xhr.onload=function (){
        const resultObj = JSON.parse(xhr.response)
        if(xhr.status === 200 || xhr.status ===201){

            alert('수정 완료')
            calbtn()
        } else {
            console.error('오류1',xhr.status)
            console.error('오류2',xhr.response)

            const values = Object.values(resultObj)
            console.log(values)
        }

    }
}
document.querySelector('#update').addEventListener('click', changeMany)

!function () {


    // Moment.js를 사용하여 현재 날짜와 시간을 처리하기 위한 변수를 생성합니다.
    var today = moment();

    // Calendar 객체 생성자 함수를 정의합니다.
    function Calendar(selector, events) {
        // HTML 문서에서 선택한 요소를 가져와서 Calendar 객체의 el 속성으로 설정합니다.
        this.el = document.querySelector(selector);

        // 이벤트 데이터를 Calendar 객체의 events 속성으로 설정합니다.
        this.events = events;

        // Calendar의 현재 날짜를 1일로 설정합니다.
        this.current = moment().date(1);

        // Calendar을 그립니다.
        this.draw();

        // 'today' 클래스를 가진 요소를 찾습니다.
        // 해당 요소가 있으면, 0.5초 후 해당 날짜를 열도록 설정합니다.
        var current = document.querySelector('.today');
        if (current) {
            var self = this;
            window.setTimeout(function () {
                // self.openDay(current);
            }, 500);
        }
    }

    // Calendar 객체의 메서드를 정의합니다.
    Calendar.prototype.draw = function () {
        // 헤더를 생성하는 함수를 호출합니다.
        this.drawHeader();

        // 월을 그리는 함수를 호출합니다.
        this.drawMonth();

        // 레전드(범례)를 생성하는 함수를 호출합니다.
        this.drawLegend();
    }

    Calendar.prototype.drawHeader = function () {
        var self = this;
        if (!this.header) {
            // 헤더 요소를 생성합니다.
            this.header = createElement('div', 'header');
            this.header.className = 'header';

            // 월과 연도를 표시할 요소를 생성합니다.
            this.title = createElement('h1');

            // 다음 달로 이동하는 버튼 요소를 생성합니다.
            var right = createElement('div', 'right');
            right.addEventListener('click', function () {
                self.nextMonth();
                console.log('right');
            });

            // 이전 달로 이동하는 버튼 요소를 생성합니다.
            var left = createElement('div', 'left');
            left.addEventListener('click', function () {
                self.prevMonth();
                console.log('left');
            });

            // 생성한 요소들을 헤더에 추가합니다.
            this.header.appendChild(this.title);
            this.header.appendChild(right);
            this.header.appendChild(left);
            this.el.appendChild(this.header);
        }

        // 헤더의 제목에 현재 날짜를 표시합니다.
        this.title.innerHTML = this.current.format('MMMM YYYY');
    }

    // Calendar 객체의 drawMonth 메서드 정의
    Calendar.prototype.drawMonth = function () {
        var self = this;

        // 이벤트 데이터를 반복 처리하면서 각 이벤트의 날짜를 현재 월에 랜덤으로 할당합니다.
        this.events.forEach(function (ev) {
            ev.date = self.current.clone().date(Math.random() * (29 - 1) + 1);
        });

        if (this.month) {
            this.oldMonth = this.month;

            // 이전 달의 애니메이션 클래스를 설정하고 애니메이션이 끝나면 이전 달을 삭제하고 새로운 달을 생성합니다.
            this.oldMonth.className = 'month out ' + (self.next ? 'next' : 'prev');
            this.oldMonth.addEventListener('webkitAnimationEnd', function () {
                self.oldMonth.parentNode.removeChild(self.oldMonth);
                self.month = createElement('div', 'month');
                self.backFill();
                self.currentMonth();
                self.fowardFill();
                self.el.appendChild(self.month);

                // 16밀리초 후에 새로운 달에 애니메이션 클래스를 설정합니다.
                window.setTimeout(function () {
                    self.month.className = 'month in ' + (self.next ? 'next' : 'prev');
                }, 16);
            });
        } else {
            this.month = createElement('div', 'month');
            this.el.appendChild(this.month);
            this.backFill();
            this.currentMonth();
            this.fowardFill();
            this.month.className = 'month new';
        }
    }

    // Calendar 객체의 backFill 메서드 정의
    Calendar.prototype.backFill = function () {
        var clone = this.current.clone();
        var dayOfWeek = clone.day();

        if (!dayOfWeek) {
            return;
        }

        clone.subtract('days', dayOfWeek + 1);

        for (var i = dayOfWeek; i > 0; i--) {
            this.drawDay(clone.add('days', 1));
        }
    }

    // Calendar 객체의 fowardFill 메서드 정의
    Calendar.prototype.fowardFill = function () {
        var clone = this.current.clone().add('months', 1).subtract('days', 1);
        var dayOfWeek = clone.day();

        if (dayOfWeek === 6) {
            return;
        }

        for (var i = dayOfWeek; i < 6; i++) {
            this.drawDay(clone.add('days', 1));
        }
    }

    // Calendar 객체의 currentMonth 메서드 정의
    Calendar.prototype.currentMonth = function () {
        var clone = this.current.clone();

        while (clone.month() === this.current.month()) {
            this.drawDay(clone);
            clone.add('days', 1);
        }
    }

    // Calendar 객체의 getWeek 메서드 정의
    Calendar.prototype.getWeek = function (day) {
        if (!this.week || day.day() === 0) {
            this.week = createElement('div', 'week');
            this.month.appendChild(this.week);
        }
    }


    // Calendar 객체의 drawDay 메서드 정의
    Calendar.prototype.drawDay = function (day) {
        var self = this;
        this.getWeek(day);

        // Outer Day
        var outer = createElement('div', this.getDayClass(day));

        // Day Name
        var name = createElement('div', 'day-name', day.format('ddd'));

        // Day Number
        var number = createElement('div', 'day-number', day.format('DD'));

        // Add your year, month, and day to the day element 변경점
        var year = createElement('div', 'day-year', day.format('YYYY'));
        var month = createElement('div', 'day-month', day.format('MM'));
        var dayElement = createElement('div', 'day-day', day.format('DD'));

        var dateValue = year.innerText + '-' + month.innerText + '-' + dayElement.innerText;

        outer.setAttribute('data-bs-toggle', 'modal');
        outer.setAttribute('data-bs-target', '#exampleModal');

        // 클릭 이벤트 처리
        outer.addEventListener('click', function () {
            // Calendar 객체에서 클릭한 날짜에 대한 세부 정보를 표시하고,
            // 해당 날짜에 대한 이벤트 정보를 가져와 렌더링합니다.
            // self.openDay(this);

            // schDateInput를 가져오기 위한 참조
            var schDateInput = document.querySelector('#schDateInput');

            // 클릭한 일의 날짜를 가져와서 schDateInput.value에 할당
            schDateInput.value = dateValue;

            // 달력 내용 표시
            calbtn()

            console.log("click = " + schDateInput.value);
            console.log(schDateInput.value);

            // 모달 제목 엘리먼트를 가져오고
            const modalTitle = document.getElementById('exampleModalLabel');

            // 모달 제목 엘리먼트에 일을 동적으로 설정
            modalTitle.textContent = dayElement.textContent + " 일";

        });

        // Events
        var events = createElement('div', 'day-events');

        // count(*)
        const xhr = new XMLHttpRequest();
        xhr.open('GET', `/user/count/` + dateValue +"/"+ idx);
        xhr.send();

        xhr.onload = function () {
            if (xhr.status === 200 || xhr.status === 201) {
                const count = JSON.parse(xhr.response);

                const rainbowColors = ['#e36363', 'orange', 'yellow', 'green', 'blue', 'purple'];

                // 기존 내용을 비우기 위해 빈 문자열을 할당합니다.
                events.innerHTML = '';

                const content = document.createElement('span');
                content.classList.add('box');

                for (let j = 0; j < count; j++) {
                    const box = document.createElement('span');
                    const colorIndex = j % rainbowColors.length;
                    box.style.backgroundColor = rainbowColors[colorIndex];

                    content.appendChild(box);
                }

                events.appendChild(content);
            } else {
                console.error('오류1', xhr.status, xhr.response);
                console.error('오류2', xhr.response);
            }
        };

        this.drawEvents(day, events);

        outer.appendChild(name);
        outer.appendChild(number);
        outer.appendChild(events);
        this.week.appendChild(outer);

    }

    // Calendar 객체의 drawEvents 메서드 정의
    Calendar.prototype.drawEvents = function (day, element) {
        if (day.month() === this.current.month()) {
            var todaysEvents = this.events.reduce(function (memo, ev) {
                if (ev.date.isSame(day, 'day')) {
                    memo.push(ev);
                }
                return memo;
            }, []);

            todaysEvents.forEach(function (ev) {
                var evSpan = createElement('span', ev.color);
                element.appendChild(evSpan);
            });
        }
    }

    // Calendar 객체의 getDayClass 메서드 정의
    Calendar.prototype.getDayClass = function (day) {
        classes = ['day'];
        if (day.month() !== this.current.month()) {
            classes.push('other');
        } else if (today.isSame(day, 'day')) {
            classes.push('today');
        }
        return classes.join(' ');
    }

    // Calendar 객체의 openDay 메서드 정의 (변경)
    // Calendar.prototype.openDay = function (el) {
    //     var arrow;
    //     var details
    //
    //     // 클릭한 날짜 요소에서 해당 날짜를 가져오는 부분입니다. 클릭한 요소의 자식 요소에서 날짜 정보를 추출합니다.
    //     var dayNumber = +el.querySelectorAll('.day-number')[0].innerText || +el.querySelectorAll('.day-number')[0].textContent;
    //     // 클릭한 날짜를 moment.js 라이브러리를 사용하여 현재 날짜와 연동하여 생성합니다.
    //     var day = this.current.clone().date(dayNumber);
    //
    //     // 현재 열려 있는 세부 정보 상자를 찾습니다. 이 세부 정보 상자는 이미 열려 있는 경우 다른 날짜를 클릭할 때 닫아야 합니다.
    //     var currentOpened = document.querySelector('.details');
    //
    //     // 현재 행에 열려 있는 세부 정보 상자가 있는지 확인
    //     if (currentOpened && currentOpened.parentNode === el.parentNode) {
    //         // 세부 정보를 담는 컨테이너
    //         details = currentOpened;
    //
    //         // 상단에 표시되는 화살표
    //         arrow = document.querySelector('.arrow');
    //
    //     } else {
    //         // 애니메이션을 실행하고 화면에서 삭제합니다. 그 후, 새로운 세부 정보 상자를 생성하게 됩니다.
    //         if (currentOpened) {
    //
    //             currentOpened.addEventListener('webkitAnimationEnd', function () {
    //                 currentOpened.parentNode.removeChild(currentOpened);
    //             });
    //             currentOpened.addEventListener('oanimationend', function () {
    //                 currentOpened.parentNode.removeChild(currentOpened);
    //             });
    //             currentOpened.addEventListener('msAnimationEnd', function () {
    //                 currentOpened.parentNode.removeChild(currentOpened);
    //             });
    //             currentOpened.addEventListener('animationend', function () {
    //                 currentOpened.parentNode.removeChild(currentOpened);
    //             });
    //             currentOpened.className = 'details out';
    //         }
    //
    //         // 세부 정보 컨테이너 생성
    //         details = createElement('div', 'details in');
    //
    //         // 화살표 생성
    //         var arrow = createElement('div', 'arrow');
    //
    //         // 이벤트 래퍼 생성
    //         details.appendChild(arrow);
    //         el.parentNode.appendChild(details);
    //     }
    //
    //     // 선택한 날짜에 해당하는 이벤트 가져오기
    //     var todaysEvents = this.events.reduce(function (memo, ev) {
    //         if (ev.date.isSame(day, 'day')) {
    //             // 이벤트가 날짜와 일치하면 memo 배열에 추가
    //             memo.push(ev);
    //         }
    //         return memo;
    //     }, []);
    //
    //     // 세부 정보 컨테이너에 이벤트 렌더링
    //     this.renderEvents(todaysEvents, details);
    //
    //     // 화살표 위치 설정
    //     arrow.style.left = el.offsetLeft - el.parentNode.offsetLeft + 27 + 'px';
    // }


    Calendar.prototype.renderEvents = function (events, ele) {
        //Remove any events in the current details element
        var currentWrapper = ele.querySelector('.events');
        var wrapper = createElement('div', 'events in' + (currentWrapper ? ' new' : ''));

        events.forEach(function (ev) {
            var div = createElement('div', 'event');

            var square = createElement('div', 'event-category ' + ev.color);
            var span = createElement('span', '', ev.eventName);

            div.appendChild(square);
            div.appendChild(span);
            wrapper.appendChild(div);
        });

        if (!events.length) {
            var div = createElement('div', 'event empty');

            wrapper.appendChild(div);
        }


        if (currentWrapper) {
            currentWrapper.className = 'events out';

            currentWrapper.addEventListener('webkitAnimationEnd', function () {
                currentWrapper.parentNode.removeChild(currentWrapper);
                ele.appendChild(wrapper);
            });
            currentWrapper.addEventListener('oanimationend', function () {
                currentWrapper.parentNode.removeChild(currentWrapper);
                ele.appendChild(wrapper);
            });
            currentWrapper.addEventListener('msAnimationEnd', function () {
                currentWrapper.parentNode.removeChild(currentWrapper);
                ele.appendChild(wrapper);
            });
            currentWrapper.addEventListener('animationend', function () {
                currentWrapper.parentNode.removeChild(currentWrapper);
                ele.appendChild(wrapper);
            });

        } else {
            ele.appendChild(wrapper);
        }
    }


    Calendar.prototype.drawLegend = function () {
        var legend = createElement('div', 'legend');
        var calendars = this.events.map(function (e) {
            return e.calendar + '|' + e.color;
        }).reduce(function (memo, e) {
            if (memo.indexOf(e) === -1) {
                memo.push(e);
            }
            return memo;
        }, []).forEach(function (e) {
            var parts = e.split('|');
            var entry = createElement('span', 'entry ' + parts[1], parts[0]);
            legend.appendChild(entry);
        });
        this.el.appendChild(legend);
    }

    Calendar.prototype.nextMonth = function () {
        this.current.add('months', 1);
        this.next = true;
        this.draw();
    }

    Calendar.prototype.prevMonth = function () {
        this.current.subtract('months', 1);
        this.next = false;
        this.draw();
    }

    window.Calendar = Calendar;

    function createElement(tagName, className, innerText) {
        var ele = document.createElement(tagName);
        if (className) {
            ele.className = className;
        }
        if (innerText) {
            ele.innerText = ele.textContent = innerText;
        }
        return ele;
    }
}();

!function () {
    var data = [
        // {eventName: 'Lunch Meeting w/ Mark', calendar: 'Work', color: 'orange'},
        // {eventName: 'Interview - Jr. Web Developer', calendar: 'Work', color: 'orange'},
        // {eventName: 'Demo New App to the Board', calendar: 'Work', color: 'orange'},
        // {eventName: 'Dinner w/ Marketing', calendar: 'Work', color: 'orange'},
        //
        // {eventName: 'Game vs Portalnd', calendar: 'Sports', color: 'blue'},
        // {eventName: 'Game vs Houston', calendar: 'Sports', color: 'blue'},
        // {eventName: 'Game vs Denver', calendar: 'Sports', color: 'blue'},
        // {eventName: 'Game vs San Degio', calendar: 'Sports', color: 'blue'},
        //
        // {eventName: 'School Play', calendar: 'Kids', color: 'yellow'},
        // {eventName: 'Parent/Teacher Conference', calendar: 'Kids', color: 'yellow'},
        // {eventName: 'Pick up from Soccer Practice', calendar: 'Kids', color: 'yellow'},
        // {eventName: 'Ice Cream Night', calendar: 'Kids', color: 'yellow'},
        //
        // {eventName: 'Free Tamale Night', calendar: 'Other', color: 'green'},
        // {eventName: 'Bowling Team', calendar: 'Other', color: 'green'},
        // {eventName: 'Teach Kids to Code', calendar: 'Other', color: 'green'},
        // {eventName: 'Startup Weekend', calendar: 'Other', color: 'green'}
    ];


    function addDate(ev) {

    }

    // Calendar 객체를 생성하고 데이터 추가
    var calendar = new Calendar('#calendar', data);

}();