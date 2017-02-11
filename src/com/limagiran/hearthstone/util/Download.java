package com.limagiran.hearthstone.util;

import static java.io.File.separator;
import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
import java.security.*;
import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.zip.GZIPInputStream;

/**
 *
 * @author Vinicius Silva
 */
public class Download implements Runnable {

    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36";

    public static final int STATUS_OK = 0;
    public static final int STATUS_ERROR = 1;
    public static final int STATUS_DOWNLOADING = 2;

    private static final String MSG_INVALID_HASH;

    static {
        if (Locale.getDefault().toString().equalsIgnoreCase("pt_BR")) {
            MSG_INVALID_HASH = "Hash Inválido";
        } else {
            MSG_INVALID_HASH = "Invalid Hash";
        }
    }

    private final String url;
    private final String hash;
    private final String cookies;
    private String velocity = "";
    private File file;
    private byte[] bytes;
    private int status;
    private int totalBytes = 0;
    private int downloadedBytes = 0;
    private int bytesTaxa = 0;
    private boolean canceled = false;
    private boolean finish = false;
    private Exception error;
    private final CountDownLatch latch = new CountDownLatch(1);

    private Download(String url, File file, String hash, String cookies) {
        this.url = url;
        this.file = ((file != null) ? file.getAbsoluteFile() : file);
        this.hash = hash;
        this.cookies = cookies;
    }

    @Override
    public void run() {
        status = STATUS_DOWNLOADING;
        try {
            downloadBytes();
            checkHash();
            save();
            status = STATUS_OK;
        } catch (Exception ex) {
            status = STATUS_ERROR;
            error = ex;
        } finally {
            finish = true;
        }
        latch.countDown();
    }

    /**
     * Trava a Thread e espera o download terminar
     */
    public void waitFinish() {
        try {
            latch.await();
        } catch (Exception ex) {
            //ignorar
        }
    }

    /**
     * Baixa um arquivo para o computador
     *
     * @param url URL do arquivo a ser baixado
     * @param file local onde o arquivo será salvo em disco. {@code null} para
     * não salvar em disco.
     * @param hash código hash SHA-1 para verificar a integridade do arquivo.
     * {@code null} para não verificar
     * @param cookies valores para cookies
     * @return objeto Download
     */
    public static Download download(String url, File file, String hash, String cookies) {
        Download download = new Download(url, file, hash, cookies);
        new Thread(download).start();
        return download;
    }

    /**
     * Baixa um arquivo para o computador
     *
     * @param url URL do arquivo a ser baixado
     * @return objeto Download
     */
    public static Download download(String url) {
        Download download = new Download(url, null, null, null);
        new Thread(download).start();
        return download;
    }

    /**
     * Baixa o conteúdo de um arquivo da internet
     *
     * @param url URL do arquivo a ser baixado
     * @return byte array com o conteúdo do arquivo.
     * @throws java.lang.Exception falha no download
     *
     */
    private void downloadBytes() throws Exception {
        byte buf[] = new byte[4096];
        int len;
        try {
            rateDownload();
            URLConnection conn = new URL(url).openConnection();
            conn.setRequestProperty("User-Agent", USER_AGENT);
            Optional.ofNullable(cookies).ifPresent(c -> conn.setRequestProperty("Cookie", c));

            //HttpsURLConnection conn = (HttpsURLConnection) new URL(url).openConnection();
            try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    InputStream is = conn.getInputStream()) {
                totalBytes = conn.getContentLength();
                fileName(conn);
                downloadedBytes = 0;
                bytesTaxa = 0;
                while ((len = is.read(buf)) >= 0) {
                    baos.write(buf, 0, len);
                    downloadedBytes += len;
                    bytesTaxa += len;
                    downloadedBytes = ((downloadedBytes > totalBytes) ? totalBytes : downloadedBytes);
                    if (canceled) {
                        throw new Exception("canceled");
                    }
                }
                bytes = baos.toByteArray();
            }
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Retorna o conteúdo baixado em formato String
     *
     * @return String com o conteúdo do arquivo.
     */
    public String getText() {
        return getText("UTF-8");
    }

    /**
     * Retorna o conteúdo baixado em formato String
     *
     * @param charset codificação do texto
     * @return String com o conteúdo do arquivo.
     */
    public String getText(String charset) {
        waitFinish();
        try {
            if ((status == STATUS_OK) && isCompressed()) {
                try (ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
                        GZIPInputStream gis = new GZIPInputStream(bais);) {
                    StringBuilder _return = new StringBuilder();
                    byte[] buf = new byte[524288];
                    int len;
                    while ((len = gis.read(buf)) >= 0) {
                        _return.append(new String(buf, 0, len, charset));
                    }
                    return _return.toString();
                }
            }
            return new String(bytes, charset).replace("﻿", "");
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * Retorna o conteúdo baixado
     *
     * @return bytes do arquivo
     */
    public byte[] getBytes() {
        waitFinish();
        try {
            if ((status == STATUS_OK) && isCompressed()) {
                try (ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
                        GZIPInputStream gis = new GZIPInputStream(bais)) {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    byte[] buf = new byte[524288];
                    int len;
                    while ((len = gis.read(buf)) >= 0) {
                        baos.write(buf, 0, len);
                    }
                    return baos.toByteArray();
                }
            }
            return Arrays.copyOf(bytes, bytes.length);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Determines if a byte array is compressed. The java.util.zip GZip
     * implementaiton does not expose the GZip header so it is difficult to
     * determine if a string is compressed.
     *
     * @return true if the array is compressed or false otherwise
     */
    public boolean isCompressed() {
        if ((bytes == null) || (bytes.length < 2)) {
            return false;
        } else {
            return ((bytes[0] == (byte) (GZIPInputStream.GZIP_MAGIC))
                    && (bytes[1] == (byte) (GZIPInputStream.GZIP_MAGIC >> 8)));
        }
    }

    /**
     * Retorna a porcentagem do download sendo realizado atualmente
     *
     * @return String com a informação da porcentagem e taxa de transferência
     */
    public String getPercentageString() {
        return new DecimalFormat("##0.0").format(getPercentage()) + "%";
    }

    /**
     * Retorna a porcentagem do download sendo realizado atualmente
     *
     * @return porcentagem de 0.0 a 100.0
     */
    public double getPercentage() {
        return ((totalBytes > 0) ? (((double) downloadedBytes / (double) totalBytes) * 100.0) : 0.0);
    }

    /**
     * Retorna a porcentagem do download sendo realizado atualmente
     *
     * @return String com a informação da porcentagem e taxa de transferência
     */
    public String getVelocity() {
        return velocity;
    }

    public String getBytesStatus() {
        return String.format("%skb/%skb", (downloadedBytes / 1024), (totalBytes / 1024));
    }

    /**
     * Retorna a taxa de transferência do download (por segundo)
     */
    private void rateDownload() {
        new Thread(() -> {
            while (!finish) {
                velocity = ("Velocidade: " + (int) (((double) bytesTaxa) / 1000.0) + "KB");
                bytesTaxa = 0;
                sleep(1000);
            }
        }).start();
    }

    private static void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (Exception ignore) {
        }
    }

    /**
     * Retorna o status do download.<br>
     * <ul>
     * <li>Download.STATUS_OK</li>
     * <li>Download.STATUS_ERROR</li>
     * <li>Download.STATUS_DOWNLOADING</li>
     * </ul>
     *
     * @return status atual
     */
    public int getStatus() {
        return status;
    }

    /**
     * Verifica se houve erro no download
     *
     * @return {@code true} para erro. {@code false} o contrário.
     */
    public boolean isError() {
        waitFinish();
        return (status == STATUS_ERROR);
    }

    /**
     * Verifica se houve erro no download
     *
     * @return {@code true} para erro. {@code false} o contrário.
     */
    public boolean isOk() {
        waitFinish();
        return (status == STATUS_OK);
    }

    public boolean isCanceled() {
        return canceled;
    }

    public boolean isFinish() {
        return finish;
    }

    public void cancel() {
        canceled = true;
    }

    private void save() throws IOException {
        if (file != null) {
            mkdirs();
            try (FileOutputStream fos = new FileOutputStream(
                    new File(file.getAbsoluteFile() + (isCompressed() ? ".gz" : "")));) {
                fos.write(bytes);
            }
        }
    }

    private void fileName(URLConnection conn) {
        if ((file != null) && file.isDirectory()) {
            String fileName;
            if (url.contains("/") && !url.endsWith("/")) {
                fileName = url.split("/")[url.split("/").length - 1];
            } else {
                String type = conn.getContentType();
                type = (type != null) ? ("." + type.split("/")[1]) : "";
                fileName = "Download " + Long.toString(System.currentTimeMillis()).substring(7) + type;
            }
            file = new File(file.getAbsolutePath() + separator + fileName);
        }
    }

    public Exception getError() {
        return error;
    }

    /**
     * Verifica se o hash do arquivo baixado corresponde ao hash passado no
     * parâmetro do download
     *
     * @param bytes bytes do arquivo baixado
     * @return {@code true} para hash correto. {@code false} o contrário.
     */
    private void checkHash() throws Exception {
        if ((hash != null) && !getHash(Arrays.copyOf(bytes, bytes.length)).equals(hash)) {
            throw new Exception(MSG_INVALID_HASH);
        }
    }

    /**
     * Retorna o hash do arquivo
     *
     * @param in bytes do arquivo
     * @return hash SHA-1
     * @throws IOException exception
     * @throws NoSuchAlgorithmException exception
     */
    public static String getHash(byte[] in) throws IOException, NoSuchAlgorithmException {
        byte[] out = MessageDigest.getInstance("SHA-1").digest(in);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < out.length; i++) {
            sb.append(Integer.toString((out[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }

    /**
     * Cria as pastas e subpastas do arquivo File, caso não existam
     */
    private void mkdirs() {
        try {
            if (file.getName().contains(".")) {
                file.getParentFile().mkdirs();
            } else {
                file.mkdirs();
            }
        } catch (Exception ignore) {
        }
    }

    /**
     * Envia uma requisição via POST
     *
     * @param url url desejada
     * @param map mapa com os parâmetros a serem enviados
     * @param cookie cookies
     * @return bytes de resposta
     */
    public static byte[] sendPost(String url, Map<String, String> map, String... cookie) {
        try {
            StringBuilder params = new StringBuilder();
            map.forEach((s, o) -> params.append(s).append("=").append(normalizeParam(o)).append("&"));
            byte[] postData = params.toString().getBytes(Charset.forName("UTF-8"));
            int postDataLength = postData.length;
            URL _url = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) _url.openConnection();
            conn.setDoOutput(true);
            conn.setInstanceFollowRedirects(false);
            conn.setRequestMethod("POST");
            conn.addRequestProperty("User-Agent", USER_AGENT);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("charset", "utf-8");
            conn.setRequestProperty("Content-Length", Integer.toString(postDataLength));
            if (cookie.length > 0) {
                conn.setRequestProperty("Cookie", String.join("; ", cookie));
            }
            conn.setUseCaches(false);
            try (DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
                wr.write(postData);
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try (InputStream is = conn.getInputStream()) {
                byte[] buf = new byte[524288];
                int len;
                while ((len = is.read(buf, 0, buf.length)) >= 0) {
                    baos.write(buf, 0, len);
                }
            }
            return baos.toByteArray();
        } catch (Exception e) {
            return new byte[0];
        }
    }

    private static String normalizeParam(String s) {
        return s.replace("+", "%2B").replace("=", "%3D").replace(" ", "%20").replace("&", "%26");
    }

    /**
     * Envia uma requisição via POST
     *
     * @param url url desejada
     * @param map mapa com os parâmetros a serem enviados
     * @param cookie cookies
     * @return bytes de resposta
     */
    public static String sendPostGetString(String url, Map<String, String> map, String... cookie) {
        return new String(sendPost(url, map, cookie), Charset.forName("UTF-8"));
    }

    /**
     * Envia uma requisição via POST
     *
     * @param url url desejada
     * @param map mapa com os parâmetros a serem enviados
     * @return bytes de resposta
     */
    public static byte[] sendPostLarge(String url, Map<String, String> map) {
        try {
            String charset = "UTF-8";
            String boundary = Long.toHexString(System.currentTimeMillis());
            String CRLF = "\r\n";

            URLConnection connection = new URL(url).openConnection();
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

            try (OutputStream output = connection.getOutputStream();
                    PrintWriter writer = new PrintWriter(new OutputStreamWriter(output, charset), true)) {
                map.forEach((key, content) -> {
                    /*// Send normal param.
                    writer.append("--" + boundary).append(CRLF);
                    writer.append("Content-Disposition: form-data; name=\"" + key + "\"").append(CRLF);
                    writer.append("Content-Type: text/plain; charset=" + charset).append(CRLF);
                    writer.append(CRLF).append(content).append(CRLF).flush();*/

                    writer.append("--" + boundary).append(CRLF);
                    writer.append("Content-Disposition: form-data; name=\"" + key + "\"; filename=\"" + key + "\"").append(CRLF);
                    writer.append("Content-Type: " + URLConnection.guessContentTypeFromName(key)).append(CRLF);
                    writer.append("Content-Transfer-Encoding: binary").append(CRLF);
                    writer.append(CRLF).flush();
                    try {
                        output.write(content.getBytes("UTF-8"));
                        output.flush();
                    } catch (Exception ignore) {
                    }
                    writer.append(CRLF).flush();
                });
                writer.append("--" + boundary + "--").append(CRLF).flush();
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try (InputStream is = connection.getInputStream()) {
                byte[] buf = new byte[524288];
                int len;
                while ((len = is.read(buf, 0, buf.length)) >= 0) {
                    baos.write(buf, 0, len);
                }
            }
            return baos.toByteArray();
        } catch (Exception e) {
            return new byte[0];
        }
    }

    /**
     * Envia uma requisição via POST
     *
     * @param url url desejada
     * @param map mapa com os parâmetros a serem enviados
     * @return bytes de resposta ou {@code null} para erro.
     */
    public static String sendPostLargeGetString(String url, Map<String, String> map) {
        return new String(sendPostLarge(url, map), Charset.forName("UTF-8"));
    }

    public static class Builder {

        public final String url;
        private File file;
        private String hash;
        private String cookies;

        public Builder(String url) {
            this.url = url;
        }

        public static Builder url(String url) {
            return new Builder(url);
        }

        public Download create() {
            return Download.download(url, file, hash, cookies);
        }

        public Builder file(File file) {
            this.file = file;
            return this;
        }

        public Builder hash(String hash) {
            this.hash = hash;
            return this;
        }

        public Builder cookies(String cookies) {
            this.cookies = cookies;
            return this;
        }
    }
}
