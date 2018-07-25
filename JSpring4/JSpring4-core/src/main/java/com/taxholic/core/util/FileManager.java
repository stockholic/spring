package com.taxholic.core.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.taxholic.core.web.dto.FileInfo;

public class FileManager {

	/**
	 * 파일업로드
	 * @param files
	 * @param filePath
	 * @return
	 * @throws Exception
	 */
	public static List<FileInfo> upload(List<MultipartFile> files, String filePath) throws Exception{

		List<FileInfo> list = new ArrayList<FileInfo>();

		if (null != files && files.size() > 0) {

			File dir = new File(filePath);
			if(!dir.exists())	dir.mkdirs();

			for (MultipartFile multipartFile : files) {

				if(!multipartFile.isEmpty()){
					String userFilenm = multipartFile.getOriginalFilename();

					String fileExt = userFilenm.substring(userFilenm.lastIndexOf(".") + 1,userFilenm.length()).toLowerCase();
					if(fileExt.equals("jsp")) fileExt = "txt";
					String systemFilenm = userFilenm + "." + fileExt;
					long fileSize = multipartFile.getSize();

					File f = new File(filePath + "/" + systemFilenm);
					if(f.exists()) f.delete();

					multipartFile.transferTo(f);

					FileInfo dto = new FileInfo();
					dto.setFilePath(filePath);
					dto.setSystemFilenm(systemFilenm);
					dto.setUserFilenm(userFilenm);
					dto.setFileSize(Long.toString(fileSize));

					list.add(dto);

				}
			}

		}

		return list;
	}

	/**
	 * 파일업로드
	 * @param mf
	 * @param filePath
	 * @return
	 * @throws Exception
	 */
	public static FileInfo upload(MultipartFile mf, String filePath) throws Exception{

		FileInfo result = new FileInfo();

		if (null != mf) {
			File dir = new File(filePath);
			if(!dir.exists())	dir.mkdirs();

			if(!mf.isEmpty()){
				String userFilenm = mf.getOriginalFilename();

				String fileExt = userFilenm.substring(userFilenm.lastIndexOf(".") + 1,userFilenm.length()).toLowerCase();
				if(fileExt.equals("jsp")) fileExt = "txt";
				String systemFilenm = userFilenm;
//				String systemFilenm = userFilenm + "." + fileExt;
				long fileSize = mf.getSize();

				File f = new File(filePath + "/" + systemFilenm);
				if(f.exists()) f.delete();

				mf.transferTo(f);

				result.setFilePath(filePath);
				result.setSystemFilenm(systemFilenm);
				result.setUserFilenm(userFilenm);
				result.setFileSize(Long.toString(fileSize));
			}
		}
		return result;
	}

	
	/**
	 * 파일 다운로드
	 * @param request
	 * @param response
	 * @param vo
	 * @throws UnsupportedEncodingException
	 */
	public static void fileDownload(HttpServletRequest request, HttpServletResponse response, FileInfo vo) throws UnsupportedEncodingException {
		
		if(vo == null) return;
		if(vo.getFilePath().indexOf("..") != -1) return;
		if(vo.getSystemFilenm().indexOf("..") != -1) return;
		if(vo.getUserFilenm().indexOf("..") != -1) return;
		
		String filePath = vo.getFilePath();
		String systemFilenm = vo.getSystemFilenm();
		String fileNm =  URLEncoder.encode(vo.getUserFilenm(),"UTF-8");
		
		try{ 
			File f =  new File(filePath,systemFilenm); 
			
	        response.setContentType("application/octet-stream");
	        response.setHeader("Content-Disposition", "attachment; filename=" +  fileNm);
	        response.setHeader("Content-Length", "" + f.length() );
	        
	        InputStream in = new FileInputStream(f);
	        OutputStream out =  response.getOutputStream();        
	        
	        BufferedInputStream bin = null;
	        BufferedOutputStream bos = null; 
	        
	        try {
	            bin = new BufferedInputStream( in );
	            bos = new BufferedOutputStream( out ); 
	        
	            byte[] buf = new byte[2048]; //buffer size 2K.
	            int read = 0;
	            while ((read = bin.read(buf)) != -1) {
	                bos.write(buf,0,read);
	            }
	        } finally {
	        	try {if(bos != null) bos.close();} catch(Exception e){};
	        	try {if(bin != null) bin.close();} catch(Exception e){};
	        }        

		}catch(Exception e){
			e.getStackTrace();
		}
    	
		
	}

}
