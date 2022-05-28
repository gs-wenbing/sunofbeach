package com.zwb.lib_common.view.keyboard

import android.R
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.app.Activity
import android.content.res.Configuration
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.zwb.lib_base.utils.KeyBoardUtils
import com.zwb.lib_base.utils.UIUtils

/**
 * 类描述：防止软键盘弹出时挡住相关按钮或布局
 * 创建人：huangyaobin
 * github：https://github.com/yoyoyaobin/PreventKeyboardBlockUtil
 * 这个类有问题，存在内存泄漏，只是参考
 */
object PreventKeyboardBlockUtil {
    private var preventKeyboardBlockUtil: PreventKeyboardBlockUtil? = null
    var mActivity: Activity? = null
    var mBtnView: View? = null
    var rootView: ViewGroup? = null
    var isMove = false
    var marginBottom = 0
    var keyboardHeightProvider: KeyboardHeightProvider? = null
    var keyBoardHeight = 0
    var btnViewY = 0
    var isRegister = false
    var animSet = AnimatorSet()

    fun getInstance(activity: Activity): PreventKeyboardBlockUtil {
        if (preventKeyboardBlockUtil == null) {
            preventKeyboardBlockUtil = this
        }
        initData(activity)
        return preventKeyboardBlockUtil!!
    }


    private fun initData(activity: Activity) {
        mActivity = activity
        mActivity!!.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
        rootView =
            (mActivity!!.findViewById<View>(R.id.content) as ViewGroup).getChildAt(0) as ViewGroup
        isMove = false
        marginBottom = 0
        if (keyboardHeightProvider != null) {
            keyboardHeightProvider!!.recycle()
            keyboardHeightProvider = null
        }
        keyboardHeightProvider = KeyboardHeightProvider(
            mActivity!!
        )
    }

    fun setBtnView(btnView: View?): PreventKeyboardBlockUtil {
        mBtnView = btnView
        return preventKeyboardBlockUtil!!
    }


    private var mHandler: Handler = @SuppressLint("HandlerLeak")
    object : Handler() {
        override fun handleMessage(msg: Message) {
            startAnim(msg.arg1)
        }
    }

    fun startAnim(transY: Int) {
        val curTranslationY = rootView!!.translationY
        val objectAnimator =
            ObjectAnimator.ofFloat(rootView, "translationY", curTranslationY, transY.toFloat())
        animSet.play(objectAnimator)
        animSet.duration = 200
        animSet.start()
    }

    fun register() {
        isRegister = true
        keyboardHeightProvider!!.setKeyboardHeightObserver(object : KeyboardHeightObserver {
            override fun onKeyboardHeightChanged(height: Int, orientation: Int) {
                if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    return
                }
                if (!isRegister) {
                    return
                }
                keyBoardHeight = if (keyBoardHeight == height) {
                    return
                } else {
                    height
                }
                if (keyBoardHeight <= 0) { //键盘收起
                    if (isMove) {
                        sendHandlerMsg(0)
                        isMove = true
                    }
                } else { //键盘打开
                    val keyBoardTopY: Int = UIUtils.getScreenHeight() - keyBoardHeight
                    if (keyBoardTopY > btnViewY + mBtnView!!.height) {
                        return
                    }
                    val margin = keyBoardTopY - (btnViewY + mBtnView!!.height)
                    Log.i("tag=====", "margin:$margin")
                    sendHandlerMsg(margin)
                    isMove = true
                }
            }
        })
        mBtnView!!.post {
            btnViewY = getViewLocationYInScreen(mBtnView)
            keyboardHeightProvider!!.start()
        }
    }

    fun unRegister() {
        isRegister = false
        KeyBoardUtils.hideInputForce(mActivity)
        keyBoardHeight = 0
        sendHandlerMsg(0)
        if (keyboardHeightProvider != null) {
            keyboardHeightProvider!!.setKeyboardHeightObserver(null)
            keyboardHeightProvider!!.close()
        }
    }

    fun recycle() {
        mActivity = null
        if (keyboardHeightProvider != null) {
            keyboardHeightProvider!!.recycle()
            keyboardHeightProvider = null
        }
    }

    private fun sendHandlerMsg(i: Int) {
        val message = Message()
        message.arg1 = i
        mHandler.sendMessage(message)
    }

    private fun getViewLocationYInScreen(view: View?): Int {
        val location = IntArray(2)
        view!!.getLocationOnScreen(location)
        return location[1]
    }
}