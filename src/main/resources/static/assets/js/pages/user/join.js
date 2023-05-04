    $(function (){
        $("#saveBtn").on("click", function (e){
            e.preventDefault();
            getParameter("userAdd")
                .then(res => {
                    console.log(res);

                    $.ajax({
                        url: '/api/joinProc',
                        method: 'POST',
                        data: JSON.stringify(res),
                        contentType: "application/json"
                    })
                        .done(function(res) {

                        })
                        .fail(function(request, status, error){
                            console.log('request : ', request)
                            console.log('request text : ', JSON.parse(request.responseText));
                        })
                })
        })

        $("#dupBtn").on("click", function (e) {
            e.preventDefault();
            const username = $("#username").val()
            console.log("username : ", username);

            $.ajax({
                url: `/api/users/${username}/exists`,
                method: 'GET'
            })
                .done(function(res){
                    // moreMessage : true -> 중복x , false -> 중복o
                    if(!res.moreMessage){
                        alert("사용 가능 ID")
                    }
                })
        })
    })

    async function getParameter(formId) {
        const form = document.getElementById(formId);
        const formData = new FormData(form);

        const data = Array.from(formData.entries()).reduce((perv, [key, value]) => {
            if (perv[key]) {
                Array.isArray(perv[key]) ? perv[key].push(value) : (perv[key] = [perv[key], value]);
            } else {
                perv[key] = value;
            }
            return perv;
        }, {});
        return data;
    }
