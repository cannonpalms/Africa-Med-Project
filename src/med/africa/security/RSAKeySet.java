package med.africa.security;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;



public class RSAKeySet {

	private static final int KEY_LENGTH = 1024;

	private RSAPrivateKeySpec privateKey;
	private RSAPublicKeySpec publicKey;

	public RSAKeySet() {
		generateKeySet();
	}

	private void generateKeySet() {
		try {
			KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
			kpg.initialize(KEY_LENGTH);
			KeyPair kp = kpg.genKeyPair();

			KeyFactory fact = KeyFactory.getInstance("RSA");
			privateKey = fact.getKeySpec(kp.getPrivate(), RSAPrivateKeySpec.class);
			publicKey = fact.getKeySpec(kp.getPublic(), RSAPublicKeySpec.class);
		} catch (NoSuchAlgorithmException e) {
			//will never happen
		} catch (InvalidKeySpecException e) {
			//will never happen
		}
	}

	public void saveToFile(String filename, KeyType keyType) throws IOException {
		if (keyType == KeyType.PUBLIC) {
			savePublicToFile(filename);
		}
		else {
			savePrivateToFile(filename);
		}
	}

	private void savePublicToFile(String filename) throws IOException {
		ObjectOutputStream out = new ObjectOutputStream(
				new BufferedOutputStream(new FileOutputStream(filename)));
		try {
			out.writeObject(publicKey.getModulus());
			out.writeObject(publicKey.getPublicExponent());
		} catch (Exception e) {
			throw new IOException("Unexpected error", e);
		} finally {
			out.close();
		}
	}

	private void savePrivateToFile(String filename) throws IOException {
		ObjectOutputStream out = new ObjectOutputStream(
				new BufferedOutputStream(new FileOutputStream(filename)));
		try {
			out.writeObject(privateKey.getModulus());
			out.writeObject(privateKey.getPrivateExponent());
		} catch (Exception e) {
			throw new IOException("Unexpected error", e);
		} finally {
			out.close();
		}
	}
	
	
	
	public static void main(String[] args) throws IOException {
		RSAKeySet rsa = new RSAKeySet();
		
		rsa.saveToFile("testPub", KeyType.PUBLIC);
		rsa.saveToFile("testPriv", KeyType.PRIVATE);
	}
}

enum KeyType {

	PUBLIC, PRIVATE;
}