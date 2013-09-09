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
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JSONParser {

	private static final JSONParser INSTANCE = new JSONParser();

	private JSONParser() {

	}

	public static JSONParser getParser() {
		return INSTANCE;
	}

	public Patient parseJSON(String json) {
		try {
			JSONObject jsonObj = JSONObject.fromObject(json);
			return parseToPatient(jsonObj);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Patient parseJSONFromURL(String url) {
		try {
			String json = getJSONStringFromURL(url);
			return parseJSON(json);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getJSONStringFromURL(String url) throws IOException {
		InputStream in = new URL(url).openStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(in, Charset.forName("UTF-8")));
		try {
			String jsonText = readAll(br);
			return jsonText;
		}
		finally {
			br.close();
		}
	}

	private Patient parseToPatient(JSONObject json) {
		Patient.Builder builder = new Patient.Builder();

		long patientID = json.getLong("patient_id");
		String firstName = json.getString("first_name");
		String middleName = json.getString("middle_name");
		String lastName = json.getString("last_name");
		Gender gender = Enum.valueOf(Gender.class, json.getString("gender"));
		Date dob = Date.valueOf(json.getString("dob"));
		Set<Allergy> allergies = new HashSet<Allergy>();

		JSONArray jsonAllergyArray = json.getJSONArray("allergies");
		int N = jsonAllergyArray.size();
		for (int i = 0; i < N; i++) {
			JSONObject a = jsonAllergyArray.getJSONObject(i);
			long id = a.getLong("id");
			String name = a.getString("name");
			Allergy toAdd = new Allergy(id, name);
			allergies.add(toAdd);
		}

		Patient patient = builder.setID(patientID)
				.setFirstName(firstName)
				.setMiddleName(middleName)
				.setLastName(lastName)
				.setGender(gender)
				.setBirthDate(dob)
				.setAllergies(allergies)
				.build();

		return patient;
	}

	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();

		int c;

		while ((c = rd.read()) != -1) {
			sb.append((char) c);
		}

		return sb.toString();
	}
}
