package com.lenovo.themoviedb;


import com.lenovo.themoviedb.model.MovieDetailsResponse;
import com.lenovo.themoviedb.model.MovieResponse;
import com.lenovo.themoviedb.model.TrailerModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface ApiServices {

    @GET
    Call<MovieResponse> getPopular(@Url String url);

    @GET
    Call<MovieResponse> getTopRated(@Url String url);

    @GET
    Call<MovieDetailsResponse> getoverview(@Url String url);

    @GET
    Call<TrailerModel> getmovieDetail(@Url String url);
}
