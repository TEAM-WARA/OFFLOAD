



document.getElementById("createStoreButton").addEventListener("click",
    function () {
        var data = {
            "name": document.getElementById("name").value,
            "email": document.getElementById("subject").value,
            "address": document.getElementById("addr").value
        };
        var formData = new FormData();
        formData.append('images', document.getElementById("image").files[0]); // 이미지
        formData.append('data', new Blob([JSON.stringify(data)], { type: "application/json" }));
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
        fetch('https://port-0-creativefusion-jvpb2aln5qmjmz.sel5.cloudtype.app/store', {
            method: 'POST',
            headers: {
                "Authorization" : document.getElementById("message").value
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



