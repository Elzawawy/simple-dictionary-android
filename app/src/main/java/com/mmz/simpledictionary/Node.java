package com.mmz.simpledictionary;

public class Node {
    private String key;
    private boolean red;
    private Node left , right , parent;

    public Node(){
        red = false;
    }


    public Node(String key){
        this.key = key;
        red = true;
        left = right = parent =null;
    }



    public String getKey() {
        return key;
    }

    public boolean isRed() {
        return red;
    }

    public void setRed(boolean red) {
        this.red = red;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    @Override
    public String toString() {
        if (red)
            return "R," + key;
        return "B," + key;
    }
}
