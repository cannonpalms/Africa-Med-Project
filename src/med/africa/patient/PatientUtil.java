package med.africa.patient;

import java.sql.Date;
import java.util.GregorianCalendar;
import java.util.List;

import med.africa.hibernate.HibernateUtil;
import med.africa.hibernate.Persistable;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

@SuppressWarnings("unused")
public class PatientUtil {

	private PatientUtil() { }
	
	private static void addAllergiesToPatient(Patient patient, int numAllergies) {
		for (int i = 0; i < numAllergies; i++) {
			Allergy allergy = getAllergy(randomAllergyID());
			patient.addAllergy(allergy);
		}
	}
	
	private static void createRandomPatient() {
		String firstName = randomStr();
		String lastName = randomStr();
		Date dob = randomDate();
		
		Patient randPatient = new Patient(firstName, lastName, dob);
		
		addToDatabase(randPatient);
	}
	
	@SuppressWarnings("unchecked")
	public static List<Patient> getAllPatients() {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		String hql = "from Patient";
		Query query = session.createQuery(hql);
		List<Patient> result = (List<Patient>) query.list();
		session.close();
		return result;
	}
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
	
	private static void createRandomAllergy() {
		String allergyName = randomStr();
		
		Allergy randAllergy = new Allergy(allergyName);
		
		addToDatabase(randAllergy);
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
	
	private static String randomStr() {
		byte[] data = new byte[12];
		
		for (int i = 0; i < data.length; i++) {
			data[i] = (byte) (97 + Math.random() * 26);
		}
		
		return new String(data);
	}
	
	private static long randomAllergyID() {
		return (long) (1 + Math.random() * 101);
	}
	
	private static Date randomDate() {
		return new Date(
					new GregorianCalendar()
					.getTimeInMillis()
					);
	}
}
