package com.example.android.books;

/**
 * Created by berso on 5/16/17.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder>{

    List<Book> books;
    Context context;


    View bookView;
    ViewHolder bookViewHolder;

    public BookAdapter(Context context, List<Book> books){

        this.books = books;
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView title;
        private TextView subtitle;
        private TextView autors;

        public ViewHolder(View view){

            super(view);

            title     = (TextView)view.findViewById(R.id.title_textview);
            subtitle  = (TextView)view.findViewById(R.id.subtitle_textview);
            autors    = (TextView)view.findViewById(R.id.autor_textview);
        }
    }


    @Override
    public BookAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){

        bookView = LayoutInflater.from(context).inflate(R.layout.recyclerview_items,parent,false);

        bookViewHolder = new ViewHolder(bookView);

        return bookViewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){

        //Get current Book
        Book book = books.get(position);

        //Set the items of Recycler view
        TextView title    = holder.title;
        TextView subtitle = holder.subtitle;
        TextView autors   = holder.autors;

        title.setText(book.getTitle());
        subtitle.setText(book.getSubtitle());
        autors.setText("Author(s): "+book.getAutors());

    }

    @Override
    public int getItemCount(){
        return books.size();
    }

    public void updateItems(List<Book> books) {
        this.books = books;
        notifyDataSetChanged();
    }

}
