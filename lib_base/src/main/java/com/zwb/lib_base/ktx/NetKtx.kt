package com.zwb.lib_base.ktx

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.zwb.lib_base.bean.ListData
import com.zwb.lib_base.bean.PageViewData
import com.zwb.lib_base.mvvm.vm.BaseViewModel
import com.zwb.lib_base.net.BaseResponse
import com.zwb.lib_base.net.NetExceptionHandle
import com.zwb.lib_base.net.State
import com.zwb.lib_base.net.StateType
import kotlinx.coroutines.launch

/**
 * zwb
 * Description:数据解析扩展函数
 * @CreateDate: 2022/4/30 17:35
 */

fun <T> BaseResponse<T>.dataConvert(
    loadState: MutableLiveData<State>,
    urlKey: String = ""
): T {
    return if (code == 10000 && success) {
        if (data is List<*>) {
            if ((data as List<*>).isEmpty()) {
                loadState.value = State(StateType.EMPTY, urlKey)
            } else {
                loadState.value = State(StateType.SUCCESS, urlKey)
            }
        } else if(data is ListData<*>){
            if ((data as ListData<*>).list.isEmpty()) {
                loadState.postValue(State(StateType.EMPTY,urlKey))
            } else {
                loadState.value = State(StateType.SUCCESS, urlKey)
            }
        } else if(data is PageViewData<*>){
            if ((data as PageViewData<*>).content.isEmpty()) {
                loadState.postValue(State(StateType.EMPTY,urlKey))
            } else {
                loadState.value = State(StateType.SUCCESS, urlKey)
            }
        } else {
            loadState.value = State(StateType.SUCCESS, urlKey)
        }
        data
    } else {
        loadState.postValue(State(StateType.ERROR, urlKey, message = message))
        data
    }
}


fun BaseViewModel.initiateRequest(
    block: suspend () -> Unit,
    loadState: MutableLiveData<State>,
    urlKey: String = ""
) {
    viewModelScope.launch {
        runCatching {
            block()
        }.onSuccess {
        }.onFailure {
            NetExceptionHandle.handleException(it, loadState, urlKey)
        }
    }
}
