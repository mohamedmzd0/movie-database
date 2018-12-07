package com.lenovo.themoviedb.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class MovieResponse {

    @SerializedName("results")
    @Expose
    private ArrayList<Result> results = null;

    public ArrayList<Result> getResults() {
        return results;
    }


    public class Result {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("vote_average")
        @Expose
        private Float voteAverage;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("poster_path")
        @Expose
        private String posterPath;
        @SerializedName("original_title")
        @Expose
        private String originalTitle;
        @SerializedName("overview")
        @Expose
        private String overview;
        @SerializedName("release_date")
        @Expose
        private String releaseDate;

          public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public float getVoteAverage() {
            return voteAverage;
        }


        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }


        public String getPosterPath() {
            return posterPath;
        }


        public String getOriginalTitle() {
            return originalTitle;
        }


    }
}