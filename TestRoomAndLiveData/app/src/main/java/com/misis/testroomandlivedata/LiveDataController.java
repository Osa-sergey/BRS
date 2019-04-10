package com.misis.testroomandlivedata;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

public class LiveDataController {
    private static LiveDataController instance;
    private MutableLiveData<String> liveData;

    LiveData<String> getData() {
        return liveData;
    }
    private LiveDataController(String str){
        liveData =  new MutableLiveData<>();
        liveData.setValue(str);
    }

    public void setText(String str){
        liveData.setValue(str);
    }
    public static LiveDataController getInstance(String str){
        if (instance == null) {
            instance = new LiveDataController(str);
        }
        return instance;
    }

}
