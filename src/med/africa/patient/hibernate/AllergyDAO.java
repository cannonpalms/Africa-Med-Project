package med.africa.patient.hibernate;

import java.util.List;

import med.africa.hibernate.HibernateUtil;
import med.africa.patient.Allergy;

import org.hibernate.Query;
import org.hibernate.Session;

public class AllergyDAO {

	
	public Allergy get(long allergyID) {
		Session session = HibernateUtil.openSession();
		
		try {
			Allergy result = (Allergy) session.get(Allergy.class, allergyID);
			return result;
		} finally {
			session.close();
		}
	}
	
	public List<Allergy> getAll() {
		Session session = HibernateUtil.openSession();
		
		try {
			String hql = "from Allergy";
			Query query = session.createQuery(hql);
			
			@SuppressWarnings("unchecked")
			List<Allergy> results = (List<Allergy>) query.list();
			
			return results;
		} finally {
			session.close();
		}
	}
}
