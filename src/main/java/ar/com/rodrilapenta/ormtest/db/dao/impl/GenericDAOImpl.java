package ar.com.rodrilapenta.ormtest.db.dao.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class GenericDAOImpl {
	private SessionFactory sessionFactory;
	private SqlSessionFactory sqlSessionFactory;

	public GenericDAOImpl(SessionFactory sessionFactory, SqlSessionFactory sqlSessionFactory) {
		this.sessionFactory = sessionFactory;
		this.sqlSessionFactory = sqlSessionFactory;
	}

	public void insertElements(String tabla, List<String> registros, String file, List<String> emails) {
		Session s = sessionFactory.openSession();
		//TODO implementación con MyBatis
		List<String> fallidos = new ArrayList<String>();
		for (String r : registros) {
			try {
				s.beginTransaction();
				s.createSQLQuery("insert into " + tabla + " VALUES ( " + r + ")").executeUpdate();
				s.getTransaction().commit();
			} catch (Exception e) {
				// e.printStackTrace();
				fallidos.add(r);
			}
		}

		if (!fallidos.isEmpty()) {
			int pos = file.lastIndexOf("\\");
			SimpleDateFormat df = new SimpleDateFormat("dd-MM-YYYY hh.mm.ss");
			String fileName = file.substring(0, pos + 1) + tabla + " (" + df.format(new Date()) + ") - fallidos.log";
			BufferedWriter writer;

			try {
				writer = new BufferedWriter(new FileWriter(fileName));
				for (String string : fallidos) {
					writer.write(string);
					writer.newLine();
				}
				writer.close();
				List<File> list = new ArrayList<File>();
				File f = new File(fileName);
				list.add(f);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		s.close();

	}
	
	public void genericQuery() {
		if(sessionFactory != null) {
			Session s = sessionFactory.openSession();
			
			Query query = s.createQuery("from Usuario where id = :id ");
			query.setParameter("id", 1);

			query.uniqueResult();
			
			s.close();
		}
		else {
			SqlSession session = sqlSessionFactory.openSession();
			
		}
	}
}