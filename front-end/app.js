const { kakao } = window;

var mapDiv = document.querySelector('#map');
var mapOption = { 
    center: new kakao.maps.LatLng(35.8678658, 128.5967954),
    level: 3
};

var map = new kakao.maps.Map(mapDiv, mapOption);

mapDiv.style.width = '100%';  // 지도의 너비를 100%로 설정
mapDiv.style.height = '100vh';  // 지도의 높이를 뷰포트의 100%로 설정

map.relayout();  // 지도 다시 그리기

let currentPopup = null; // 현재 열려있는 팝업 정보를 저장하는 변수, boolean


var imageSize = new kakao.maps.Size(26, 38); // 마커의 크기 기존 33, 36
var choiceImageSize = new kakao.maps.Size(28, 40); // 선택한 마커의 크기 기존 38, 40

var imageSrc = "/iconImage/iconRed.png", // 이미지의 경로
    imageSrc2 = "/iconImage/iconBlue.png";


// MakrerImage 객체를 생성하여 반환하는 함수
function createMarkerImage(markerScr, markerSize) {
    var markerImage = new kakao.maps.MarkerImage(markerScr, markerSize);
    return markerImage;
}


 // 기본 마커이미지, 오버 마커이미지, 클릭 마커이미지 생성
 var clickImage = createMarkerImage(imageSrc, choiceImageSize),
    normalImage = createMarkerImage(imageSrc2, imageSize);


// 마커 정보를 담은 배열
var markerList = [
    { "lat" : 35.8678658, "lon": 128.5967954, "Shopping": true,
    "place": "빨간바지", // 상점위치 string
    "address" : "대구 중구 동성로2길 48", // 주소 string
    "storeItem" : "장바구니 물품 존재 - 아우터 외 2개" // 물건 string
  },
    { "lat": 35.8709820, "lon": 128.5926346, "Shopping": true,
    "place": "한우장설렁탕", // 상점위치 string
    "address" : "대구 중구 국채보상로 567", // 주소 string
    "storeItem" : "장바구니 물품 존재 - 설렁탕" // 물건 string
   },
    { "lat": 35.8704173, "lon": 128.5953787, "Shopping": false,
    "place": "CGV 대구한일", // 상점위치 string
    "address" : "대구광역시 중구 동성로 39", // 주소 string
    "storeItem" : "장바구니 물품 없음" // 물건 string
  }
  ],
  selectedMarker = null; // 클릭한 마커를 담을 변수

// 지도에 마커를 표시하는 함수입니다
function displayMarker(place) {
    
// 마커를 생성하고 지도에 표시합니다
    var marker = new kakao.maps.Marker({
        map: map,
        position: new kakao.maps.LatLng(place.y, place.x) 
    });

}

function initKakaoMap() {

// InfoWindow 객체들의 참조를 저장하는 배열
var infowindowList = [];

markerList.forEach(function(markerInfo) {
    // 마커를 생성합니다
    
    var marker = new kakao.maps.Marker({
        position: new kakao.maps.LatLng(markerInfo.lat, markerInfo.lon),
        map: map,
        image: normalImage, // 마커 이미지
    });
        marker.normalImage = normalImage;
        marker.clickImage = clickImage;

    // Popup창 정보, 디자인
    var popupContent =`
    <div class='content'>
      <div class='img-box'>
        <a href='javascript:void(0)' onclick='onClose()' class='btn-close'></a>
      </div>
      <div class='info-box'>
        <p style='margin-bottom: 7px; overflow: hidden;'>
          <span class='tit'>${markerInfo.place}</span>
          <a href='/' target='_blank' class='link' style='color: #3D6DCC; font-size: 13px; float: right;'>가게이동</a>
        </p>
        <p><span class='new-addr'>${markerInfo.address}</span></p>
        <p><span class='old-addr'>${markerInfo.storeItem}</span></p>
      </div>
    </div>`;  

    // 인포윈도우를 생성하고 지도에 표시합니다
    var infoWindow = new kakao.maps.InfoWindow({    
        content : popupContent,
        removable : true
    });

    infowindowList.push(infoWindow);


//Popup창 켜고 끄는 method
function showPopup(info, popupContent) {
    var popup = document.createElement('div');
    popup.style.position = 'fixed';
    popup.style.top = '78%';
    popup.style.left = '50%';
    popup.style.transform = 'translate(-50%, -50%)';
    popup.style.zIndex = 10;
  
// 새로운 팝업이 같은 마커의 팝업이라면 닫고 함수 종료
if(currentPopup === info){
    removePopup();
    currentPopup = null;
    return;
}
    popup.innerHTML=popupContent;
    popup.className="popup";

    // 기존 팝업 삭제 후 새로운 팝업 추가
    removePopup();
    mapDiv.appendChild(popup);

// 현재 열린 팝업 정보 업데이트 
    currentPopup = info;
}

function removePopup(){
    if(mapDiv.lastChild.className == "popup"){
        mapDiv.removeChild(mapDiv.lastChild);
    }
 }

    // 마커에 click 이벤트를 등록합니다
    kakao.maps.event.addListener(marker, 'click', function() {
        // 클릭된 마커가 없거나, click 마커가 클릭된 마커가 아니면
        // 마커의 이미지를 클릭 이미지로 변경합니다

        if (!selectedMarker || selectedMarker !== marker) {

            // 클릭된 마커 객체가 null이 아니면
            // 클릭된 마커의 이미지를 기본 이미지로 변경하고
            !!selectedMarker && selectedMarker.setImage(selectedMarker.normalImage);

            // 현재 클릭된 마커의 이미지는 클릭 이미지로 변경합니다
            marker.setImage(marker.clickImage);
            // 클릭된 마커를 현재 클릭된 마커 객체로 설정합니다
            selectedMarker = marker;

        }else if(selectedMarker === marker){
            selectedMarker.setImage(selectedMarker.normalImage);
            selectedMarker = null;
        }

        showPopup(markerInfo, popupContent);

        // 지도 중심을 클릭된 마커 위치로 이동
        map.panTo(new kakao.maps.LatLng(markerInfo.lat, markerInfo.lon));
    });


        
});






}

window.onload = function() {
    initKakaoMap();
  };



