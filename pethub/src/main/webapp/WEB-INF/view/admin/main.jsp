<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<style>
div.smoothie-chart-tooltip {
  background: #FFFBAC;
  padding: 5px;
  margin-top: 20px;
  color: #000;
  font-size: 12px;
  pointer-events: none;
  border-radius: 5px;
}
.systemInfo{
	height:20px;
	font-weight: bold;
	font-size: 16px;
	color: #3A7099;
}
.systemInfo span{
	display:inline-block;
}

</style>

<script type="text/javascript" src="/static/js/smoothie.js"></script>
<script type="text/javascript" src="/static/js/jquery.knob.min.js"></script>

<!-- <section class="content-header">
  <h1>
    <small></small>  
  </h1>
  <ol class="breadcrumb">
    <li><a href="#"> Level</a></li>
    <li class="active">Here</li>
  </ol>
</section> -->


<section class="content container-fluid">


<div class="row">
  <div class="col-xs-12">
    <div class="box box-solid">
      <div class="box-header">
        <i class="fa fa-bar-chart-o"></i>

        <h3 class="box-title">시스템 현황 : <span id="cpuName"></span></h3>

        <div class="box-tools pull-right">
          <button type="button" class="btn btn-default btn-sm" data-widget="collapse"><i class="fa fa-minus"></i>
          </button>
          <!-- <button type="button" class="btn btn-default btn-sm" data-widget="remove"><i class="fa fa-times"></i>
          </button> -->
        </div>
      </div>
      <!-- /.box-header -->
      <div class="box-body" style="">
        <div class="row">
        
          <div class="col-xs-6 col-md-4 text-center">
            <div style="display:inline;width:110px;height:110px;">
            	<div >Memory</div>
            	<input type="text" class="knob" id="memKnob" value="0" data-thickness="0.2" data-width="110" data-height="110" data-fgcolor="#3c8dbc" data-readonly="true" style="margin-top:none;width: 0px">
            	<div id="memUsed"></div>
            </div>
          </div>
          
          <div class="col-xs-6 col-md-4 text-center">
          	<div style="display:inline;width:130px;height:130px;">
          		<div>CPU</div>
          		<input type="text" class="knob" id="cpuKnob" value="0" data-thickness="0.2" data-width="130" data-height="130" data-fgcolor="#F39C12" data-readonly="true" style="border:none;width: 0px">
          	</div>
          </div>
          
          <div class="col-xs-6 col-md-4 text-center">
         	 <div style="display:inline;width:110px;height:110px;">
         	 <div>Packet</div>
            <input type="text" class="knob" id="packetKnob" value="0" data-thickness="0.1" data-width="110" data-height="110"  data-max="10000" data-fgcolor="#00a65a" data-readonly="true"  style="border:none;width: 0px">
            <div id="packetInfo"></div>
            </div>
          </div>
          
        </div>
      </div>
    </div>
  </div>
</div>


<div class="row">

	<div class="col-md-6">
		<div class="box box-solid">
            <div class="box-body">
            	<div class="systemInfo text-center">CPU : <span id="cpu"></span>%</div>
            	<div>
        			<canvas id="cupCanvas" style="width:100%;height:100px"></canvas>
        		</div>
            </div>
          </div>
	</div>
	
	<div class="col-md-6">
		<div class="box box-solid">
            <div class="box-body">
            	<div class="systemInfo text-center"><span id="netName"></span> Speed : <span id="netSpeed"></span></div>
            	<div>
        			<canvas id="netCanvas" style="width:100%;height:100px"></canvas>
        		</div>
            </div>
          </div>
	</div>
		
</div>

<div class="row">

	<div class="col-md-6">
		<div class="box box-solid">
            <div class="box-body">
            	<div class="systemInfo text-center">Memory : <span id="mem"></span>%</div>
            	<div>
        			<canvas id="memCanvas" style="width:100%;height:100px"></canvas>
        		</div>
            </div>
          </div>
	</div>
	
	<div class="col-md-6">
		<div class="box box-solid">
            <div class="box-body">
            	<div style="width:100%;height:125px">
            	
            	<div class="col-md-12 diskWrap">
                  <p class="systemInfo text-center">
                    <strong>Disk Used</strong>
                  </p>

        		</div>
            </div>
          </div>
		</div>
	</div>
	
</div>


<div id="progressTpl" style="display: none;">
	<div class="progress-group">
	  <span class="progress-text diskName"></span>
	  <span class="progress-number diskUsed"></span>
	  <div class="progress sm"><div class="progress-bar diskProgress" style="background:#26A0DA;"></div></div>
	</div>
</div>



<div class="row">
	<div class="col-md-6">
	
		
		<div class="box box-primary">
            <div class="box-header with-border">
              <h3 class="box-title">공지사항</h3>

              <div class="box-tools pull-right">
               <!--  <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
                </button>
                <button type="button" class="btn btn-box-tool" data-widget="remove"><i class="fa fa-times"></i></button> -->
              </div>
            </div>
            <!-- /.box-header -->
            <div class="box-body">
              <ul class="products-list product-list-in-box">
              
                <li class="item">
                  <div>
                    	<a href="javascript:void(0)">PlayStation 4</a>
                      	<span class="label label-success pull-right">new</span>
                        <span class="product-description">
                          PlayStation 4 500GB Console (PS4)
                        </span>
                  </div>
                </li>
                <li class="item">
                  <div>
                    	<a href="javascript:void(0)">PlayStation 4</a>
                      	<span class="label label-success pull-right">new</span>
                        <span class="product-description">
                          PlayStation 4 500GB Console (PS4)
                        </span>
                  </div>
                </li>
                
              </ul>
            </div>
            
            <div class="box-footer text-center">
              <a href="javascript:void(0)" class="uppercase">더보기 ..</a>
            </div>
          </div>
		
	</div>
	
	<div class="col-md-6">
	
	</div>
	
</div>


</section>

<script>

var cupSeries = initChart('cupCanvas', 0, 100);
var memSeries = initChart('memCanvas', 0, 100);
var netSeries = initChart('netCanvas');

var ws = null;
var init = true;
var oldRecvPacket = 0;
var oldSentPacket = 0;
function connect() {
 	var host = "ws://"+window.location.hostname;
	var port = window.location.port
	ws = new WebSocket(host + ":" + port+ "/system"); 
	
	ws.onmessage = function(message){
		var data = JSON.parse(message.data);
//		console.log( data.disk[0].mount)
		//---------------------------------------CUP Info
		var cpuData = (data.cpu.load * 100).toFixed(2);
		$("#cpuName").text(data.cpu.name);
		$("#cpuKnob").val(Math.round(cpuData)).trigger('change');
		$("#cpu").text(cpuData);
		cupSeries.append(new Date().getTime(), cpuData);
		
		//--------------------------------------- Memory Info
		var memoryUse = Math.round(((data.memory.total - data.memory.available) / data.memory.total)*100 );
		$("#mem").text(memoryUse);
		$("#memKnob").val(memoryUse).trigger('change');
		$("#memUsed").text( com.formatBytes( data.memory.total - data.memory.available , 1) +" / "+ com.formatBytes( data.memory.total ),1 );
		memSeries.append(new Date().getTime(), memoryUse);
		
		//--------------------------------------- Packet Info
		var n = 0;
		var recvPacket = data.net[n].recvPacket - oldRecvPacket;
		var sentPacket = data.net[n].sentPacket - oldSentPacket;
		if(init == false){
			$("#packetKnob").val(Math.round(recvPacket+sentPacket)).trigger('change');
			$("#packetInfo").text( "R : " + com.formatNumber(recvPacket) +" / S : "+ com.formatNumber(sentPacket)  );
			netSeries.append(new Date().getTime(), recvPacket+sentPacket);
		}
		$("#netName").text(data.net[n].name); 
		$("#netSpeed").text(com.formatBytes(data.net[n].speed)); 
		
		oldRecvPacket = data.net[n].recvPacket;
		oldSentPacket = data.net[n].sentPacket;
		
		//--------------------------------------- Disk Info
		for( i = 0; i < data.disk.length; i++ ){
			if(init == true){
				$("#progressTpl > .progress-group").clone().appendTo($(".diskWrap"));
			}else{
				var diskUsed = data.disk[i].total - data.disk[i].usable;
				var usedRate = Math.round((diskUsed / data.disk[i].total) * 100);
				$(".diskWrap .diskName").eq(i).text(data.disk[i].mount + " " + com.formatBytes(data.disk[i].usable) + " 가능"); 
				$(".diskWrap .diskUsed").eq(i).text( com.formatBytes(diskUsed) + " / " + com.formatBytes(data.disk[i].total) + " (" + (data.disk[i].total > 0 ? usedRate : 0) + "%)" ); 
				if(data.disk[i].total > 0)	$(".diskWrap .diskProgress").eq(i).css("width", usedRate + "%" );
			}
		}
		
		init = false;
	}
}

function initChart(canvasId, min, max){
	var chart=  new SmoothieChart({
		minValue : min,
		maxValue : max,
		millisPerPixel : 50,			// 속도
		grid:{
			fillStyle:'#ffffff',
			strokeStyle:'rgba(224,224,224,0.61)',
			sharpLines:true,
			borderVisible:false
		},
		labels:{fillStyle:'#454545',fontSize:14, precision:0},		//precision Max,min 포멧
		//tooltip:true,
		responsive : true
	});
	
	var series = new TimeSeries();
	chart.addTimeSeries(series, {lineWidth:1,strokeStyle:'#3E8DBB',fillStyle:'#B4D8F3'});
	chart.streamTo(document.getElementById(canvasId), 1000);
	
	
	return series;
}

function disconnect() {
    if (ws != null)  ws.close();
    console.log("Disconnected");
}


$( document ).ready(function() {
	
	connect();

	$("#memKnob").knob({
	  'format' : function (value) {
	     return value + '%';
	  }
	});
	$("#cpuKnob").knob({
	  'format' : function (value) {
	     return value + '%';
	  }
	});
	$("#packetKnob").knob({
	  'format' : function (value) {
	    return com.formatNumber(value);
	  }
	});
	
});


</script>
