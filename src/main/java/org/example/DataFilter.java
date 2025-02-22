package org.example;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DataFilter {
    private static String outputDir = ".";
    private static String filePrefix = "";
    private static boolean appendMode = false;
    private static boolean shortStatistics = false;
    private static boolean fullStatistics = false;
    private static List<String> inputFiles = new ArrayList<>();

    public static void main(String[] args) {
        parseArguments(args);

        if (inputFiles.isEmpty()) {
            System.err.println("Ошибка: Входные файлы не указаны.");
            return;
        }

        List<String> integerLines = new ArrayList<>();
        List<String> floatLines = new ArrayList<>();
        List<String> stringLines = new ArrayList<>();

        FileProcessor fileProcessor = new FileProcessor();
        for (String file : inputFiles) {
            File inputFile = new File(file);
            if (!inputFile.exists()) {
                System.err.println("Ошибка: файл '" + file + "' не существует.");
                continue;
            }
            fileProcessor.processFile(file, integerLines, floatLines, stringLines);
        }

        OutputWriter outputWriter = new OutputWriter();
        outputWriter.writeOutputFiles(outputDir, filePrefix, appendMode, integerLines, floatLines, stringLines);

        printStatistics(integerLines, floatLines, stringLines);
    }

    private static void parseArguments(String[] args) {
        int i = 0;
        while (i < args.length) {
            String arg = args[i];
            if (arg.startsWith("-")) {
                switch (arg) {
                    case "-o":
                        if (i + 1 >= args.length) {
                            System.err.println("Ошибка: отсутствует значение для опции -o.");
                            System.exit(1);
                        }
                        outputDir = args[i + 1];
                        i += 2;
                        break;
                    case "-p":
                        if (i + 1 >= args.length) {
                            System.err.println("Ошибка: отсутствует значение для опции -p.");
                            System.exit(1);
                        }
                        filePrefix = args[i + 1];
                        i += 2;
                        break;
                    case "-a":
                        appendMode = true;
                        i++;
                        break;
                    case "-s":
                        shortStatistics = true;
                        i++;
                        break;
                    case "-f":
                        fullStatistics = true;
                        i++;
                        break;
                    default:
                        System.err.println("Ошибка: Неизвестная опция: " + arg);
                        System.exit(1);
                }
            } else {
                inputFiles.add(arg);
                i++;
            }
        }

        if (shortStatistics && fullStatistics) {
            System.err.println("Ошибка: параметры -s и -f нельзя использовать вместе.");
            System.exit(1);
        }
    }

    private static void printStatistics(List<String> integerLines, List<String> floatLines, List<String> stringLines) {
        IntStatistics intStats = new IntStatistics();
        for (String line : integerLines) {
            try {
                long value = Long.parseLong(line);
                intStats.update(value);
            } catch (NumberFormatException e) {
                System.err.println("Ошибка: Неверный целочисленный формат: " + line);
            }
        }

        FloatStatistics floatStats = new FloatStatistics();
        for (String line : floatLines) {
            try {
                double value = Double.parseDouble(line);
                floatStats.update(value);
            } catch (NumberFormatException e) {
                System.err.println("Ошибка: Неверный вещественный формат: " + line);
            }
        }

        StringStatistics stringStats = new StringStatistics();
        for (String line : stringLines) {
            stringStats.update(line.length());
        }

        if (shortStatistics) {
            System.out.println("Целые числа: " + intStats.getCount());
            System.out.println("Вещественные числа: " + floatStats.getCount());
            System.out.println("Строки: " + stringStats.getCount());
        } else if (fullStatistics) {
            if (intStats.getCount() > 0) {
                System.out.println("Целые числа:");
                System.out.println("  Количество: " + intStats.getCount());
                System.out.println("  Минимум: " + intStats.getMin());
                System.out.println("  Максимум: " + intStats.getMax());
                System.out.println("  Сумма: " + intStats.getSum());
                System.out.println("  Среднее значение: " + (intStats.getSum() / (double) intStats.getCount()));
            }
            if (floatStats.getCount() > 0) {
                System.out.println("Вещественные числа:");
                System.out.println("  Количество: " + floatStats.getCount());
                System.out.println("  Минимум: " + floatStats.getMin());
                System.out.println("  Максимум: " + floatStats.getMax());
                System.out.println("  Сумма: " + floatStats.getSum());
                System.out.println("  Среднее значение: " + (floatStats.getSum() / floatStats.getCount()));
            }
            if (stringStats.getCount() > 0) {
                System.out.println("Строки:");
                System.out.println("  Количество: " + stringStats.getCount());
                System.out.println("  Минимальная длина: " + stringStats.getMinLength());
                System.out.println("  Максимальная длина: " + stringStats.getMaxLength());
            }
        }
    }
}