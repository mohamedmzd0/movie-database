package com.lenovo.themoviedb;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.lenovo.themoviedb.adapter.AdapterTrailer;
import com.lenovo.themoviedb.model.MovieDetailsResponse;
import com.lenovo.themoviedb.model.TrailerModel;
import com.squareup.picasso.Picasso;

import java.net.HttpURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    public static final String MOVIE_ID = "movie_id";
    public static final String MOVIE_POSTER = "MOVIE_POSTER";
    ImageView imageView;
    RatingBar ratingBar;
    TextView overview_tv, date_tv, language_tv;
    RecyclerView recyclerView;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        imageView = findViewById(R.id.poster_imageView);
        overview_tv = findViewById(R.id.overview_tv);
        fab = findViewById(R.id.fab);
        date_tv = findViewById(R.id.date_tv);
        recyclerView = findViewById(R.id.trailers);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3, 1);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = getIntent().getIntExtra(MOVIE_ID, 1);
                (getSharedPreferences("Movie", MODE_PRIVATE).edit()).putBoolean(String.valueOf(id), !getSharedPreferences("Movie", MODE_PRIVATE).getBoolean(String.valueOf(id), false)).commit();
                checkFav(id);
            }
        });
        Picasso.get().load(getIntent().getStringExtra(MOVIE_POSTER)).into(imageView);
        ratingBar = findViewById(R.id.ratingbar);
        ratingBar.setMax(5);
        ratingBar.setStepSize(.1f);
        ratingBar.setEnabled(false);
        language_tv = findViewById(R.id.language_tv);
        ApiClient.getAPI().create(ApiServices.class).getoverview("movie/" + getIntent().getIntExtra(MOVIE_ID, 1) +
                "?api_key=832f13a97b5d2df50ecf0dbc8a0f46ae").enqueue(new Callback<MovieDetailsResponse>() {
            @Override
            public void onResponse(Call<MovieDetailsResponse> call, Response<MovieDetailsResponse> response) {
                if (response.code() == 200) {
                    CollapsingToolbarLayout toolbarLayout = findViewById(R.id.collapsing_toolbar);
                    toolbarLayout.setTitle(response.body().getTitle());
                    overview_tv.setText(response.body().getOverview());
                    date_tv.setText("published date " + response.body().getReleaseDate());
                    ratingBar.setRating(response.body().getVoteAverage() / 2);
                    language_tv.setText("original language " + response.body().getOriginalLanguage());
                    getTrailers(getIntent().getIntExtra(MOVIE_ID, 1));
                    checkFav(getIntent().getIntExtra(MOVIE_ID, 1));
                } else
                    Toast.makeText(DetailActivity.this, "" + response.message(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<MovieDetailsResponse> call, Throwable t) {
                Toast.makeText(DetailActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkFav(Integer id) {
        if (getSharedPreferences("Movie", MODE_PRIVATE).getBoolean(String.valueOf(id), false)) {
            fab.setImageResource(R.drawable.ic_favorite_black_24dp);
        } else fab.setImageResource(R.drawable.ic_favorite_border_black_24dp);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            supportFinishAfterTransition();
        return true;
    }

    private void getTrailers(final int id) {
        Call<TrailerModel> TrailerModelCall = ApiClient.getAPI().create(ApiServices.class).getmovieDetail("movie/" + id + "/videos?api_key=832f13a97b5d2df50ecf0dbc8a0f46ae");
        TrailerModelCall.enqueue(new Callback<TrailerModel>() {
            @Override
            public void onResponse(Call<TrailerModel> call, Response<TrailerModel> response) {
                if (response.code() == HttpURLConnection.HTTP_OK)
                    recyclerView.setAdapter(new AdapterTrailer(getApplicationContext(), response.body().getResults()));
            }

            @Override
            public void onFailure(Call<TrailerModel> call, Throwable t) {

            }
        });
    }

}
