package kr.zchat.webapp.admin.site.dao;


import java.util.List;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import kr.zchat.core.support.dao.MultiSqlSessionDaoSupport;
import kr.zchat.webapp.admin.site.model.Board;

@Repository
public class BoardDao extends MultiSqlSessionDaoSupport{	
	
	@Override
	@Autowired
	@Qualifier(value = "sqliteSqlSessionFactory")
	public void setSessionFactory(SqlSessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
	
	public int selectBoardCount(Board board){
		return getInt("selectBoardCount",board);
	}

	public List<Board> selectBoardList(Board board){
		return  selectList("selectBoardList",board);
	}

	public Board selectBoard(Integer boardSrl){
		return selectOne("selectBoard",boardSrl);
	}
	
	public int selectBoardFid(String flag){
		return getInt("selectBoardFid",flag);
	}

	public int insertBoard(Board board){
		return  insert("insertBoard",board);
	}

	public int updateBoard(Board board){
		return  update("updateBoard",board);
	}

	public int updateBoardRead(Integer boardSrl){
		return  update("updateBoardRead",boardSrl);
	}
	
	public int deleteBoard(Board board){
		return  delete("deleteBoard",board);
	}
	
	public Board selectBoardReply(Integer boardSrl){
		return selectOne("selectBoardReply",boardSrl);
	}
	
	public int updateBoardReplyStp(Board board){
		return  update("updateBoardReplyStp",board);
	}
	
	
}
