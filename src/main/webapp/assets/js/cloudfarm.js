//ready start	
$(document).ready(function() { 
	var user =sessionStorage.getItem("userinfo");
	if(user){
		user = JSON.parse(user);
        $('#per-information').show().find('a').text(user['name']);
        $('#btn-logout').show();
	}else if(window.location.href.indexOf("/product.html") >0
			||window.location.href.indexOf("/charity.html") >0		
			||window.location.href.indexOf("/article.html") >0		
			||window.location.href.indexOf("/space.html") >0		
	){
		
	}else if(window.location.href.indexOf("/index.html") == -1){
		window.location.href="index.html";
	}else if(window.location.href.indexOf("/index.html") >0){
		$("#contact-form").show();
	}
}); // ready end
//用户退出登录
$('#btn-logout').click(function(){
	$.get( 
			"/api/logout", 
			null, 
			function(data) {
			    //清空本地存储-用户信息
				sessionStorage.removeItem("userinfo");
				window.location.href="index.html";
		    });
});
