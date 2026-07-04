package org.example;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

public class FileWriter {
    private FileOutputStream out_fd = null;
    private PrintWriter out = null;
    private String outputFileName = null;

    public FileWriter(String outputFileName) {
        this.outputFileName = outputFileName;
    }

    public void println(String line) {
        if (outputFileName == null)
            return;
        if (out == null) {
            try {
                out_fd = new FileOutputStream(outputFileName, true);
                out = new PrintWriter(out_fd);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                outputFileName = null;
            }
        }
        out.println(line);
        out.flush();
    }
}
