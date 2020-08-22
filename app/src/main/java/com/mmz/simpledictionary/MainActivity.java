package com.mmz.simpledictionary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private RBtree rBtree;
    private TextView wordTV , dictSizeCount , treeHeight;
    private String fileName = "words.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rBtree = new RBtree(this , fileName);
        dictSizeCount = findViewById(R.id.dict_size_counter);
        treeHeight = findViewById(R.id.tree_height_counter);

        dictSizeCount.setText(rBtree.getSize()+"");
        treeHeight.setText(rBtree.getTreeHeight()+"");

        Button insertB = findViewById(R.id.button_insert);
        insertB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String word = wordTV.getText().toString();
                if (TextUtils.isEmpty(word))
                    makeToast(R.string.no_word);
                else {
                    if (word.matches("[a-zA-Z]+")) {
                        if (rBtree.insertRB(word)) {
                            makeToast(R.string.insert_successful);
                            dictSizeCount.setText(rBtree.getSize() + "");
                            treeHeight.setText(rBtree.getTreeHeight() + "");
                            wordTV.setText("");
                        } else
                            makeToast(R.string.word_exists);
                    } else {
                        makeToast(R.string.not_word);
                    }
                }
            }
        });

        Button deleteB = findViewById(R.id.button_remove);
        deleteB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String word = wordTV.getText().toString();
                if (TextUtils.isEmpty(word))
                    makeToast(R.string.no_word);
                else {
                    if (word.matches("[a-zA-Z]+")) {
                        if (rBtree.RBdelete(word)) {
                            makeToast(R.string.delete_successful);
                            dictSizeCount.setText(rBtree.getSize()+"");
                            treeHeight.setText(rBtree.getTreeHeight()+"");
                            wordTV.setText("");
                        } else
                            makeToast(R.string.doesnt_exist);
                    }else {
                        makeToast(R.string.not_word);
                    }
                }
            }
        });
        Button searchB = findViewById(R.id.button_search);
        searchB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String word = wordTV.getText().toString();
                if (TextUtils.isEmpty(word)){
                    makeToast(R.string.no_word);
                }else {
                    if (word.matches("[a-zA-Z]+")) {
                        if (rBtree.wordExists(word))
                            makeToast(R.string.word_exists);
                        else
                            makeToast(R.string.doesnt_exist);
                    } else {
                        makeToast(R.string.not_word);
                    }
                }

            }
        });
        wordTV = findViewById(R.id.word_edit_text);


    }

    @Override
    protected void onResume() {
        super.onResume();
        if (rBtree == null)
            rBtree = new RBtree(this , fileName);
    }

    @Override
    protected void onStop() {
        super.onStop();
        rBtree.saveWords(fileName);
    }

    private void makeToast(int res) {
        Toast.makeText(MainActivity.this, res, Toast.LENGTH_SHORT).show();
    }
}
