package ar.com.rodrilapenta.ormtest.util;

import java.awt.Image;
import java.lang.reflect.Field;
import java.net.URL;

import javax.swing.ImageIcon;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

public class Util
{
	public static Image getImage(String path, Object o)
	{
		URL imageURL = o.getClass().getResource(path);

		if (imageURL == null) {
			System.err.println("Resource not found: " + path);
			return null;
		} else {
			return (new ImageIcon(imageURL)).getImage();
		}
	}
	
	public static Object getLastRegister(Class<?> clazz, Session session) throws Exception {
		
		Field[] fields = clazz.getDeclaredFields();
		String pkName = null;
		
		for(Field f: fields) {
			if(f.getAnnotation(javax.persistence.Id.class) != null) {
				pkName = f.getName();
			}
		}
		
		if(pkName == null) {
			throw new Exception("No tiene pk");
		}
		else {
			Criteria c = session.createCriteria(clazz).addOrder(Order.desc(pkName)).setFetchSize(1);
			
			return c.list().get(0);
		}
	}
}
