package ru.shafikovs.ComressionService.Models;

import ru.shafikovs.ComressionService.Services.Node;

public class HuffmanCode {
    private Node huffmanTree;
    private String code;

    public HuffmanCode(Node huffmanTree, String code) {
        this.huffmanTree = huffmanTree;
        this.code = code;
    }

    public Node getHuffmanTree() {
        return huffmanTree;
    }

    public void setHuffmanTree(Node huffmanTree) {
        this.huffmanTree = huffmanTree;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
