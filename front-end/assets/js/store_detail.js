async function fetchImage(imageId, token) {
    try {
        let imageUrl = `https://port-0-creativefusion-jvpb2aln5qmjmz.sel5.cloudtype.app/image/${imageId}`;
        let imageResponse = await $.ajax({
            url: imageUrl,
            method: "GET",
            headers: {
                "Authorization": `${token}`
            },
            xhrFields: {
                responseType: 'blob'
            }
        });
        
        return new Promise((resolve) => {
            let reader = new FileReader();
            reader.onloadend = function() {
                resolve(reader.result);  // Data URL
            }
            reader.readAsDataURL(imageResponse);
        });

    } catch (error) {
        console.error("Image fetching failed:", error);
        return "";  // 에러가 발생한 경우 빈 문자열 반환
    }
}

$(document).ready(function() {
    const token = sessionStorage.getItem('token'); // token 값을 가져옵니다.
    const urlParams = new URLSearchParams(window.location.search);
    const storeid = urlParams.get('storeid');
    console.log(storeid);
    const url = `https://port-0-creativefusion-jvpb2aln5qmjmz.sel5.cloudtype.app/store/${storeid}`;
    
    $.ajax({
        url: url,
        method: "GET",
        headers: {
            "Content-Type": "application/json",
            "Authorization": `${token}`
        },
        success: async function(response) {
            // 이미지를 호출합니다.
            const mainImageSrc = await fetchImage(response.images[0], token);
            
            // 데이터를 받아온 후 해당 placeholder에 값을 삽입합니다.
            $('#main-image-src').attr('src', mainImageSrc); 
            $('#property-name').text(response.name);
            $('#property-address').text(response.address);
            $('#property-email').text(response.email);
            $('#property-phone').text(response.phone);
            $('#property-content').text(response.content);
        },
        error: function() {
            alert("요청 처리 실패.");
        }
    });
});
