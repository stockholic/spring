 <%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
	<link rel="stylesheet" href="/static/assets/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
	
	<script type="text/javascript" src="/static/assets/ztree/js/jquery.ztree.core-3.5.js"></script>
	<script type="text/javascript" src="/static/assets/ztree/js/jquery.ztree.exedit-3.5.js"></script>
	<script type="text/javascript" src="/static/js/ztree.custom.js"></script>
	
	<script type="text/javascript">
	var setting ={
			callback : {
				//==============우클릭================
				onRightClick:  function (event, treeId, treeNode) {
					if(!edit){	//tree 수정 가능
						if (!treeNode && event.target.tagName.toLowerCase() != "button" && $(event.target).parents("a").length == 0) {
							zTree.cancelSelectedNode();
							zgTree.showRMenu("root", event.clientX, event.clientY);
						} else if (treeNode && !treeNode.noR) {
							zTree.selectNode(treeNode);
							zgTree.showRMenu("node", event.clientX, event.clientY);
						}
					}
				}
			}
		};
	
		var selectedNode;
		function manageUrl(){
			zgTree.hideRMenu();
			selectedNode = zTree.getSelectedNodes()[0];
			$("#lab").text(selectedNode.name); 
			$("#inputUrl").val(selectedNode.url);
			$("#inputTarget").val(selectedNode.target);
		}
		
		function setUrl(){
			zTree.getSelectedNodes()[0].url = $("#inputUrl").val();
			zTree.getSelectedNodes()[0]. target =$("#inputTarget").val();
			selectedNode = "";
		}
		
		//======================================save========================================
		function save() {
			var url = "/zTree/saveTree";
			zgTree.treeSave({
				url : url
			});
		}
		
		var edit = false ;
		function  editAble(){
			zgTree.setEdit(edit);		//tree 수정 가능 여부 변경
			$("#editAble").val("편집모드 : "+edit);
			if(edit){
				edit = false;
				$(".right").show();
			}else{
				edit = true;
				$(".right").hide();
			}
		}
		
		var zTree;
		$(document).ready(function() {
			$("#treeDemo").makeTree({
				setting : setting,
				data : zNodes
			});
			
			zTree = $.fn.zTree.getZTreeObj("treeDemo");		//우클릭과 setEditable에 쓸 obj
			editAble();		//편집모드
		});
		
	</script>
<style type="text/css">
div#rMenu {position:absolute; visibility:hidden; top:0; background-color: #555;text-align: left;padding: 2px;}
div#rMenu ul li{ margin: 1px 0; padding: 0 5px; cursor: pointer; list-style: none outside none; background-color: #DFDFDF; }
div,  dd, ul, li { margin: 0;padding: 0;border: 0;outline: 0;font-weight: inherit;font-style: inherit;font-size: 100%;font-family: inherit;vertical-align: baseline;}
</style>

 <div class="x_title">
    <h2>zTree</h2>
 	 <div class="clearfix"></div>
</div>

	<div class="left" style=" display: inline-table;width:300px">
		<input type="button" class="btn btn-default" id="editAble" value="편집모드 : false" onclick="editAble();"/>
		<div>
			<ul id="treeDemo" class="ztree"></ul>
		</div>
		<input type="button" class="btn btn-default" id="submit" value="저장" onclick="save()"/>
	</div>
	
	<div>
		<div>
			<table class="table" style="width:200px">
				<tr>
					<td>node </td>
					<td><div id="lab"></div></td>
				</tr>
				<tr>
					<td>url</td>
					<td><input type="text" id="inputUrl"></td>
				</tr>
				<tr>
					<td>target</td>
					<td><input type="text" id="inputTarget"></td>
				</tr>
				<tr>
					<td colspan="2"><input type="button" class="btn btn-default" id="submit2" value="Node 수정" onclick="setUrl()"/></td>
				</tr>
			</table>
		</div>
	</div>
