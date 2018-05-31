package com.ivan.multistatuslayoutmanager.ui;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.io.Serializable;

/*
 * @author liuwei
 * @email 13040839537@163.com
 * create at 2018/5/23
 * description:当前状态视图的信息
 */
public class ViewInfo {
    private Context context;
    private ViewGroup parentView;
    private View oldContent;
    private int childIndex;


    public ViewInfo(Context context, ViewGroup parentView, View oldContent, int childIndex) {
        this.context = context;
        this.parentView = parentView;
        this.oldContent = oldContent;
        this.childIndex = childIndex;
    }

    public Context getContext() {
        return context;
    }

    public ViewGroup getParentView() {
        return parentView;
    }

    public View getOldContent() {
        return oldContent;
    }

    public int getChildIndex() {
        return childIndex;
    }



}
