package com.ivan.multistatuslayoutmanager.core;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/*
 * @author liuwei
 * @email 13040839537@163.com
 * create at 2018/5/22
 * description:定义状态的容器。包含各种状态的定义
 */
public class StatusContainer {

    public static final int CONTENT_STATUS = 0x0;
    public static final int LOADING_STATUS = 0x1;
    public static final int ERROR_STATUS = 0x2;
    public static final int EMPTY_STATUS = 0x3;
    public static final int NO_NETWORK_STATUS = 0x4;

    @IntDef({CONTENT_STATUS, LOADING_STATUS, ERROR_STATUS, EMPTY_STATUS, NO_NETWORK_STATUS})
    @Retention(RetentionPolicy.SOURCE)
    public @interface StatusType {
    }
}
