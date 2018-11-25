package ar.com.rodrilapenta.ormtest.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import ar.com.rodrilapenta.ormtest.db.dao.impl.MainDAO;
import java.io.Reader;

public class MainService {
	private SessionFactory sessionFactory;
	private SqlSessionFactory sqlSessionFactory;
	private MainDAO dao;

	public MainService(Boolean useHibernate) {
		if (useHibernate) {
			File config = new File("mysql.txt");
			Configuration configuration = new Configuration();
			if (config != null) {
				InputStream file;
				try {
					file = new FileInputStream(config);
					Properties p = new Properties();
					p.load(file);

					/*
					 * for(Entry<Object, Object> e: p.entrySet()) {
					 * configuration.setProperty(e.getKey().toString(), e.getValue().toString()); }
					 */

					configuration.setProperty("hibernate.connection.url", p.getProperty("base"));
					configuration.setProperty("hibernate.connection.username", p.getProperty("usuario"));
					configuration.setProperty("hibernate.connection.password", p.getProperty("password"));
				}
				catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			configuration.configure();
			StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
					.applySettings(configuration.getProperties());
			sessionFactory = configuration.buildSessionFactory(builder.build());
		} else {
			File config = new File("mysql.txt");
			if (config != null) {
				InputStream file;
				try {
					file = new FileInputStream(config);
					Properties p = new Properties();
					p.load(file);

					Reader reader = Resources.getResourceAsReader("mybatis.cfg.xml");

					sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader, p);

				}
				catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		dao = new MainDAO(sessionFactory, sqlSessionFactory);
	}

	public MainDAO getDao() {
		return dao;
	}
}
