package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class FileProcessor {
    public void processFile(String fileName, List<String> integerLines, List<String> floatLines, List<String> stringLines) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                DataType type = determineDataType(line);
                switch (type) {
                    case INTEGER:
                        integerLines.add(line);
                        break;
                    case FLOAT:
                        floatLines.add(line);
                        break;
                    case STRING:
                        stringLines.add(line);
                        break;
                }
            }
        } catch (IOException e) {
            System.err.println("Ошибка чтения файла '" + fileName + "': " + e.getMessage());
        }
    }

    private DataType determineDataType(String line) {
        if (line.matches("^-?\\d+$")) {
            return DataType.INTEGER;
        }
        if (line.matches("^-?\\d+(\\.\\d+)?([eE][+-]?\\d+)?$") ||
                line.matches("^-?\\.\\d+([eE][+-]?\\d+)?$")) {
            return DataType.FLOAT;
        }
        return DataType.STRING;
    }

    private enum DataType {
        INTEGER, FLOAT, STRING
    }
}