$(function () {
        $("#captcha").click(function () {
            let date = new Date();
            let name = "/captcha/" + date.getTime();
            $("#captcha").attr("src", name);
        })
    }
);