package com.example.basicmusic.repo;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PagingState;
import androidx.paging.rxjava3.RxPagingSource;

import com.example.basicmusic.api.MusicApi;
import com.example.basicmusic.api.MusicApiProvider;
import com.example.basicmusic.data.Music;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MusicPagingSource extends RxPagingSource<Integer, Music> {
    private MusicApi mMusicApi = MusicApiProvider.getMusicApi();
    public boolean isLocal = true;

    @Nullable
    @Override
    public Integer getRefreshKey(@NonNull PagingState<Integer, Music> pagingState) {
        // Try to find the page key of the closest page to anchorPosition, from
        // either the prevKey or the nextKey, but you need to handle nullability
        // here:
        //  * prevKey == null -> anchorPage is the first page.
        //  * nextKey == null -> anchorPage is the last page.
        //  * both prevKey and nextKey null -> anchorPage is the initial page, so
        //    just return null.
        Integer anchorPosition = pagingState.getAnchorPosition();
        if (anchorPosition == null) {
            return null;
        }

        LoadResult.Page<Integer, Music> anchorPage = pagingState.closestPageToPosition(anchorPosition);
        if (anchorPage == null) {
            return null;
        }

        Integer prevKey = anchorPage.getPrevKey();
        if (prevKey != null) {
            return prevKey + 1;
        }

        Integer nextKey = anchorPage.getNextKey();
        if (nextKey != null) {
            return nextKey - 1;
        }

        return null;
    }

    @NonNull
    @Override
    public Single<LoadResult<Integer, Music>> loadSingle(@NonNull LoadParams<Integer> loadParams) {
        Integer nextPageNumber = loadParams.getKey();
        if(nextPageNumber == null){
            nextPageNumber = 1;
        }
        Integer loadSize = loadParams.getLoadSize();

        return mMusicApi.getMusic(nextPageNumber, loadSize)
                .subscribeOn(Schedulers.io())
                .map(this::toLoadResult)
                .onErrorReturn(LoadResult.Error::new);
    }

    private LoadResult<Integer, Music> toLoadResult(
            @NonNull MusicApi.MusicResponse response) {
        return new LoadResult.Page<>(
                response.getMusics(),
                null, // Only paging forward.
                response.getNextPage(),
                LoadResult.Page.COUNT_UNDEFINED,
                LoadResult.Page.COUNT_UNDEFINED);
    }
}
