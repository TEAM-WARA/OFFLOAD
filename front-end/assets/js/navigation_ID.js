document.addEventListener("DOMContentLoaded", function() {
    const userId = sessionStorage.getItem('email');
    const userRole = sessionStorage.getItem('role');
    const storeId = sessionStorage.getItem('storeid');

    const loginMenuItem = document.getElementById('loginMenuItem');
    const loginMenu = document.getElementById('loginMenu');
    const dropdownContent = document.getElementById('dropdownContent');
    const storeIFO = document.getElementById('storeIFO');
    const logoutAction = document.getElementById('logoutAction');
    const storeRegisterItem = document.getElementById('storeRegisterItem'); // 가게등록 항목
    const storeManageItem = document.getElementById('storeManageItem'); // 가게 물품 관리 항목

    console.log("storeid: ", storeId);  

    if (userId) {
        loginMenu.textContent = userId;
        registeredItem.style.display = 'block'; 
        if (userRole === 'saller') {
            storeRegisterItem.style.display = 'block';
            storeManageItem.style.display = 'block';
        } else if (userRole === 'user') {
            storeRegisterItem.style.display = 'none';
            storeManageItem.style.display = 'none';
        }

        storeIFO.addEventListener('click', function(event) {
            event.preventDefault();
            if (storeId) {
                window.location.href = `property-details.html?storeid=${storeId}`;
            } else {
                alert("일반 유저는 접근할수 없습니다.");
            }
        });

        logoutAction.addEventListener('click', function(event) {
            event.preventDefault();
            sessionStorage.removeItem('token');
            sessionStorage.removeItem('email');
            sessionStorage.removeItem('role');
            sessionStorage.removeItem('storeid');
            window.location.href = 'index.html';
        });
    } else {
        loginMenu.textContent = "로그인";
        dropdownContent.style.display = "none";
        storeRegisterItem.style.display = 'none';
        storeManageItem.style.display = 'none';
        registeredItem.style.display = 'none'; 
        loginMenuItem.addEventListener('click', function() {
            window.location.href = 'login.html';
        });
    }

    // storeid가 0일 경우 "가게정보" 드롭박스 항목을 숨김
    if (storeId === '0') {
        storeIFO.style.display = 'none';
    }
});
