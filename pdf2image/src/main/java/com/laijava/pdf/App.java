package com.laijava.pdf;

import java.io.*;
import java.util.UUID;

public class App {
    public static void main(String args[]) throws IOException {
        File file = new File("F:\\live\\31420191144011505791.pdf");
        UUID uuid = UUID.randomUUID();

        String fileId = uuid.toString().replaceAll("-", "");
        InputStream targetStream = new FileInputStream(file);

        PdfUtils.pdf2multiImage(targetStream,
                "F:\\live\\", fileId, "0", 300);
    }
}
