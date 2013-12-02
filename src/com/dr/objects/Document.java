package com.dr.objects;

import java.io.Serializable;

/**
 * Created by Pranit on 11/18/13.
 */
public class Document implements Serializable {
    int id;
    String title, fileName, description;
    int type;

    public static final int RESUME = 1;
    public static final int COVER_LETTER = 2;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return this.getTitle();
    }

}
