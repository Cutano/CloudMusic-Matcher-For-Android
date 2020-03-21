package com.hongbojiang;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Song {
    private String name;
    private String url;
    private String id;
    private int index;

    public Song(String name, String url, String id) {
        this.name = name;
        this.url = url;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getId() {
        return id;
    }

    public int getIndex() {
        return index;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof Song) {
            Song p = (Song) obj;
            return this.id.equals(p.id);
        } else {
            return false;
        }
    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }
}
