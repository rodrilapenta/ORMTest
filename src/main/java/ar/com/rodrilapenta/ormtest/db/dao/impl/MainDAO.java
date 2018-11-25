package ar.com.rodrilapenta.ormtest.db.dao.impl;

import org.apache.ibatis.session.SqlSessionFactory;
import org.hibernate.SessionFactory;

public class MainDAO {

	private GenericDAOImpl genericDAO;
	public SessionFactory sessionFactory;
	public SqlSessionFactory sqlSessionFactory;

	public MainDAO(SessionFactory sessionFactory, SqlSessionFactory sqlSessionFactory) {
		this.sessionFactory = sessionFactory;
		this.sqlSessionFactory = sqlSessionFactory;
		genericDAO = new GenericDAOImpl(sessionFactory, sqlSessionFactory);
	}

	public GenericDAOImpl getGenericDAO() {
		return genericDAO;
	}
}