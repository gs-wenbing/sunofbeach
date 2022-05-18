package com.zwb.lib_base.callback

import com.kingja.loadsir.callback.Callback
import com.zwb.lib_base.R

class EmptyCallback :Callback(){
    override fun onCreateView(): Int = R.layout.base_layout_empty
}