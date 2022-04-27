package com.yinp.tools.fragment_dialog

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.annotation.FloatRange
import androidx.annotation.IntDef
import androidx.annotation.StyleRes
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.viewbinding.ViewBinding
import com.yinp.tools.R
import java.util.*


const val BOTTOM = Gravity.BOTTOM
const val TOP = Gravity.TOP
const val CENTER = Gravity.CENTER
const val START = Gravity.START
const val END = Gravity.END

@Retention(AnnotationRetention.SOURCE)
@IntDef(BOTTOM, TOP, CENTER, START, END)
annotation class gravity

/**
 * author:yinp
 * 2022/4/24+21:08
 * describe:
 */
abstract class BaseDialogFragment(var mSetKeyListener: Boolean = false) : DialogFragment() {
    protected var mLayoutResId = 0
    protected var layoutView: View? = null
    protected var binding: ViewBinding? = null
    protected var mContext: Context? = null

    private var mResId = -1 //添加背景图，可以避免弹窗在底部时无法填满

    private lateinit var mGravity: IntArray//表示位置

    private var mWidth = 0
    private var mHeight = 0//弹窗的宽高，一般不自己设置，都是根据layout自动
    private var mMargin = 0//左右边距
    private var topDy = 0//上下唯一
    private var bottomDy = 0//上下唯一
    private var animStyle = -1 //动画，通过style的形式设置

    private var mDimAmount = 0.6f //背景透明都0-1f

    private var mCancelable = true //是否能够点击其他区域取消

    private var isMathParentWidth = true
    private var isMathParentHeight = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return if (layoutView == null) {
            mLayoutResId = updateLayoutId()
            val view = inflater.inflate(mLayoutResId, container, false)
            convertView(DialogFragmentHolder.create(view), this)
            if (mSetKeyListener) {
                //  用于退出app的功能
                dialog?.setOnKeyListener { dialog: DialogInterface?, keyCode: Int, event: KeyEvent? ->
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        if (!isQuit) {
                            isQuit = true
                            Toast.makeText(
                                mContext, getString(R.string.exit_tips),
                                Toast.LENGTH_SHORT
                            ).show()
                            val task: TimerTask
                            task = object : TimerTask() {
                                override fun run() {
                                    isQuit = false
                                }
                            }
                            timer.schedule(task, 2000)
                        } else {
                            dismiss()
                        }
                        return@setOnKeyListener true
                    }
                    false
                }
            }
            view
        } else {
            convertView(DialogFragmentHolder.create(layoutView!!), this, binding!!)
            layoutView
        }
    }

    private var isQuit = false
    var timer = Timer()
    override fun onResume() {
        super.onResume()
        dialog?.window?.let {
            it.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT)) //解决弹窗有空的问题
            if (mResId != -1) {
                it.decorView.setBackgroundResource(mResId)
            } else {
                it.decorView.setBackgroundResource(R.drawable.bg_transparent)
            }
            val layoutParams = it.attributes
            if (mWidth != 0) {
                layoutParams.width = mWidth - 2 * mMargin
            } else {
                layoutParams.width =
                    if (isMathParentWidth) WindowManager.LayoutParams.MATCH_PARENT else WindowManager.LayoutParams.WRAP_CONTENT
            }
            if (mHeight != 0) {
                layoutParams.height = mHeight
            } else {
                layoutParams.height =
                    if (isMathParentHeight) WindowManager.LayoutParams.MATCH_PARENT else WindowManager.LayoutParams.WRAP_CONTENT
            }
            if (topDy != 0) {
                layoutParams.y = topDy
            }
            if (bottomDy != 0) {
                layoutParams.y = -bottomDy
            } else {
                for (i in mGravity.indices) {
                    if (i == CENTER) {
                        layoutParams.y = -dp2px(20f)
                        break
                    }
                }
            }
            for (item in mGravity.indices) {
                layoutParams.gravity = layoutParams.gravity or item
            }
            if (animStyle != -1) {
                layoutParams.windowAnimations = animStyle
            }
            isCancelable = mCancelable
            it.setDimAmount(mDimAmount)
            it.attributes = layoutParams
        }
    }

    fun show(manager: FragmentManager) {
        show(manager, "")
    }

    /**
     * 设置左右边距
     *
     * @param mMargin
     * @return
     */
    fun setRightLeftMargin(mMargin: Int): BaseDialogFragment {
        this.mMargin = dp2px(mMargin.toFloat())
        return this
    }

    /**
     * 设置上位移
     *
     * @param topDy
     * @return
     */
    fun setTopDy(topDy: Int): BaseDialogFragment {
        this.topDy = dp2px(topDy.toFloat())
        return this
    }

    fun setTopDy(topDy: Float): BaseDialogFragment {
        this.topDy = topDy.toInt()
        return this
    }

    /**
     * 设置上位移
     *
     * @param bottomDy
     * @return
     */
    fun setBottomDy(bottomDy: Int): BaseDialogFragment {
        this.bottomDy = dp2px(bottomDy.toFloat())
        return this
    }

    /**
     * 设置背景
     *
     * @param resid
     * @return
     */
    fun setBackgroundResource(@DrawableRes resid: Int): BaseDialogFragment {
        this.mResId = resid
        return this
    }

    /**
     * 设置宽高
     *
     * @param width
     * @param height
     * @return
     */
    fun setSize(width: Int, height: Int): BaseDialogFragment {
        this.mWidth = dp2px(width.toFloat())
        mHeight = dp2px(height.toFloat())
        return this
    }

    /**
     * 设置宽高
     *
     * @param width
     * @param height
     * @return
     */
    fun setPercentSize(width: Int, height: Int): BaseDialogFragment {
        this.mWidth = width
        mHeight = height
        return this
    }

    fun setPercent(isMathParentWidth: Boolean, isMathParentHeight: Boolean): BaseDialogFragment {
        this.isMathParentWidth = isMathParentWidth
        this.isMathParentHeight = isMathParentHeight
        return this
    }

    fun setPercentSize(widthPercent: Float, heightPercent: Float): BaseDialogFragment {
        mWidth = (getScreenWidth() * widthPercent).toInt()
        mHeight = (getScreenHeight() * heightPercent).toInt()
        return this
    }

    /**
     * 设置上下左右中
     *
     * @param gravitys
     * @return
     */
    @SuppressLint("WrongConstant")
    fun setGravity(@gravity vararg gravitys: Int): BaseDialogFragment {
        this.mGravity = gravitys
        return this
    }

    /**
     * 设置进入退出动画
     *
     * @param animStyle
     * @return
     */
    fun setAnimStyle(@StyleRes animStyle: Int): BaseDialogFragment {
        this.animStyle = animStyle
        return this
    }

    /**
     * 设置是否能够点击其他区域取消弹窗
     *
     * @param isCancelable
     * @return
     */
    fun setIsCancelable(isCancelable: Boolean): BaseDialogFragment {
        this.mCancelable = isCancelable
        return this
    }

    /**
     * 设置背景透明度
     *
     * @param dimAmount
     * @return
     */
    fun setDimAmount(@FloatRange(from = 0.0, to = 1.0) dimAmount: Float): BaseDialogFragment {
        this.mDimAmount = dimAmount
        return this
    }

    protected abstract fun updateLayoutId(): Int

    protected abstract fun updateLayoutView(): View?

    /**
     * 操作dialog布局
     *
     * @param holder
     * @param dialog
     */
    protected abstract fun convertView(holder: DialogFragmentHolder, dialog: BaseDialogFragment)

    protected abstract fun convertView(
        holder: DialogFragmentHolder,
        dialog: BaseDialogFragment,
        binding: ViewBinding
    )

    /**
     * 获取屏幕宽度
     *
     * @return
     */
    private fun getScreenWidth(): Int {
        return Resources.getSystem().displayMetrics.widthPixels
    }

    private fun getScreenHeight(): Int {
        return Resources.getSystem().displayMetrics.heightPixels
    }

    private fun dp2px(dipValue: Float): Int {
        val scale = Resources.getSystem().displayMetrics.density
        return (dipValue * scale + 0.5f).toInt()
    }
}