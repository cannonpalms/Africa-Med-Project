package med.africa.hibernate.demo;

import med.africa.hibernate.HibernateUtil;

import org.hibernate.Query;
import org.hibernate.Session;

public class Demo {

	public static void main(String[] args) {
		for (int i = 0; i < 1000; i++) {
			String username = randomString();
			String password = randomString();
			addUserToDatabase(username, password);
			User user = searchForUser(username);
			System.out.println(user);
		}
	}
	
	private static void addUserToDatabase(String username, String password) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		session.beginTransaction();
		User user = new User(username, password);
		session.save(user);
		session.getTransaction().commit();
		session.close();
	}
	
	private static User searchForUser(String username) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		String hql = "from User as user where user.username = '" + username + "'";
		Query query = session.createQuery(hql);
	
		User result = (User) query.uniqueResult(); //only one result from this query
		
		session.close();
		return result;
	}
	
	private static String randomString() {
		byte[] chars = new byte[10];
		
		for (int i = 0; i < 10; i++) {
			chars[i] = (byte) (Math.random() * 26 + 97); //random a-z byte code
		}
		
		return new String(chars);
	}
}
