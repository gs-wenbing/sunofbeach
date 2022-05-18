package com.zwb.lib_base.callback

import android.content.Context
import android.view.View
import com.kingja.loadsir.callback.Callback

class PlaceHolderCallback(var layoutId:Int) :Callback(){

    override fun onCreateView(): Int = layoutId

    override fun onReloadEvent(context: Context?, view: View?): Boolean = true


}