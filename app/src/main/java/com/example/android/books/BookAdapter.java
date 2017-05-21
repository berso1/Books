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

    private List<Book> books;
    private Context context;
    TextView emptyView;


    public BookAdapter(Context context, List<Book> books,TextView emptyView){
        this.books = books;
        this.context = context;
        this.emptyView = emptyView;
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
       // bookView = LayoutInflater.from(context).inflate(R.layout.recyclerview_items,parent,false);
      //  bookViewHolder = new viewHolder(bookView);
       // return bookViewHolder;

        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View bookView = inflater.inflate(R.layout.recyclerview_items, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(bookView);
        return viewHolder;

    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position){

        //Get current Book
        Book book = books.get(position);

        //Set the items of Recycler view
        TextView title    = holder.title;
        TextView subtitle = holder.subtitle;
        TextView autors   = holder.autors;

        autors.setText(book.getAutors());
        title.setText(book.getTitle());
        subtitle.setText(book.getSubtitle());


    }

    @Override
    public int getItemCount(){
        //Set the emptyView if no books
        emptyView.setText(R.string.no_books);
        if(books.size() == 0) {
            emptyView.setVisibility(View.VISIBLE);
        }else{
            emptyView.setVisibility(View.GONE);
        }
        return books.size();
    }

    public void updateItems(List<Book> books) {
        this.books = books;
        notifyDataSetChanged();
    }



}
