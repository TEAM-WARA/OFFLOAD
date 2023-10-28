
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


  var startDate = document.getElementById('startDate');

    //필드에서 포커스 아웃(떠남) 이벤트를 모니터링
    startDate.addEventListener('blur', function() {
        var startDateTime = startDate.value;
        console.log(startDateTime);  // 사용자가 입력한 날짜와 시간을 콘솔에 출력합니다.
    });

    var expirationDate = document.getElementById('expirationDate');

    //필드에서 포커스 아웃(떠남) 이벤트를 모니터링
    expirationDate.addEventListener('blur', function() {
        var expirationDateTime = expirationDate.value;
        console.log(expirationDateTime);  // 사용자가 입력한 날짜와 시간을 콘솔에 출력합니다.
    });


// 'cameraButton' id를 가진 버튼을 클릭하면 카메라를 실행합니다.
document.getElementById('cameraButton').addEventListener('click', function() {
    document.getElementById('imageUpload').setAttribute('capture', 'camera');
    document.getElementById('imageUpload').click();
});

// 파일 입력 요소에서 사진을 찍거나 선택하면 'change' 이벤트가 발생하며 설정된 이벤트 리스너 함수가 실행됩니다.
document.getElementById('imageUpload').addEventListener('change', function(e) {
    var file = e.target.files[0];
    // 이곳에서 file 객체를 이용해 필요한 처리를 진행합니다.
    // 예를 들어, file 객체를 서버에 업로드하거나, 이미지를 미리 보여주는 등의 작업을 수행할 수 있습니다.
});


  document.getElementById("itemButton").addEventListener("click",
    function () {
        var data = {
            "name": document.getElementById("name").value,
            "storeEmail": "상점이메일",
            "storeId": 1,
            "address": "상점주소",
            "content": document.getElementById("content").value,
            "start" : document.getElementById("startDate").value,
            "expiration" : document.getElementById("expirationDate").value,
            "phone" : document.getElementById("phoneNum").value,

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
        fetch('https://port-0-creativefusion-jvpb2aln5qmjmz.sel5.cloudtype.app/storage', {
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
            })
            .catch(error => {
                console.error(error);
            });

    }

)
