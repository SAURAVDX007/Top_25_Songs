package com.example.saurav.top_25_songs;

/**
 * Created by saura on 04-12-2016.
 */

public class Application {
    private String name;
    private String artist;
    private String price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Name: " + name + "\n" +
                "Singer: " + artist + "\n" +
                "Price: " + price +"\n";
    }
}
