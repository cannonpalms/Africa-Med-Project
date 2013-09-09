package med.africa.hibernate;

import med.africa.patient.Allergy;
import med.africa.patient.Patient;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistryBuilder;

/**
 * @author Cannon
 * 
 * Provides access to the singleton database session factory. 
 * <br>
 * <br>
 * <br>
 * To insert data (example usage):
<br>
<br>
	<code>
		Session session = HibernateUtil.getSessionFactory().openSession(); <br>
		session.beginTransaction(); <br>
		session.save(foo); <br>
		session.getTransaction().commit(); <br>
		session.close();<br>
	</code>
<br>
<br>
 * To query data:
<br>
<br>
	<code>
		Session session = HibernateUtil.getSessionFactory().openSession(); <br>
		Query query = session.createQuery(HQLquery); <br>
		session.close(); <br>
		List list = query.list(); //query results <br>
	</code>
<br>
 *
 */
public class HibernateUtil {

	private static final SessionFactory SESSION_FACTORY;
	
	private static final String CONFIG_FILE_PATH = "med/africa/hibernate/config/hibernate.cfg.xml";
	
	static {
		Configuration conf = new Configuration()
						.configure(CONFIG_FILE_PATH)
						.addPackage("med.africa.patient")
						.addAnnotatedClass(Patient.class)
						.addAnnotatedClass(Allergy.class);
		
		SESSION_FACTORY = conf.buildSessionFactory(
								new ServiceRegistryBuilder()
							.applySettings(conf.getProperties())
							.buildServiceRegistry());
	}
	
	public static SessionFactory getSessionFactory() {
		return SESSION_FACTORY;
	}
	
	public static Session openSession() {
		return SESSION_FACTORY.openSession();
	}
}
