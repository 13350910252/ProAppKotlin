package com.yinp.proappkotlin.study.wanAndroid.dialog

import android.content.Intent
import android.widget.EditText
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.yinp.proappkotlin.R
import com.yinp.proappkotlin.constant.SpConstants
import com.yinp.proappkotlin.study.wanAndroid.data.WanLoginBean
import com.yinp.proappkotlin.study.wanAndroid.model.WanLoginModel
import com.yinp.proappkotlin.utils.MMKVUtils
import com.yinp.proappkotlin.web.data.WanResultDispose
import com.yinp.tools.fragment_dialog.BaseDialogFragment
import com.yinp.tools.fragment_dialog.CommonDialogFragment
import com.yinp.tools.fragment_dialog.DialogFragmentHolder
import com.yinp.tools.fragment_dialog.ViewConvertListener
import com.yinp.tools.shap_view.ShapeTextView
import com.yinp.tools.utils.LoadingUtils
import com.yinp.tools.utils.ToastUtil
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * @author   :yinpeng
 * date      :2022/1/11
 * package   :com.yinp.proappkotlin.study.wanAndroid.dialog
 * describe  :
 */
object DialogShow {
    inline fun <reified T : FragmentActivity> setLoginDialog(
        activity: FragmentActivity,
        isSkip: Boolean,
        manager: FragmentManager
    ) {
        CommonDialogFragment.newInstance(activity).setLayoutId(R.layout.dialog_login)
            .setViewConvertListener(object : ViewConvertListener() {
                override fun convertView(
                    holder: DialogFragmentHolder,
                    dialogFragment: BaseDialogFragment
                ) {
                    val viewModel = ViewModelProvider(activity)[WanLoginModel::class.java]

                    val loadingUtils = LoadingUtils()
                    holder.setText(R.id.tv_title, "玩Android登录")
                    val etAccount = holder.getView<EditText>(R.id.et_account)
                    val etPassword = holder.getView<EditText>(R.id.et_password)
                    val stvLogin = holder.getView<ShapeTextView>(R.id.stv_login)
                    val value = MMKVUtils.getValue(SpConstants.WAN_ACCOUNT, "")

                    if (value.isNotEmpty()) {
                        etAccount.setText(value)
                    }

                    stvLogin.setOnClickListener {
                        val account = etAccount.text.toString().trim()
                        val password = etPassword.text.toString().trim()
                        account.ifEmpty {
                            ToastUtil.initToast(activity, "账号还没有填写")
                            return@setOnClickListener
                        }
                        password.ifEmpty {
                            ToastUtil.initToast(activity, "密码还没有填写")
                            return@setOnClickListener
                        }
                        //登录的逻辑
                        viewModel.login(account, password)
                        activity.lifecycleScope.launch {
                            viewModel.wanLoginBean.collect {
                                when (it) {
                                    is WanResultDispose.Start -> loadingUtils.show(
                                        manager,
                                        "登录中..."
                                    )
                                    is WanResultDispose.Success -> {
                                        loadingUtils.close()
                                        WanLoginBean.saveUserInfo(it.data, activity)
                                        MMKVUtils.saveValue(account, SpConstants.WAN_ACCOUNT)
                                        if (isSkip) {
                                            activity.startActivity(Intent(activity, T::class.java))
                                        }
                                        dialogFragment.dismiss()
                                    }
                                    is WanResultDispose.Error -> {
                                        loadingUtils.close()
                                        ToastUtil.initToast(activity, "登录失败")
                                    }
                                }
                            }
                        }
                    }
                }
            }).setGravity(BaseDialogFragment.CENTER).setAnimStyle(R.style.CenterDialogAnimation)
            .setPercentSize(0.8f, 0f).show(manager)
    }
}