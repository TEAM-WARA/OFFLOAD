const { kakao } = window;

var mapDiv = document.querySelector('#storeMap'), // 지도를 표시할 div 
    mapOption = { 
        center: new kakao.maps.LatLng(35.856047838165004, 128.49278206824263), // 지도의 중심좌표 (35.8678658, 128.5967954)
        level: 3 // 지도의 확대 레벨
    };

var map = new kakao.maps.Map(mapDiv, mapOption);  // 지도를 생성합니다

mapDiv.style.width = '100%';   //MapSize 조절
mapDiv.style.height = '500px';
// mapDiv.style.
// mapDiv.style.

map.relayout();