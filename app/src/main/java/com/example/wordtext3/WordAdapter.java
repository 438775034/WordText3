package com.example.wordtext3;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class WordAdapter extends RecyclerView.Adapter<WordAdapter.ViewHolder>{
    private List<WordBook> mList;
    private OnItemClickListener onItemClick;//实现单点监听的接口
    private OnItemLongClickListener onItemClickListener;//实现长按监听的接口

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView word_eng;
        TextView word_chi;
        LinearLayout word;
        public ViewHolder(View view){
            super(view);
            word_eng=(TextView)view.findViewById(R.id.word_eng);
            word_chi=(TextView)view.findViewById(R.id.word_chi);
            word=(LinearLayout)view.findViewById(R.id.item_id);
        }
    }
    public WordAdapter(List<WordBook> wordBookStoresList){
        mList= wordBookStoresList;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.word_item,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        WordBook wordBook =mList.get(position);
        holder.word_eng.setText(wordBook.getEng());
        holder.word_chi.setText(wordBook.getChi());
        final int posi=position;

        holder.word.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                onItemClickListener.OnItemLongClick(posi);
                return true;
            }
        });

        holder.word.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClick.OnItemClick(posi);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }







    //定义长按接口
    public interface OnItemLongClickListener {

        void OnItemLongClick(int i);
    }
    //定义单点接口
    public interface OnItemClickListener {
        void OnItemClick(int i);
    }
    //定义长按接口的实现
    public void setOnItemLongClickListener(OnItemLongClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    //定义单点接口的实现
    public void setOnItemClickListener(OnItemClickListener onItemClick) {
        this.onItemClick = onItemClick;
    }

    public void setFilter(List<WordBook>filterWords){
        mList=filterWords;
        notifyDataSetChanged();
    }





    


}

