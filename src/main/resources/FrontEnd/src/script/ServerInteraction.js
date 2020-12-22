/**
 * @param type: (String)请求为Post还是Get
 * @param url: (String)请求的url
 * @param isAsynchronous: (boolean)请求是否为异步
 * @param func: (函数)需要执行的操作
 */
function ajax(type, url, data, isAsynchronous, f) {
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
    xmlHttp.open(type, url, isAsynchronous);
    if (type == "get" | type == "GET") {
        xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    }
    xmlHttp.send(data);
}


function login_ajax() {

    let logname = document.getElementsByTagName('input')['Username'].value;
    let logpass = document.getElementsByTagName('input')['Password'].value;

    if (logname == "" || logpass == "") {
        // var obj = document.getElementById("errorMsg");
        alert("用户名或密码不能为空！");
        // obj.setAttribute("class", "tip-box visiblility-show");
        return;
    }

    var userinfo = "inAccount=" + logname + "&inPsw=" + logpass;

    var url = "http://36058s3d36.zicp.vip:55374/user/login?name=" + logname + "&password=" + logpass;

    xmlhttprequest = window.XMLHttpRequest ? new XMLHttpRequest() : new ActiveXObject('Microsoft.XMLHTTP');

    xmlhttprequest.onreadystatechange = function() {
        if (xmlhttprequest.readyState == 4 && xmlhttprequest.status == 200) {
            var result = xmlhttprequest.responseText;
            if (result != "Fail") {
                ajax("get","http://36058s3d36.zicp.vip:55374/userhash/findHashByUid?uid="+result,null,false,function(x){
                    window.location.href = "Store.html?uid=" + x.responseText;
                })
                // window.location.href = "Store.html?uid=" + result;
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



function sign_in() {
    let success = 1;
    //todo 
    if (success) {
        window.location.href = "Store.html"; //跳转
    }
}

function parsePageUrl(pageUrl) {
    var result = "{"
    var index = pageUrl.indexOf("?")
    var temp = pageUrl.substr(index + 1)
    var strList = temp.split("&")
    for (var str in strList) {
        index = strList[str].indexOf("#")
        while (index >= 0) {
            strList[str] = strList[str].substr(0, index)
            index = strList[str].indexOf("#")
        }
        index = strList[str].indexOf("=")
        if (result != "{") { result += "," }
        result += '"'
        result += strList[str].substr(0, index)
        result += '"'
        result += ":"
        result += '"'
        result += strList[str].substr(index + 1)
        result += '"'

    }
    result += "}"

    return JSON.parse(result)
}

function show(divID) {
    var hideDiv = document.getElementById("hideDiv")
    var showDiv = document.getElementById(divID)
    hideDiv.style.display = "block "
    hideDiv.style.opacity = 0.6
    hideDiv.style.zIndex = 6
    showDiv.style.display = "block "
}

function hide(divID) {
    var hideDiv = document.getElementById("hideDiv")
    var showDiv = document.getElementById(divID)
    hideDiv.style.opacity = 0
    hideDiv.style.zIndex = -1
    showDiv.style.display = "none "
}