function community_ajax() {
    let logname = document.getElementsByTagName('input')['Username'].value;
    let logpass = document.getElementsByTagName('input')['Password'].value;

    if (logname == "" || logpass == "") {
        // var obj = document.getElementById("errorMsg");
        alert("用户名或密码不能为空！");
        // obj.setAttribute("class", "tip-box visiblility-show");
        return;
    }

    var userinfo = "inAccount=" + logname + "&inPsw=" + logpass;

    var url = "http://10.21.52.79:8080/test/login?name=" + logname + "&password=" + logpass;

    xmlhttprequest = window.XMLHttpRequest ? new XMLHttpRequest() : new ActiveXObject('Microsoft.XMLHTTP');

    xmlhttprequest.onreadystatechange = function() {
        if (xmlhttprequest.readyState == 4 && xmlhttprequest.status == 200) {
            var result = xmlhttprequest.responseText;
            if (result != "Fail") {
                window.location.href = "Store.html";
            } else {
                // document.getElementById("xiaoxi").innerHTML = "登录失败！";
                alert("用户名或密码错误！")
            }
        }
    }
    xmlhttprequest.open("get", url, true);
    xmlhttprequest.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xmlhttprequest.send(null); //这时才开始发送请求
}


function addDiv() {
    alert("666")
    var div = document.createElement("div");
    div.innerHTML = "内容";
    document.body.appendChild(div);
}

function publish(){
    var text = $('#txtNew').val();

    var uid=1;
    var gid=1;
    var url = "http://10.21.100.129:9090/postbar/setupPostbar?title=" + text+"&uid="+uid+"&gid="+gid;

    xmlhttprequest = window.XMLHttpRequest ? new XMLHttpRequest() : new ActiveXObject('Microsoft.XMLHTTP');

    xmlhttprequest.onreadystatechange = function() {
        if (xmlhttprequest.readyState == 4 && xmlhttprequest.status == 200) {
            var result = xmlhttprequest.responseText;
        }
    }
    xmlhttprequest.open("post", url, true);
    xmlhttprequest.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xmlhttprequest.send(); //这时才开始发送请求


}




function getName(uid){
    uid = 1
    ajax("get", "http://10.21.100.129:9090/user/findByUid?="+uid,function(x){alert(x.responseText)})

    document.getElementById("user-name").innerHTML = uid+"sd";
}

function ajax(type, url, data, f) {
    let xmlHttp = null;
    if (window.XMLHttpRequest) {
        xmlHttp = new XMLHttpRequest();
    } else {
        xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
    }
    xmlHttp.onreadystatechange = function() {
        if (xmlHttp.readyState === 4 && xmlHttp.status === 200) {
            f(xmlHttp)
        } else {
            return null;
        }
    }
    xmlHttp.open(type, url, true);
    if (type == "get" | type == "GET") {
        xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    }
    xmlHttp.send(data);
}
