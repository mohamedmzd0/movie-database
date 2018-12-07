package com.lenovo.themoviedb.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lenovo.themoviedb.R;
import com.lenovo.themoviedb.model.TrailerModel;

import java.util.List;

public class AdapterTrailer extends RecyclerView.Adapter<AdapterTrailer.TrailerHolder> {


    Context context;
    private List<TrailerModel.Result> results;

    public AdapterTrailer(Context context, List<TrailerModel.Result> results) {
        this.results = results;
        this.context = context;
    }

    @NonNull
    @Override
    public TrailerHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new TrailerHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.trailer_item, null));
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerHolder trailerHolder, final int i) {
        trailerHolder.textView_title.setText(results.get(i).getName());

    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    class TrailerHolder extends RecyclerView.ViewHolder {

        TextView textView_title;

        public TrailerHolder(@NonNull View itemView) {
            super(itemView);
            textView_title = itemView.findViewById(R.id.title);
            textView_title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("https://www.youtube.com/watch?v=" + results.get(getAdapterPosition()).getKey()));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
        }
    }
}
