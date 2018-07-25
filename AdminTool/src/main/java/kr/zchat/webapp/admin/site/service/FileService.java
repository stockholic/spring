package kr.zchat.webapp.admin.site.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import kr.zchat.core.module.model.FileInfo;
import kr.zchat.core.utils.FileManager;
import kr.zchat.webapp.admin.site.dao.FileDao;


@Service
public class FileService{
	
	@Value("${board.file.path}") 
	private String boardFilePath;
	
	@Autowired
	FileDao fileDao;
	
	public List<FileInfo> multiFileUpload(List<MultipartFile> file, String flag) throws Exception{
		return  FileManager.upload(file, boardFilePath + "/" + flag);
	}

	public FileInfo singleFileUpload(MultipartFile file, String flag) throws Exception{
		return  FileManager.upload(file, boardFilePath + "/" + flag);
	}
	
	public List<FileInfo> selectBoardFileList(Integer boardSrl){ 
		return  fileDao.selectBoardFileList(boardSrl);
	}

	public FileInfo selectBoardFile(Integer boarFiledSrl){ 
		return  fileDao.selectBoardFile(boarFiledSrl);
	}
	
}
