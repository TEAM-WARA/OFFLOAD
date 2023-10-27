document.addEventListener('DOMContentLoaded', function() {
    const loginCard = document.getElementById('loginCard');
    const registerCard = document.getElementById('registerCard');
    const showRegisterLink = document.getElementById('showRegister');
    const showLoginLink = document.getElementById('showLogin');

    showRegisterLink.addEventListener('click', function(e) {
        e.preventDefault();
        loginCard.style.display = 'none';
        registerCard.style.display = 'block';
    });

    showLoginLink.addEventListener('click', function(e) {
        e.preventDefault();
        registerCard.style.display = 'none';
        loginCard.style.display = 'block';
    });
});


document.getElementById('LoginButton').addEventListener('click', async function(event) {
    event.preventDefault();

    const email = document.getElementById('LoginInputEmail').value;
    const password = document.getElementById('LoginInputPassword').value;

    const response = await fetch('https://port-0-creativefusion-jvpb2aln5qmjmz.sel5.cloudtype.app/auth/signin', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            email: email,
            password: password
        })
    });

    const result = await response.json();

    if (response.status === 200) {
        // Store token in local storage
        localStorage.setItem('token', result.token);
        localStorage.setItem('email', result.email);  // 여기서 userid를 저장합니다.
        // Redirect to map.html
        window.location.href = 'map.html';
    } else {
        alert("로그인에 실패하였습니다"); // token contains the error message in this case
    }
});

document.getElementById('registerButton').addEventListener('click', async function(event) {
    event.preventDefault();

    // Get form data
    const email = document.querySelector('#resisterInputEmail').value;
    const password = document.querySelector('#resisterInputPassword').value;
    const repeatPassword = document.querySelector('#resisterRepeatPassword').value;
    
    // Get selected role
    const selectedRole = document.querySelector('input[name="userRole"]:checked').value;

    // Check if passwords match
    if (password !== repeatPassword) {
        alert('비밀번호가 일치하지 않습니다.');
        return;
    }

    // Create payload
    const payload = {
        email: email,
        password: password,
        role: selectedRole
    };

    try {
        const response = await fetch('https://port-0-creativefusion-jvpb2aln5qmjmz.sel5.cloudtype.app/auth/signup', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(payload)
        });

        const data = await response.json();

        if (response.status === 201) {
            // Redirect to login.html
            window.location.href = 'login.html';
        } else if (response.status === 400) {
            // Handle error
            alert(`회원가입 실패: ${data.email}`);
        }
    } catch (error) {
        console.error('오류 발생:', error);
    }
});

