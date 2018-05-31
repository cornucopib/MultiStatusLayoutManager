package com.ivan.multistatuslayoutmanager.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.SparseArray;
import android.view.View;
import android.widget.FrameLayout;

import com.ivan.multistatuslayoutmanager.core.StatusContainer;
import com.ivan.multistatuslayoutmanager.core.SwitchStatusLayoutManager;

/*
 * @author liuwei
 * @email 13040839537@163.com
 * create at 2018/5/23
 * description:容器布局类
 */
public class ContainLayout extends FrameLayout {
    private Context context;
    private SparseArray<View> views = new SparseArray<>();
    private SparseArray<SwitchStatusLayoutManager.StatusClickListener> clickListeners = new SparseArray<>();
    private int currentStatus;

    public ContainLayout(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    /**
     * 添加内容视图到容器
     */
    public void addContent(View view) {
        view.setVisibility(View.GONE);
        addView(view);
        views.put(StatusContainer.CONTENT_STATUS, view);
        currentStatus = StatusContainer.CONTENT_STATUS;
    }

    /**
     * 显示状态视图
     */
    public void showStatusView(@StatusContainer.StatusType int status) {
        checkStatusViewExist(status);
        if (currentStatus != -1) {
            if (currentStatus == status) {
                return;
            }
            //onDetach();
        }
        if (getChildCount() > 1) {
            removeViewAt(1);
        }
        if (status == StatusContainer.CONTENT_STATUS) {
            views.get(status).setVisibility(View.VISIBLE);
        } else {
            views.get(StatusContainer.CONTENT_STATUS).setVisibility(View.GONE);
            View view = views.get(status);
            if (view != null) {
                final SwitchStatusLayoutManager.StatusClickListener clickListener = clickListeners.get(status);
                if(clickListener!=null) {
                    view.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            clickListener.click(v);
                        }
                    });
                }
                addView(view);
            } else {
                throw new IllegalArgumentException("There is no corresponding layout for the status (%d) ");
            }
        }
        currentStatus = status;
    }

    /**
     * 检测状态视图是否存在
     */
    private void checkStatusViewExist(int status) {
        if (views.get(status) == null) {
            String errorInfo = String.format("the status (%d) is  not exist", status);
            throw new IllegalArgumentException(errorInfo);
        }
    }

    /**
     * 添加状态布局
     */
    public void addStatusView(@StatusContainer.StatusType int status, int id) {
        View view = View.inflate(context, id, null);
        if (view != null) {
            views.put(status, view);
        }
    }


    /**
     * 清理缓存
     */
    public void release() {
        views.clear();
        clickListeners.clear();
    }


    /**
     * 给状态视图添加点击事件
     *
     * @param status              状态标识
     * @param statusClickListener 点击监听
     */
    public void addClickListener(@StatusContainer.StatusType int status, SwitchStatusLayoutManager.StatusClickListener statusClickListener) {
        clickListeners.put(status, statusClickListener);
    }
}
