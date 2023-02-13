package com.example.basicmusic.ViewModel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelKt;
import androidx.paging.Pager;
import androidx.paging.PagingConfig;
import androidx.paging.PagingData;
import androidx.paging.rxjava3.PagingRx;

import com.example.basicmusic.api.BaseResponse;
import com.example.basicmusic.data.Music;
import com.example.basicmusic.repo.MusicPagingSource;
import com.example.basicmusic.repo.MusicRepository;

import java.io.File;
import java.util.List;

import io.reactivex.rxjava3.core.Flowable;
import kotlinx.coroutines.CoroutineScope;
import retrofit2.Call;

public class MainActivityViewModel extends ViewModel {
    private MusicRepository mMusicRepository;
    Pager<Integer, Music> pager = new Pager<>(
            new PagingConfig(/* pageSize = */ 20),
            () -> new MusicPagingSource());

    CoroutineScope viewModelScope = ViewModelKt.getViewModelScope(this);
    Flowable<PagingData<Music>> flowable = PagingRx.getFlowable(pager);
    {
        PagingRx.cachedIn(flowable, viewModelScope);
    }

    public MainActivityViewModel() {
        mMusicRepository = new MusicRepository();
    }

    public LiveData<List<Music>> getMusics() {
        return mMusicRepository.getMusics();
    }

    public LiveData<List<Music>> getLocalMusic(Context context){
        return mMusicRepository.getLocalMusic(context);
    }
}
