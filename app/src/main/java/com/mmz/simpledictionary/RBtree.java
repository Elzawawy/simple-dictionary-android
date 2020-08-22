package com.mmz.simpledictionary;

import android.content.Context;
import android.util.Log;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class RBtree {
    private static final String TAG = RBtree.class.getName();
    private int nodeCount = 0;
    private RBNode root;
    private RBNode nil;
    private Context context;

    public RBtree(Context context, String fileName) {
        this.context = context;
        nil = new RBNode();
        root = nil;
        File dictionary = new File(context.getFilesDir(), fileName);
        if (!dictionary.exists())
            populateWithDef(fileName);
        else
            loadDict(fileName);
    }

    public void saveWords(String fileName) {
        FileOutputStream outputStream = null;
        try {
            outputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            saveWord(outputStream, root);
            outputStream.close();
        } catch (IOException e) {
            Log.e(TAG, "error");
        }
    }

    public boolean insertRB(String key) {
        //create new node z , left = right = null , color = red
        RBNode z = new RBNode(key);
        //parent of the currently being inserted node
        RBNode parentOfZ = nil;
        //root of RB tree
        RBNode x = root;
        while (x != nil) {
            parentOfZ = x;
            if (z.getKey().compareToIgnoreCase(x.getKey()) < 0)
                x = x.getLeft();
            else if (z.getKey().compareToIgnoreCase(x.getKey()) > 0)
                x = x.getRight();
            else
                return false;
        }
        z.setParent(parentOfZ);
        //if it was the first element
        if (parentOfZ == nil)
            root = z;
        else if (z.getKey().compareToIgnoreCase(parentOfZ.getKey()) < 0)
            parentOfZ.setLeft(z);
        else
            parentOfZ.setRight(z);

        z.setLeft(nil);
        z.setRight(nil);
        RBinsertFixUp(z);
        //increment counter
        nodeCount++;
        return true;
    }

    public boolean RBdelete(String key) {
        RBNode z = RBsearch(key);
        if (z == nil)
            return false;
        RBNode succesor = z;
        RBNode x = null;
        boolean successorOrgColor = succesor.isRed();
        if (z.getLeft() == nil) {
            x = z.getRight();
            RBtransplant(z, z.getRight());
        } else if (z.getRight() == nil) {
            x = z.getLeft();
            RBtransplant(z, z.getLeft());
        } else {
            succesor = treeMin(z.getRight());
            successorOrgColor = succesor.isRed();
            x = succesor.getRight();
            if (succesor.getParent() == z) {
                x.setParent(succesor);
            } else {
                RBtransplant(succesor, succesor.getRight());
                succesor.setRight(z.getRight());
                succesor.getRight().setParent(succesor);
            }
            RBtransplant(z, succesor);
            succesor.setLeft(z.getLeft());
            succesor.getLeft().setParent(succesor);
            succesor.setRed(z.isRed());
        }
        if (!successorOrgColor)
            RBdeleteFixUp(x);
        nodeCount--;
        return true;
    }

    public boolean wordExists(String key) {
        if (RBsearch(key) != nil)
            return true;
        return false;
    }

    public int getTreeHeight() {
        if (nodeCount == 0)
            return 0;
        return (int) Math.floor(Math.log(nodeCount) / Math.log(2));
    }

    public int getSize() {
        return nodeCount;
    }

    //temp method to print RB tree
    public void print() {
        System.out.println("-------------------------");
        if (root == nil)
            return;
        int maxChars = (int) Math.pow(2, Math.floor(Math.log(nodeCount) / Math.log(2)));
        maxChars = maxChars + maxChars - 1;
        ArrayList<RBNode> nodesInLevel = new ArrayList<>();
        nodesInLevel.add(root);
        while (nodesInLevel.size() != 0) {
            int currentSize = nodesInLevel.size();
            int step = (int) Math.ceil(maxChars / currentSize * 2);
            for (int i = 0; i < currentSize; i++) {
                System.out.print(nodesInLevel.get(i).toString() + " ");
                if (nodesInLevel.get(i).getLeft() != null)
                    nodesInLevel.add(nodesInLevel.get(i).getLeft());
                if (nodesInLevel.get(i).getRight() != null)
                    nodesInLevel.add(nodesInLevel.get(i).getRight());
            }
            for (int i = 0; i < currentSize; i++) {
                nodesInLevel.remove(0);
            }
            System.out.println();
        }

    }

    private void populateWithDef(String fileName) {

        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        FileOutputStream outputStream = null;
        try {
            inputStream = context.getAssets().open("testfile.txt");
            outputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);

            if (inputStream != null) {
                inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                bufferedReader = new BufferedReader(inputStreamReader);
                String word = "";
                while ((word = bufferedReader.readLine()) != null) {
                    String string = word.trim() + "\n";
                    outputStream.write(string.getBytes());
                    insertRB(word);
                }
            }

            inputStream.close();
            inputStreamReader.close();
            bufferedReader.close();
            outputStream.close();

        } catch (FileNotFoundException e) {
            Log.e(TAG, "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e(TAG, "Can not read file: " + e.toString());
        }
    }

    private void loadDict(String fileName) {
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;

        try {
            inputStream = context.openFileInput(fileName);

            if (inputStream != null) {
                inputStreamReader = new InputStreamReader(inputStream);
                bufferedReader = new BufferedReader(inputStreamReader);
                String word = "";
                while ((word = bufferedReader.readLine()) != null) {
                    insertRB(word.trim());
                }

                inputStream.close();
                inputStreamReader.close();
                bufferedReader.close();

            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

    }

    private void saveWord(FileOutputStream outputStream, RBNode node) throws IOException {
        if (node == nil)
            return;
        saveWord(outputStream, node.getLeft());
        String string = node.getKey() + "\n";
        outputStream.write(string.getBytes());
        saveWord(outputStream, node.getRight());
    }

    private void RBinsertFixUp(RBNode z) {
        RBNode uncle = null;
        while (z.getParent().isRed()) {
            if (z.getParent() == z.getParent().getParent().getLeft()) {
                uncle = z.getParent().getParent().getRight();
                if (uncle.isRed()) {
                    z.getParent().setRed(false);
                    uncle.setRed(false);
                    z.getParent().getParent().setRed(true);
                    z = z.getParent().getParent();
                } else if (z == z.getParent().getRight()) {
                    z = z.getParent();
                    leftRotate(z);
                    z.getParent().setRed(false);
                    z.getParent().getParent().setRed(true);
                    rightRotate(z.getParent().getParent());
                } else {
                    z.getParent().setRed(false);
                    z.getParent().getParent().setRed(true);
                    rightRotate(z.getParent().getParent());
                }
            } else {
                uncle = z.getParent().getParent().getLeft();
                if (uncle.isRed()) {
                    z.getParent().setRed(false);
                    uncle.setRed(false);
                    z.getParent().getParent().setRed(true);
                    z = z.getParent().getParent();
                } else if (z == z.getParent().getLeft()) {
                    z = z.getParent();
                    rightRotate(z);
                    z.getParent().setRed(false);
                    z.getParent().getParent().setRed(true);
                    leftRotate(z.getParent().getParent());
                } else {
                    z.getParent().setRed(false);
                    z.getParent().getParent().setRed(true);
                    leftRotate(z.getParent().getParent());
                }
            }
        }

        root.setRed(false);
    }

    private void leftRotate(RBNode x) {
        RBNode y = x.getRight();
        x.setRight(y.getLeft());
        if (y.getLeft() != nil)
            y.getLeft().setParent(x);
        y.setParent(x.getParent());
        if (x.getParent() == nil)
            root = y;
        else if (x == x.getParent().getLeft())
            x.getParent().setLeft(y);
        else
            x.getParent().setRight(y);
        y.setLeft(x);
        x.setParent(y);
    }

    private void rightRotate(RBNode x) {
        RBNode y = x.getLeft();
        x.setLeft(y.getRight());
        if (y.getRight() != nil)
            y.getRight().setParent(x);
        y.setParent(x.getParent());
        if (x.getParent() == nil)
            root = y;
        else if (x == x.getParent().getRight())
            x.getParent().setRight(y);
        else
            x.getParent().setLeft(y);
        y.setRight(x);
        x.setParent(y);
    }

    private void RBtransplant(RBNode u, RBNode v) {
        if (u.getParent() == nil)
            root = v;
        else if (u == u.getParent().getLeft())
            u.getParent().setLeft(v);
        else
            u.getParent().setRight(v);
        v.setParent(u.getParent());
    }

    private RBNode treeMin(RBNode x) {
        while (x.getLeft() != nil)
            x = x.getLeft();
        return x;
    }

    private RBNode RBsearch(String key) {
        RBNode x = root;
        boolean found = false;
        while (x != nil) {
            if (x.getKey().compareToIgnoreCase(key) == 0) {
                found = true;
                return x;
            } else if (x.getKey().compareToIgnoreCase(key) > 0)
                x = x.getLeft();
            else
                x = x.getRight();
        }
        if (found)
            return x;
        return nil;
    }

    private void RBdeleteFixUp(RBNode x) {
        RBNode sibling = null;
        while (x != root && !x.isRed()) {
            if (x == x.getParent().getLeft()) {
                sibling = x.getParent().getRight();
                if (sibling.isRed()) {
                    sibling.setRed(false);
                    x.getParent().setRed(true);
                    leftRotate(x.getParent());
                    sibling = x.getParent().getRight();
                }
                if (!sibling.getLeft().isRed() && !sibling.getRight().isRed()) {
                    sibling.setRed(true);
                    x = x.getParent();
                } else if (!sibling.getRight().isRed()) {
                    sibling.getLeft().setRed(false);
                    sibling.setRed(true);
                    rightRotate(sibling);
                    sibling = x.getParent().getRight();

                    sibling.setRed(x.getParent().isRed());
                    x.getParent().setRed(false);
                    sibling.getRight().setRed(false);
                    leftRotate(x.getParent());
                    x = root;
                } else {
                    sibling.setRed(x.getParent().isRed());
                    x.getParent().setRed(false);
                    sibling.getRight().setRed(false);
                    leftRotate(x.getParent());
                    x = root;
                }
            } else {
                sibling = x.getParent().getLeft();
                if (sibling.isRed()) {
                    sibling.setRed(false);
                    x.getParent().setRed(true);
                    rightRotate(x.getParent());
                    sibling = x.getParent().getLeft();
                }
                if (!sibling.getRight().isRed() && !sibling.getLeft().isRed()) {
                    sibling.setRed(true);
                    x = x.getParent();
                } else if (!sibling.getLeft().isRed()) {
                    sibling.getRight().setRed(false);
                    sibling.setRed(true);
                    leftRotate(sibling);
                    sibling = x.getParent().getLeft();

                    sibling.setRed(x.getParent().isRed());
                    x.getParent().setRed(false);
                    sibling.getLeft().setRed(false);
                    rightRotate(x.getParent());
                    x = root;
                } else {
                    sibling.setRed(x.getParent().isRed());
                    x.getParent().setRed(false);
                    sibling.getLeft().setRed(false);
                    rightRotate(x.getParent());
                    x = root;
                }
            }
        }
        x.setRed(false);
    }
}
