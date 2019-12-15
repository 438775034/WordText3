package com.example.wordtext3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Add_Word extends AppCompatActivity {
    private MyDatabaseHelper dbHelper;
    private String eng="123";
    private String chi="123";
    private String exp="123";
    private EditText editText1;
    private EditText editText2;
    private EditText editText3;
    private Button confirm;
    private Button cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__word);
        dbHelper=new MyDatabaseHelper(this,"WordBook.db",null,1);
        editText1=findViewById(R.id.editText1);
        editText2=findViewById(R.id.editText2);
        editText3=findViewById(R.id.editText3);
        confirm=findViewById(R.id.btn_confirm);
        cancel=findViewById(R.id.btn_cancel);


        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                eng=editText1.getText().toString();
                chi=editText2.getText().toString();
                exp=editText3.getText().toString();
                values.put("eng",eng);
                values.put("chi",chi);
                values.put("example",exp);
                db.insert("WordBook",null,values);
                Toast.makeText(view.getContext(),"插入单词成功",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Add_Word.this,Main2Activity.class);
                startActivity(intent);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Add_Word.this,Main2Activity.class);
                startActivity(intent);
            }
        });

    }
}
