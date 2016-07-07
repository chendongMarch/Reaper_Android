package com.march.reaper.common;

import com.march.bean.AlbumDetail;
import com.march.bean.RecommendAlbumItem;
import com.march.bean.WholeAlbumItem;
import com.march.dao.AlbumDetailDao;
import com.march.dao.RecommendAlbumItemDao;
import com.march.reaper.utils.Lg;

import java.util.List;

import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by march on 16/6/30.
 * 数据库结构化查询
 */
public class DbHelper {

    private static DbHelper mInst;

    private DbHelper() {

    }

    public static DbHelper get() {
        if (mInst == null) {
            synchronized (DbHelper.class) {
                if (mInst == null) {
                    mInst = new DbHelper();
                }
            }
        }
        return mInst;
    }

    public long queryCount(Class cls) {
        QueryBuilder queryBuilder = DaoHelper.get().
                getDao(cls).queryBuilder();
        long count = queryBuilder.count();
        Lg.e(count + "");
        return count;
    }

    public interface OnQueryReadyListener<T> {
        void queryReady(List<T> list);
    }

    //分页加载所有的专辑
    public void queryWholeAlbum(final int skip, final int limit, final OnQueryReadyListener<WholeAlbumItem> onQueryReadyListener) {
        new SimpleQueryTask<WholeAlbumItem>() {
            @Override
            protected List<WholeAlbumItem> query() {
                Query<WholeAlbumItem> query = DaoHelper.get()
                        .getWholeAlbumItemDao().queryBuilder()
                        .offset(skip).limit(limit)
                        .build();
                return query.list();
            }

            @Override
            protected void afterQuery(List<WholeAlbumItem> list) {
                if (onQueryReadyListener != null) {
                    onQueryReadyListener.queryReady(list);
                }
            }
        }.execute();
    }


    //分页加载   所有推荐专辑 || 某类专辑
    public void queryAllRecommendAlbum(final String type, final int skip, final int limit, final OnQueryReadyListener<RecommendAlbumItem> onQueryReadyListener) {
        new SimpleQueryTask<RecommendAlbumItem>() {
            @Override
            protected List<RecommendAlbumItem> query() {
                queryCount(RecommendAlbumItem.class);
                QueryBuilder<RecommendAlbumItem> queryBuilder = DaoHelper.get()
                        .getRecommendAlbumItemDao().queryBuilder();
                queryBuilder.offset(skip).limit(limit);
                if (type != null)
                    queryBuilder.where(RecommendAlbumItemDao.Properties.Album_type.eq(type));
                queryBuilder.build();
                return queryBuilder.list();
            }

            @Override
            protected void afterQuery(List<RecommendAlbumItem> list) {
                if (onQueryReadyListener != null) {
                    onQueryReadyListener.queryReady(list);
                }
            }
        }.execute();
    }


    //查询专辑的详情
    public void queryAlbumDetail(final String albumLink, final int skip, final int limit, final OnQueryReadyListener<AlbumDetail> onQueryReadyListener) {
        new SimpleQueryTask<AlbumDetail>() {
            @Override
            protected List<AlbumDetail> query() {
                Query<AlbumDetail> query = DaoHelper.get()
                        .getAlbumDetailDao().queryBuilder()
                        .where(AlbumDetailDao.Properties.Album_link.eq(albumLink))
                        .offset(skip).limit(limit)
                        .build();
                return query.list();
            }

            @Override
            protected void afterQuery(List<AlbumDetail> list) {
                if (onQueryReadyListener != null) {
                    onQueryReadyListener.queryReady(list);
                }
            }
        }.execute();
    }
}
