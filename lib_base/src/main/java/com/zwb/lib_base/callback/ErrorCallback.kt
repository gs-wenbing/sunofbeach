package com.zwb.lib_base.callback

import com.kingja.loadsir.callback.Callback
import com.zwb.lib_base.R

class ErrorCallback :Callback(){
    override fun onCreateView(): Int = R.layout.base_layout_error
}