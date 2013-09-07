package med.africa.patient;

import med.africa.hibernate.HibernateUtil;
import med.africa.hibernate.Persistable;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class PatientUtil {

	private PatientUtil() { }
	
	public static Patient getPatient(long patientID) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		String hql = "from Patient where patient_id=:patientID";
		Query query = session.createQuery(hql).setLong("patientID", patientID);
		Patient result = (Patient) query.uniqueResult();
		session.close();
		return result;
	}
	
	public static Allergy getAllergy(long allergyID) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		String hql = "from Allergy where allergy_id=:allergyID";
		Query query = session.createQuery(hql).setLong("allergyID", allergyID);
		Allergy result = (Allergy) query.uniqueResult();
		session.close();
		return result;
	}
	
	public static void addToDatabase(Persistable obj) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		try {
			session.save(obj);
			session.getTransaction().commit();
		} catch (HibernateException e) {
			session.getTransaction().rollback();
		} finally {
			session.close();
		}
	}
	
}
