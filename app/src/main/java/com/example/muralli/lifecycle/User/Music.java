package com.example.muralli.lifecycle.User;

/**
 * Created by Muralli on 14-07-2018.
 */
public class Music {
    String song;
    String movie;

    public Music(String song, String movie) {
        this.song = song;
        this.movie = movie;
    }
    public Music(){}

    public String getSong() {
        return song;
    }

    public void setSong(String song) {
        this.song = song;
    }

    public String getMovie() {
        return movie;
    }

    public void setMovie(String movie) {
        this.movie = movie;
    }
}
