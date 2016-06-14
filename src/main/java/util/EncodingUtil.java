package util;

import java.security.MessageDigest;

/**
 * 
 * @author Wilson Chen
 * 
 */
public class EncodingUtil {

	public static  final String MD2 = "MD2";
	public static  final String MD5 = "MD5";
	public static  final String SHA1 = "SHA-1";
	public static  final String SHA256 = "SHA-256";
	public static final  String SHA384 = "SHA-384";
	public static  final String SHA512 = "SHA-512";

	private static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
	'e', 'f' };

	/**
	 * encode string
	 *
	 * @param hashType
	 * @param str
	 * @return String
	 */
	public static String encodeString(String str, String hashType) {
		if (str == null) {
			return null;
		}
		try {
			MessageDigest messageDigest = MessageDigest.getInstance(hashType);
			messageDigest.update(str.getBytes());
			return toHexString(messageDigest.digest());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * encode string
	 * 
	 * @param hashType
	 * @param str
	 * @return String
	 */
	public static String encodeString(byte bytes[], String hashType) {
		try {
			MessageDigest messageDigest = MessageDigest.getInstance(hashType);
			messageDigest.update(bytes);
			return toHexString(messageDigest.digest());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Takes the raw bytes from the digest and formats them correct.
	 * 
	 * @param bytes
	 *            the raw bytes from the digest.
	 * @return the formatted bytes.
	 */
	private static String toHexString(byte[] bytes) {
		int len = bytes.length;
		StringBuilder buf = new StringBuilder(len * 2);
		// 把密文转换成十六进制的字符串形式
		for (int j = 0; j < len; j++) {
			buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
			buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
		}
		return buf.toString();
	}

}