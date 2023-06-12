package ru.shafikovs.ComressionService.Services;

import java.util.Queue;

class Tree {
    public Node creatingHuffmanTree(Queue<Node> queueOfNodes, int size) {
        while (queueOfNodes.peek().iData != size) {
            Node newNode = new Node();
            newNode.leftChild = queueOfNodes.remove();
            newNode.rightChild = queueOfNodes.remove();
            newNode.iData = (newNode.leftChild.iData + newNode.rightChild.iData);
            queueOfNodes.add(newNode);
        }
        return queueOfNodes.remove();
    }

    public String getCodeForSymbol(Node localRoot, Character symbol, String line) {
        if (localRoot.fData == symbol) {
            return line;
        } else {
            if (localRoot.leftChild != null) {
                String path = getCodeForSymbol(localRoot.leftChild, symbol, line + 0);
                if (path != null) {
                    return path;
                }
            }
            if (localRoot.rightChild != null) {
                String path = getCodeForSymbol(localRoot.rightChild, symbol, line + 1);
                if (path != null) {
                    return path;
                }
            }
        }
        return null;
    }
}
