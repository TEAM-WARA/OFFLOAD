
function resisterGoods(type) {
    const token = sessionStorage.getItem('token');
    const email = sessionStorage.getItem('email');
    let url;

    if (type === 'User_registeredItems') {
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

            if(type === 'User_registeredItems'){
                for (const item of data) {
                    let imageSrc = await fetchImage(item.images[0]);    
                    let buttonsHtml =`
                    <div class="main-button">
                        <button class="btn btn-primary ViewDetails" data-store-id="${item.storeId}">상세보기</button>
                    </div>`;

                    if(item.allow) {
                        propertiesBox.append(`
                        <div class="col-lg-4 col-md-6 align-self-center mb-30 properties-items col-md-6" data-item-id="${item.id}">
                            <span class="card-id">${item.id}</span>
                            <div class="item">
                                <img src="${imageSrc}" alt="">
                                <span class="category">${item.name} 님</span>
    
                                <h4>${item.phone}</h4>
    
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
            }else{
                for (const item of data) {
                    let imageSrc = await fetchImage(item.images[0]);    
    
                    if(!item.allow) {
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
        
                            </div>
                        </div>
                    `);
                    }
                }
            }
        },
        error: function() {
            alert("데이터를 불러오는 데 실패했습니다.");
        }
    });
}

$(".properties-box").on("click", ".ViewDetails", function() {
    const storeid = $(this).data('store-id');
    window.location.href = `property-details.html?storeid=${storeid}`;
});

// 페이지 로드 시 '등록 물품 건' 데이터 불러오기
$(document).ready(function() {

    resisterGoods('User_registeredItems');
});

