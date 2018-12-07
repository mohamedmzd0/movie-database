package com.lenovo.themoviedb.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.lenovo.themoviedb.DetailActivity;
import com.lenovo.themoviedb.R;
import com.lenovo.themoviedb.model.MovieResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.Holder> {

    List<MovieResponse.Result> results;
    Context context;

    public Adapter(List<MovieResponse.Result> results, Context context) {
        this.results = results;
        this.context = context;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_recyclerview, null);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, final int i) {
        holder.name.setText(results.get(i).getTitle());
        holder.detail.setText(results.get(i).getOriginalTitle());
        holder.ratingBar.setRating(results.get(i).getVoteAverage() / 2);
        Picasso.get().load(context.getString(R.string.poster_path) + results.get(i).getPosterPath()).into(holder.poster);

    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        ImageView poster;
        TextView name, detail;
        RatingBar ratingBar;

        public Holder(@NonNull View itemView) {
            super(itemView);
            poster = itemView.findViewById(R.id.poster_imageView);
            name = itemView.findViewById(R.id.name_textView);
            detail = itemView.findViewById(R.id.detail_textView);
            ratingBar = itemView.findViewById(R.id.ratingbar);
            ratingBar.setMax(5);
            ratingBar.setStepSize(.1f);
            ratingBar.setEnabled(false);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra(DetailActivity.MOVIE_ID, results.get(getAdapterPosition()).getId());
                    intent.putExtra(DetailActivity.MOVIE_POSTER, context.getString(R.string.poster_path) + results.get(getAdapterPosition()).getPosterPath());
                    Pair<View, String> p1 = Pair.create((View) poster, context.getString(R.string.image_transition));
                    ActivityOptionsCompat options = ActivityOptionsCompat.
                            makeSceneTransitionAnimation((Activity) context, p1);

                    context.startActivity(intent, options.toBundle());
                }
            });
        }

    }
}
