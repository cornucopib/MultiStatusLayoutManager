package com.ivan.multistatuslayoutmanager.core;

import android.app.Activity;
import android.content.Context;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;

import com.ivan.multistatuslayoutmanager.ui.ViewInfo;

/*
 * @author liuwei
 * @email 13040839537@163.com
 * create at 2018/5/22
 * description:多状态布局管理
 */
public class MultiStatusLayoutManager {

    private Builder builder;

    private MultiStatusLayoutManager() {
        this.builder = new Builder();
    }

    private MultiStatusLayoutManager(Builder builder) {
        this.builder = builder;
    }

    public static MultiStatusLayoutManager getInstance() {
        return SingleTon.instance;
    }

    private static class SingleTon {
        private static MultiStatusLayoutManager instance = new MultiStatusLayoutManager();
    }

    private void setBuilder(Builder builder) {
        this.builder = builder;
    }

    public static Builder newBuilder(){
        return new Builder();
    }

    public static class Builder {
        private SparseIntArray statusViewMap = new SparseIntArray();

        public Builder setErrorView(int layoutId) {
            return setStatusView(StatusContainer.ERROR_STATUS,layoutId);
        }

        public Builder setLoadingView(int layoutId) {
            return setStatusView(StatusContainer.LOADING_STATUS,layoutId);
        }

        public Builder setNoNetWorkView(int layoutId) {
            return setStatusView(StatusContainer.NO_NETWORK_STATUS,layoutId);
        }

        public Builder setEmptyView(int layoutId) {
            return setStatusView(StatusContainer.EMPTY_STATUS,layoutId);
        }

        private Builder setStatusView(@StatusContainer.StatusType int status, int layoutId) {
            statusViewMap.put(status,layoutId);
            return this;
        }


        public SparseIntArray getStatusViewMap() {
            return statusViewMap;
        }

        public void commit() {
            getInstance().setBuilder(this);
        }

        public MultiStatusLayoutManager build() {
            return new MultiStatusLayoutManager(this);
        }
    }

    public SwitchStatusLayoutManager register(Object target) {
        ViewInfo viewInfo = generateViewInfo(target);
        return new SwitchStatusLayoutManager(viewInfo, builder);
    }




    /**
     * 生成ViewInfo,包括上下文，父类视图，内容视图，内容视图相对于父类视图的位置index
     *
     * @param target activity，fragment，view
     */
    private ViewInfo generateViewInfo(Object target) {
        ViewGroup contentParent;
        Context context;
        if (target instanceof Activity) {
            Activity activity = (Activity) target;
            context = activity;
            contentParent = activity.findViewById(android.R.id.content);
        } else if (target instanceof View) {
            View view = (View) target;
            contentParent = (ViewGroup) view.getParent();
            context = view.getContext();
        } else {
            throw new IllegalArgumentException("");
        }
        int childIndex = 0;
        int childCount = contentParent == null ? 0 : contentParent.getChildCount();
        View oldContent;
        if (target instanceof View) {
            oldContent = (View) target;
            for (int i = 0; i < childCount; i++) {
                if (contentParent.getChildAt(i) == oldContent) {
                    childIndex = i;
                    break;
                }
            }
        } else {
            oldContent = contentParent != null ? contentParent.getChildAt(0) : null;
        }
        if (oldContent == null) {
            String error = String.format("unexpected error when generate 'ViewInfo' in %s", target.getClass().getSimpleName());
            throw new IllegalArgumentException(error);
        }
        if (contentParent != null) {
            contentParent.removeView(oldContent);
        }
        return new ViewInfo(context, contentParent, oldContent, childIndex);
    }


}
