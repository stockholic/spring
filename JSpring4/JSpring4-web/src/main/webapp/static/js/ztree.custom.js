	var zNodes = [
	{
		id : 1
		,pId : 0
		,name : "부모 노드 1"
		,url:"http://www.daum.net/"
		, target:"_blank"
		,open : true
	}, {
		id : 11
		,pId : 1
		,name : "잎이 노드 1-1"
		,url:"/admin/board/list.do"
		, target:"_blank"
	}, {
		id : 12
		,pId : 1
		,name : "잎이 노드 1-2"
		,url:"/ztree/main.do"
		, target:"_blank"
	}, {
		id : 13
		,pId : 1
		,name : "잎이 노드 1-3"
		,url:"http://www.naver.com/"
		, target:"_blank"
	}, {
		id : 2,
		pId : 0,
		name : "부모 노드 2",
		open : true
	}, {
		id : 21,
		pId : 2,
		name : "잎이 노드 2-1"
	}, {
		id : 22,
		pId : 2,
		name : "잎이 노드 2-2"
	}, {
		id : 23,
		pId : 2,
		name : "잎이 노드 2-3"
	}, {
		id : 3,
		pId : 0,
		name : "부모 노드 3",
		open : true
	}, {
		id : 31,
		pId : 3,
		name : "잎이 노드 3-1"
	}, {
		id : 32,
		pId : 3,
		name : "잎이 노드 3-2"
	}, {
		id : 33,
		pId : 3,
		name : "잎이 노드 3-3"
	} ];


(function($){
	var elementId;
	$.fn.makeTree =  function(options) {
		var defaultSet ={
			view : {
				selectedMulti : false		//멀티 select 막기
			},
			edit : {
				enable : false
				
				/* '이름 바꾸기', '삭제' 아이콘 보이기 / 숨기기	*/
				,showRenameBtn : false
				,showRemoveBtn : false
			},
			data : {
				simpleData : {
					enable : true
				}
			},
			callback : {
				//드래그 앤 드롭
				beforeDrag: true,
				beforeDrop: true
			}
		};
		$.extend(defaultSet,options.setting);
		elementId = $(this);
		
		// 트리 작성
		$.fn.zTree.init(elementId,defaultSet, options.data);
		
		//가져오는  노드의 마지막 ID 값 가져오기
		 var max;
			for(var i =0; options.data.length-1 > i ; i++){
				if(options.data[i].id > options.data[i+1].id){
					max = options.data[i].id;
				}else{
					max = options.data[i+1].id;
				}
			}
			zgTree.newCount = max+1;		//addNode시 시작할 아이디값
	};
	
	var zgTree = {
			jsonObj : [],
			newCount : 1,
			rMenu : "",
			
			//우클릭시 메뉴 div  - show
			showRMenu : function(type, x, y) {
				rMenu = $("#rMenu");
				$("#rMenu ul").show();
				if (type=="root") {
					$("#m_del").hide();
				} else {
					$("#m_del").show();
				}
				rMenu.css({"top":y+"px", "left":x+"px", "visibility":"visible"});
			
				$("body").bind("mousedown", this.onBodyMouseDown);
			},
			
			//메뉴 div - hidden 
			hideRMenu: function() {
				if (rMenu) rMenu.css({"visibility": "hidden"});
				$("body").unbind("mousedown", this.onBodyMouseDown);
			},
			
			//메뉴 div  - hidden
			onBodyMouseDown: function (event){
				if (!(event.target.id == "rMenu" || $(event.target).parents("#rMenu").length>0)) {
					rMenu.css({"visibility" : "hidden"});
				}
			},
			
			//노드 추가
			addTreeNode : function() {
				this.hideRMenu();			//메뉴 div  - hidden
				var getNode= zTree.getSelectedNodes()[0];		//선택한 노드 정보 가져오기
				var pId;
				if(!getNode){		//root가 하나도 없을 경우
					pId = null;
				}else{
					pId = getNode.pId;
				}
				zTree.addNodes(zTree.getSelectedNodes()[0], {
						id : (this.newCount),
						pId : pId,
						name : "new node " + (this.newCount++),
						url : ""
					});
				//console.log(zTree.getNodes());
			},
			
			//노드 삭제
			removeTreeNode : function () {
				this.hideRMenu();		//메뉴 div  - hidden
				var nodes = zTree.getSelectedNodes();		//선택한 노드 정보 가져오기
				if (nodes && nodes.length>0) {
					
					//부모노드 삭제
					if (nodes[0].children && nodes[0].children.length > 0) {
						var msg = "이 부모 노드를 삭제시 하위 노드도 삭제 됩니다.";
						if (confirm(msg)==true){
							zTree.removeNode(nodes[0]);		//노드 삭제
						}
					//자식노드 삭제			
					} else {
						zTree.removeNode(nodes[0]);		//노드 삭제
					}
				}
			},
			
			//전체트리에서 Json값  가져오기
			getJsonData : function (data) {
				for ( var obj in data) {
					if (typeof data[obj] == "object") {
						this.setJsonData(data[obj]);
						this.getJsonData(data[obj]);
					}
				}
			},
			
			//필요한 키만 가져오기
			setJsonData : function (obj) {
				var jsonData = {};
				for ( var o in obj) {
					if (o == 'id' || o == 'pId' || o == 'name' || o == 'url' || o == "target") {
						jsonData[o] = obj[o];
					}
				}
				if (jsonData.id != undefined) {
					jsonObj.push(jsonData);
				}
			},
			
			//ajax 호출 - tree  데이터 저장
			treeSave : function(url){
				jsonObj = [];		//초기화
				this.getJsonData(zTree.getNodes());		//전체트리에서 Json값  가져오기
				var jsonStr = JSON.stringify(jsonObj);	//object to String
				console.log(jsonStr);
				$.ajax({
					url : url.url
			        , method : "post"
					, dataType : 'json'
					, data : jsonStr
			//		, processData : true /*querySTring make false*/
					, contentType : "application/json; charset=UTF-8"
					, success : function(data, stat, xhr) {
						alert("success");
					}
				    , error : function(xhr, stat, err) {
				    	alert("error");
				    }
				});
			},
			
			//tree 수정 가능 여부 변경
			setEdit : function(bool){
				zTree.setEditable(bool);
				if(bool){
					zgTree.createMenu();
				}else{
					zgTree.removeMenu();
				}
			},
			
			//우클릭 시 메뉴 div 작성
			createMenu : function(){
				var div = "";
				div += "<div id = 'rMenu'>";
				div += "<ul>";
				div += "<li id = 'm_add' onclick = 'zgTree.addTreeNode();'>Add Node</li>";
				div += "<li id = 'm_del' onclick = 'zgTree.removeTreeNode();'>Delete Node</li>";
				div += "<li id = 'm_manage' onclick = 'manageUrl();'>Manage URL</li>";
				div += "</ul>";
				div += "</div>";
				
				$("body").append(div);
			},
			//우클릭 시 메뉴 div 삭제
			removeMenu : function(){
				$("#rMenu").remove();
			}
		};
	window.zgTree = zgTree;		//전역변수 선언 ========
	
}(jQuery));
