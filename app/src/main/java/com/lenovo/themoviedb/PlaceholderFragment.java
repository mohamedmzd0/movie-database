package com.lenovo.themoviedb;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lenovo.themoviedb.adapter.Adapter;
import com.lenovo.themoviedb.model.MovieResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlaceholderFragment extends Fragment {

    public static final String ARG_SECTION_NUMBER = "section_number";
    ApiServices apiServices;
    int page = 0;
    Adapter adapter;
    ArrayList<MovieResponse.Result> arrayList = new ArrayList<>();
    private RecyclerView mRecyclerView_movie;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private boolean loading = true;
    private int pastVisibleItems, visibleItemCount, totalItemCount;


    public PlaceholderFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        mSwipeRefreshLayout = rootView.findViewById(R.id.swipeRefreshlayuot);
        mRecyclerView_movie = rootView.findViewById(R.id.movieRecycerView);
        int numberOfColumn = 1;
        mSwipeRefreshLayout.setRefreshing(true);
        adapter = new Adapter(arrayList, getContext());
        if (getActivity().getWindowManager().getDefaultDisplay().getWidth() >= 600)
            numberOfColumn = 2;
        else if (getActivity().getWindowManager().getDefaultDisplay().getWidth() >= 900)
            numberOfColumn = 3;
        final StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(numberOfColumn, 1);
        mRecyclerView_movie.setLayoutManager(manager);
        mRecyclerView_movie.setHasFixedSize(true);
        apiServices = ApiClient.getAPI().create(ApiServices.class);
        mRecyclerView_movie.setAdapter(adapter);
        loadMore();
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 0;
                int arrayzie = arrayList.size();
                arrayList.clear();
                if (arrayzie > 0)
                    adapter.notifyDataSetChanged();
                loadMore();
            }
        });

///////////////////
        final String TAG = "TAGGGG";

        mRecyclerView_movie.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                visibleItemCount = manager.getChildCount();
                totalItemCount = manager.getItemCount();
                int[] firstVisibleItems = null;
                firstVisibleItems = manager.findFirstVisibleItemPositions(firstVisibleItems);
                if (firstVisibleItems != null && firstVisibleItems.length > 0) {
                    pastVisibleItems = firstVisibleItems[0];
                }

                if (loading) {
                    if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                        loading = false;
                        mSwipeRefreshLayout.setRefreshing(true);
                        loadMore();
                    }
                }


            }
        });


        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        page = 0;

    }

    private void loadMore() {
        page++;
        if (getArguments().getInt(ARG_SECTION_NUMBER, 0) == 0)
            getTopRated();
        else if (getArguments().getInt(ARG_SECTION_NUMBER, 0) == 1)
            getPopular();
    }

    private void getPopular() {
        loading = true;
        apiServices.getPopular("movie/popular?api_key=832f13a97b5d2df50ecf0dbc8a0f46ae&query=star+wars&page=" + page).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.code() == 200) {
                    if (arrayList.size() == 0) {
                        arrayList.addAll(response.body().getResults());
                        adapter.notifyItemInserted(0);
                    } else {
                        arrayList.addAll(arrayList.size() - 1, response.body().getResults());
                        adapter.notifyItemInserted(arrayList.size());
                    }
                }
                mSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void getTopRated() {
        loading = true;
        apiServices.getTopRated("movie/top_rated?api_key=832f13a97b5d2df50ecf0dbc8a0f46ae&query=star+wars&page=" + page).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (arrayList.size() == 0) {
                    arrayList.addAll(0, response.body().getResults());
                    adapter.notifyItemInserted(0);
                } else {
                    arrayList.addAll(arrayList.size() - 1, response.body().getResults());
                    adapter.notifyItemInserted(arrayList.size());
                }
                mSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }


}
