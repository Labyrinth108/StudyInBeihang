package com.examples.sony.util;

import android.view.View;

import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by SONY on 2016/3/17.
 */
public interface PtrHandler {

    /**
     * 检查是否可以执行下来刷新，比如列表为空或者列表第一项在最上面时。
     * <p/>
     * {@link in.srain.cube.views.ptr.PtrDefaultHandler#checkContentCanBePulledDown}
     */
    public boolean checkCanDoRefresh(final PtrFrameLayout frame, final View content, final View header);

    /**
     * 需要加载数据时触发
     *
     * @param frame
     */
    public void onRefreshBegin(final PtrFrameLayout frame);
}