package kr.zchat.webapp.admin.site.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import kr.zchat.core.module.model.FileInfo;
import kr.zchat.core.utils.FileManager;
import kr.zchat.webapp.admin.site.service.FileService;


@Controller
public class FileController{
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private FileService fileService; 
	
	/**
	 * 다중 파일 등록
	 * @param files
	 * @param flag
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/board/{flag}/multFileUpload", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<FileInfo>  fileUpload (@RequestParam("file") List<MultipartFile> file, @PathVariable String flag) throws Exception {
		return fileService.multiFileUpload(file, flag);
	} 
	
	
	/**
	 * 단일 파일 등록
	 * @param files
	 * @param flag
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/board/{flag}/fileUpload", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public FileInfo  fileUpload (@RequestParam("file") MultipartFile file, @PathVariable String flag) throws Exception {
		return fileService.singleFileUpload(file, flag);
	} 

	/**
	 * 파일다운로드	
	 * @param response
	 * @param srl
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value="/fileDownLoad")
	public void  fileDownLoad (HttpServletResponse response, @RequestParam("srl") Integer  srl) throws UnsupportedEncodingException  {
		FileInfo fileInfo = fileService.selectBoardFile(srl);
		if(fileInfo == null) return;
		FileManager.fileDownload(response, fileInfo);
	} 
	
}