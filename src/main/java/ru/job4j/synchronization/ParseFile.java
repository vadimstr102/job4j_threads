package ru.job4j.synchronization;

import java.io.*;
import java.util.function.Predicate;

public class ParseFile {
    private final File file;

    public ParseFile(File file) {
        this.file = file;
    }

    public String getContent(Predicate<Character> filter) throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            StringBuilder output = new StringBuilder();
            int data;
            char symbol;
            while ((data = bufferedReader.read()) != -1) {
                symbol = (char) data;
                if (filter.test(symbol)) {
                    output.append(symbol);
                }
            }
            return output.toString();
        }
    }
}
