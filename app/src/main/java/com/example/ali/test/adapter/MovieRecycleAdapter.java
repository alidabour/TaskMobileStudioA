package com.example.ali.test.adapter;

/**
 * Created by Ali on 2/10/2017.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ali.test.R;
import com.example.ali.test.model.Movie;

import java.util.List;


/**
 * Created by Ali on 2/9/2017.
 */

public class MovieRecycleAdapter extends RecyclerView.Adapter<MovieRecycleAdapter.ViewHolder> {
    Context context;
    List<Object> movies;
    final private OnClickHandler onClickHandler;
    public interface OnClickHandler {
        void onClick(Movie movieResult);
    }
    public MovieRecycleAdapter(Context context, List<Object> movies,OnClickHandler onClickHandler){
        this.context = context;
        this.movies=movies;
        this.onClickHandler = onClickHandler;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.poster_card_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String posterURL = "http://image.tmdb.org/t/p/w185/" + ((Movie)movies.get(position)).getPosterUrl();
        String title = ((Movie)movies.get(position)).getTitle();
        holder.title.setText(title);
        Glide.with(context).load(posterURL).into(holder.poster);
    }

    @Override
    public int getItemCount() {
        return movies.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView title;
        public ImageView poster;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            poster = (ImageView) itemView.findViewById(R.id.poster);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            onClickHandler.onClick((Movie)movies.get(position));
        }
    }

}