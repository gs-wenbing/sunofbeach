package com.zwb.lib_common.event

class UpdateItemEvent(var event: String,var id: String) {
    object Event{
        const val UPDATE_MOYU = "update_moyu"
        const val UPDATE_WENDA = "update_wenda"
    }
}