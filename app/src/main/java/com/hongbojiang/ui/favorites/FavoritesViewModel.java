package com.hongbojiang.ui.favorites;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.hongbojiang.Playlist;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class FavoritesViewModel extends AndroidViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<List<Playlist>> playlists;
    private SharedPreferences shp;
    private SharedPreferences.Editor editor;

    public FavoritesViewModel(@NonNull Application application) {
        super(application);
        mText = new MutableLiveData<>();
        playlists = new MutableLiveData<>();
        playlists.setValue(new ArrayList<Playlist>());
        shp = getApplication().getSharedPreferences("FAV_PLAYLISTS", Context.MODE_PRIVATE);
        load();
        editor = shp.edit();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public MutableLiveData<List<Playlist>> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(List<Playlist> playlists) {
        this.playlists.setValue(playlists);
    }

    public void deletePlaylist(Playlist playlist) {
        editor.remove(playlist.getName());
        editor.apply();
        load();
    }

    public void addPlaylist(Playlist playlist) {
        save(playlist.getName(), playlist.getId());
        load();
    }

    public void save(String key, String value) {
        editor.putString(key, value);
        editor.apply();
    }

    public String read(String key) {
        return shp.getString(key, "");
    }

    private void load() {
        Set<String> keys = shp.getAll().keySet();
        List<Playlist> temp = new ArrayList<>();
        for(String item : keys) {
            temp.add(new Playlist(item, (String)(shp.getAll().get(item))));
        }
        playlists.setValue(temp);
    }
}