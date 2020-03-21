package com.hongbojiang.ui.home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.hongbojiang.Song;

import java.util.List;

public class HomeViewModel extends AndroidViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<String> firstID;
    private MutableLiveData<String> secondID;
    private MutableLiveData<List<Song>> firstList;
    private MutableLiveData<List<Song>> secondList;
    private MutableLiveData<List<Song>> intersectionList;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        mText = new MutableLiveData<>();
        firstID = new MutableLiveData<>();
        secondID = new MutableLiveData<>();
        firstList = new MutableLiveData<>();
        secondList = new MutableLiveData<>();
        intersectionList = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<String> getFirstID(){
        return firstID;
    }

    public LiveData<String> getSecondID(){
        return secondID;
    }

    public void setFirstID(String firstID) {
        this.firstID.setValue(firstID);
    }

    public void setSecondID(String secondID) {
        this.secondID.setValue(secondID);
    }

    public MutableLiveData<List<Song>> getFirstList() {
        return firstList;
    }

    public void setFirstList(MutableLiveData<List<Song>> firstList) {
        this.firstList = firstList;
    }

    public MutableLiveData<List<Song>> getSecondList() {
        return secondList;
    }

    public void setSecondList(MutableLiveData<List<Song>> secondList) {
        this.secondList = secondList;
    }

    public MutableLiveData<List<Song>> getIntersectionList() {
        return intersectionList;
    }

    public void setIntersectionList(MutableLiveData<List<Song>> intersectionList) {
        this.intersectionList = intersectionList;
    }
}