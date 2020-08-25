package com.company;

import com.company.service.DecodingService;

public class Main {

    public static void main(String[] args) {
		DecodingService decodingService = new DecodingService();

		decodingService.decodeTextFromFile("resources/caesarCipher.txt");
    }
}
