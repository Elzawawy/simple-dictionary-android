package com.mmz.simpledictionary;

public class RBNode {
    private String key;
    private boolean red;
    private RBNode left , right , parent;

    public RBNode(){
        red = false;
    }

    public RBNode(String key){
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

    public RBNode getLeft() {
        return left;
    }

    public void setLeft(RBNode left) {
        this.left = left;
    }

    public RBNode getRight() {
        return right;
    }

    public void setRight(RBNode right) {
        this.right = right;
    }

    public RBNode getParent() {
        return parent;
    }

    public void setParent(RBNode parent) {
        this.parent = parent;
    }

    @Override
    public String toString() {
        if (red)
            return "R," + key;
        return "B," + key;
    }
}
