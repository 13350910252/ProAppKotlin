package com.yinp.proappkotlin.major.fragment

import android.content.Intent
import android.net.Uri
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.yinp.proappkotlin.R
import com.yinp.proappkotlin.SELECT_PHOTO
import com.yinp.proappkotlin.TAKE_PHOTO
import com.yinp.proappkotlin.base.BaseFragment
import com.yinp.proappkotlin.constant.SpConstants
import com.yinp.proappkotlin.databinding.FragmentMeBinding
import com.yinp.proappkotlin.me.AddLabelActivity
import com.yinp.proappkotlin.utils.GlideUtils
import com.yinp.proappkotlin.utils.SelectTakePhoto
import com.yinp.proappkotlin.utils.SpUtils

/**
 * @author   :yinpeng
 * date      :2021/10/11
 * package   :com.yinp.proappkotlin.major
 * describe  :
 */
class MeFragment : BaseFragment<FragmentMeBinding>() {
    private val selectTakePhoto by lazy {
        SelectTakePhoto()
    }

    override fun initViews() {
        initClick(this, bd.ivHead, bd.sllAddLabel)
        val img: String = SpUtils.getValue(SpConstants.HEAD_PICTURE, "")!!
        if (TextUtils.isEmpty(img)) {
            GlideUtils.intoRadius(requireContext(), R.mipmap.default_head, bd.ivHead, 100)
        } else {
            val uri = Uri.parse(img)
            GlideUtils.intoRadius(requireContext(), uri, bd.ivHead, 100, true)
        }
    }

    override fun onClick(v: View?) {
        when (v) {
            bd.ivHead -> selectTakePhoto.selectType(this@MeFragment, 1)
            bd.sllAddLabel -> goToActivity(AddLabelActivity::class.java)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            if (selectTakePhoto.type == TAKE_PHOTO) {
                GlideUtils.intoRadius(
                    requireContext(),
                    selectTakePhoto.imageUri,
                    bd.ivHead,
                    100,
                    true
                )
                SpUtils.saveValue(SpConstants.HEAD_PICTURE, selectTakePhoto.imageUri.toString())
            } else if (selectTakePhoto.type == SELECT_PHOTO) {
                if (data != null) {
                    val url: String = selectTakePhoto.handleImageOnKitKat(requireContext(), data)
                    GlideUtils.intoRadius(requireContext(), url, bd.ivHead, 100, true)
                }
            }
        }
    }


    override fun getBinding(inflater: LayoutInflater, parent: ViewGroup?): ViewBinding {
        return FragmentMeBinding.inflate(inflater, parent, false)
    }
}