package com.yinp.proappkotlin.study.jiguang

import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.View
import com.yinp.proappkotlin.base.BaseActivity
import com.yinp.proappkotlin.databinding.ActivityJiGuangLoginBinding
import com.yinp.proappkotlin.utils.GlideUtils
import com.yinp.proappkotlin.utils.SelectTakePhoto
import com.yinp.proappkotlin.utils.StatusBarUtil

/**
 * @author   :yinpeng
 * date      :2021/10/18
 * package   :com.yinp.proappkotlin.study
 * describe  :
 */
class JiGuangLoginActivity : BaseActivity<ActivityJiGuangLoginBinding>() {
    override fun initViews() {
        bd.header.headerCenterTitle.text = "极光登录"
        initClick(
            bd.header.headerBackImg,
            bd.tvLogin,
            bd.tvShare,
            bd.tvQZoneShare,
            bd.tvWeixinShare
        )
//        val canQQ: Boolean = JShareInterface.isClientValid(QQ.Name)
//        val QQAuthorization: Boolean = JShareInterface.isSupportAuthorize(QQ.Name)
    }

    override fun getBinding() = ActivityJiGuangLoginBinding.inflate(layoutInflater)
    var imageUrl: String? = null
    private val handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            imageUrl = msg.obj as String
            GlideUtils.into(mContext, imageUrl, bd.ivHead)
        }
    }

    val selectTakePhoto by lazy {
        SelectTakePhoto()
    }

    override fun onClick(v: View) {
        super.onClick(v)
        when (v) {
            bd.header.headerBackImg -> {
                finish()
            }
//            bd.tvLogin -> {
//                QQLogin()
//            }
//            bd.tvShare -> {
//                selectTakePhoto.selectType(this, 1)
//            }
//            bd.tvQZoneShare -> {
//                val shareParams = ShareParams()
//                shareParams.setShareType(Platform.SHARE_TEXT)
//                shareParams.setText("qq控件分享")
//                JShareInterface.share(QZone.Name, shareParams, null)
//            }
//            bd.tvWeixinShare -> {
//                val shareParams = ShareParams()
//                shareParams.setShareType(Platform.SHARE_TEXT)
//                shareParams.setText("Text") //必须
//                JShareInterface.share(Wechat.Name, shareParams, null)
//            }
        }
    }

//    private fun QQLogin() {
//        JShareInterface.authorize(QQ.Name, object : AuthListener() {
//            fun onComplete(platform: Platform?, i: Int, baseResponseInfo: BaseResponseInfo?) {
//                Log.d(TAG, "onComplete: ")
//                when (i) {
//                    Platform.ACTION_AUTHORIZING -> JShareInterface.getUserInfo(
//                        QQ.Name,
//                        object : AuthListener() {
//                            fun onComplete(
//                                platform: Platform?,
//                                action: Int,
//                                data: BaseResponseInfo
//                            ) {
//                                when (action) {
//                                    Platform.ACTION_USER_INFO -> if (data is UserInfo) {      //第三方个人信息
//                                        val openid: String = (data as UserInfo).getOpenid() //openid
//                                        val name: String = (data as UserInfo).getName() //昵称
//                                        val imageUrl: String =
//                                            (data as UserInfo).getImageUrl() //头像url
//                                        val gender: Int =
//                                            (data as UserInfo).getGender() //性别, 1表示男性；2表示女性
//                                        //个人信息原始数据，开发者可自行处理
//                                        val originData: String = data.getOriginData()
//                                        val msg = Message()
//                                        msg.obj = imageUrl
//                                        handler.sendMessage(msg)
//                                        val toastMsg = "获取个人信息成功:" + data.toString()
//                                        Log.d(TAG, "onComplete: $toastMsg")
//                                    }
//                                }
//                            }
//
//                            fun onError(
//                                platform: Platform?,
//                                i: Int,
//                                i1: Int,
//                                throwable: Throwable?
//                            ) {
//                            }
//
//                            fun onCancel(platform: Platform?, i: Int) {}
//                        })
//                }
//            }
//
//            fun onError(platform: Platform?, i: Int, i1: Int, throwable: Throwable?) {
//                Log.d(TAG, "onError: ")
//            }
//
//            fun onCancel(platform: Platform?, i: Int) {
//                Log.d(TAG, "onCancel: ")
//            }
//        })
//    }

//    private fun QQShare(path: String) {
//        val shareParams = ShareParams()
//        shareParams.setImagePath(path)
//        shareParams.setShareType(Platform.SHARE_IMAGE)
//        JShareInterface.share(QQ.Name, shareParams, object : PlatActionListener() {
//            fun onComplete(platform: Platform?, i: Int, hashMap: HashMap<String?, Any?>?) {
//                Log.d(TAG, "onComplete: ")
//            }
//
//            fun onError(platform: Platform?, i: Int, i1: Int, throwable: Throwable?) {
//                Log.d(TAG, "onError: ")
//            }
//
//            fun onCancel(platform: Platform?, i: Int) {
//                Log.d(TAG, "onCancel: ")
//            }
//        })
//    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == 1) {
//            if (selectTakePhoto.type === Constant.TAKE_PHOTO) {
//                GlideUtils.intoRadius(mContext, selectTakePhoto.imageUri, bd.ivHead, 100, true)
//                QQShare(selectTakePhoto.imageUri.toString())
//            } else if (selectTakePhoto.type === Constant.SELECT_PHOTO) {
//                if (data != null) {
//                    val url: String = selectTakePhoto.handleImageOnKitKat(mContext, data)
//                    GlideUtils.intoRadius(mContext, url, bd.ivHead, 100, true)
//                    QQShare(url)
//                }
//            }
//        }
//    }
}