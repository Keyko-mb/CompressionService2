package ru.shafikovs.ComressionService.Services;

import ru.shafikovs.ComressionService.Models.Node;

import java.util.Queue;

class Tree {
    public Node creatingHuffmanTree(Queue<Node> queueOfNodes, int size) {
        while (queueOfNodes.peek().getiData() != size) {
            Node newNode = new Node();
            newNode.setLeftChild(queueOfNodes.remove());
            newNode.setRightChild(queueOfNodes.remove());
            newNode.setiData((newNode.getLeftChild().getiData() + newNode.getRightChild().getiData()));
            queueOfNodes.add(newNode);
        }
        return queueOfNodes.remove();
    }

    public String getCodeForSymbol(Node localRoot, Character symbol, String line) {
        if (localRoot.getfData() == symbol) {
            return line;
        } else {
            if (localRoot.getLeftChild() != null) {
                String path = getCodeForSymbol(localRoot.getLeftChild(), symbol, line + 0);
                if (path != null) {
                    return path;
                }
            }
            if (localRoot.getRightChild() != null) {
                String path = getCodeForSymbol(localRoot.getRightChild(), symbol, line + 1);
                if (path != null) {
                    return path;
                }
            }
        }
        return null;
    }
}
