package com.yinp.tools.utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.IntDef;

import com.yinp.tools.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


public class ToastUtil extends Toast {
    /**
     * Construct an empty Toast object.  You must call {@link #setView} before you
     * can call {@link #show}.
     *
     * @param context The context to use.  Usually your {@link Application}
     *                or {@link Activity} object.
     */
    public ToastUtil(Context context) {
        super(context);
    }


    public static void cancelToast() {
        if (toastUtil != null) {
            toastUtil.cancel();
        }
    }

    /**
     * @hide
     */
    @IntDef(value = {
            LENGTH_SHORT,
            LENGTH_LONG
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface Duration {
    }

    static ToastUtil toastUtil;
    static Context mContext;

    public static void initToast(Context context, int resId) {
        initToast(context, context.getResources().getText(resId));
    }

    public static void initToast(Context context, CharSequence text) {
        initToast(context, text, LENGTH_SHORT);
    }

    public static void initToast(Context context, CharSequence text, @Duration long time) {
        //第二次点击及以后，它会先取消上一次的Toast, 然后show本次的Toast。
        mContext = context;
        cancelToast();

        toastUtil = new ToastUtil(context);
        View mView = LayoutInflater.from(context).inflate(R.layout.layout_toast, null);
        TextView tv_content = mView.findViewById(R.id.tv_content);
        tv_content.setText(text);
        toastUtil.setView(mView);
        toastUtil.setGravity(Gravity.BOTTOM, 0, 70);
        toastUtil.setDuration(Toast.LENGTH_LONG);
        toastUtil.show();
    }

    public static void initStatusToast(Context context, int resId, boolean status) {
        initStatusToast(context, context.getResources().getText(resId), Toast.LENGTH_SHORT, status);
    }

    public static void initStatusToast(Context context, CharSequence text, boolean status) {
        initStatusToast(context, text, Toast.LENGTH_SHORT, status);
    }

    public static void initStatusToast(Context context, CharSequence text, @Duration long time, boolean status) {
        //第二次点击及以后，它会先取消上一次的Toast, 然后show本次的Toast。
        mContext = context;
        cancelToast();

        toastUtil = new ToastUtil(context);
        View mView = LayoutInflater.from(context).inflate(R.layout.layout_toast, null);
        TextView tv_content = mView.findViewById(R.id.tv_content);
        ImageView iv_status = mView.findViewById(R.id.iv_status);
        iv_status.setVisibility(View.VISIBLE);
        if (status) {
            iv_status.setBackgroundResource(R.mipmap.success_icon);
        } else {
            iv_status.setBackgroundResource(R.mipmap.fail_icon);
        }
        tv_content.setText(text);
        toastUtil.setView(mView);
        toastUtil.setGravity(Gravity.CENTER, 0, 0);
        toastUtil.setDuration(Toast.LENGTH_LONG);
        toastUtil.show();
    }

    @Override
    public void cancel() {
        super.cancel();
    }

    @Override
    public void show() {
        super.show();
    }
}
