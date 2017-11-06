package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
	Session session;
	Statement st;
	
	public HibernateUtil() {
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		session = sessionFactory.openSession();
		try {
			Class.forName("org.hsqldb.jdbcDriver");
			System.out.println("Driver Loaded.");
			String url = "jdbc:hsqldb:hsql://localhost/mabdd;shutdown=true";
			
			Connection conn = DriverManager.getConnection(url, "sa","");
			System.out.println("Got Connection.");
			st = conn.createStatement();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public Session getSession() {
		return session;
	}
	
	public void executeUpdate(String update) {
		try {
			st.executeUpdate(update);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Statement getSt() {
		return st;
	}
	
	
	
}
