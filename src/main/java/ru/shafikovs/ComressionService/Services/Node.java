package ru.shafikovs.ComressionService.Services;

import java.io.Serializable;

public class Node implements Comparable<Node>, Serializable {
    int iData;
    Character fData;
    Node leftChild;
    Node rightChild;

    @Override
    public int compareTo(Node obj) {
        return (this.iData - obj.iData);
    }

    @Override
    public String toString() {
        return "Node{" +
                "iData=" + iData +
                ", fData=" + fData +
                ", leftChild=" + leftChild +
                ", rightChild=" + rightChild +
                '}';
    }
}