package com.march.bean;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END

import com.march.reaper.common.API;
import com.march.reaper.common.RequestCallback;
import com.march.reaper.imodel.RecommendAlbumResponse;
import com.march.reaper.utils.QueryUtils;

/**
 * Entity mapped to table "RECOMMEND_ALBUM_ITEM".
 */
public class RecommendAlbumItem extends Album {

    private String album_type;
    private String album_link;
    private String album_cover;
    private String album_desc;
    private String time_stamp;
    private Boolean isFavorite;

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    public RecommendAlbumItem() {
    }

    public RecommendAlbumItem(String album_type, String album_link, String album_cover, String album_desc, String time_stamp, Boolean isFavorite) {
        this.album_type = album_type;
        this.album_link = album_link;
        this.album_cover = album_cover;
        this.album_desc = album_desc;
        this.time_stamp = time_stamp;
        this.isFavorite = isFavorite;
    }

    public String getAlbum_type() {
        return album_type;
    }

    public void setAlbum_type(String album_type) {
        this.album_type = album_type;
    }

    public String getAlbum_link() {
        return album_link;
    }

    public void setAlbum_link(String album_link) {
        this.album_link = album_link;
    }

    public String getAlbum_cover() {
        return album_cover;
    }

    public void setAlbum_cover(String album_cover) {
        this.album_cover = album_cover;
    }

    public String getAlbum_desc() {
        return album_desc;
    }

    public void setAlbum_desc(String album_desc) {
        this.album_desc = album_desc;
    }

    public String getTime_stamp() {
        return time_stamp;
    }

    public void setTime_stamp(String time_stamp) {
        this.time_stamp = time_stamp;
    }

    public Boolean getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(Boolean isFavorite) {
        this.isFavorite = isFavorite;
    }

    // KEEP METHODS - put your custom methods here
    @Override
    public String getKey_words() {
        return null;
    }


    public static void queryRecommendAlbumForType
            (int offset, int limit,
             String type, final RequestCallback<RecommendAlbumResponse> callback) {
        final String url = API.GET_SCAN_RECOMMEND + "?offset=" + offset + "&limit=" + limit + "&albumtype=" + type;
        QueryUtils.get().query(url, RecommendAlbumResponse.class, new QueryUtils.OnQueryOverListener<RecommendAlbumResponse>() {
            @Override
            public void queryOver(RecommendAlbumResponse rst) {
                callback.onSucceed(rst);
            }

            @Override
            public void error(Exception e) {
                callback.onError(e);
            }
        });
    }
    // KEEP METHODS END

}
