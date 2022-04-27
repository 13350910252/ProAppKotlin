package com.yinp.tools.fragment_dialog

import android.view.View
import androidx.annotation.LayoutRes
import androidx.viewbinding.ViewBinding

/**
 * author:yinp
 * 2022/4/24+21:18
 * describe:
 */
class CommonDialogFragment(var setKeyListener: Boolean = false) : BaseDialogFragment(setKeyListener) {
    private var viewConvertListener: ViewConvertListener? = null

    companion object {
        fun newInstance(): CommonDialogFragment {
            return CommonDialogFragment()
        }
    }

    /**
     * 设置Dialog布局
     *
     * @param layoutId
     * @return
     */
    fun setLayoutId(@LayoutRes layoutId: Int): CommonDialogFragment {
        mLayoutResId = layoutId
        return this
    }

    fun setLayout(viewBinding: ViewBinding): CommonDialogFragment {
        binding = viewBinding
        layoutView = viewBinding.root
        return this
    }

    /**
     * 用于通讯
     *
     * @param convertListener
     * @return
     */
    fun setViewConvertListener(convertListener: ViewConvertListener?): CommonDialogFragment {
        viewConvertListener = convertListener
        return this
    }

    override fun updateLayoutId(): Int {
        return mLayoutResId
    }

    override fun updateLayoutView(): View? {
        return layoutView
    }

    /**
     * 通过id处理各种事件
     *
     * @param holder
     * @param dialog
     */
    override fun convertView(holder: DialogFragmentHolder, dialog: BaseDialogFragment) {
        if (viewConvertListener != null) {
            viewConvertListener!!.convertView(holder, dialog)
        }
    }

    override fun convertView(
        holder: DialogFragmentHolder,
        dialog: BaseDialogFragment,
        viewBinding: ViewBinding
    ) {
        if (viewConvertListener != null) {
            viewConvertListener!!.convertView(holder, dialog, viewBinding)
        }
    }
}