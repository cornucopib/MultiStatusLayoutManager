package com.ivan.multistatuslayoutmanager;

import android.app.Application;

import com.ivan.multistatuslayoutmanager.core.MultiStatusLayoutManager;

/*
 * @author liuwei
 * @email 13040839537@163.com
 * create at 2018/5/25
 * description:
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        MultiStatusLayoutManager.newBuilder().setEmptyView(R.layout.empty_view_layout)
                .setErrorView(R.layout.error_view_layout)
                .setLoadingView(R.layout.loading_layout)
                .setNoNetWorkView(R.layout.no_network_layout)
                .commit();

    }
}
