package com.songwenju.androidtvstudy;

import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * songwenju on 17-3-29 : 10 : 47.
 * 邮箱：songwenju@outlook.com
 */

public class Movie implements Serializable{
    static final long serialVersionUID = 727566175075960653L;
    private long id;
    private String title;
    private String studio;
    private String cardImageUrl;
    private String description;
    private String videoUrl;


    public Movie() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStudio() {
        return studio;
    }

    public void setStudio(String studio) {
        this.studio = studio;
    }

    public String getCardImageUrl() {
        return cardImageUrl;
    }

    public void setCardImageUrl(String cardImageUrl) {
        this.cardImageUrl = cardImageUrl;
    }

    //转URL为URI
    public URI getCardImageURI() {
        try {
            return new URI(getCardImageUrl());
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", studio='" + studio + '\'' +
                ", cardImageUrl='" + cardImageUrl + '\'' +
                ", videoUrl='" + videoUrl + '\'' +
                '}';
    }
}
