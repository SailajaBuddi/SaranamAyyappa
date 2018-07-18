package com.anvesh.saranamayyappa.model;

/**
 * Created by Hi on 27-06-2018.
 */
public class FamousTalkProvider {
    int image;
    String text,name;

    public FamousTalkProvider(int image, String text, String name) {
        this.image = image;
        this.text = text;
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}