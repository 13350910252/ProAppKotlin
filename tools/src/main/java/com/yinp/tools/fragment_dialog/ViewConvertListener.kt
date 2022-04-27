package com.yinp.tools.fragment_dialog

import androidx.viewbinding.ViewBinding

/**
 * author:yinp
 * 2022/4/25+20:38
 * describe:
 */
abstract class ViewConvertListener {
    open fun convertView(holder: DialogFragmentHolder, dialogFragment: BaseDialogFragment) {}
    open fun convertView(
        holder: DialogFragmentHolder,
        dialogFragment: BaseDialogFragment,
        viewBinding: ViewBinding
    ) {
    }
}