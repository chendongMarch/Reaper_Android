package com.march.reaper.ipresenter.impl;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.march.bean.Album;
import com.march.bean.AlbumDetail;
import com.march.bean.AlbumItemCollection;
import com.march.quickrvlibs.RvViewHolder;
import com.march.quickrvlibs.TypeRvAdapter;
import com.march.quickrvlibs.inter.OnItemClickListener;
import com.march.quickrvlibs.module.LoadMoreModule;
import com.march.reaper.R;
import com.march.reaper.base.mvp.view.BaseRgvView;
import com.march.reaper.common.API;
import com.march.reaper.common.Constant;
import com.march.reaper.common.DbHelper;
import com.march.reaper.helper.CommonHelper;
import com.march.reaper.helper.Logger;
import com.march.reaper.imodel.AlbumDetailResponse;
import com.march.reaper.ipresenter.NetLoadListPresenter;
import com.march.reaper.iview.activity.ScanImgActivity;
import com.march.reaper.utils.QueryUtils;
import com.march.reaper.widget.RecyclerGroupView;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

/**
 * Created by march on 16/7/2.
 * 详情
 */
public class AlbumDetailPresenterImpl
        extends NetLoadListPresenter<AlbumDetailPresenterImpl.AlbumDetailView, AlbumDetail> {

    private boolean isBig = false;
    private Album mAlbumData;
    private boolean isCollection;
    private AlbumItemCollection mCol;

    public interface AlbumDetailView extends BaseRgvView {
        void setModeTvText(String txt);
    }

    public AlbumDetailPresenterImpl() {
        Intent intent = mView.getData();
        mAlbumData = (Album) intent.getSerializableExtra(Constant.KEY_ALBUM_DETAIL_SHOW);
        mCol = new AlbumItemCollection(mAlbumData);
        isCollection = DbHelper.get().isAlbumCollection(mCol);
    }

    @Override
    protected RecyclerGroupView getRgv() {
        return null;
    }

    @Override
    public void justQuery() {
        if (checkCanQuery())
            queryNetDatas();
    }


    @Override
    public void queryDbDatas() {
        DbHelper.get().queryAlbumDetail(mAlbumData.getAlbum_link(), offset, limit, new DbHelper.OnQueryReadyListener<AlbumDetail>() {
            @Override
            public void queryReady(List<AlbumDetail> list) {
                handleDatasAfterQueryReady(list);
            }
        });
    }


    //从网络获取数据
    @Override
    protected void queryNetDatas() {
        String url = API.GET_SCAN_DETAIL + "?offset=" + offset + "&limit=" + limit + "&albumlink=" + mAlbumData.getAlbum_link();
        QueryUtils.get().query(url, AlbumDetailResponse.class, new QueryUtils.OnQueryOverListener<AlbumDetailResponse>() {
            @Override
            public void queryOver(AlbumDetailResponse rst) {
                List<AlbumDetail> data = rst.getData();
                handleDatasAfterQueryReady(data);
            }

            @Override
            public void error(Exception e) {
                if (mAdapter != null)
                    mAdapter.finishLoad();
                completeRefresh();
                isLoadEnd = true;
            }
        });
    }


    //构建adapter
    @Override
    protected void createRvAdapter() {
        mAdapter = new TypeRvAdapter<AlbumDetail>(getContext(), datas) {
            @Override
            public void bindData4View(RvViewHolder holder, AlbumDetail data, int pos, int type) {
                holder.setImg(getContext(), R.id.detail_item_show_iv, data.getPhoto_src());
                View bgView = holder.getView(R.id.detail_show_bg);
                bgView.setBackgroundColor(CommonHelper.randomColor());
            }

            @Override
            public void bindListener4View(RvViewHolder holder, int type) {
                super.bindListener4View(holder, type);
                ImageView iv = holder.getView(R.id.detail_item_show_iv);
                float rate;
                if (type == AlbumDetail.TYPE_SHU) {
                    rate = 1024 * 1.0f / 683;
                } else {
                    rate = 683 * 1.0f / 1024;
                }

                if (isBig) {
                    iv.getLayoutParams().height = (int) (mWidth * rate);
                } else {
                    iv.getLayoutParams().height = (int) (mWidth * rate * 0.5f);
                }
            }

            @Override
            public void bindLisAndData4Footer(RvViewHolder footer) {
                footer.setClickLis(R.id.footer_loadmore, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        justQuery();
                    }
                });
            }

            @Override
            public void bindLisAndData4Header(RvViewHolder header) {
                final ImageView mIsColIv = header.getView(R.id.iv_is_collection);
                //喜爱
                mIsColIv.setImageResource(isCollection ? R.drawable.ic_collection : R.drawable.ic_not_collection);
                mIsColIv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (isCollection) {
                            DbHelper.get().removeAlbumCollection(mCol);
                            mIsColIv.setImageResource(R.drawable.ic_not_collection);
                        } else {
                            DbHelper.get().addAlbumCollection(mCol);
                            mIsColIv.setImageResource(R.drawable.ic_collection);
                        }
                        isCollection = !isCollection;
                    }
                });
                //标签
                final TagFlowLayout mKeyWdsFlow = header.getView(R.id.head_keywds_flow);
                TextView mDescTv = header.getView(R.id.head_desc_tv);
                mDescTv.setText(mAlbumData.getAlbum_desc());
                String keyWdsStr = mAlbumData.getKey_words();
                if (keyWdsStr == null) {
                    mKeyWdsFlow.setVisibility(View.GONE);
                } else {
                    String[] keyWds = keyWdsStr.split(", ");
                    TagAdapter<String> adapter = new TagAdapter<String>(keyWds) {
                        @Override
                        public View getView(FlowLayout parent, int position, String s) {
                            TextView tv = (TextView) getActivity().getLayoutInflater().inflate(R.layout.detail_item_keywds,
                                    mKeyWdsFlow, false);
                            tv.setText(s);
                            return tv;
                        }
                    };
                    mKeyWdsFlow.setAdapter(adapter);
                }
            }
        };

        mAdapter.addHeaderOrFooter(R.layout.detail_head_list, R.layout.footer_load_more, getRgv().getRecyclerView());
        mAdapter.addType(AlbumDetail.TYPE_SHU, R.layout.detail_item_show);
        mAdapter.addType(AlbumDetail.TYPE_HENG, R.layout.detail_item_show2);
        mAdapter.addLoadMoreModule(mPreLoadNum, new LoadMoreModule.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                Logger.e("加载更多  " + offset);
                justQuery();
            }
        });

        mAdapter.setOnItemClickListener(new OnItemClickListener<RvViewHolder>() {
            @Override
            public void onItemClick(int pos, RvViewHolder holder) {
                ScanImgActivity.loadActivity(getActivity(), datas.get(pos - mAdapter.getHeaderCount()));
            }
        });
    }

    public void switchMode() {
        isBig = !isBig;
        RecyclerView.LayoutManager layoutManager;
        if (isBig) {
            mView.setModeTvText("小图");
            layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        } else {
            mView.setModeTvText("大图");
            layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        }
        getRgv().setLayoutManager(layoutManager);
        getRgv().setAdapter(mAdapter);
    }
}
