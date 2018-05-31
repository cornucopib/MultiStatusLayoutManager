package com.ivan.multistatuslayoutmanager.core;

import android.content.Context;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;

import com.ivan.multistatuslayoutmanager.ui.ContainLayout;
import com.ivan.multistatuslayoutmanager.ui.ViewInfo;

/*
 * @author liuwei
 * @email 13040839537@163.com
 * create at 2018/5/23
 * description:切换状态布局的工具类
 */
public class SwitchStatusLayoutManager {

    private ContainLayout containLayout;

    public SwitchStatusLayoutManager(ViewInfo viewInfo, MultiStatusLayoutManager.Builder builder) {
        Context context = viewInfo.getContext();
        View oldContent = viewInfo.getOldContent();
        ViewGroup.LayoutParams oldLayoutParams = oldContent.getLayoutParams();
        ViewGroup parentView = viewInfo.getParentView();
        this.containLayout = new ContainLayout(context);
        this.containLayout.addContent(oldContent);
        if (parentView != null) {
            parentView.addView(containLayout, viewInfo.getChildIndex(), oldLayoutParams);
        }
        initStatusViews(builder);
    }


    /**
     * 初始化预设的状态布局
     *
     * @param builder 多状态布局管理的构造器
     */
    private void initStatusViews(MultiStatusLayoutManager.Builder builder) {
        SparseIntArray statusViewMap = builder.getStatusViewMap();
        for (int i = 0; i < statusViewMap.size(); i++) {
            int key = statusViewMap.keyAt(i);
            int value = statusViewMap.valueAt(i);
            setStatusView(key, value);
        }
    }


    /**
     * 设置状态布局
     *
     * @param status   状态
     * @param layoutId 状态布局layoutid
     */
    public void setStatusView(@StatusContainer.StatusType int status, int layoutId) {
        containLayout.addStatusView(status, layoutId);
    }


    /**
     * 切换状态布局
     *
     * @param status 状态
     */
    public void switchTo(@StatusContainer.StatusType int status) {
        containLayout.showStatusView(status);
    }


    /**
     * 添加无网络点击事件
     * */
    public SwitchStatusLayoutManager withNoNetWorkClickListener(StatusClickListener clickListener){
        addOnClickListener(StatusContainer.NO_NETWORK_STATUS,clickListener);
        return this;
    }

    /**
     * 添加点击事件
     *
     */
    private void addOnClickListener(@StatusContainer.StatusType int status,StatusClickListener statusClickListener) {
        containLayout.addClickListener(status,statusClickListener);
    }


    public interface StatusClickListener {
         void click(View v);
    }

    /**
     * 清理缓存
     */
    public void release() {
        containLayout.release();
    }

}
