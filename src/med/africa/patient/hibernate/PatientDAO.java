package med.africa.patient.hibernate;

import java.util.List;

import med.africa.hibernate.HibernateUtil;
import med.africa.patient.Patient;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

public class PatientDAO {

	public PatientDAO() {
		
	}

	public Patient get(long patientID) {
		Session session = HibernateUtil.openSession();
		try {
			Patient result = (Patient) session.get(Patient.class, patientID);
			return result;
		} finally {
			session.close();
		}
	}
	
	public List<Patient> getAll() {
		Session session = HibernateUtil.openSession();
		try {
			String hql = "from Patient";
			Query query = session.createQuery(hql);
			
			@SuppressWarnings("unchecked")
			List<Patient> results = (List<Patient>) query.list();
			return results;
		} finally {
			session.close();
		}
	}

	public void save(Patient p) {
		Session session = HibernateUtil.openSession();
		session.beginTransaction();

		try {
			session.save(p);
			session.getTransaction().commit();
		} catch (HibernateException e) {
			session.getTransaction().rollback();
		} finally {
			session.close();
		}
	}

}
