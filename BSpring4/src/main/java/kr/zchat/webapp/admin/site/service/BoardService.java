package kr.zchat.webapp.admin.site.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import kr.zchat.core.support.model.FileInfo;
import kr.zchat.core.utils.FileManager;
import kr.zchat.webapp.admin.site.dao.BoardDao;
import kr.zchat.webapp.admin.site.dao.FileDao;
import kr.zchat.webapp.admin.site.model.Board;


@Service
public class BoardService{
	
	@Autowired
	private BoardDao boardDao;
		
	@Autowired
	private FileDao fileDao;
	
	/**
	 * 게시판 수
	 * @param board
	 * @return
	 */
	public int selectBoardCount(Board board){
		return  boardDao.selectBoardCount(board);
	}
	
	/**
	 * 게시판 목록
	 * @param board
	 * @return
	 */
	public List<Board> selectBoardList(Board board){
		board.setTotalRow(selectBoardCount(board));
		return  boardDao.selectBoardList(board);
	}

	/**
	 * 게시판 조회
	 * @param usrSrl
	 * @return
	 */
	public Board selectBoard(Integer boardSrl){
		boardDao.updateBoardRead(boardSrl);
		return  boardDao.selectBoard(boardSrl);
	}
	
	/**
	 * 게시판 등록
	 * @param board
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	@Transactional
	@SuppressWarnings("unchecked")
	public void  insertBoard(Board board) throws JsonParseException, JsonMappingException, IOException{
		
		board.setFid(boardDao.selectBoardFid(board.getFlag()));
		board.setLev(0);
		board.setStp(1);
		
		boardDao.insertBoard(board);
		
		//첨부파일 등록
		if(StringUtils.isNotEmpty(board.getFileInfo())) {
			ObjectMapper mapper = new ObjectMapper();
			Map<String,Object> map = mapper.readValue(board.getFileInfo(), Map.class);
			List<Object> fileList = (List<Object>)map.get("insertFile");
			
			for(Object obj : fileList) {
				FileInfo fileInfo = mapper.convertValue(obj, FileInfo.class);
				fileInfo.setBoardSrl(board.getBoardSrl());
				fileDao.insertBoardFile(fileInfo);
			}
		}
		
	}
	
	/**
	 * 게시판 댓글 등록
	 * @param board
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	@Transactional
	@SuppressWarnings("unchecked")
	public void  replyBoard(Board board) throws JsonParseException, JsonMappingException, IOException{
		
		Board replyInfo = boardDao.selectBoardReply(board.getBoardSrl());
		board.setFid(replyInfo.getFid());
		board.setLev(replyInfo.getLev());
		board.setStp(replyInfo.getStp());
		
		boardDao.updateBoardReplyStp(board);
		boardDao.insertBoard(board);
		
		//첨부파일 등록
		if(StringUtils.isNotEmpty(board.getFileInfo())) {
			ObjectMapper mapper = new ObjectMapper();
			Map<String,Object> map = mapper.readValue(board.getFileInfo(), Map.class);
			List<Object> fileList = (List<Object>)map.get("insertFile");
			
			for(Object obj : fileList) {
				FileInfo fileInfo = mapper.convertValue(obj, FileInfo.class);
				fileInfo.setBoardSrl(board.getBoardSrl());
				fileDao.insertBoardFile(fileInfo);
			}
		}
	}

	/**
	 * 게시판 수정
	 * @param board
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	@Transactional
	@SuppressWarnings("unchecked")
	public void  updateBoard(Board board) throws JsonParseException, JsonMappingException, IOException {
		boardDao.updateBoard(board);
		
		
		if(StringUtils.isNotEmpty(board.getFileInfo())) {
			ObjectMapper mapper = new ObjectMapper();
			Map<String,Object> map = mapper.readValue(board.getFileInfo(), Map.class);

			//첨부파일 등록
			List<Object> fileList = (List<Object>)map.get("insertFile");
			for(Object obj : fileList) {
				FileInfo fileInfo = mapper.convertValue(obj, FileInfo.class);
				fileInfo.setBoardSrl(board.getBoardSrl());
				fileDao.insertBoardFile(fileInfo);
			}
			
			//첨부파일 삭제
			List<Object> deleteList = (List<Object>)map.get("deleteFile");
			for(Object boardFileSrl : deleteList) {
				
				FileInfo  fileInfo = fileDao.selectBoardFile((Integer)boardFileSrl);
				FileManager.fileDelete(fileInfo.getFilePath() + "/" + fileInfo.getFileSysNm());
				
				fileDao.deleteBoardFile((Integer)boardFileSrl);
			}
			
		}
	}

	/**
	 * 게시판 삭제
	 * @param board
	 */
	@Transactional
	public void  deleteBoard(Board board) {

		int isDelete = boardDao.deleteBoard(board);
		
		if(isDelete > 0) {
			List<FileInfo>  fileList = fileDao.selectBoardFileList(board.getBoardSrl());
			for(FileInfo file : fileList) {
				FileManager.fileDelete(file.getFilePath() + "/" + file.getFileSysNm());
				fileDao.deleteBoardFile(file.getBoardFileSrl());
			}
		}
		
	}
	
}
