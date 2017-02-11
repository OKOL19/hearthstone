package  com.limagiran.hearthstone.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 *
 * @author Vinicius
 */
public class Zip {
    
    public static double totalFilesUnzip;
    
    public static boolean unzipDir(File in) throws Exception {
        totalFilesUnzip = 0;
        if (!in.exists()) {
            throw new IOException(in.getAbsolutePath() + " does not exist");
        }
        if (!buildDirectory(in.getParentFile())) {
            throw new IOException("Could not create directory: " + in.getParent());
        }
        try (ZipFile zipFile = new ZipFile(in)) {
            for (Enumeration entries = zipFile.entries(); entries.hasMoreElements();) {
                ZipEntry entry = (ZipEntry) entries.nextElement();
                File file = new File(in.getParent(), File.separator + entry.getName());
                if (!buildDirectory(file.getParentFile())) {
                    throw new IOException("Could not create directory: " + file.getParentFile());
                }
                if (!entry.isDirectory()) {
                    copyInputStream(zipFile.getInputStream(entry), new BufferedOutputStream(new FileOutputStream(file)));
                    totalFilesUnzip++;
                } else if (!buildDirectory(file)) {
                    throw new IOException("Could not create directory: " + file);
                }
            }
        }
        return true;
    }

    public static boolean buildDirectory(File file) {
        return file.exists() || file.mkdirs();
    }

    public static final void copyInputStream(InputStream in, OutputStream out) throws Exception {
        byte[] buffer = new byte[262144];
        int len;
        while ((len = in.read(buffer)) >= 0) {
            out.write(buffer, 0, len);
        }
        in.close();
        out.close();
    }
}