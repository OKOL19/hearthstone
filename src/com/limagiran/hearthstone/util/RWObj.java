package com.limagiran.hearthstone.util;

import static javax.crypto.Cipher.*;
import java.io.*;
import javax.crypto.Cipher;
import javax.crypto.*;
import javax.crypto.spec.*;

/**
 *
 * @author Vinicius
 */
public class RWObj {

    private static final String IV = "AAAAAAAAAAAAAAAA";

    /**
     * Retorna os bytes do objeto
     * @param o objeto
     * @return bytes do objeto ou new byte[0] para erro
     */
    public static byte[] getBytes(Object o) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(o);
            return baos.toByteArray();
        } catch (Exception e) {
            return new byte[0];
        }
    }

    /**
     * Salva um objeto no caminho passado por parâmetro
     *
     * @param object objeto a ser salvo
     * @param file local onde será salvo
     * @return true para salvo ou false para erro
     */
    public static boolean write(Object object, File file) {
        return write(object, file, null);
    }

    /**
     * Salva um objeto no caminho passado por parâmetro
     *
     * @param object objeto a ser salvo
     * @param file local onde será salvo
     * @param key senha para criptografia
     * @return true para salvo ou false para erro
     */
    public synchronized static boolean write(Object object, File file, String key) {
        File temp = new File("temp.rwo");
        try (FileOutputStream fos = new FileOutputStream(temp);
                ObjectOutputStream oos = new ObjectOutputStream(
                        ((key == null) ? fos : encrypt(fos, key)))) {
            oos.writeObject(object);
        } catch (Exception e) {
            return false;
        } finally {
            file.delete();
            temp.renameTo(file);
            temp.delete();
        }
        return true;
    }

    /**
     * Carrega um objeto salvo em disco
     *
     * @param <T> Generics
     * @param file local do objeto salvo
     * @param clazz classe a ser convertida
     * @return objeto lido ou null para erro;
     */
    public static <T> T read(File file, Class<T> clazz) {
        return read(file, clazz, null);
    }

    /**
     * Carrega um objeto salvo em disco
     *
     * @param <T> Generics
     * @param file local do objeto salvo
     * @param clazz classe a ser convertida
     * @param key senha para descriptografia
     * @return objeto lido ou null para erro;
     */
    public static <T> T read(File file, Class<T> clazz, String key) {
        try (FileInputStream fis = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(
                        ((key == null) ? fis : decrypt(fis, key)))) {
            Object _return = ois.readObject();
            return (clazz.isInstance(_return) ? ((T) _return) : null);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Carrega um objeto salvo em disco
     *
     * @param <T> Generics
     * @param file local do objeto dentro do .jar
     * @param clazz classe a ser convertida
     * @return objeto lido ou null para erro;
     */
    public static <T> T readJar(String file, Class<T> clazz) {
        return readJar(file, clazz, null);
    }

    /**
     * Carrega um objeto salvo em disco
     *
     * @param <T>
     * @param file local do objeto dentro do .jar
     * @param clazz classe a ser convertida
     * @param key senha para descriptografia
     * @return objeto lido ou null para erro;
     */
    public static <T> T readJar(String file, Class<T> clazz, String key) {
        try (InputStream is = RWObj.class.getResourceAsStream(file);
                ObjectInputStream ois = new ObjectInputStream(
                        ((key == null) ? is : decrypt(is, key)))) {
            Object _return = ois.readObject();
            return (clazz.isInstance(_return) ? ((T) _return) : null);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Clona o objeto passado por parâmetro
     *
     * @param <T>
     * @param object objeto
     * @param clazz classe a ser convertida
     * @return objeto clonado ou null para erro
     */
    public static <T> T clone(Object object, Class<T> clazz) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(baos);) {
            oos.writeObject(object);
            try (ByteArrayInputStream is = new ByteArrayInputStream(baos.toByteArray());
                    ObjectInputStream ois = new ObjectInputStream(is)) {
                Object retorno = ois.readObject();
                return (clazz.isInstance(retorno) ? ((T) retorno) : null);
            }
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Cria um OutputStream para encriptação
     *
     * @param os OutputStream utilizado
     * @param encKey chave de encriptação
     * @return CipherOutputStream
     * @throws Exception erro ao gerar CipherOutputStream
     */
    private static CipherOutputStream encrypt(OutputStream os,
            String encKey) throws Exception {
        encKey = Security.getPass16(encKey);
        Cipher cip = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE");
        SecretKeySpec key = new SecretKeySpec(encKey.getBytes("UTF-8"), "AES");
        cip.init(ENCRYPT_MODE, key, new IvParameterSpec(IV.getBytes("UTF-8")));
        return new CipherOutputStream(os, cip);
    }

    /**
     * Cria um InputStream para decriptação
     *
     * @param is InputStream utilizado
     * @param encKey chave de decriptação
     * @return CipherInputStream
     * @throws Exception erro ao gerar CipherInputStream
     */
    private static CipherInputStream decrypt(InputStream is,
            String encKey) throws Exception {
        encKey = Security.getPass16(encKey);
        Cipher cip = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE");
        SecretKeySpec key = new SecretKeySpec(encKey.getBytes("UTF-8"), "AES");
        cip.init(DECRYPT_MODE, key, new IvParameterSpec(IV.getBytes("UTF-8")));
        return new CipherInputStream(is, cip);
    }
}