package com.zwb.lib_common

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import com.zwb.lib_base.ktx.initiateRequest
import com.zwb.lib_base.mvvm.vm.BaseViewModel
import com.zwb.lib_base.net.BaseResponse
import com.zwb.lib_base.net.RetrofitFactory
import com.zwb.lib_base.utils.SpUtils
import com.zwb.lib_common.bean.CollectInputBean
import com.zwb.lib_common.bean.CollectionBean
import com.zwb.lib_common.bean.PriseQrCode
import com.zwb.lib_common.bean.TokenBean
import com.zwb.lib_common.constant.SpKey

open class CommonViewModel : BaseViewModel() {

    private val repository by lazy {
        CommonRepo(loadState)
    }

    fun checkToken() {
        initiateRequest({
            val token = repository.checkToken()
            if (token.success) {
                SpUtils.putString(SpKey.USER_ID, token.data!!.id)
                SpUtils.putString(
                    SpKey.USER_AVATAR,
                    if (TextUtils.isEmpty(token.data!!.avatar)) "" else token.data!!.avatar!!
                )
                SpUtils.putString(
                    SpKey.USER_NICKNAME,
                    if (TextUtils.isEmpty(token.data!!.nickname)) "" else token.data!!.nickname!!
                )
            } else {
                SpUtils.putBoolean(SpKey.IS_LOGIN, false)
                SpUtils.removeValuesForKeys(
                    arrayOf(
                        RetrofitFactory.SOB_TOKEN,
                        RetrofitFactory.L_C_I,
                        SpKey.USER_ID,
                        SpKey.USER_AVATAR,
                        SpKey.USER_NICKNAME
                    )
                )
            }
        }, loadState)
    }

    /**
     * 获取作者的打赏二维码
     */
    fun getPriseQrCode(userId: String): MutableLiveData<BaseResponse<PriseQrCode?>> {
        val response: MutableLiveData<BaseResponse<PriseQrCode?>> = MutableLiveData()
        initiateRequest({
            response.value = repository.getPriseQrCode(userId)
        }, loadState)
        return response
    }

    /**
     * 获取收藏夹（ucenter_module和home_module会调这个方法）
     */
    fun collectionList(page: Int, key: String): MutableLiveData<List<CollectionBean>> {
        val response: MutableLiveData<List<CollectionBean>> = MutableLiveData()
        initiateRequest({
            val res = repository.collectionList(page, key)
            res?.let {
                if (it.content.isNotEmpty()) {
                    response.value = it.content
                }
            }
        }, loadState, key)
        return response
    }


    /**
     * 收藏
     */
    fun favorite(body: CollectInputBean): MutableLiveData<BaseResponse<String?>> {
        val response: MutableLiveData<BaseResponse<String?>> = MutableLiveData()
        initiateRequest({
            val state = repository.checkCollected(body.url)
            // 未收藏
            if (state.success && !TextUtils.isEmpty(state.data) && state.data == "0") {
                response.value = repository.favorite(body)
            }
        }, loadState)
        return response
    }

    /**
     * 取消收藏
     */
    fun unFavorite(favoriteId: String): MutableLiveData<BaseResponse<String?>> {
        val response: MutableLiveData<BaseResponse<String?>> = MutableLiveData()
        initiateRequest({
            response.value = repository.unFavorite(favoriteId)
        }, loadState)
        return response
    }

    /**
     * 检测是否收藏
     */
    fun checkCollected(url: String): MutableLiveData<BaseResponse<String?>> {
        val response: MutableLiveData<BaseResponse<String?>> = MutableLiveData()
        initiateRequest({
            response.value = repository.checkCollected(url)
        }, loadState)
        return response
    }

    /**
     * 关注 or 取消关注
     */
    fun follow(userId: String): MutableLiveData<BaseResponse<Int?>> {
        val response: MutableLiveData<BaseResponse<Int?>> = MutableLiveData()
        initiateRequest({
            val state = repository.followState(userId)
            if (state.success && state.data != null) {
                //        0表示没有关注对方，可以显示为：关注
                //        1表示对方关注自己，可以显示为：回粉
                //        2表示已经关注对方，可以显示为：已关注
                //        3表示相互关注，可以显示为：相互关注
                when (state.data) {
                    0, 1 -> response.value = repository.follow(userId)
                    2, 3 -> response.value = repository.unFollow(userId)
                }
            }

        }, loadState)
        return response
    }

    /**
     * 关注状态
     */
    fun followState(userId: String): MutableLiveData<BaseResponse<Int?>> {
        val response: MutableLiveData<BaseResponse<Int?>> = MutableLiveData()
        initiateRequest({
            response.value = repository.followState(userId)
        }, loadState)
        return response
    }


}