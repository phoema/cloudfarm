//ready start	
$(document).ready(function() { 
	var user =sessionStorage.getItem("userinfo");
	if(user){
		user = JSON.parse(user);
		$('.contact-form').hide();
        $('#per-information').show().find('a').text(user['name']);
        $('#btn-logout').show();

	}
}); // ready end
// 用户登录
$('#form-login').click(function(){
	loginapp();
});
//enter
$(window).keydown(function(event){
   var e = event || window.event || arguments.callee.caller.arguments[0];
   if(e && e.keyCode!=13){ 
         return;       
    }
    loginapp();
  
});
// 用户注册
$('#register-form').click(function(){
	var senddata =  { "username": "JIAHH", "password": "123456" , "password2": "123456" };
	senddata =  { "name": $("#reg-name").val(),"username": $("#reg-username").val(), "password": $("#reg-password").val() , "password2": $("#reg-password2").val()  };
	// 1、TODO确认两次密码输入一致
	if($("#reg-password").val()!=$("#reg-password2").val() || $("#reg-password").val().length <6){
		 $("#register-info").text("两次输入密码不一致/密码长度不应小于6位");
		 $("#register-info").show();
		 return;
	}
	// 2、TODO进行输入规则验证
	// 3、提交
	  $.ajax(
              {
                  "type": "post",
                  //"dataType": "json",
                  "data": JSON.stringify(senddata),
                  "contentType": "application/json; charset=utf-8",
                  "url" : "/api/user/save",
                  "success": function(data)
                  {     
              		 $("#register-info").text("注册成功，请登录");
            		 $("#register-info").show();
            		 return;
                  },"error": function(XMLHttpRequest, textStatus, errorThrown )
                  {     
             		 $("#register-info").text(XMLHttpRequest.responseText);
            		 $("#register-info").show();
            		 return;
                   }
               });
	  
	  
  });

function loginapp(){
     senddata =  { "username": $("#username").val(), "password": $("#password").val()};
   if($("#username").val() == ""){
     $("#login-info").text("请输入用户名");
     $("#login-info").show();
     return;
   }
   if($("#password").val() == ""){
     $("#login-info").text("请输入密码");
     $("#login-info").show();
     return;
   }
    //清空本地存储-用户信息
  sessionStorage.removeItem("userinfo");
    $.ajax(
              {
                  "type": "post",
                  //"dataType": "json",
                  "data": JSON.stringify(senddata),
                  "contentType": "application/json; charset=utf-8",
                  "url" : "/api/login?username="+$("#username").val() +"&password="+$("#password").val(),
                  "success": function(user)
                  {   
                     //获取用户信息,设置本地存储
                       sessionStorage.setItem("userinfo",JSON.stringify(user));
                       $('.contact-form').hide();
                       $('#per-information').show().find('a').text(user['name']);
                       $('#btn-logout').show();
                       

                  },"error": function(XMLHttpRequest, textStatus, errorThrown )
                  {     
                    alert(XMLHttpRequest.responseText);
                  }
  });
}


