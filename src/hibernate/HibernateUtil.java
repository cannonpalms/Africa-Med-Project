package hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistryBuilder;

/**
 * @author Cannon
 * 
 * Provides access to the singleton database session factory. 
 * <br>
 * To insert data:
<br>
	<code>
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		session.save(foo);
		session.getTransaction().commit();
	</code>
<br>
<br>
 * To query data:
<br>
	<code>
		Session session = HibernateUtil.getSessionFactory().openSession();
		Query query = session.createQuery(HQLquery);
		return query.list();
	</code>
<br>
 * 
 *
 *
 * @author Cannon
 *
 */
public class HibernateUtil {

	private static final SessionFactory SESSION_FACTORY;
	
	private static final String CONFIG_FILE_PATH = "hibernate/config/hibnernate.cfg.xml";
	
	static {
		Configuration conf = new Configuration()
						.configure(CONFIG_FILE_PATH);
		
		SESSION_FACTORY = conf.buildSessionFactory(
								new ServiceRegistryBuilder()
							.applySettings(conf.getProperties())
							.buildServiceRegistry());
	}
	
	public static SessionFactory getSessionFactory() {
		return SESSION_FACTORY;
	}
}
