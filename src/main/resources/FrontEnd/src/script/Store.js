let GAME_LIST = [];

/**
 * 登陆功能
 */
function login() {
	window.location.href = "./Login.html";
}


/**
 * 主页面搜索功能
 */
function search() {
	let cur_info = parsePageUrl(window.location.href);
	let query = document.getElementById("search_content").value;
	if (query !== "") {
		if (!cur_info.hasOwnProperty("uid")) {
			window.location.href = "./Search.html" + "?query=" + query;
		} else {
			let uid = parsePageUrl(window.location.href).uid;
			window.location.href = "./Search.html?uid=" + uid + "&query=" + query;
		}
	}
}

/**
 * 实现页面无限下拉
 */
let GAME_LIST_COUNT = 0
function arrange_game_list(){
    //let game_list = getFromServer();
	let list = GAME_LIST;
    let game_list = [];
	let game_pics = [];
	let game_descriptions = [];
	let gid = [];
    for (let i = GAME_LIST_COUNT; i < GAME_LIST_COUNT+5 && i < list.length; i++){
    	game_list.push(list[i]["name"]);
    	game_pics.push("http://36058s3d36.zicp.vip/static/game/"+list[i]["gid"].toString()+"game/shoot/1shoot.jpg");
		game_descriptions.push(list[i]["g_des"]);
		gid.push(list[i]["gid"]);
	}

    let game_list_div = document.getElementById("game_list");
    for(let i = 0; i < game_list.length; i ++){
        let div = document.createElement("div");
        div.className = "game_list_div";
        game_list_div.appendChild(div);
    }
    for(let i = 0; i < game_list.length; i ++){
        let name = document.createElement("span");
        name.innerHTML = "<b>"+game_list[i]+"</b>";
        game_list_div.children[i + GAME_LIST_COUNT].appendChild(name);

        let pic = document.createElement("img");
        pic.src = game_pics[i];
        game_list_div.children[i + GAME_LIST_COUNT].appendChild(pic);

        let description = document.createElement("p");
        description.innerHTML = game_descriptions[i];
        game_list_div.children[i + GAME_LIST_COUNT].appendChild(description);

        game_list_div.children[i + GAME_LIST_COUNT].addEventListener("click", function(){
        	if (parsePageUrl(window.location.href).uid === null) {
				window.location.href = "./login.html";
			}else {
        		window.location.href = "./gamePage.html?uid="+parsePageUrl(window.location.href).uid+"&gid=" + gid[i].toString();
			}
		});
    }
    GAME_LIST_COUNT += game_list.length;
}
//滚动条在Y轴上的滚动距离
function getScrollTop(){
    let scrollTop, bodyScrollTop = 0, documentScrollTop = 0;
    if(document.body){
        bodyScrollTop = document.body.scrollTop;
    }
    if(document.documentElement){
        documentScrollTop = document.documentElement.scrollTop;
    }
    scrollTop = (bodyScrollTop - documentScrollTop > 0) ? bodyScrollTop : documentScrollTop;
    return scrollTop;
}

//文档的总高度
function getScrollHeight(){
    let scrollHeight, bodyScrollHeight = 0, documentScrollHeight = 0;
    if(document.body){
        bodyScrollHeight = document.body.scrollHeight;
    }
    if(document.documentElement){
        documentScrollHeight = document.documentElement.scrollHeight;
    }
    scrollHeight = (bodyScrollHeight - documentScrollHeight > 0) ? bodyScrollHeight : documentScrollHeight;
    return scrollHeight;
}

//浏览器视口的高度
function getWindowHeight(){
    let windowHeight;
    if(document.compatMode === "CSS1Compat"){
        windowHeight = document.documentElement.clientHeight;
    }else{
        windowHeight = document.body.clientHeight;
    }
    return windowHeight;
}

//js原生监听滚动事件
window.onscroll = function() {

    if(getScrollTop() + getWindowHeight() === getScrollHeight()) {
		if (GAME_LIST_COUNT === 0) {
			ajax("get", "http://10.21.100.129:9090/game/getAllGame", null, true, function (game_list) {
				console.log("entering return");
				let result = JSON.parse(game_list.responseText);
				console.log(result);
				GAME_LIST = result;
			});
		}
    	arrange_game_list();
    }
}


/**
 * 实现轮播图
 */

$(function() {
	
	var posObject = {
		A:{"width":257,"height":143,"left":-134,"top":164,"opacity":0},
		B:{"width":513,"height":285,"left":0,"top":93,"opacity":1},
		C:{"width":800,"height":445,"left":200,"top":0,"opacity":1},
		D:{"width":513,"height":285,"left":687,"top":93,"opacity":1},
		E:{"width":257,"height":143,"left":1060,"top":164,"opacity":0}
	}
	//特效最主要就是移动posArrary数组的位置，从而切换图片
	var posArrary = [
		posObject.C,
		posObject.D,
		posObject.E,
		posObject.E,
		posObject.E,
		posObject.E,
		posObject.E,
		posObject.E,
		posObject.A,
		posObject.B,
	];
	var zindexArrary = [3,2,1,1,1,1,1,1,1,2];
	var maskArrary = [0,0.4,0.4,0.4,0.4,0.4,0.4,0.4,0.4,0.4];
	
	//设置初始值
	setEveryLiByArr();
	
	//设置一个锁，防止用户快速点击，造成动画排队
	var god = false;
	
	//设置图片信号数
	var idx = 0;
	
	//右按钮事件
	$(".right_btn").click(function() {
		//alert(11);
		//防止动画排队
		if ($("ul li").is(":animated")&&!god) {
			return;
		}
		posArrary.unshift(posArrary.pop());
//		unshift() 方法可向数组的开头添加一个或更多元素，并返回新的长度。
//		push() 方法可以给数组末尾添加一个或多个数组项。
//		pop() 方法可以从数组的末尾删除一个数组项，并返回删除的数组项
//		shift() 方法和 pop() 方法刚好相反，它可以删除数组的第一项，并返回删除的数组项
		zindexArrary.unshift(zindexArrary.pop());
		maskArrary.unshift(maskArrary.pop());
		setEveryLiByArr();
		idx++;
		//图片到最后一张返回第一张
		if (idx > 9) {
			idx = 0;
		}
		//小圆点跟随图片移动
		$("ol li").eq(idx).addClass("cur").siblings().removeClass("cur");
	});
	//左按钮事件
	$(".left_btn").click(function() {
		//alert(11);
		if ($("ul li").is(":animated")&&!god) {
			return;
		}
		posArrary.push(posArrary.shift());
		zindexArrary.push(zindexArrary.shift());
		maskArrary.push(maskArrary.shift());

		setEveryLiByArr();
		idx--;
		if (idx < 0) {
			idx = 10;
		}
		$("ol li").eq(idx).addClass("cur").siblings().removeClass("cur");
	});
	
	//点击小圆点跳转图片
	$("ol li").click(function() {
		//alert(11);
		var _idx = $(this).index();
		//console.log(_idx);
		if (_idx > idx) {
			var count = _idx - idx;
			god = true;
			while (count--){
				//模拟右按钮点击事件，跳转到点击圆点中的图片
				$(".right_btn").trigger("click");
			}
			god = false;
		} else {
			var count = idx - _idx;
			god = true;
			while (count--){
				$(".left_btn").trigger("click");
			}
			god = false;
		}
	});
	
	//设置每一个li标签的三个数组数据
	function setEveryLiByArr(){
		var during = god?80:800;
		$("ul li").each(function(index){
			//console.log($(this));
			$(this).css("z-index",zindexArrary[index]);
			$(this).animate(posArrary[index],during);
			$(this).find(".mask").animate({"opacity":maskArrary[index]},during);
		});
	}
})

/**
 * 点赞点踩功能
 */
let LIKE_CLICKED = false;
let DISLIKE_CLICKED = false;
function community_like(){
	if (!LIKE_CLICKED && !DISLIKE_CLICKED){
		ajax("get", "http://10.21.100.129:9090/comment/like?cid=7", null, true, function (){});
    	let like = document.getElementById("like");
    	like.src = "../../figures/icon/liked.png";
    	console.log("like");
    	LIKE_CLICKED = true;
	}else if (LIKE_CLICKED && !DISLIKE_CLICKED){
		ajax("get", "http://10.21.100.129:9090/comment/likeWithdraw?cid=7", null, true, function (){});
		let like = document.getElementById("like");
		like.src = "../../figures/icon/like.png";
		console.log("like withdraw");
		LIKE_CLICKED = false;
	}
}
function community_dislike(){
	if (!LIKE_CLICKED && !DISLIKE_CLICKED) {
		ajax("get", "http://10.21.100.129:9090/comment/dislike?cid=7", null, true, function () {});
		let dislike = document.getElementById("dislike");
		dislike.src = "../../figures/icon/disliked.png";
		console.log("dislike");
		DISLIKE_CLICKED = true;
	}else if (!LIKE_CLICKED && DISLIKE_CLICKED){
		ajax("get", "http://10.21.100.129:9090/comment/dislikeWithdraw?cid=7", null, true, function (){});
		let dislike = document.getElementById("dislike");
		dislike.src = "../../figures/icon/dislike.png";
		console.log("dislike withdraw");
		DISLIKE_CLICKED = false;
	}
}

/**
 * 社区推荐
 */
let RECOMMEND_TID = Math.trunc(Math.random()*5);//硬核推荐系统 todo
RECOMMEND_TID = 1;
function get_community_recommend(){
	ajax("get", "http://36058s3d36.zicp.vip:55374/comment/getAllByTid?tid="+RECOMMEND_TID.toString(), 1, true, function(community){
		community = JSON.parse(community.responseText);
		console.log("community recommend get sent!");
		console.log(community);
		let score = document.getElementById("score");
		score.innerHTML = "<b>"+community[RECOMMEND_TID]["points"]+"</b>"
		let user_name = document.getElementById("username");
		let avatar = document.getElementById("avatar");
		ajax("get", "http://10.21.100.129:9090/user/findNameByUid?uid="+community[RECOMMEND_TID]["uid"].toString(), null, true, function(ua){
			user_name.innerHTML = ua.responseText;
		});
		avatar.src = "http://36058s3d36.zicp.vip/static/user/"+community[RECOMMEND_TID]["uid"].toString()+"/photo.jpg";

		let community_picture = document.getElementById("community_picture");
		let game_info = document.getElementById("game_info");
		let content = document.getElementById("content");
		community_picture.src = community[RECOMMEND_TID]["picture"];
		content.innerHTML = community[RECOMMEND_TID]["content"];
		ajax("get", "http://36058s3d36.zicp.vip:55374/game/getAllBygid?gid="+community[RECOMMEND_TID]["gid"].toString(), null, true, function(gn){
			gn = JSON.parse(gn.responseText);
			game_info.innerHTML = gn["name"];
		});

		let more_link = document.getElementById("more_link");
		more_link.addEventListener("click", function (){
			more_link.href = UID !== null ? "gamePage.html?gid="+community[RECOMMEND_TID]["gid"].toString()+"&uid="+UID : "gamePage.html?gid="+community[RECOMMEND_TID]["gid"].toString();
		});
	});
}

/**
 * 获取轮播图
 */
function get_swiper(){
	let imgs = document.getElementsByClassName("swiper_img");
	let gids = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10];
	for (let i = 0; i < imgs.length; i ++){
		imgs[i].src = "http://36058s3d36.zicp.vip/static/game/"+gids[i].toString()+"game/shoot/1shoot.jpg";
	}

}
