package com.lenovo.themoviedb.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MovieDetailsResponse {


    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("original_language")
    @Expose
    private String originalLanguage;
    @SerializedName("overview")
    @Expose
    private String overview;
    @SerializedName("release_date")
    @Expose
    private String releaseDate;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("vote_average")
    @Expose
    private Float voteAverage;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getOriginalLanguage() {
        return originalLanguage;
    }


    public String getOverview() {
        return overview;
    }


    public String getReleaseDate() {
        return releaseDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public float getVoteAverage() {
        return voteAverage;
    }


}

