
	var farmid = 2;
	var table;
	var ALL_FARMS = [];
	$(document).ready(function() {
		// 公共函数确保user有意义
		var user =sessionStorage.getItem("userinfo");
		user = JSON.parse(user);

		var url = "../api/farm/get?id="+farmid;
		var url_farmlist = "../api/farm/getbyuserid?uid="+user.uid;

        // farmlist start
    	$.ajax({
			url: url_farmlist,
			type: "POST",
			dataType:'json',
			success:function(farmlist){
				ALL_FARMS = farmlist;
				var data ={};
				if(farmlist && farmlist.length > 0){
					for (var i = 0; i< farmlist.length; i++){
				        //套餐列表模板 内容追加
				        $('#template-farm').tmpl(farmlist[i]).appendTo('#farmlist');
					}
					data = farmlist[0];
					$( "#farmlist" ).val(data.id);
					// 切换和farm属性有关的元素
					 changeFarm(data);

				}else{
					window.location.href="open.html";
				}
				
			},
			error:function(XMLHttpRequest, textStatus, errorThrown )
            {     
                $('div.modal-body p').text(XMLHttpRequest.responseText);
				alert(XMLHttpRequest.responseText);
			}
	});

  	//公告
  	   $.ajax(
                {
                    "type": "post",
                    "contentType": "application/json; charset=utf-8",
                    "url" : "../api/master/getnotice",
                    "success": function(master)
                    { 
                        $('#txt-notice').text(master.value);
                       return;
                    },"error": function(XMLHttpRequest, textStatus, errorThrown )
                    {     
                        alert(XMLHttpRequest.responseText);
                        return;
                     }
        });


	} );


	function action(action){
		var farmid = $("#farmlist").val();
		var url = "../api/farmaction/action?farmid="+farmid + "&action="+action;
		$.ajax({
			url: url,
			//data:{Full:"fu"},
			type: "POST",
			dataType:'json',
			success:function(data){
				// 切换和farm属性有关的元素
				 changeFarm(data);
			
			},
			error:function(XMLHttpRequest, textStatus, errorThrown )
            {     
                $('div.modal-body p').text(XMLHttpRequest.responseText);
				alert(XMLHttpRequest.responseText);
			}
	});
}

function level(parm){
	var level ="progress-bar progress-bar-info";
	if(parm<40){
		level = "progress-bar progress-bar-danger";
	}else if(parm<70){
		level = "progress-bar progress-bar-warning";
	}else if(parm>70){
		level = "progress-bar progress-bar-success";
	}
	return level;
}
//按钮操作
//浇水
	$('#btn-water').click(function(){
		action('WATER');
		$('div.water-hu img').show(500).animate({left:"30%",top:"30%"},300,function(){
				setTimeout(function(){
					$('div.water-hu img').css({left:"40%",top:"-30%"}).hide(500);
				},3000);
		
		});
		
	});
	//松土
	$('#btn-land').click(function(){
		action('LAND');
		$('.plant').append('<img src="../assets/img/farm/land.gif" alt=""/>');
		setTimeout(function(){
					$('.plant img').remove();
				},3000);
	});
	//施肥
	$('#btn-feed').click(function(){
		action('FEED');
		$('div.shi-feed img').show(500).animate({left:"15%",top:"20%"},300,function(){
				setTimeout(function(){
					$('div.shi-feed img').css({left:"40%",top:"-30%"}).hide(500);
				},3000);
		
		});
		
	});
	//除虫
	$('#btn-bug').click(function(){
		action('BUG');
		$('div.pen-bug img').show(500).animate({left:"10%",top:"20%"},300,function(){
				setTimeout(function(){
					$('div.pen-bug img').css({left:"70%",top:"-30%"}).hide(500);
				},3000);
		
		});
		
	});
	//收获
	$('#btn-harvest').click(function(){
		var farmid = $("#farmlist").val();
		var url = "../api/farm/harvest?farmid="+farmid;
		$.ajax({
				url: url,
				//data:{Full:"fu"},
				type: "POST",
				dataType:'json',
				success:function(data){
					
					var real = "";
					//产量预期获取数据
					for(var i=0;i<data.pkage.pkage_Product.length;i++){
						real +=data.pkage.pkage_Product[i].product.name+':'+data.pkage.pkage_Product[i].real+'\r\n';
	                }
					alert("农田产品收获入库(KG):\r\n"+ real);
					// 切换和farm属性有关的元素
					changeFarm(data);


				},
				error:function(XMLHttpRequest, textStatus, errorThrown )
                {     
					alert(XMLHttpRequest.responseText);
				}
		});
		
	});
	$( "#farmlist" ).change(function() {
		  var data = {};
		  for(var i=0;i<ALL_FARMS.length;i++){
			  if(ALL_FARMS[i].id ==$(this).val() ){
					data = ALL_FARMS[i];
			  }
			  
		  }
			// 切换和farm属性有关的元素
		  changeFarm(data);
		});
	// 切换和farm属性有关的元素
	function changeFarm(data){
		$("#progress-water").css("width",data.shirundu+"%").html('<small>'+data.shirundu+'%</small>');
		$("#progress-land").css("width",data.zacaodu+"%").html('<small>'+data.zacaodu+'%</small>');
		$("#progress-feed").css("width",data.feiwodu+"%").html('<small>'+data.feiwodu+'%</small>');
		$("#progress-bug").css("width",data.haichongdu+"%").html('<small>'+data.haichongdu+'%</small>');

		$("#progress-water").attr("class",level(data.shirundu));
		$("#progress-land").attr("class",level(data.zacaodu));
		$("#progress-feed").attr("class",level(data.feiwodu));
		$("#progress-bug").attr("class",level(data.haichongdu));
		
		//收获按钮判断
		if(data.state=="HARVEST"){
			$('#btn-harvest').show();
		}else{
			$('#btn-harvest').hide();
		}
		//操作按钮判断
		if(data.state=="CLOSED"){
			$('#oper :button').hide();
		}else{
			$('#oper :button').show();
		}
		$('#grow-s').html(data.state_cn);
		var len=data.pkage.pkage_Product.length;
		//产量预期获取数据
		//var theader = $('#table tr:first');
		$('#table').html("");
		for(var i=0;i<len;i++){
           $('#table').append('<tr><td>'+data.pkage.pkage_Product[i].product.name+'</td><td>'+data.pkage.pkage_Product[i].min+'</td><td>'+data.pkage.pkage_Product[i].max+'</td><td>'+data.reallist[i]+'</td><tr>');
        }
        //农场不同时期植物
		differentTimes(data);
	}
	function differentTimes(data){
		if(data.state_cn=='播种期' || data.state_cn=='已关闭'){
			$('div.plant').css('background','');
			return;
		}
		if(data.state_cn=='萌芽期'){
			$('div.plant').css('background','url(./assets/img/farm/sproute.png) no-repeat center center');
			return;
		}
		if(data.state_cn=='生长期'){
			$('div.plant').css('background','url(./assets/img/farm/seeding.png) no-repeat center center');
			return;
		}
		if(data.state_cn=='成熟期'){
			$('div.plant').css('background','url(./assets/img/farm/flower.png) no-repeat center center');
			return;
		}
		if(data.state_cn=='收获期'){
			$('div.plant').css('background','url(./assets/img/farm/fruit.png) no-repeat center center');
			return;
		}

	}