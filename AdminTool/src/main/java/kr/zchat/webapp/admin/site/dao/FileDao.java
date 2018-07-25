package kr.zchat.webapp.admin.site.dao;


import java.util.List;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import kr.zchat.core.module.dao.MultiSqlSessionDaoSupport;
import kr.zchat.core.module.model.FileInfo;

@Repository
public class FileDao extends MultiSqlSessionDaoSupport{	
	
	@Override
	@Autowired
	@Qualifier(value = "sqliteSqlSessionFactory")
	public void setSessionFactory(SqlSessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
	
	public int insertBoardFile(FileInfo fileInfo){
		return  insert("insertBoardFile",fileInfo);
	}

	public List<FileInfo> selectBoardFileList(Integer boardSrl){
		return  selectList("selectBoardFileList",boardSrl);
	}

	public FileInfo selectBoardFile(Integer boarFiledSrl){
		return  selectOne("selectBoardFile",boarFiledSrl);
	}

	public int deleteBoardFile(Integer boarFiledSrl){
		return  delete("deleteBoardFile",boarFiledSrl);
	}
}
