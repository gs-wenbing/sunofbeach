package com.zwb.lib_base.net

/**
 * Description: 返回数据基类
 * @date: 2022/4/30
 * Time: 16:04
 */

open class BaseResponse<T>(
    var success: Boolean = false,
    var data: T,
    var code: Int = -1,
    var message: String = ""
)