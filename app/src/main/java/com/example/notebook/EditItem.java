package com.example.notebook;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditItem extends Activity{

    String word;
    String detail;

    EditText wordeEditText;
    EditText detailEditText;

    Button save;

    ContentResolver contentResolver;

    /* (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        setContentView(R.layout.edit);

        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();

        wordeEditText=(EditText)findViewById(R.id.word_list_edit);
        detailEditText=(EditText)findViewById(R.id.detail_list_edit);

        word=bundle.getString("word");
        detail=bundle.getString("detail");

        wordeEditText.setText(word);
        detailEditText.setText(detail);

        save=(Button)findViewById(R.id.positive);

        save.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                contentResolver=getContentResolver();
                ContentValues values=new ContentValues();

                wordeEditText=(EditText)findViewById(R.id.word_list_edit);
                detailEditText=(EditText)findViewById(R.id.detail_list_edit);

                word=wordeEditText.getText().toString();
                detail=detailEditText.getText().toString();

                values.put(Words.Word.WORD, word);
                values.put(Words.Word.DETAIL, detail);

                contentResolver.update(Words.Word.DICT_CONTENT_URI, values, "word like ? or detail like ?",new String[]{"%"+word+"%","%"+detail+"%"});
                EditItem.this.finish();
                Toast.makeText(EditItem.this, "修改成功", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
