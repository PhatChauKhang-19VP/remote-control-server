package pck.rcserver.helper;


import java.io.File;
import java.nio.file.Files;

public class FileHelper {
    public static byte[] getFileBuffer(File file) {
        byte[] buffer = new byte[(int) file.length()];

        try {
            return Files.readAllBytes(file.toPath());
        } catch (Exception e) {
            e.printStackTrace();
            return buffer;
        }
    }
}