package com.example.wordtext3;


import android.app.AlertDialog;
import android.app.Dialog;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final List<WordBook> WordList=new ArrayList<>();
    private List<WordBook> filterList=new ArrayList<>();

    int ID;
    int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initBook();
        RecyclerView recyclerView =(RecyclerView)findViewById(R.id.Recycler_View);
        SearchView searchView = findViewById(R.id.search_word);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        final WordAdapter wordAdapter =new WordAdapter(WordList);
        recyclerView.setAdapter(wordAdapter);

//        长按删除单词
        wordAdapter.setOnItemLongClickListener(new WordAdapter.OnItemLongClickListener() {
            @Override
            public void OnItemLongClick(int i) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                ID=WordList.get(i).getId();
                position=i;
                String word = WordList.get(i).getEng();
                builder.setTitle("提示");
                builder.setMessage("确认删除单词"+word+"吗?");
                builder.setPositiveButton("确 定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //删除单词
                        dialogInterface.cancel();
                        deleteWord();
                        WordList.remove(position);
                        wordAdapter.notifyDataSetChanged();
                        Toast.makeText(MainActivity.this,"删除成功！",Toast.LENGTH_SHORT).show();

                    }
                });

                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                Dialog dialog = builder.create();
                dialog.show();

            }
        });

//        点击编辑单词
        wordAdapter.setOnItemClickListener(new WordAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(int i) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                View view = View.inflate(MainActivity.this, R.layout.cheak_dialog, null);
                final EditText edit_eng=(EditText)view.findViewById(R.id.edit_eng);
                final EditText edit_chi=(EditText)view.findViewById(R.id.edit_chi);
                final EditText edit_exp=(EditText)view.findViewById(R.id.edit_exp);
                ID=WordList.get(i).getId();
                position=i;
                Log.i("IDDD",String.valueOf(ID));
                Log.i("IDDD1",WordList.get(position).getEng());
                String s = edit_eng.getText().toString();
                Log.i("IDDD2",s);
                edit_eng.setText(WordList.get(position).getEng());
                edit_chi.setText(WordList.get(position).getChi());
                edit_exp.setText(WordList.get(position).getExp());
                Log.i("IIIIIII",String.valueOf(i));

                builder.setTitle("单词编辑");
                builder.setView(view);
                builder.setPositiveButton("确 定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //修改单词
                        WordList.get(position).setEng(edit_eng.getText().toString());
                        WordList.get(position).setChi(edit_chi.getText().toString());
                        WordList.get(position).setExp(edit_exp.getText().toString());
                        updateWord(WordList.get(position));
                        wordAdapter.notifyDataSetChanged();
                        dialogInterface.cancel();
                        Toast.makeText(MainActivity.this,"修改成功！",Toast.LENGTH_SHORT).show();

                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                Dialog dialog = builder.create();
                dialog.show();

            }
        });


//        搜索框点击
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                filterList=filter(s);
                wordAdapter.setFilter(filterList);
                return true;
            }
        });

    }



//    初始化单词本列表，获取数据库内容
    private void initBook(){
        String sqlSelect="select * from 'WordBook'";
        //扫描数据库，将信息放入wordlist
        MyDatabaseHelper mdb = new MyDatabaseHelper(this, "WordBook.db", null, 1);//打开数据库
        SQLiteDatabase sd = mdb.getReadableDatabase();//获取数据库
        Cursor cursor=sd.rawQuery(sqlSelect,new String[]{});
        Log.i("test_Count",String.valueOf(cursor.getCount()));
        while(cursor.moveToNext()){
            int id=cursor.getInt(cursor.getColumnIndex("id"));
            String eng=cursor.getString(cursor.getColumnIndex("eng"));
            String chi=cursor.getString(cursor.getColumnIndex("chi"));
            String exp=cursor.getString(cursor.getColumnIndex("example"));
            WordBook wordBook =new WordBook(id,eng,chi,exp);
            WordList.add(wordBook);
        }
        cursor.close();

    }

    public int deleteWord() {
        MyDatabaseHelper mdb = new MyDatabaseHelper(this, "WordBook.db", null, 1);//打开数据库
        SQLiteDatabase sd = mdb.getWritableDatabase();//获取数据库
        return sd.delete("WordBook", "id=?", new String[] { String.valueOf(ID) });
    }
    public int updateWord(WordBook word) {
        MyDatabaseHelper mdb = new MyDatabaseHelper(this, "WordBook.db", null, 1);//打开数据库
        SQLiteDatabase sd = mdb.getWritableDatabase();//获取数据库
        ContentValues value = new ContentValues();
        value.put("eng", word.getEng());
        value.put("chi", word.getChi());
        value.put("example", word.getExp());
        return sd.update("WordBook", value, "id=?", new String[] { String.valueOf(ID) });
    }


    //模糊查询
    private List<WordBook>filter(String text){
        filterList=new ArrayList<>();

        for (WordBook word:WordList){
            if (word.getExp().contains(text)||word.getChi().contains(text))
                filterList.add(word);
        }
        return filterList;
    }
}

