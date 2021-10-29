package com.yinp.tools.fragment_dialog;

import android.content.Context;
import android.view.View;

import androidx.annotation.LayoutRes;
import androidx.viewbinding.ViewBinding;

/**
 * 公用样式Dialog
 */
public class CommonDialogFragment extends BaseDialogFragment {
    private ViewConvertListener viewConvertListener = null;

    public static CommonDialogFragment newInstance(Context context) {
        return newInstance(context, false);
    }

    public static CommonDialogFragment newInstance(Context context, boolean setKeyListener) {
        CommonDialogFragment dialog = new CommonDialogFragment();
        mContext = context;
        mSetKeyListener = setKeyListener;
        return dialog;
    }

    /**
     * 设置Dialog布局
     *
     * @param layoutId
     * @return
     */
    public CommonDialogFragment setLayoutId(@LayoutRes int layoutId) {
        this.mLayoutResId = layoutId;
        return this;
    }

    public CommonDialogFragment setLayout(ViewBinding viewBinding) {
        this.binding = viewBinding;
        this.layoutView = viewBinding.getRoot();
        return this;
    }

    /**
     * 用于通讯
     *
     * @param convertListener
     * @return
     */
    public CommonDialogFragment setViewConvertListener(ViewConvertListener convertListener) {
        viewConvertListener = convertListener;
        return this;
    }

    @Override
    protected int updateLayoutId() {
        return mLayoutResId;
    }

    @Override
    protected View updateLayoutView() {
        return layoutView;
    }

    /**
     * 通过id处理各种事件
     *
     * @param holder
     * @param dialog
     */
    @Override
    public void convertView(DialogFragmentHolder holder, BaseDialogFragment dialog) {
        if (viewConvertListener != null) {
            viewConvertListener.convertView(holder, dialog);
        }
    }

    @Override
    protected void convertView(DialogFragmentHolder holder, BaseDialogFragment dialog, ViewBinding viewBinding) {
        if (viewConvertListener != null) {
            viewConvertListener.convertView(holder, dialog, viewBinding);
        }
    }
}