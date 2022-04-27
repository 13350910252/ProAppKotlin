package com.yinp.proappkotlin.study.wanAndroid

import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewbinding.ViewBinding
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.yinp.proappkotlin.R
import com.yinp.proappkotlin.base.BaseActivity
import com.yinp.proappkotlin.databinding.ActivityWanMeBinding
import com.yinp.proappkotlin.study.wanAndroid.data.WanLoginBean
import com.yinp.proappkotlin.study.wanAndroid.model.WanMeModel
import com.yinp.proappkotlin.utils.AppUtils
import com.yinp.proappkotlin.utils.JumpWebUtils
import com.yinp.proappkotlin.utils.StatusBarUtil
import com.yinp.proappkotlin.web.data.WanResultDispose
import com.yinp.tools.databinding.DialogTipsBinding
import com.yinp.tools.fragment_dialog.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * @author   :yinpeng
 * date      :2022/1/10
 * package   :com.yinp.proappkotlin.study.wanAndroid
 * describe  :
 */
class WanMeActivity : BaseActivity<ActivityWanMeBinding>() {
    //是否已经登录的标志
    private var mLogin = false

    private val viewModel by lazy {
        ViewModelProvider(this)[WanMeModel::class.java]
    }

    override fun initViews() {
        setStatusBarHeight(StatusBarUtil.getStatusBarHeight(mContext))
        bd.header.headerCenterTitle.text = "我的"
        initClick(
            bd.tvNickName,
            bd.llJoinOpenSource,
            bd.llOpenSourceWeb,
            bd.llSetting,
            bd.llIntegralRank,
            bd.llCollect,
            bd.sllLoginOut
        )
        bindData()
    }

    override fun bindData() {
        viewModel.getIntegralInfo()
        initData()
    }

    override fun onResume() {
        super.onResume()
        initUserInfo()
    }

    private fun initUserInfo() {
        val wanLoginBean = WanLoginBean.getUserInfo(mContext)
        if (wanLoginBean != null) {
            if (wanLoginBean.username.isEmpty()) {
                bd.cuaUserHead.setUserName("登录")
                bd.tvNickName.text = "请先登录~"
                mLogin = false
            } else {
                mLogin = true
                bd.cuaUserHead.setUserName(AppUtils.getDecodeName(wanLoginBean.nickname))
                bd.tvNickName.text = wanLoginBean.nickname
            }
        } else {
            bd.cuaUserHead.setUserName("登录")
            bd.tvNickName.text = "请先登录~"
            mLogin = false
        }
    }

    override fun onClick(v: View) {
        super.onClick(v)
        when (v) {
            bd.tvNickName -> {
                if (!mLogin) {
//                setLoginDialog()
                }
            }
            bd.llOpenSourceWeb -> {
                JumpWebUtils.startWebView(
                    mContext,
                    "玩Android",
                    "https://www.wanandroid.com/"
                )
            }
            bd.llJoinOpenSource -> {
                JumpWebUtils.startWebView(
                    mContext,
                    "WanAndroid——WJX",
                    "https://github.com/wangjianxiandev/WanAndroidMvp"
                )
            }
            bd.llIntegralRank -> {
                gotoActivity<WanRankActivity>()
            }
            bd.llCollect -> {
                gotoActivity<WanCollectionActivity>()
            }
            bd.sllLoginOut -> {
                loginOutDialog()
            }
        }
    }
//
//    private fun setLoginDialog() {
//        showLoading("登录中...")
//        CommonDialogFragment.newInstance(this).setLayoutId(R.layout.dialog_login)
//            .setViewConvertListener(object : ViewConvertListener() {
//                override fun convertView(
//                    holder: DialogFragmentHolder,
//                    dialogFragment: BaseDialogFragment
//                ) {
//                    val tv_title = holder.getView<TextView>(R.id.tv_title)
//                    tv_title.text = "玩Android登录"
//                    val et_account = holder.getView<EditText>(R.id.et_account)
//                    val et_password = holder.getView<EditText>(R.id.et_password)
//                    val stv_login = holder.getView<ShapeTextView>(R.id.stv_login)
//                    stv_login.setOnClickListener { v: View? ->
//                        val account = et_account.text.toString().trim { it <= ' ' }
//                        val password = et_password.text.toString().trim { it <= ' ' }
//                        if (TextUtils.isEmpty(account)) {
//                            showToast("账号还没有填写")
//                            return@setOnClickListener
//                        }
//                        if (TextUtils.isEmpty(password)) {
//                            showToast("密码还没有填写")
//                            return@setOnClickListener
//                        }
//                        presenter.login(account, password, object : WanObserver<WanData?>() {
//                            fun onSuccess(o: WanData) {
//                                getIntegral()
//                                hideLoading()
//                                dialogFragment.dismiss()
//                                val data: JsonElement = o.getData().getAsJsonObject()
//                                if (data.isJsonNull) {
//                                    showToast("没有获取到登录信息，请重试")
//                                    return@setOnClickListener
//                                }
//                                val wanLoginBean =
//                                    Gson().fromJson(data, WanLoginBean::class.java)
//                                WanLoginBean.saveUserInfo(wanLoginBean, mContext)
//                                bd.tvNickName.text = wanLoginBean.nickname
//                                bd.cuaUserHead.setUserName(AppUtils.getDecodeName(wanLoginBean.nickname))
//                            }
//
//                            fun onError(msg: String?) {
//                                hideLoading()
////                                showToast(msg)
//                            }
//
//                            fun onCodeFail(msg: String?) {
//                                hideLoading()
////                                showToast(msg)
//                            }
//                        })
//                    }
//                }
//            }).setGravity(BaseDialogFragment.CENTER).setAnimStyle(R.style.CenterDialogAnimation)
//            .setPercentSize(0.8f, 0f).show(
//                supportFragmentManager
//            )
//    }

    private fun loginOutDialog() {
        CommonDialogFragment.newInstance().setLayout(DialogTipsBinding.inflate(layoutInflater))
            .setViewConvertListener(object : ViewConvertListener() {
                override fun convertView(
                    holder: DialogFragmentHolder,
                    dialogFragment: BaseDialogFragment,
                    viewBinding: ViewBinding
                ) {
                    (viewBinding as DialogTipsBinding).apply {
                        tvTitle.text = "是否登出WanAndroid?"
                        tvLeft.let {
                            it.text = "否"
                            it.setOnClickListener { dialogFragment.dismiss() }
                        }
                        tvRight.let {
                            it.text = "是"
                            it.setOnClickListener {
                                viewModel.getIntegralInfo()
                                dialogFragment.dismiss()
                            }
                        }

                    }
                }
            }).setGravity(CENTER).setAnimStyle(R.style.CenterDialogAnimation)
            .setPercentSize(0.8f, 0f).show(
                supportFragmentManager
            )
    }

    private fun initData() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.integralBean.collect {
                    when (it) {
                        is WanResultDispose.Success -> {
                            it.data.run {
                                bd.tvIntegralRanking.text =
                                    "积分：${coinCount} 排行：${rank}"
                            }
                        }
                        is WanResultDispose.Error -> {
                            bd.tvIntegralRanking.text = "积分:--" + " 排行:--"
                        }
                    }
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loginOut.collect {
                    when (it) {
                        is WanResultDispose.Success -> {
//                        showToast("登出成功")
                            //清除个人信息
                            WanLoginBean.clear(mContext)
                            //退出登录时，清除本地cookie
                            val sharedPrefsCookiePersistor =
                                SharedPrefsCookiePersistor(mContext)
                            sharedPrefsCookiePersistor.clear()
                        }
                        is WanResultDispose.Error -> {
//                        showToast("登出失败")
                        }
                    }
                }
            }
        }
    }

    /**
     * 登出
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onLoginOut() {
        bd.tvIntegralRanking.text = "积分:--" + " 排行:--"
    }

    /**
     * 登录
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onLogin() {
//        setLoginDialog()
    }

    override fun getBinding() = ActivityWanMeBinding.inflate(layoutInflater)
}