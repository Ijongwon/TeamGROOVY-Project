//사진 넘기기
document.querySelectorAll('.imageList').forEach((imageList, index) => {
    let currentIndex = 0;

    // 이전 버튼과 다음 버튼 가져오기
    const prevBtn = document.querySelectorAll('.prevBtn')[index];
    const nextBtn = document.querySelectorAll('.nextBtn')[index];

    // 이미지가 한 장인 경우에만 화살표 버튼 숨기기
    if (imageList.children.length <= 1) {
        prevBtn.style.display = 'none';
        nextBtn.style.display = 'none';
    } else {
        // 이전 버튼 클릭 시 이벤트 핸들러
        prevBtn.addEventListener('click', function () {
            currentIndex = (currentIndex - 1 + imageList.children.length) % imageList.children.length;
            updateSlider(imageList, currentIndex);
        });

        // 다음 버튼 클릭 시 이벤트 핸들러
        nextBtn.addEventListener('click', function () {
            currentIndex = (currentIndex + 1) % imageList.children.length;
            updateSlider(imageList, currentIndex);
        });
    }

    // 초기화: 처음에는 각 작성자가 올린 이미지 중 첫 번째 이미지만 보이도록 설정
    updateSlider(imageList, currentIndex);

    // 슬라이드쇼 업데이트 함수
    function updateSlider(list, nowindex) {
        // 모든 이미지 숨기기
        Array.from(list.children).forEach(image => {
            image.style.display = 'none';
        });

        // 현재 인덱스의 이미지만 표시
        list.children[nowindex].style.display = 'block';


    }
});