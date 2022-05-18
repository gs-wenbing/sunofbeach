package com.zwb.sob_ucenter

import androidx.lifecycle.MutableLiveData
import com.zwb.lib_base.bean.ListData
import com.zwb.lib_base.bean.PageViewData
import com.zwb.lib_base.ktx.dataConvert
import com.zwb.lib_base.net.BaseResponse
import com.zwb.lib_base.net.RetrofitFactory
import com.zwb.lib_base.net.State
import com.zwb.lib_common.CommonRepo
import com.zwb.lib_common.bean.MoyuItemBean
import com.zwb.lib_common.bean.UserBean
import com.zwb.sob_ucenter.bean.*

class UcenterRepo(private val loadState: MutableLiveData<State>) : CommonRepo(loadState) {

    private val apiService by lazy {
        RetrofitFactory.instance.getService(UcenterApi::class.java, UcenterApi.BASE_URL)
    }
//
//    suspend fun checkToken(key: String): TokenBean? {
//        return apiService.checkToken().dataConvert(loadState, key)
//    }


    suspend fun getUserInfo(userId: String, key: String): UserBean? {
        return apiService.getUserInfo(userId).dataConvert(loadState, key)
    }

    suspend fun getTotalSobCoin(): BaseResponse<Int?> {
        return apiService.getTotalSobCoin()
    }

    suspend fun getSobList(userId: String, page: Int, key: String): ListData<SobBean>? {
        return apiService.getSobList(userId, page).dataConvert(loadState, key)
    }


    suspend fun logout(): BaseResponse<String?> {
        return apiService.logout()
    }

    suspend fun avatar(phoneNum: String): BaseResponse<String?> {
        return apiService.avatar(phoneNum)
    }


    suspend fun getUserMoyuList(userId: String, page: Int, key: String): List<MoyuItemBean>? {
        return apiService.getUserMoyuList(userId, page).dataConvert(loadState, key)
    }

    suspend fun getUserArticleList(userId: String, page: Int, key: String): ListData<ArticleBean>? {
        return apiService.getUserArticleList(userId, page).dataConvert(loadState, key)
    }

    suspend fun getUserShareList(userId: String, page: Int, key: String): ListData<ShareBean>? {
        return apiService.getUserShareList(userId, page).dataConvert(loadState, key)
    }

    suspend fun getUserWendaList(
        userId: String,
        page: Int,
        key: String
    ): PageViewData<UserWendaBean>? {
        return apiService.getUserWendaList(userId, page).dataConvert(loadState, key)
    }

    suspend fun getUserAchievement(userId: String, key: String): AchievementBean? {
        return apiService.getUserAchievement(userId).dataConvert(loadState, key)
    }

    suspend fun getMyAchievement(key: String): AchievementBean? {
        return apiService.getMyAchievement().dataConvert(loadState, key)
    }

    suspend fun getMsgCount(key: String): MsgCountBean? {
        return apiService.getMsgCount().dataConvert(loadState, key)
    }

    suspend fun followList(userId: String, page: Int, key: String): ListData<FollowBean>? {
        return apiService.followList(userId, page).dataConvert(loadState, key)
    }

    suspend fun fansList(userId: String, page: Int, key: String): ListData<FollowBean>? {
        return apiService.fansList(userId, page).dataConvert(loadState, key)
    }

    suspend fun msgRead(): BaseResponse<String?> {
        return apiService.msgRead()
    }

    suspend fun rankingSob(key: String): List<RankingSobBean>? {
        return apiService.rankingSob().dataConvert(loadState, key)
    }

    suspend fun favoriteList(collectionId: String,page: Int,key: String): PageViewData<FavoriteBean>? {
        return apiService.favoriteList(collectionId,page).dataConvert(loadState, key)
    }


}