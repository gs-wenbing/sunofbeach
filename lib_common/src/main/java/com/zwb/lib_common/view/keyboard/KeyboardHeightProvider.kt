package com.zwb.lib_common.view.keyboard

import android.app.Activity
import android.content.res.Configuration
import android.graphics.Point
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.view.WindowManager
import android.widget.PopupWindow
import com.zwb.lib_common.R

/**
 * The keyboard height provider, this class uses a PopupWindow
 * to calculate the window height when the floating keyboard is opened and closed.
 */
class KeyboardHeightProvider(activity: Activity) : PopupWindow(activity) {

    /**
     * The tag for logging purposes
     */
    private val TAG = "sample_KeyboardHeightProvider"

    /**
     * The keyboard height observer
     */
    private var observer: KeyboardHeightObserver? = null

    /**
     * The cached landscape height of the keyboard
     */
    private var keyboardLandscapeHeight = 0

    /**
     * The cached portrait height of the keyboard
     */
    private var keyboardPortraitHeight = 0

    /**
     * The view that is used to calculate the keyboard height
     */
    private var popupView: View? = null

    /**
     * The parent view
     */
    private var parentView: View? = null

    /**
     * The root activity that uses this KeyboardHeightProvider
     */
    private var activity: Activity? = activity


    private var lasTimekeyboardHeight = 0

    private var mOnGlobalLayoutListener:OnGlobalLayoutListener

    init {
        val inflator = activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        popupView = inflator.inflate(R.layout.popupwindow, null, false)
        contentView = popupView
        softInputMode =
            WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE or WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE
        inputMethodMode = INPUT_METHOD_NEEDED
        parentView = activity.findViewById(android.R.id.content)
        //  这样既能测量高度，又不会导致界面不能点击
        width = 0
        height = WindowManager.LayoutParams.MATCH_PARENT

        mOnGlobalLayoutListener = OnGlobalLayoutListener {
            if (popupView != null) {
                handleOnGlobalLayout()
            }
        }
        popupView!!.viewTreeObserver.addOnGlobalLayoutListener(mOnGlobalLayoutListener)
    }


    /**
     * Start the KeyboardHeightProvider, this must be called after the onResume of the Activity.
     * PopupWindows are not allowed to be registered before the onResume has finished
     * of the Activity.
     */
    fun start() {
        if (!isShowing && parentView!!.windowToken != null) {
            setBackgroundDrawable(ColorDrawable(0))
            showAtLocation(parentView, Gravity.NO_GRAVITY, 0, 0)
        }
    }

    /**
     * Close the keyboard height provider,
     * this provider will not be used anymore.
     */
    fun close() {
        observer = null
        dismiss()
    }

    /**
     * Set the keyboard height observer to this provider. The
     * observer will be notified when the keyboard height has changed.
     * For example when the keyboard is opened or closed.
     *
     * @param observer The observer to be added to this provider.
     */
    fun setKeyboardHeightObserver(observer: KeyboardHeightObserver?) {
        this.observer = observer
    }

    /**
     * Get the screen orientation
     *
     * @return the screen orientation
     */
    private fun getScreenOrientation(): Int {
        return activity!!.resources.configuration.orientation
    }

    /**
     * Popup window itself is as big as the window of the Activity.
     * The keyboard can then be calculated by extracting the popup view bottom
     * from the activity window height.
     */
    private fun handleOnGlobalLayout() {
        val screenSize = Point()
        activity!!.windowManager.defaultDisplay.getSize(screenSize)
        val rect = Rect()
        popupView!!.getWindowVisibleDisplayFrame(rect)

        // REMIND, you may like to change this using the fullscreen size of the phone
        // and also using the status bar and navigation bar heights of the phone to calculate
        // the keyboard height. But this worked fine on a Nexus.
        val orientation = getScreenOrientation()
        val keyboardHeight = screenSize.y - rect.bottom
        lasTimekeyboardHeight = if (lasTimekeyboardHeight == keyboardHeight) {
            return
        } else {
            keyboardHeight
        }
        when {
            keyboardHeight == 0 -> {
                notifyKeyboardHeightChanged(0, orientation)
            }
            orientation == Configuration.ORIENTATION_PORTRAIT -> {
                keyboardPortraitHeight = keyboardHeight
                notifyKeyboardHeightChanged(keyboardPortraitHeight, orientation)
            }
            else -> {
                keyboardLandscapeHeight = keyboardHeight
                notifyKeyboardHeightChanged(keyboardLandscapeHeight, orientation)
            }
        }
    }

    /**
     *
     */
    private fun notifyKeyboardHeightChanged(height: Int, orientation: Int) {
        if (observer != null) {
            observer!!.onKeyboardHeightChanged(height, orientation)
        }
    }

    fun recycle() {
        dismiss()
        popupView!!.viewTreeObserver.removeOnGlobalLayoutListener(mOnGlobalLayoutListener)
        observer = null
        activity = null
        parentView = null
        popupView = null
    }
}