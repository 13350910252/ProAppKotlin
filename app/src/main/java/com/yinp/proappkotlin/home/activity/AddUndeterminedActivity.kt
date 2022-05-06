package com.yinp.proappkotlin.home.activity

import android.view.View
import com.yinp.proappkotlin.RESULT_CODE
import com.yinp.proappkotlin.base.BaseActivity
import com.yinp.proappkotlin.databinding.ActivityAddUndeterminedBinding
import com.yinp.proappkotlin.utils.StatusBarUtil

/**
 * @author   :yinpeng
 * date      :2021/10/12
 * package   :com.yinp.proappkotlin.home.activity
 * describe  :添加待做的任务，或者其他的什么东西
 */
class AddUndeterminedActivity : BaseActivity<ActivityAddUndeterminedBinding>() {
    override fun initViews() {
        initClick( bd.header.headerBackImg, bd.stvSave)
        bd.header.headerCenterTitle.text = "添加任务今日"
//        localeTaskBeanDaoUtils = DaoUtils(LocaleTaskBean::class.java, App.daoSession.getLocaleTaskBeanDao())
    }

    override fun onClick(v: View) {
        when (v) {
            bd.header.headerBackImg -> {
                setResult(RESULT_CODE)
                finish()
            }
            bd.stvSave -> saveDialog()
        }
    }

    private fun insertData() {
//        if (TextUtils.isEmpty(bd.etContent.text)) {
//            showToast("还没有填写内容")
//            return
//        }
//        val localeTaskBean = LocaleTaskBean()
//        localeTaskBean.setContent(bd.etContent.text.toString().trim())
//        localeTaskBean.setEmpirical(2)
//        localeTaskBean.setDate(DateUtils.curData.curDate())
//        localeTaskBean.setTime(DateUtils.curData.curTime())
//        localeTaskBean.setUserId(1001L)
//        if (!TextUtils.isEmpty(bd.etTitle.text)) {
//            localeTaskBean.setTitle(bd.etTitle.text.toString().trim())
//        }
//        val isSuccess: Boolean = localeTaskBeanDaoUtils.insert(localeTaskBean)
//        if (isSuccess) {
//            showToast("添加成功")
//            setResult(Constant.RESULT_CODE)
//            finish()
//        } else {
//            showToast("添加失败")
//        }
    }

    private fun saveDialog() {
//        CommonDialogFragment.newInstance(this)
//            .setLayout(DialogTipBinding.inflate(LayoutInflater.from(mContext), null, false))
//            .setViewConvertListener(object : ViewConvertListener() {
//                override fun convertView(
//                    holder: DialogFragmentHolder,
//                    dialogFragment: BaseDialogFragment,
//                    viewBinding: ViewBinding
//                ) {
//                    super.convertView(holder, dialogFragment, viewBinding)
//                    val binding: DialogTipBinding = viewBinding as DialogTipBinding
//                    binding.tvTitle.setText("确定添加吗")
//                    binding.tvLeft.setOnClickListener { v -> dialogFragment.dismiss() }
//                    binding.tvRight.setOnClickListener { v ->
//                        dialogFragment.dismiss()
//                        insertData()
//                    }
//                }
//            }).setAnimStyle(BaseDialogFragment.CENTER).setGravity(BaseDialogFragment.CENTER)
//            .setPercentSize(0.9f, 0f).show(
//                supportFragmentManager
//            )
    }

    override fun getBinding() = ActivityAddUndeterminedBinding.inflate(layoutInflater)
}