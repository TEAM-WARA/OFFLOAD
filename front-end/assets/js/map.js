const { kakao } = window;
const token = sessionStorage.getItem('token');
var mapDiv = document.querySelector('#map'), // 지도를 표시할 div 
    mapOption = { 
        center: new kakao.maps.LatLng(35.856047838165004, 128.49278206824263), // 지도의 중심좌표 (35.8678658, 128.5967954)
        level: 3 // 지도의 확대 레벨
    };

var map = new kakao.maps.Map(mapDiv, mapOption);  // 지도를 생성합니다
const ps = new kakao.maps.services.Places(); // 장소 검색 객체 생성

var markers = [];


mapDiv.style.width = '100%';   //MapSize 조절
mapDiv.style.height = '85vh';

map.relayout();


// 마커 정보를 담은 배열
var markerList = []

var circle = new kakao.maps.Circle({
    center : new kakao.maps.LatLng(35.856047838165004, 128.49278206824263),
    radius: 100,
    strokeWeight: 5, // 선의 두께입니다 
    strokeColor: '#75B8FA', // 선의 색깔입니다
    strokeOpacity: 0, // 선의 불투명도 입니다 1에서 0 사이의 값이며 0에 가까울수록 투명합니다
    strokeStyle: 'dashed', // 선의 스타일 입니다
    fillOpacity: 0  // 채우기 불투명도 입니다 

}); 
var centerAround = circle.getBounds(); 
console.log(centerAround);


// centerAround의 남서쪽과 북동쪽 좌표를 가져옵니다
var swLatLng = centerAround.getSouthWest();
var neLatLng = centerAround.getNorthEast();

var circleXY = { 
    "minX" : swLatLng.getLng(), // 남서쪽 경도
    "minY" : swLatLng.getLat(), // 남서쪽 위도
    "maxX" : neLatLng.getLng(), // 북동쪽 경도
    "maxY" : neLatLng.getLat(), // 북동쪽 위도
};

var prevLatlng// 이전 중심 좌표를 저장할 변수

// 지도가 이동, 확대, 축소로 인해 중심좌표가 변경되면 마지막 파라미터로 넘어온 함수를 호출하도록 이벤트를 등록합니다
kakao.maps.event.addListener(map, 'center_changed', function() {

    // 지도의  레벨을 얻어옵니다
    var level = map.getLevel();

    // 지도의 중심좌표를 얻어옵니다 
    var latlng = map.getCenter();   


    circle.setPosition(latlng); // 지도의 중심좌표를 원의 중심으로 설정합니다
    circle.setRadius(level * 1000); // 원의 반지름을 지도의 레벨 * 1000으로 설정합니다
    circle.setMap(map); // 원을 지도에 표시합니다

    // 이전 중심 좌표가 있고, 새로운 중심 좌표와의 차이가 0.001 미만이면 AJAX 요청을 보내지 않습니다
    if (prevLatlng && Math.abs(prevLatlng.getLat() - latlng.getLat()) < 0.1 && Math.abs(prevLatlng.getLng() - latlng.getLng()) < 0.1) {
        return;
    }
    // 새로운 중심 좌표를 이전 중심 좌표로 저장합니다
    prevLatlng = latlng;

    var centerAround = circle.getBounds();
    console.log(centerAround);

    swLatLng = centerAround.getSouthWest();
    neLatLng = centerAround.getNorthEast();

    circleXY = {
    "minX" : swLatLng.getLng(),
    "minY" : swLatLng.getLat(),
    "maxX" : neLatLng.getLng(),
    "maxY" : neLatLng.getLat(),
};

    for (var i = 0; i < markers.length; i++) {
        markers[i].setMap(null);
    }            

    // markers 배열 초기화
    markers = [];

var BodyJson = JSON.stringify(circleXY);

    $.ajax({
        type: 'POST',
        headers:{
            "Content-type": "application/json; charset=utf-8",
            "Authorization": `${token}`
        },
        url: "https://port-0-creativefusion-jvpb2aln5qmjmz.sel5.cloudtype.app/store/range",
        data: BodyJson,
        success: function(data){
            // 서버에서 받은 데이터를 markerList에 저장
            markerList = data;
            console.log("데이터 전송 완료");
            console.log(markerList);

            initKakaoMap();
        },
        error: function(request, status, error,body) {
            console.log(request);
            console.log(body);
            console.log(error);
        }
    });  

});


let currentPopup = null; // 현재 열려있는 팝업 정보를 저장하는 변수, boolean


var imageSize = new kakao.maps.Size(26, 38); // 마커의 크기 기존 33, 36
var choiceImageSize = new kakao.maps.Size(28, 40); // 선택한 마커의 크기 기존 38, 40

var imageSrc = "assets/icon/iconImage/iconRed.png", // 이미지의 경로
    imageSrc2 = "assets/icon/iconImage/iconBlue.png";


// MakrerImage 객체를 생성하여 반환하는 함수
function createMarkerImage(markerScr, markerSize) {
    var markerImage = new kakao.maps.MarkerImage(markerScr, markerSize);
    return markerImage;
}


 // 기본 마커이미지, 오버 마커이미지, 클릭 마커이미지 생성
 var clickImage = createMarkerImage(imageSrc, choiceImageSize),
    normalImage = createMarkerImage(imageSrc2, imageSize);


  selectedMarker = null; // 클릭한 마커를 담을 변수



// 지도 검색 기능
  var search = document.getElementById("searchForm");

  search.addEventListener("submit", function(event) {
      event.preventDefault();
      var searchValue = document.getElementById("searchBox").value;
      console.log(searchValue);
      searchPlace(searchValue);
  }
  );


searchPlace = (place)=>{
    // 키워드로 장소를 검색합니다
    ps.keywordSearch("대구" + place, placesSearchCB); 
    }


  // 키워드 검색 완료 시 호출되는 콜백함수 입니다
  function placesSearchCB (data, status, pagination) {
    if (status === kakao.maps.services.Status.OK) {

        // 검색된 장소 위치를 기준으로 지도 범위를 재설정하기위해
        // LatLngBounds 객체에 좌표를 추가합니다
        var bounds = new kakao.maps.LatLngBounds();

        for (var i=0; i<1; i++) {  
            bounds.extend(new kakao.maps.LatLng(data[i].y, data[i].x));
        }

        // 검색된 장소 위치를 기준으로 지도 범위를 재설정합니다
        map.setBounds(bounds);
    } 
}



function initKakaoMap() {

// InfoWindow 객체들의 참조를 저장하는 배열
var infowindowList = [];


markerList.forEach(function(markerInfo) {
    
    // 마커를 생성합니다
    var marker = new kakao.maps.Marker({
        position: new kakao.maps.LatLng(markerInfo.coordinateY, markerInfo.coordinateX),
        map: map,
        image: normalImage, // 마커 이미지
    });
        marker.normalImage = normalImage;
        marker.clickImage = clickImage;

    markers.push(marker);
    console.log(markerList);

    // Popup창 정보, 디자인
    var popupContent =`
    <div class='content'>
        <div class='img-box' style='background: #f5f5f5 url(https://port-0-creativefusion-jvpb2aln5qmjmz.sel5.cloudtype.app/image/${markerInfo.images[0]}) no-repeat center; background-size: contain;'>
        <a href='javascript:void(0)' onclick='onClose()' class='btn-close'></a>
        </div>
        <div class='info-box'>
        <p style='margin-bottom: 7px; overflow: hidden;'>
            <span class='tit'>${markerInfo.name}</span>
            <button class='link btn-register' data-store-id='${markerInfo.id}' style='color: #3D6DCC; font-size: 13px; float: right;'>물품등록</button>
        </p>
        <p><span class='new-addr'>${markerInfo.address}</span></p>
        <p><span class='old-addr'>${markerInfo.email}</span></p>
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
        map.panTo(new kakao.maps.LatLng(markerInfo.coordinateY, markerInfo.coordinateX));
    });


});


}

$(document).on("click", ".content .btn-register", function() {
    const storeid = $(this).data('store-id');
    const storeEmail = $(this).data('store-Email');

    // Check if the user is logged in
    const token = sessionStorage.getItem('token');
    const email = sessionStorage.getItem('email');

    if (token && email) {
        // User is logged in, redirect to the item registration page
        window.location.href = `itemRegistration.html?storeid=${storeid}`;
    } else {
        // User is not logged in, show a warning message
        if(confirm("로그인이 필요합니다. 로그인 페이지로 이동하시겠습니까?")) {
            // If user clicks "OK", redirect to the login page
            window.location.href = 'login.html';
        }
        // If user clicks "Cancel", do nothing and stay on the current page
    }
});



window.onload = function() {
    initKakaoMap();
  };


