package com.company.service;

public class DecodingService {

    private static final char[] RUSSIAN_LETTERS_SORTED = {'о','е','а','и','н','т','с','р','в','л','к','м','д','п','у','я','ы','ь','г','з','б','ч','й','х','ж','ш','ю','ц','щ','э','ф','ъ','ё'};
    private static final double[] RUSSIAN_LETTERS_FREQUENCY = {0.10983, 0.08483, 0.07998, 0.067, 0.06318, 0.05473, 0.04746, 0.04533, 0.04343, 0.03486, 0.03203, 0.02977, 0.02804, 0.02615, 0.02001, 0.01898, 0.01735, 0.01687, 0.01641, 0.01592, 0.0145, 0.01208, 0.00966, 0.0094, 0.00718, 0.00639, 0.00486, 0.00361, 0.00331, 0.00267, 0.00037, 0.00013};

    private void decodeText(StringBuilder text, int shift){
        //TODO
    }

    private boolean isRightDecoding(StringBuilder decodedText){
        //TODO
        return false;
    }

    public void decodeTextFromFile(String fileName){
        //TODO
    }
}