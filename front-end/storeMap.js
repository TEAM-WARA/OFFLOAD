const { kakao } = window;

var mapDiv = document.querySelector('#storeMap'), // 지도를 표시할 div 
    mapOption = { 
        center: new kakao.maps.LatLng(35.856047838165004, 128.49278206824263), // 지도의 중심좌표 (35.8678658, 128.5967954)
        level: 3 // 지도의 확대 레벨
    };

var map = new kakao.maps.Map(mapDiv, mapOption);  // 지도를 생성합니다


var geocoder = new kakao.maps.services.Geocoder(); // 주소-좌표 변환 객체를 생성합니다

mapDiv.style.width = '100%';   //MapSize 조절
mapDiv.style.height = '500px';

map.relayout();




  document.addEventListener("DOMContentLoaded", function() {
    // 가게 주소 필드 엘리먼트 가져오기
    const storeAddressField = document.getElementById('storeAddress');

    // 가게 주소 필드에서 포커스 아웃(떠남) 이벤트를 모니터링
    storeAddressField.addEventListener('blur', function() {
        // 가게 주소 필드의 값을 가져옵니다.
        const storeAddress = storeAddressField.value;

        // 주소로 좌표를 검색합니다
    geocoder.addressSearch(storeAddress, function(result, status) {

    // 정상적으로 검색이 완료됐으면 
     if (status === kakao.maps.services.Status.OK) {

        var coords = new kakao.maps.LatLng(result[0].y, result[0].x);

        // 결과값으로 받은 위치를 마커로 표시합니다
        var marker = new kakao.maps.Marker({
            map: map,
            position: coords
        });

        map.panTo(coords);
    }
})
    });
});




document.getElementById('imageUpload').addEventListener('change', function(e) {
    const file = e.target.files[0];
    if (file) {
      const reader = new FileReader();
      reader.onload = function(e) {
        document.getElementById('imagePreview').src = e.target.result;
        document.getElementById('imagePreview').style.display = 'block';
      };
      reader.readAsDataURL(file);
    }
  });

  document.getElementById('uploadButton').addEventListener('click', function() {
    document.getElementById('imageUpload').click();
  });



document.getElementById("createStoreButton").addEventListener("click",
    function () {
        var data = {
            "name": document.getElementById("name").value,
            "email": document.getElementById("email").value,
            "address": document.getElementById("storeAddress").value
        };
        var formData = new FormData();
        formData.append('images', document.getElementById("imageUpload").files[0]); // 이미지
        formData.append('data', new Blob([JSON.stringify(data)], { type: "application/json" }));
        console.log(formData);
        // formData에 이미지와 json을 합친
        for (let value of formData.values()) {
            if (value instanceof Blob) {
                var reader = new FileReader();
                reader.onload = function () {
                    console.log(reader.result); // Blob 내부 데이터를 콘솔에 출력
                };
                reader.readAsText(value);
            } else {
                console.log(value);
            }
        }
        fetch('https://port-0-creativefusion-jvpb2aln5qmjmz.sel5.cloudtype.app/store', {
            method: 'POST',
            headers: {
                // "Content-Type" : "multipart/form-data",
                // "Authorization" : document.getElementById("message").value
            },
            body: formData
        })
            .then(response => {
                if (response.ok) {
                    return response.json(); // JSON 형식의 응답을 파싱
                }
                throw new Error('네트워크 응답이 실패했습니다.');
            })
            .then(data => {
                alert('성공!');
                console.log(data);
                window.location.href = 'properties.html';
            })
            .catch(error => {
                console.error(error);
            });

    }

)
