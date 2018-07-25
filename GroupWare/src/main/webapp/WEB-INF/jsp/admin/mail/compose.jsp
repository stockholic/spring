 <%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>

<div class="row">
	<div class="col-md-12">
	
	<div class="mail-env">
	
	<!-- Mail Body -->
	<div class="mail-body">
		
		<div class="mail-header">
			<!-- title -->
			<div class="mail-title">
				메일작성 <i class="entypo-pencil"></i>
			</div>
			
			<!-- links -->
			<div class="mail-links">
			
				<a href="#" class="btn btn-default">
					<i class="entypo-cancel"></i>
				</a>
			
				<a href="#" class="btn btn-default btn-icon">
					Draft
					<i class="entypo-tag"></i>
				</a>
				
				<a class="btn btn-success btn-icon">
					Send
					<i class="entypo-mail"></i>
				</a>
				
			</div>
		</div>
		
		
		<div class="mail-compose">
		
			<form method="post" role="form">
				
				<div class="form-group">
					<label for="to">To:</label>
					<input type="text" class="form-control" id="to" tabindex="1" />
					
					<div class="field-options">
						<a href="javascript:;" onclick="$(this).hide(); $('#cc').parent().removeClass('hidden'); $('#cc').focus();">CC</a>
						<a href="javascript:;" onclick="$(this).hide(); $('#bcc').parent().removeClass('hidden'); $('#bcc').focus();">BCC</a>
					</div>
				</div>
				
				<div class="form-group hidden">
					<label for="cc">CC:</label>
					<input type="text" class="form-control" id="cc" tabindex="2" />
				</div>
				
				<div class="form-group hidden">
					<label for="bcc">BCC:</label>
					<input type="text" class="form-control" id="bcc" tabindex="2" />
				</div>
				
				<div class="form-group">
					<label for="subject">Subject:</label>
					<input type="text" class="form-control" id="subject" tabindex="1" />
				</div>
				
				
				<div class="compose-message-editor">
					<textarea class="form-control wysihtml5" data-stylesheet-url="/static/assets/css/wysihtml5-color.css" name="sample_wysiwyg" id="sample_wysiwyg"></textarea>
				</div>
				
			</form>
		
		</div>
		
	</div>
	
	
	</div>
</div>
</div>


