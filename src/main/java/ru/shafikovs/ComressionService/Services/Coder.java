package ru.shafikovs.ComressionService.Services;

import ru.shafikovs.ComressionService.Models.HuffmanCode;

import java.nio.charset.StandardCharsets;
import java.util.*;

public class Coder {

    public HuffmanCode compress(byte[] bytes) {
        String line = new String(bytes, StandardCharsets.UTF_8);

        Map<Character, Integer> frequencyOfSymbols = frequency(line);
        System.out.println("frequencyOfSymbols " + frequencyOfSymbols);
        Queue<Node> queueOfNodes = creatingOfPriorityQueue(frequencyOfSymbols);
        System.out.println("queueOfNodes " + queueOfNodes);

        Tree huffmanTree = new Tree();
        Node root = huffmanTree.creatingHuffmanTree(queueOfNodes, line.length());
        System.out.println(root);

        Map<Character, String> codesForSymbols = new TreeMap<>();
        for (Character symbol : frequencyOfSymbols.keySet()) {
            codesForSymbols.put(symbol, huffmanTree.getCodeForSymbol(root, symbol, ""));
        }

        System.out.println("Кодовая таблица");
        System.out.println(codesForSymbols);

//        System.out.println("Строка в закодированном виде");
        String lineAfterCoding = coding(line, codesForSymbols);
//        System.out.println(lineAfterCoding);

        return new HuffmanCode(root, lineAfterCoding);
    }

    public static Map<Character, Integer> frequency(String line) {
        Map<Character, Integer> frequencyOfSymbols = new TreeMap<>();
        for (int i = 0; i < line.length(); i++) {
            char currentChar = line.charAt(i);
            if (frequencyOfSymbols.containsKey(currentChar)) {
                int n = frequencyOfSymbols.get(currentChar) + 1;
                frequencyOfSymbols.put(currentChar, n);
            } else frequencyOfSymbols.put(currentChar, 1);
        }
        return frequencyOfSymbols;
    }

    public static Queue<Node> creatingOfPriorityQueue(Map<Character, Integer> frequencyOfSymbols) {
        Queue<Node> queueOfNodes = new PriorityQueue<>();
        for (var entry : frequencyOfSymbols.entrySet()) {
            Node newNode = new Node();
            newNode.iData = entry.getValue();
            newNode.fData = entry.getKey();
            queueOfNodes.add(newNode);
        }
        return queueOfNodes;
    }

    public static String coding(String originalLine, Map<Character, String> codesForSymbols) {
        StringBuilder lineAfterCoding = new StringBuilder();
        for (int i = 0; i < originalLine.length(); i++) {
            char symbol = originalLine.charAt(i);
            for (char c : codesForSymbols.keySet()) {
                if (c == symbol) {
                    lineAfterCoding.append(codesForSymbols.get(c));
                }
            }
        }
        return lineAfterCoding.toString();
    }
}
