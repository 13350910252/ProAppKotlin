package com.yinp.tools.fragment_dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.annotation.FloatRange;
import androidx.annotation.IntDef;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewbinding.ViewBinding;

import com.yinp.tools.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Timer;
import java.util.TimerTask;

public abstract class BaseDialogFragment extends DialogFragment {
    protected int mLayoutResId;
    protected View layoutView;
    protected ViewBinding binding;
    protected static Context mContext;
    protected static boolean mSetKeyListener = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private int resid = -1;//添加背景图，可以避免弹窗在底部时无法填满
    private int[] gravity;//表示位置
    private int width, height;//弹窗的宽高，一般不自己设置，都是根据layout自动
    private int mMargin;//左右边距
    private int topDy;//上下唯一
    private int bottomDy;//上下唯一
    private int animStyle = -1;//动画，通过style的形式设置
    private float dimAmount = 1;//背景透明都0-1f
    private boolean isCancelable = true;//是否能够点击其他区域取消
    private boolean isMathParentWidth = true, isMathParentHeight = false;

    public static final int BOTTOM = Gravity.BOTTOM;
    public static final int TOP = Gravity.TOP;
    public static final int CENTER = Gravity.CENTER;
    public static final int RIGHT = Gravity.RIGHT;
    public static final int LEFT = Gravity.LEFT;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({BOTTOM, TOP, CENTER, RIGHT, LEFT})
    public @interface gravity {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (layoutView == null) {
            mLayoutResId = updateLayoutId();
            View view = inflater.inflate(mLayoutResId, container, false);
            convertView(DialogFragmentHolder.create(view), this);
            if (mSetKeyListener) {
                //  用于退出app的功能
                getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
                    @Override
                    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_BACK) {
                            if (!isQuit) {
                                isQuit = true;
                                Toast.makeText(mContext, getString(R.string.exit_tips),
                                        Toast.LENGTH_SHORT).show();
                                TimerTask task;
                                task = new TimerTask() {
                                    public void run() {
                                        isQuit = false;
                                    }
                                };
                                timer.schedule(task, 2000);
                            } else {
                                dismiss();
                            }
                            return true;
                        }
                        return false;
                    }
                });
            }
            return view;
        } else {
            convertView(DialogFragmentHolder.create(layoutView), this, binding);
            return layoutView;
        }
    }

    private static Boolean isQuit = false;
    Timer timer = new Timer();

    @Override
    public void onResume() {
        super.onResume();
        Window window = getDialog().getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));//解决弹窗有空的问题
            if (resid != -1) {
                window.getDecorView().setBackgroundResource(resid);
            } else {
                window.getDecorView().setBackgroundResource(R.drawable.bg_transparent);
            }
            WindowManager.LayoutParams layoutParams = window.getAttributes();
            if (width != 0) {
                layoutParams.width = width - 2 * mMargin;
            } else {
                layoutParams.width = isMathParentWidth ? WindowManager.LayoutParams.MATCH_PARENT : WindowManager.LayoutParams.WRAP_CONTENT;
            }
            if (height != 0) {
                layoutParams.height = height;
            } else {
                layoutParams.height = isMathParentHeight ? WindowManager.LayoutParams.MATCH_PARENT : WindowManager.LayoutParams.WRAP_CONTENT;
            }
            if (topDy != 0) {
                layoutParams.y = topDy;
            }
            if (bottomDy != 0) {
                layoutParams.y = -bottomDy;
            } else {
                for (int i = 0; i < gravity.length; i++) {
                    if (gravity[i] == CENTER) {
                        layoutParams.y = -dp2px(20);
                        break;
                    }
                }
            }
            for (int i = 0; i < gravity.length; i++) {
                layoutParams.gravity |= gravity[i];
            }

            if (animStyle != -1) {
                layoutParams.windowAnimations = animStyle;
            }
            setCancelable(isCancelable);
            window.setDimAmount(dimAmount);
            window.setAttributes(layoutParams);
        }
    }

    /**
     * 设置左右边距
     *
     * @param mMargin
     * @return
     */
    public BaseDialogFragment setRightLeftMargin(int mMargin) {
        this.mMargin = dp2px(mMargin);
        return this;
    }

    /**
     * 设置上位移
     *
     * @param topDy
     * @return
     */
    public BaseDialogFragment setTopDy(int topDy) {
        this.topDy = dp2px(topDy);
        return this;
    }
    public BaseDialogFragment setTopDy(float topDy) {
        this.topDy = (int) topDy;
        return this;
    }
    /**
     * 设置上位移
     *
     * @param bottomDy
     * @return
     */
    public BaseDialogFragment setBottomDy(int bottomDy) {
        this.bottomDy = dp2px(bottomDy);
        return this;
    }

    /**
     * 设置背景
     *
     * @param resid
     * @return
     */
    public BaseDialogFragment setBackgroundResource(@DrawableRes int resid) {
        this.resid = resid;
        return this;
    }

    /**
     * 设置宽高
     *
     * @param width
     * @param height
     * @return
     */
    public BaseDialogFragment setSize(int width, int height) {
        this.width = dp2px(width);
        this.height = dp2px(height);
        return this;
    }

    /**
     * 设置宽高
     *
     * @param width
     * @param height
     * @return
     */
    public BaseDialogFragment setPercentSize(int width, int height) {
        this.width = width;
        this.height = height;
        return this;
    }

    public BaseDialogFragment setPercent(boolean isMathParentWidth, boolean isMathParentHeight) {
        this.isMathParentWidth = isMathParentWidth;
        this.isMathParentHeight = isMathParentHeight;
        return this;
    }

    public BaseDialogFragment setPercentSize(float widthPercent, float heightPercent) {
        width = (int) (getScreenWidth() * widthPercent);
        height = (int) (getScreenHeight() * heightPercent);
        return this;
    }

    /**
     * 设置上下左右中
     *
     * @param gravity
     * @return
     */
    public BaseDialogFragment setGravity(@gravity int... gravity) {
        this.gravity = gravity;
        return this;
    }

    /**
     * 设置进入退出动画
     *
     * @param animStyle
     * @return
     */
    public BaseDialogFragment setAnimStyle(@StyleRes int animStyle) {
        this.animStyle = animStyle;
        return this;
    }

    /**
     * 设置是否能够点击其他区域取消弹窗
     *
     * @param isCancelable
     * @return
     */
    public BaseDialogFragment setIsCancelable(boolean isCancelable) {
        this.isCancelable = isCancelable;
        return this;
    }

    /**
     * 设置背景透明度
     *
     * @param dimAmount
     * @return
     */
    public BaseDialogFragment setDimAmount(@FloatRange(from = 0, to = 1) float dimAmount) {
        this.dimAmount = dimAmount;
        return this;
    }

    public void show(FragmentManager manager) {
        super.show(manager, String.valueOf(System.currentTimeMillis()));
    }

    public void show(FragmentManager manager, String tag) {
        super.show(manager, tag);
    }

    protected abstract int updateLayoutId();

    protected abstract View updateLayoutView();

    /**
     * 操作dialog布局
     *
     * @param holder
     * @param dialog
     */
    protected abstract void convertView(DialogFragmentHolder holder, BaseDialogFragment dialog);

    protected abstract void convertView(DialogFragmentHolder holder, BaseDialogFragment dialog, ViewBinding binding);

    /**
     * 获取屏幕宽度
     *
     * @return
     */
    private static int getScreenWidth() {
        DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
        return displayMetrics.widthPixels;
    }

    private static int getScreenHeight() {
        DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
        return displayMetrics.heightPixels;
    }

    private static int dp2px(float dipValue) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
