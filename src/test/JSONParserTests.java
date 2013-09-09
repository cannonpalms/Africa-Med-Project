package test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import med.africa.patient.JSONParser;
import med.africa.patient.Patient;
import med.africa.patient.PatientUtil;
import med.africa.server.LoginForm;

import org.junit.Test;

public class JSONParserTests {

	JSONParser parser = JSONParser.getParser();
	
	@Test
	public void testLogin() {
		String username = "exampleuser";
		String password = "examplepassword";
		
		LoginForm form = new LoginForm();
		form.setUsername(username);
		form.setPassword(password);
		
		
	}
	
	@Test
	public void testParserAndURL() {
		long id = (long) (Math.random() * 100 + 1);
		String URL = String.format("http://cannoncomputing.com/africa/query.php?id=%d", id);

		try {
			Patient fromParser = parser.parseJSONFromURL(URL);
			Patient fromQuery = PatientUtil.getPatient(id);
			
			System.out.println("\n");
			System.out.println(fromParser);
			System.out.println(fromQuery);
			assertTrue(fromParser.equals(fromQuery));
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
}
