package org.example;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class OutputWriter {
    public void writeOutputFiles(String outputDir, String prefix, boolean appendMode,
                                 List<String> integerLines, List<String> floatLines, List<String> stringLines) {
        createDirectory(outputDir);

        writeFile(outputDir, prefix + "integers.txt", integerLines, appendMode);
        writeFile(outputDir, prefix + "floats.txt", floatLines, appendMode);
        writeFile(outputDir, prefix + "strings.txt", stringLines, appendMode);
    }

    private void createDirectory(String path) {
        File dir = new File(path);
        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                System.err.println("Не удалось создать каталог: " + path);
            }
        }
    }

    private void writeFile(String outputDir, String fileName, List<String> lines, boolean append) {
        if (lines.isEmpty()) {
            return;
        }
        File file = new File(outputDir, fileName);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, append))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Ошибка записи в файл '" + file.getAbsolutePath() + "': " + e.getMessage());
        }
    }
}