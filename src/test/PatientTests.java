package test;

import static org.junit.Assert.fail;

import java.util.List;

import med.africa.patient.Patient;
import med.africa.patient.hibernate.PatientDAO;

import org.hibernate.HibernateException;
import org.junit.Test;

public class PatientTests {

	@Test
	public void testHibernateConstruction() {
		try {
			Patient p = new PatientDAO().getPatient(1l);
			System.out.println(p);
		} catch (HibernateException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testGetAllPatients() {
		try {
			@SuppressWarnings("unused")
			List<Patient> list = new PatientDAO().getAll(Patient.class);
		} catch (HibernateException e) {
			fail(e.getMessage());
		}
	}

}
