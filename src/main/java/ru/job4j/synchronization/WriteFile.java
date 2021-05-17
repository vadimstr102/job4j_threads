package ru.job4j.synchronization;

import java.io.*;

public class WriteFile {
    private final File file;

    public WriteFile(File file) {
        this.file = file;
    }

    public void saveContent(String content) throws IOException {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
            bufferedWriter.write(content);
        }
    }
}
