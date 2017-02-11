package  com.limagiran.hearthstone.util;

import static javax.crypto.Cipher.*;
import java.security.MessageDigest;
import javax.crypto.Cipher;
import javax.crypto.spec.*;

/**
 *
 * @author Vinicius Silva
 */
public class Security {

    private static final String IV = "AAAAAAAAAAAAAAAA";

    /**
     * Criptografar um texto
     *
     * @param plainText texto a ser criptografado
     * @param encKey chave
     * @return texto criptografado
     * @throws Exception erro ao criptografar
     */
    public static byte[] encrypt(String plainText, String encKey) throws Exception {
        final Cipher cip = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE");
        final SecretKeySpec key = new SecretKeySpec(encKey.getBytes("UTF-8"), "AES");
        cip.init(ENCRYPT_MODE, key, new IvParameterSpec(IV.getBytes("UTF-8")));
        return cip.doFinal(plainText.getBytes("UTF-8"));
    }

    /**
     * Descriptografar um texto
     *
     * @param cipherText texto criptografado
     * @param encKey chave
     * @return texto descriptografado
     * @throws Exception erro ao descriptografar
     */
    public static String decrypt(byte[] cipherText, String encKey) throws Exception {
        final Cipher cip = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE");
        final SecretKeySpec key = new SecretKeySpec(encKey.getBytes("UTF-8"), "AES");
        cip.init(DECRYPT_MODE, key, new IvParameterSpec(IV.getBytes("UTF-8")));
        return new String(cip.doFinal(cipherText), "UTF-8");
    }

    /**
     * Retorna o hash SHA256 da string passada por parâmetro
     *
     * @param value string a ser codificada
     * @return hash SHA256 hexadecimal
     * @throws java.lang.Exception
     */
    public static String getSHA256(String value) throws Exception {
        final MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
        final byte messageDigest[] = algorithm.digest(value.getBytes("UTF-8"));
        final StringBuilder hexString = new StringBuilder();
        for (byte b : messageDigest) {
            hexString.append(String.format("%02X", 0xFF & b));
        }
        return hexString.toString();
    }

    /**
     * Retorna o password com 16 dígitos
     *
     * @param key senha a ser formatada
     * @return password com 16 dígitos
     */
    public static String getPass16(String key) {
        key = (((key == null) || key.isEmpty()) ? "A" : key);
        try {
            return getSHA256(key).substring(0, 16);
        } catch (Exception e) {
            while (key.length() < 16) {
                key += key;
            }
            return key.substring(0, 16);
        }
    }

    /**
     * Criptografar um texto
     *
     * @param bytes bytes a serem criptografados
     * @param encKey chave
     * @return texto criptografado
     * @throws Exception erro ao criptografar
     */
    public static byte[] encrypt(byte[] bytes, String encKey) throws Exception {
        encKey = getPass16(encKey);
        final Cipher cip = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE");
        final SecretKeySpec key = new SecretKeySpec(encKey.getBytes("UTF-8"), "AES");
        cip.init(ENCRYPT_MODE, key, new IvParameterSpec(IV.getBytes("UTF-8")));
        return cip.doFinal(bytes);
    }

    /**
     * Descriptografar um texto
     *
     * @param cipherText texto criptografado
     * @param encKey chave
     * @return bytes descriptografados
     * @throws Exception erro ao descriptografar
     */
    public static byte[] decryptBytes(byte[] cipherText, String encKey) throws Exception {
        encKey = getPass16(encKey);
        final Cipher cip = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE");
        final SecretKeySpec key = new SecretKeySpec(encKey.getBytes("UTF-8"), "AES");
        cip.init(DECRYPT_MODE, key, new IvParameterSpec(IV.getBytes("UTF-8")));
        return cip.doFinal(cipherText);
    }
}