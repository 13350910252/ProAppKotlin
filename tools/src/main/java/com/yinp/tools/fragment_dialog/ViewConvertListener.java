package com.yinp.tools.fragment_dialog;

import androidx.viewbinding.ViewBinding;

/**
 * 用于dialogFragment和Activity的通讯
 */
public abstract class ViewConvertListener {
   public void convertView(DialogFragmentHolder holder, BaseDialogFragment dialogFragment) {
    }
    public void convertView(DialogFragmentHolder holder, BaseDialogFragment dialogFragment, ViewBinding viewBinding) {
    }
}
