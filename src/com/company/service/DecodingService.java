package com.company.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

public class DecodingService {

    // а б в г д
    // е ё ж з и
    // й к л м н
    // о п р с т
    // у ф х ц ч
    // ш щ ъ ы ь
    // э ю я
    private static final double[] RUSSIAN_LETTERS_BASIC_USAGE_FREQUENCY = {0.07998, 0.01592, 0.04533, 0.01687, 0.02977,
            0.08483, 0.00013, 0.0094, 0.01641, 0.07367,
            0.01208, 0.03486, 0.04343, 0.03203, 0.067,
            0.10983, 0.02804, 0.04746, 0.05473, 0.06318,
            0.02615, 0.00267, 0.00966, 0.00486, 0.0145,
            0.00718, 0.00361, 0.00037, 0.01898, 0.01735,
            0.00331, 0.00639, 0.02001};
    private static final int RUSSIAN_LETTERS_NUM = 33;
    private static final int ALPHABET_LETTER_YOUR_POSITION = 6;
    private static final int UNICODE_SMALL_LETTER_YOUR_SHIFT = 33;

    private StringBuilder decodeText(StringBuilder text, int shift) {
        StringBuilder decodedText = new StringBuilder(text);

        for (int i = 0; i < decodedText.length(); i++) {
            char symbol = decodedText.charAt(i);

            if (Character.isLetter(symbol)) {
                boolean isUpperCase = Character.isUpperCase(symbol);
                symbol = Character.toLowerCase(symbol);
                int letterAlphabetPosition = symbol - 'а';

                if ((letterAlphabetPosition >= ALPHABET_LETTER_YOUR_POSITION) && (letterAlphabetPosition != UNICODE_SMALL_LETTER_YOUR_SHIFT)) {
                    letterAlphabetPosition = letterAlphabetPosition + 1;
                } else if (letterAlphabetPosition == UNICODE_SMALL_LETTER_YOUR_SHIFT) {
                    letterAlphabetPosition = ALPHABET_LETTER_YOUR_POSITION;
                }

                letterAlphabetPosition = (letterAlphabetPosition + shift) % RUSSIAN_LETTERS_NUM;
                int unicodePosition = 'а';

                if (letterAlphabetPosition > ALPHABET_LETTER_YOUR_POSITION) {
                    letterAlphabetPosition = letterAlphabetPosition - 1;
                } else if (letterAlphabetPosition == ALPHABET_LETTER_YOUR_POSITION) {
                    letterAlphabetPosition = UNICODE_SMALL_LETTER_YOUR_SHIFT;
                }

                unicodePosition = unicodePosition + letterAlphabetPosition;
                symbol = isUpperCase ? Character.toUpperCase((char) unicodePosition) : (char) unicodePosition;
                decodedText.setCharAt(i, symbol);
            }
        }

        return decodedText;
    }

    private double[] findLettersUsageFrequency(StringBuilder text) {

        double[] lettersFrequency = new double[RUSSIAN_LETTERS_NUM];
        int textLength = text.length();

        for (int i = 0; i < textLength; i++) {
            char symbol = text.charAt(i);

            if (!Character.isLetter((int) symbol)) {
                continue;
            }

            symbol = Character.toLowerCase(symbol);
            char firstLetter = 'а';

            if(symbol == (firstLetter + UNICODE_SMALL_LETTER_YOUR_SHIFT)) {
                lettersFrequency[ALPHABET_LETTER_YOUR_POSITION]++;
            }
            else {

                int j = 0;

                while ((symbol != (firstLetter + j)) && (j < (RUSSIAN_LETTERS_NUM - 1))){
                    j++;
                }

                if (j < ALPHABET_LETTER_YOUR_POSITION) {
                    lettersFrequency[j]++;
                } else {
                    lettersFrequency[j + 1]++;
                }
            }
        }

        for (double letterFrequency : lettersFrequency) {
            letterFrequency = letterFrequency / textLength;
        }

        return lettersFrequency;
    }

    private double getDistanceBetweenFrequencies(StringBuilder decodedText) {

        double[] lettersFrequency = findLettersUsageFrequency(decodedText);
        double distanceBetweenFrequencies = 0;

        for (int i = 0; i < RUSSIAN_LETTERS_NUM; i++) {
            distanceBetweenFrequencies += (lettersFrequency[i] - RUSSIAN_LETTERS_BASIC_USAGE_FREQUENCY[i]) * (lettersFrequency[i] - RUSSIAN_LETTERS_BASIC_USAGE_FREQUENCY[i]);
        }

        return Math.sqrt(distanceBetweenFrequencies);
    }

    private StringBuilder readTextFromFile(String fileName) {
        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource(fileName);

        if (resource != null) {
            File file = new File(resource.getFile());

            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line = reader.readLine();
                StringBuilder strBuilder = new StringBuilder();

                while (line != null) {
                    strBuilder.append(line);
                    line = reader.readLine();
                }

                return strBuilder;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void decodeTextFromFile(String fileName) {

        StringBuilder text = readTextFromFile(fileName);

        if (text != null) {

            StringBuilder bestDecodedTextVersion = new StringBuilder();
            double minDistance = Double.MAX_VALUE;

            for (int i = 1; i < RUSSIAN_LETTERS_NUM; i++) {

                StringBuilder decodedText = decodeText(text, i);
                double distance = getDistanceBetweenFrequencies(decodedText);

                if (distance < minDistance) {
                    minDistance = distance;
                    bestDecodedTextVersion = decodedText;
                }
            }

            System.out.println(bestDecodedTextVersion.toString());
        }
    }
}