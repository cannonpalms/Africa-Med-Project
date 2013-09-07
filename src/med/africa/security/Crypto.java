package med.africa.security;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.Cipher;

public class Crypto {

	private Crypto() { }


	private static final String PRIVATE_KEY_FILE = "private.key";
	private static final String PUBLIC_KEY_FILE = "public.key";

	public static void generateAndSaveKeySet() {
		RSAKeySet keySet = new RSAKeySet();

		try {
			keySet.saveToFile(PRIVATE_KEY_FILE, KeyType.PRIVATE);
			keySet.saveToFile(PUBLIC_KEY_FILE, KeyType.PUBLIC);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static PrivateKey readPrivateKeyFromFile() {
		try {
			InputStream in = new FileInputStream(PRIVATE_KEY_FILE);
			ObjectInputStream oin = new ObjectInputStream(new BufferedInputStream(in));
			try {
				BigInteger m = (BigInteger) oin.readObject();
				BigInteger e = (BigInteger) oin.readObject();
				RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(m, e);
				KeyFactory fact = KeyFactory.getInstance("RSA");
				PrivateKey privKey = fact.generatePrivate(keySpec);
				return privKey;
			} catch (Exception e) {
				throw new RuntimeException("Spurious serialisation error", e);
			} finally {
				oin.close();
			}
		} catch (IOException e) {
			//will never happen
		}
		return null;
	}
	
	private static PublicKey readPublicKeyFromFile() {
		try {
			InputStream in = new FileInputStream(PUBLIC_KEY_FILE);
			ObjectInputStream oin = new ObjectInputStream(new BufferedInputStream(in));
			try {
				BigInteger m = (BigInteger) oin.readObject();
				BigInteger e = (BigInteger) oin.readObject();
				RSAPublicKeySpec keySpec = new RSAPublicKeySpec(m, e);
				KeyFactory fact = KeyFactory.getInstance("RSA");
				PublicKey pubKey = fact.generatePublic(keySpec);
				return pubKey;
			} catch (Exception e) {
				throw new RuntimeException("Spurious serialisation error", e);
			} finally {
				oin.close();
			}
		} catch (IOException e) {
			//will never happen
		}
		return null;
	}

	public static String decryptString(String encryptedStr) {
		try {
			byte[] cipherBytes = encryptedStr.getBytes();
			PrivateKey privKey = readPrivateKeyFromFile();
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.DECRYPT_MODE, privKey);
			byte[] decryptedBytes = cipher.doFinal(cipherBytes);
			return new String(decryptedBytes);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static String encryptString(String data) {
		try {
			byte[] dataBytes = data.getBytes();
			PublicKey pubKey = readPublicKeyFromFile();
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, pubKey);
			byte[] encryptedBytes = cipher.doFinal(dataBytes);
			return new String(encryptedBytes);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void main(String[] args) {
		Crypto.generateAndSaveKeySet();
		String foo = "blahblahblah this is a message that will be encrypted";
		System.out.println("Original message: " + foo);
		String encrypted = Crypto.encryptString(foo);
		System.out.println("Encrypted message: " + encrypted);
		String decrypted = Crypto.decryptString(encrypted);
		System.out.println("Decrypted message: " + decrypted);
		System.out.println("Equality: " + foo.equals(decrypted));
	}
}
