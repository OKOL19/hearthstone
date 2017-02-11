package com.limagiran.hearthstone.util;

import static javax.crypto.Cipher.*;
import java.io.*;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.crypto.Cipher;
import javax.crypto.*;
import javax.crypto.spec.*;

/**
 *
 * @author Vinicius Silva
 */
public class RW {

    private static final String IV = "AAAAAAAAAAAAAAAA";
    private static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    public static String readS(InputStream inputStream, Charset charset, String key) throws FileNotFoundException, IOException, Exception {
        StringBuilder sb = new StringBuilder();
        try (ByteArrayInputStream fis = new ByteArrayInputStream(read(inputStream, key));
                InputStreamReader isr = new InputStreamReader(fis, charset);
                BufferedReader br = new BufferedReader(isr)) {
            String firstLine = br.readLine();
            sb.append(firstLine == null ? "" : firstLine);
            while (br.ready()) {
                sb.append("\n");
                sb.append(br.readLine());
            }
        }
        return sb.toString().replace("﻿", "");
    }

    public static String readS(File file, Charset charset, String key) throws FileNotFoundException, IOException, Exception {
        try (FileInputStream fis = new FileInputStream(file)) {
            return readS(fis, charset, key);
        }
    }

    public static String readS(File file) throws FileNotFoundException, IOException, Exception {
        return readS(file, DEFAULT_CHARSET, null);
    }

    public static String readS(File file, Charset charset) throws FileNotFoundException, IOException, Exception {
        return readS(file, charset, null);
    }

    public static String readS(File file, String key) throws FileNotFoundException, IOException, Exception {
        return readS(file, DEFAULT_CHARSET, key);
    }

    public static void writeS(File file, String value, Charset charset, String key) throws IOException, Exception {
        if (key != null) {
            write(file, value.getBytes(charset), key);
            return;
        }
        try (FileOutputStream fos = new FileOutputStream(mkdirs(file));
                OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
                BufferedWriter bw = new BufferedWriter(osw)) {
            String[] buff = value.split("\n");
            bw.write(buff[0]);
            for (int indice = 1; indice < buff.length; indice++) {
                bw.newLine();
                bw.write(buff[indice]);
            }
            bw.close();
        }
    }

    public static void writeS(File file, String value) throws IOException, Exception {
        writeS(file, value, DEFAULT_CHARSET, null);
    }

    public static void writeS(File file, String value, Charset charset) throws IOException, Exception {
        writeS(file, value, charset, null);
    }

    public static void writeS(File file, String value, String key) throws IOException, Exception {
        writeS(file, value, DEFAULT_CHARSET, key);
    }

    public static String readJarS(String file, Charset charset, String key) {
        try (InputStream is = RW.class.getResourceAsStream(file)) {
            return readS(is, charset, key);
        } catch (Exception ex) {
            return "";
        }
    }

    public static String readJarS(String file, Charset charset) {
        return readJarS(file, charset, null);
    }

    public static String readJarS(String file, String key) {
        return readJarS(file, DEFAULT_CHARSET, key);
    }

    public static String readJarS(String file) {
        return readJarS(file, DEFAULT_CHARSET, null);
    }

    public static byte[] read(InputStream is, String key) throws Exception {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
                InputStream is2 = ((key == null) ? is : decrypt(is, key))) {
            int i;
            byte buf[] = new byte[2048];
            while ((i = is2.read(buf)) >= 0) {
                baos.write(buf, 0, i);
            }
            return baos.toByteArray();
        }
    }

    public static byte[] read(File file, String key) throws Exception {
        try (FileInputStream fis = new FileInputStream(file)) {
            return read(fis, key);
        }
    }

    public static byte[] read(File file) throws Exception {
        return read(file, null);
    }

    public static byte[] read(InputStream is) throws Exception {
        return read(is, null);
    }

    public static byte[] readJar(String url, String key) throws Exception {
        try (InputStream is = RW.class.getResourceAsStream(url)) {
            return read(is, key);
        }
    }

    public static byte[] readJar(String url) throws Exception {
        return readJar(url, null);
    }

    public static void write(File file, byte[] bytes) throws IOException, Exception {
        write(file, bytes, null);
    }

    public static void write(File file, byte[] bytes, String key) throws IOException, Exception {
        try (FileOutputStream fos = new FileOutputStream(mkdirs(file))) {
            write(fos, bytes, key);
        }
    }

    public static void write(OutputStream os, byte[] bytes, String key) throws IOException, Exception {
        try (OutputStream os2 = ((key == null) ? os : encrypt(os, key))) {
            os2.write(bytes);
        }
    }

    private static CipherOutputStream encrypt(OutputStream os, String encKey) throws Exception {
        encKey = Security.getPass16(encKey);
        Cipher cip = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE");
        SecretKeySpec key = new SecretKeySpec(encKey.getBytes("UTF-8"), "AES");
        cip.init(ENCRYPT_MODE, key, new IvParameterSpec(IV.getBytes("UTF-8")));
        return new CipherOutputStream(os, cip);
    }

    private static CipherInputStream decrypt(InputStream is, String encKey) throws Exception {
        encKey = Security.getPass16(encKey);
        Cipher cip = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE");
        SecretKeySpec key = new SecretKeySpec(encKey.getBytes("UTF-8"), "AES");
        cip.init(DECRYPT_MODE, key, new IvParameterSpec(IV.getBytes("UTF-8")));
        return new CipherInputStream(is, cip);
    }

    public static File mkdirs(File file) {
        try {
            file = file.getAbsoluteFile();
            if (file.getName().contains(".")) {
                file.getParentFile().mkdirs();
            } else {
                file.mkdirs();
            }
        } catch (Exception ignore) {
        }
        return file;
    }

    public static void log(Exception e) {
        File log = new File("log.txt");
        try {
            String txt = (log.exists() ? readS(log) : "");
            String date = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()) + " - " + e;
            txt += "\n" + date;
            RW.writeS(log, txt);
            txt = date;
            for (int i = e.getStackTrace().length - 1; i >= 0; i--) {
                txt += "\n" + e.getStackTrace()[i];
            }
            RW.writeS(new File(Long.toString(System.currentTimeMillis()) + ".txt"), txt);
        } catch (Exception ignore) {
        }
    }
}
