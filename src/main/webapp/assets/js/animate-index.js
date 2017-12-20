$(function(){
	$(window).scroll(function(){
		var top=$(document).scrollTop();
		//console.log(top);
		if(top>300){
			$('.about-us').find('h3').addClass('animated bounceInDown');
			$('.about-us').find('p').addClass('animated bounceInDown');
		}
		 if(top>700){
			$('.special-a .spl-a').find('img').addClass('animated bounceInLeft');
			$('.special-a .spl-b').find('h3,p').addClass('animated bounceInRight');
		}
		 if(top>1300){
			$('.special-b .spl-b').find('img').addClass('animated fadeInUp');
			$('.special-b .spl-a').find('h3,p').addClass('animated tada');
		}
	});
	$('.con-product img,.special-a img,.special-b img').mouseover(function(){
				$(this).addClass('animated pulse');
			}).mouseout(function(){
				$(this).removeClass('pulse');
	});
	$('#mypage').click(function(){

		var t=$(document).scrollTop();
		var timer=setInterval(function(){
			t-=60;
			$(document).scrollTop(t)
			if(t<0){$(document).scrollTop(0);clearInterval(timer);}
		},30);
	});
})