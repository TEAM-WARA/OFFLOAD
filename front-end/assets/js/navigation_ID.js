document.addEventListener("DOMContentLoaded", function() {
    const userId = sessionStorage.getItem('email');
    const userRole = sessionStorage.getItem('role');
    const loginMenuItem = document.getElementById('loginMenuItem');
    const loginMenu = document.getElementById('loginMenu');
    const dropdownContent = document.getElementById('dropdownContent');
    const logoutAction = document.getElementById('logoutAction');
    const storeRegisterItem = document.getElementById('storeRegisterItem'); // 가게등록 항목
    const storeManageItem = document.getElementById('storeManageItem'); // 가게 물품 관리 항목

    if (userId) {
        loginMenu.textContent = userId;
        registeredItem.style.display = 'block'; // 로그인 한 경우 등록한 물품 항목 보이기

        if (userRole === 'saller') {
            storeRegisterItem.style.display = 'block';
            storeManageItem.style.display = 'block';
        } else if (userRole === 'user') {
            storeRegisterItem.style.display = 'none';
            storeManageItem.style.display = 'none';
        }

        logoutAction.addEventListener('click', function(event) {
            event.preventDefault();
            sessionStorage.removeItem('token');    // 수정된 부분
            sessionStorage.removeItem('email');   // 수정된 부분
            sessionStorage.removeItem('role');    // 수정된 부분
            location.reload();
        });
    } else {
        loginMenu.textContent = "로그인";
        dropdownContent.style.display = "none";
        storeRegisterItem.style.display = 'none'; 
        storeManageItem.style.display = 'none'; 
        registeredItem.style.display = 'none'; // 로그아웃 한 경우 등록한 물품 항목 숨기기
        loginMenuItem.addEventListener('click', function() {
            window.location.href = 'login.html';
        });
    }
});
