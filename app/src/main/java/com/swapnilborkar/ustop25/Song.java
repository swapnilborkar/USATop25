package com.swapnilborkar.ustop25;

/**
 * Created by SWAPNIL on 05-01-2016.
 */
public class Song {

    String name;
    String artist;
    String release;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    @Override
    public String toString() {
        return  " Name: "+getName()+"\n"+
                " Artist: "+getArtist()+"\n"+
                " Release Date: "+getRelease()+"\n";
    }
}


