function fetchData(type) {
    const token = sessionStorage.getItem('token');
    const email = sessionStorage.getItem('email');
    let url;

    if (type === 'registeredItems') {
        url = `https://port-0-creativefusion-jvpb2aln5qmjmz.sel5.cloudtype.app/storage/seller/allow?email=${email}`;
    } else if (type === 'requestItems') {
        url = `https://port-0-creativefusion-jvpb2aln5qmjmz.sel5.cloudtype.app/storage/seller?email=${email}`;
    } else if (type === 'User_registeredItems') {
        url = `https://port-0-creativefusion-jvpb2aln5qmjmz.sel5.cloudtype.app/storage/user?email=${email}`;
    } else if(type === 'User_requestItems') {
        url = `https://port-0-creativefusion-jvpb2aln5qmjmz.sel5.cloudtype.app/storage/user?email=${email}`;
    }

    async function fetchImage(imageId) {
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

    $.ajax({
        url: url,
        method: "GET",
        headers: {
            "Content-Type": "application/json",
            "Authorization": `${token}`
        },
        success: async function(data) {
            const propertiesBox = $(".properties-box");
            propertiesBox.empty();

            for (const item of data) {
                let imageSrc = await fetchImage(item.images[0]);

                let buttonsHtml = "";
                if (type === 'requestItems') {
                    buttonsHtml = `
                        <div class="main-button">
                            <button class="btn btn-primary allowBtn">등록 요청 허용</button>
                            <button class="btn btn-danger denyBtn">등록 요청 거부</button>
                        </div>
                    `;
                }else if(item.content=="물품 보관 요청이 거부되었습니다."){
                    buttonsHtml = "등록이 거부된 물품";
                }

                
                if(!item.allow || type === 'registeredItems') {
                    propertiesBox.append(`
                    <div class="col-lg-4 col-md-6 align-self-center mb-30 properties-items col-md-6" data-item-id="${item.id}">
                        <span class="card-id">${item.id}</span>
                        <div class="item">
                            <img src="${imageSrc}" alt="">
                            <span class="category">${item.name} 님</span>

                            <h4>010-1111-2222</h4>

                            <h4>${item.email}</h4>

                            <h4>${item.content}</h4>
                            
                            <ul>
                                <li>시작시간: <span>${item.start}</span></li>
                                <li>끝 시간: <span>${item.expiration}</span></li>
                            </ul>
                            ${buttonsHtml}
                        </div>
                    </div>
                `);
                
                
                }
            }
        },
        error: function() {
            alert("데이터를 불러오는 데 실패했습니다.");
        }
    });
}

// 페이지 로드 시 '등록 물품 건' 데이터 불러오기
$(document).ready(function() {
    fetchData('registeredItems');
});


$(".properties-box").on("click", ".allowBtn", function() {
    const itemId = $(this).closest('.properties-items').data('item-id');
    console.log(itemId);
    const email = sessionStorage.getItem('email');
    const token = sessionStorage.getItem('token');
    const url = `https://port-0-creativefusion-jvpb2aln5qmjmz.sel5.cloudtype.app/storage/allow/${itemId}?email=${email}`;
    
    $.ajax({
        url: url,
        method: "PUT",
        headers: {
            "Content-Type": "application/json",
            "Authorization": `${token}`
        },
        success: function(response) {
            alert("등록 요청이 허용되었습니다.");
            // 필요한 경우 UI를 업데이트하거나 데이터를 새로 고치세요.
            fetchData('registeredItems');
        },
        error: function() {
            alert("요청 처리 실패.");
        }
    });
});

$(".properties-box").on("click", ".denyBtn", function() {
    const itemId = $(this).closest('.properties-items').data('item-id');
    const email = sessionStorage.getItem('email');
    const token = sessionStorage.getItem('token');
    const url = `https://port-0-creativefusion-jvpb2aln5qmjmz.sel5.cloudtype.app/storage/denied/${itemId}?email=${email}`;
    
    $.ajax({
        url: url,
        method: "PUT",
        headers: {
            "Content-Type": "application/json",
            "Authorization": `${token}`
        },
        success: function(response) {
            alert("등록 요청이 거부되었습니다.");
            // 필요한 경우 UI를 업데이트하거나 데이터를 새로 고치세요.
            fetchData('requestItems');
        },
        error: function() {
            alert("요청 처리 실패.");
        }
    });
});
