package com.march.reaper.imodel;

import com.march.bean.AlbumDetail;

import java.util.List;

/**
 * Created by march on 16/7/1.
 *
 */
public class AlbumDetailResponse extends BaseResponse {

    private List<AlbumDetail> data;


    public List<AlbumDetail> getData() {
        return data;
    }

    public void setData(List<AlbumDetail> data) {
        this.data = data;
    }
}