package com.pasudo.database;

import java.util.List;

public class MysqlLocalConnector implements ConnectionMaker{
	@Override
	public void insertDatabase(List<String[]> allRowsData) {
		// TODO Auto-generated method stub
	}

	@Override
	public void executeInsertQuery(String DOC_SEQ, String TITLE, String REG_DT) {
		// TODO Auto-generated method stub
	}

	@Override
	public List<String[]> selectDatabase(String sortCase, Integer order) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String[]> executeSelectQuery(String paramQuery) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String[]> resultSetProcess(String query) {
		// TODO Auto-generated method stub
		return null;
	}

}
