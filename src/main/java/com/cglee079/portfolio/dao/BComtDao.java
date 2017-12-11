package com.cglee079.portfolio.dao;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cglee079.portfolio.model.BComtVo;
import com.cglee079.portfolio.model.BoardVo;

@Repository
public class BComtDao {
	private static final String namespace = "com.cglee079.portfolio.mapper.BComtMapper";
	
	@Autowired
	private SqlSessionTemplate sqlSession;

	public List<BoardVo> list() {
		return sqlSession.selectList(namespace +".list");
	}

	public List<BComtVo> list(int boardSeq, int startRow, int perPgLine) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("boardSeq", boardSeq);
		map.put("startRow", startRow);
		map.put("perPgLine", perPgLine);
		return sqlSession.selectList(namespace +".list", map);
	}

	public int count(int boardSeq) {
		return sqlSession.selectOne(namespace +".count", boardSeq);
	}

	public boolean insert(BComtVo bcomt) {
		return sqlSession.insert(namespace + ".insert", bcomt) == 1;
	}

	public boolean delete(int seq) {
		return sqlSession.delete(namespace + ".delete", seq) == 1;
	}

}
