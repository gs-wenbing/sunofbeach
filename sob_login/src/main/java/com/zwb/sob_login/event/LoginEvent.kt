package com.zwb.sob_login.event

data class LoginEvent(var key: String, var value: String? = null) {

    companion object {
        const val L_C_I = "l_c_i"
    }
}