package med.africa.server;

import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginForm {

	private static final String URL = "http://cannoncomputing.com/africa/process_login.php";

	private String username;
	private String hashedPassword;

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		String hash = hashSHA512(password);
		this.hashedPassword = "0".concat(hash);
	}

	public boolean attemptLogin() {
		nullCheck();

		try {
			String params = String.format("username=%s&p=%s", 
					URLEncoder.encode(username, "UTF-8"), 
					URLEncoder.encode(hashedPassword, "UTF-8"));
			URL u = new URL(URL);
			HttpURLConnection conn = (HttpURLConnection) u.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Length", String.valueOf(params.length()));
			OutputStream os = conn.getOutputStream();
			os.write(params.getBytes());

			String response = conn.getResponseMessage();
			int responseCode = conn.getResponseCode();
			InputStream is = null;
			if (responseCode >= 400) {
				is = conn.getErrorStream();
			}
			else {
				is = conn.getInputStream();
			}
			String fullResponse = convertStreamToString(is);
			
			System.out.println(response);
			System.out.println(responseCode);
			System.out.println(fullResponse);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	private String hashSHA512(String password) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-512");
		} catch (NoSuchAlgorithmException e) {
			// this will never happen
			e.printStackTrace();
		}
		byte[] hashedBytes = md.digest(password.getBytes());
		BigInteger dec = new BigInteger(1, hashedBytes);
		return dec.toString(16);
	}

	private void nullCheck() {
		if (username == null)
			throw new IllegalStateException("Username cannot be null.");
		else if (hashedPassword == null)
			throw new IllegalStateException("Password cannot be null.");
	}

	static String convertStreamToString(java.io.InputStream is) {
		@SuppressWarnings("resource")
		java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
		return s.hasNext() ? s.next() : "";
	}

	public static void main(String[] args) {
		LoginForm form = new LoginForm();
		form.setUsername("exampleuser");
		form.setPassword("examplepassword");
		form.attemptLogin();
	}
}
