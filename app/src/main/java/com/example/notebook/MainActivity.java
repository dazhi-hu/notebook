package com.example.notebook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends Activity {
    private MyDatabaseHelper dbHelper;
    ActionBar actionBar;
    ContentResolver contentResolver;
    Button insert=null;
    Button search=null;
    Button delete=null;

    ImageButton clear1;
    ImageButton clear2;
    ImageButton clear3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new MyDatabaseHelper(this,"book.db",1);
        dbHelper.getWritableDatabase();

        actionBar=getActionBar();
        //actionBar.setDisplayShowHomeEnabled(false);
        //actionBar.hide();

        contentResolver=getContentResolver();
        insert=(Button)findViewById(R.id.insert);
        search=(Button)findViewById(R.id.search);
        delete=(Button)findViewById(R.id.delete);

        delete.setText("清空单词本");

        insert.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                String word=((EditText)findViewById(R.id.word)).getText().toString();
                String detail=((EditText)findViewById(R.id.detail)).getText().toString();
                if(word.equals("")||detail.equals("")){
                    Toast.makeText(MainActivity.this, "输入无效", Toast.LENGTH_SHORT).show();
                }
                else{
                    ContentValues values=new ContentValues();
                    values.put(Words.Word.WORD, word);
                    System.out.println(word);
                    values.put(Words.Word.DETAIL, detail);
                    contentResolver.insert(Words.Word.DICT_CONTENT_URI, values);
                    Toast.makeText(MainActivity.this, "添加单词成功", Toast.LENGTH_LONG).show();
                    ((EditText)findViewById(R.id.word)).setText("");
                    ((EditText)findViewById(R.id.detail)).setText("");
                }



            }
        });
        search.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String key=((EditText)findViewById(R.id.search_edittext)).getText().toString();
                Cursor cursor=contentResolver.query(Words.Word.DICT_CONTENT_URI, null, "word like ? or detail like ?", new String[]{"%"+key+"%","%"+key+"%"},null);
                Bundle bundle=new Bundle();
                bundle.putSerializable("data", converCursorToList(cursor));
                Intent intent=new Intent();
                intent.setClass(MainActivity.this, com.example.notebook.Result.class);
                intent.putExtras(bundle);
                startActivity(intent);
                ((EditText)findViewById(R.id.search_edittext)).setText("");
            }
        });

        delete.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                AlertDialog.Builder builder=new Builder(MainActivity.this);
                builder.setTitle("确定删除所有单词？");

                builder.setPositiveButton("确定",new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        contentResolver.delete(Words.Word.DICT_CONTENT_URI, null, null);
                        Toast.makeText(MainActivity.this, "删除成功", Toast.LENGTH_LONG).show();
                    }
                });

                builder.setNegativeButton("取消",new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub

                    }
                });

                builder.show();

            }
        });

        clear1=(ImageButton)findViewById(R.id.clear1);
        clear2=(ImageButton)findViewById(R.id.clear2);
        clear3=(ImageButton)findViewById(R.id.clear3);

        clear1.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                ((EditText)findViewById(R.id.word)).setText("");
            }
        });
        clear2.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                ((EditText)findViewById(R.id.detail)).setText("");
            }
        });
        clear3.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                ((EditText)findViewById(R.id.search_edittext)).setText("");
            }
        });

    }

    protected ArrayList<Map<String, String>> converCursorToList(Cursor cursor) {
        // TODO Auto-generated method stub
        ArrayList<Map<String, String>> result=new ArrayList<Map<String,String>>();
        while(cursor.moveToNext()){
            Map<String, String> map=new HashMap<String, String>();
            map.put(Words.Word.WORD, cursor.getString(1));
            map.put(Words.Word.DETAIL, cursor.getString(2));
            result.add(map);
        }
        return result;
    }

}