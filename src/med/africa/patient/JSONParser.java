package med.africa.patient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.sql.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import net.sf.json.JSONObject;

public class JSONParser {

	private static final JSONParser INSTANCE = new JSONParser();
	
	private JSONParser() {
		
	}
	
	public static JSONParser getParser() {
		return INSTANCE;
	}
	
	public Patient parseJSON(String json) {
		Patient patient = new Patient();
		
		try {
			JSONObject jsonObj = readJSONFromURL("http://www.cannoncomputing.com/africa/lookup.php?id=7");
			parseJSONObject(jsonObj, patient);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	private static void parseJSONObject(JSONObject json, Patient patient) {
		long patientID = json.getLong("patient_id");
		String firstName = json.getString("first_name");
		String middleName = json.getString("middle_name");
		String lastName = json.getString("last_name");
		boolean gender = parseBitBoolean(json.getInt(("gender")));
		Date dob = Date.valueOf(json.getString("dob"));
		Set<Allergy> allergies = new HashSet<Allergy>();
		{
			Iterator<String> iter = (Iterator<String>) json.getJSONArray("allergies").iterator();
			while (iter.hasNext()) {
				allergies.add(new Allergy(iter.next()));
			}
		}
		
		patient.setId(patientID);
		patient.setFirstName(firstName);
		patient.setMiddleName(middleName);
		patient.setLastName(lastName);
		patient.setGender(gender);
		patient.setBirthDate(dob);
		patient.setAllergies(allergies);
		
		System.out.println(patient);
		
	}
	
	private static JSONObject readJSONFromURL(String url) throws IOException {
		InputStream in = new URL(url).openStream();
		
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(in, Charset.forName("UTF-8")));
			
			String jsonText = readAll(br);
			
			return JSONObject.fromObject(jsonText);
		}
		finally {
			in.close();
		}
	}
	
	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		
		int c;
		
		while ((c = rd.read()) != -1) {
			sb.append((char) c);
		}
		
		return sb.toString();
	}
	
	private static boolean parseBitBoolean(int num) {
		return num == 1;
	}
}
