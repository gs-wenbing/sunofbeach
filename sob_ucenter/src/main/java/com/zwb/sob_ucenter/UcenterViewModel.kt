package com.zwb.sob_ucenter

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import com.zwb.lib_base.bean.ListData
import com.zwb.lib_base.bean.PageViewData
import com.zwb.lib_base.ktx.initiateRequest
import com.zwb.lib_base.net.BaseResponse
import com.zwb.lib_common.CommonViewModel
import com.zwb.lib_common.bean.MoyuItemBean
import com.zwb.lib_common.bean.UserBean
import com.zwb.lib_common.constant.Constants
import com.zwb.lib_common.constant.Constants.Ucenter.PAGE_FOLLOW
import com.zwb.sob_ucenter.bean.*

class UcenterViewModel : CommonViewModel() {

    private val userRepo by lazy {
        UcenterRepo(loadState)
    }

    fun getUserInfo(userId: String = "", key: String): MutableLiveData<UserBean?> {
        val response: MutableLiveData<UserBean?> = MutableLiveData()
        initiateRequest({
            if (!TextUtils.isEmpty(userId)) {
                response.value = userRepo.getUserInfo(userId, key)
            } else {
                val token = userRepo.checkToken()
                if(token.success){
                    if (token.data != null) {
                        response.value = userRepo.getUserInfo(token.data!!.id, key)
                    } else {
                        response.value = null
                    }
                }
            }
        }, loadState, key)
        return response
    }

    fun getTotalSobCoin(): MutableLiveData<BaseResponse<Int?>> {
        val response: MutableLiveData<BaseResponse<Int?>> = MutableLiveData()
        initiateRequest({
            response.value = userRepo.getTotalSobCoin()
        }, loadState)
        return response
    }


    fun getSobList(userId: String, page: Int, key: String): MutableLiveData<ListData<SobBean>?> {
        val response: MutableLiveData<ListData<SobBean>?> = MutableLiveData()
        initiateRequest({
            response.value = userRepo.getSobList(userId, page, key)
        }, loadState, key)
        return response
    }


    fun logout(key: String): MutableLiveData<BaseResponse<String?>> {
        val response: MutableLiveData<BaseResponse<String?>> = MutableLiveData()
        initiateRequest({
            response.value = userRepo.logout()
        }, loadState, key)
        return response
    }

    fun avatar(phoneNum: String, key: String): MutableLiveData<BaseResponse<String?>> {
        val response: MutableLiveData<BaseResponse<String?>> = MutableLiveData()
        initiateRequest({
            response.value = userRepo.avatar(phoneNum)
        }, loadState, key)
        return response
    }

    /**
     * ????????????????????????
     */
    fun getUserArticleList(
        userId: String,
        page: Int,
        key: String
    ): MutableLiveData<ListData<ArticleBean>?> {
        val response: MutableLiveData<ListData<ArticleBean>?> = MutableLiveData()
        initiateRequest({
            response.value = userRepo.getUserArticleList(userId, page, key)
        }, loadState, key)
        return response
    }

    /**
     * ????????????????????????
     */
    fun getUserMoyuList(
        userId: String,
        page: Int,
        key: String
    ): MutableLiveData<List<MoyuItemBean>?> {
        val response: MutableLiveData<List<MoyuItemBean>?> = MutableLiveData()
        initiateRequest({
            response.value = userRepo.getUserMoyuList(userId, page, key)
        }, loadState, key)
        return response
    }

    /**
     * ????????????????????????
     */
    fun getUserShareList(
        userId: String,
        page: Int,
        key: String
    ): MutableLiveData<ListData<ShareBean>?> {
        val response: MutableLiveData<ListData<ShareBean>?> = MutableLiveData()
        initiateRequest({
            response.value = userRepo.getUserShareList(userId, page, key)
        }, loadState, key)
        return response
    }

    /**
     * ????????????????????????
     */
    fun getUserWendaList(
        userId: String,
        page: Int,
        key: String
    ): MutableLiveData<PageViewData<UserWendaBean>?> {
        val response: MutableLiveData<PageViewData<UserWendaBean>?> = MutableLiveData()
        initiateRequest({
            response.value = userRepo.getUserWendaList(userId, page, key)
        }, loadState, key)
        return response
    }


    /**
     * ??????????????????
     */
    fun getUserAchievement(userId: String, key: String): MutableLiveData<AchievementBean?> {
        val response: MutableLiveData<AchievementBean?> = MutableLiveData()
        initiateRequest({
            response.value = userRepo.getUserAchievement(userId, key)
        }, loadState, key)
        return response
    }

    /**
     * ??????????????????
     */
    fun getMyAchievement(key: String): MutableLiveData<AchievementBean?> {
        val response: MutableLiveData<AchievementBean?> = MutableLiveData()
        initiateRequest({
            response.value = userRepo.getMyAchievement(key)
        }, loadState, key)
        return response
    }

    /**
     * ???????????????
     */
    fun getMsgCount(key: String): MutableLiveData<MsgCountBean?> {
        val response: MutableLiveData<MsgCountBean?> = MutableLiveData()
        initiateRequest({
            response.value = userRepo.getMsgCount(key)
        }, loadState, key)
        return response
    }

    /**
     * ????????????/????????????
     */
    fun followList(
        pageType: Int,
        userId: String,
        page: Int,
        key: String
    ): MutableLiveData<ListData<FollowBean>> {
        val response: MutableLiveData<ListData<FollowBean>> = MutableLiveData()
        initiateRequest({
            response.value =
                if (pageType == PAGE_FOLLOW)
                    userRepo.followList(userId, page, key)
                else
                    userRepo.fansList(userId, page, key)
        }, loadState, key)
        return response
    }

    /**
     * ????????????
     */
    fun msgRead(): MutableLiveData<BaseResponse<String?>> {
        val response: MutableLiveData<BaseResponse<String?>> = MutableLiveData()
        initiateRequest({
            response.value = userRepo.msgRead()
        }, loadState)
        return response
    }

    /**
     * sob?????????
     */
    fun rankingSob(key: String): MutableLiveData<List<RankingSobBean>> {
        val response: MutableLiveData<List<RankingSobBean>> = MutableLiveData()
        initiateRequest({
            response.value = userRepo.rankingSob(key)
        }, loadState,key)
        return response
    }

    /**
     * ????????????????????????????????????
     */
    fun favoriteList(collectionId: String,page: Int,key: String): MutableLiveData<PageViewData<FavoriteBean>?> {
        val response: MutableLiveData<PageViewData<FavoriteBean>?> = MutableLiveData()
        initiateRequest({
            response.value = userRepo.favoriteList(collectionId,page,key)
        }, loadState,key)
        return response
    }

    /**
     * ????????????????????????
     */
    fun messageSystemList(page: Int,key: String): MutableLiveData<PageViewData<MsgSystemBean>?> {
        val response: MutableLiveData<PageViewData<MsgSystemBean>?> = MutableLiveData()
        initiateRequest({
            response.value = userRepo.messageSystemList(page,key)
        }, loadState,key)
        return response
    }
    /**
     * ????????????????????????
     */
    fun messageArticleList(page: Int,key: String): MutableLiveData<PageViewData<MsgArticleBean>?> {
        val response: MutableLiveData<PageViewData<MsgArticleBean>?> = MutableLiveData()
        initiateRequest({
            response.value = userRepo.messageArticleList(page,key)
        }, loadState,key)
        return response
    }

    /**
     * ???????????????????????????
     */
    fun messageMomentList(page: Int,key: String): MutableLiveData<PageViewData<MsgMomentBean>?> {
        val response: MutableLiveData<PageViewData<MsgMomentBean>?> = MutableLiveData()
        initiateRequest({
            response.value = userRepo.messageMomentList(page,key)
        }, loadState,key)
        return response
    }

    /**
     * ???????????????????????????
     */
    fun messageThumbList(page: Int,key: String): MutableLiveData<PageViewData<MsgThumbBean>?> {
        val response: MutableLiveData<PageViewData<MsgThumbBean>?> = MutableLiveData()
        initiateRequest({
            response.value = userRepo.messageThumbList(page,key)
        }, loadState,key)
        return response
    }

    /**
     * ???????????????AT??????
     */
    fun messageAtList(page: Int,key: String): MutableLiveData<PageViewData<MsgAtBean>?> {
        val response: MutableLiveData<PageViewData<MsgAtBean>?> = MutableLiveData()
        initiateRequest({
            response.value = userRepo.messageAtList(page,key)
        }, loadState,key)
        return response
    }
    /**
     * ????????????
     */
    fun articleState(msgId: String): MutableLiveData<BaseResponse<String?>> {
        val response: MutableLiveData<BaseResponse<String?>> = MutableLiveData()
        initiateRequest({
            response.value = userRepo.articleState(msgId)
        }, loadState)
        return response
    }
    /**
     * ?????????????????????????????????
     */
    fun momentState(msgId: String): MutableLiveData<BaseResponse<String?>> {
        val response: MutableLiveData<BaseResponse<String?>> = MutableLiveData()
        initiateRequest({
            response.value = userRepo.momentState(msgId)
        }, loadState)
        return response
    }
    /**
     * at??????
     */
    fun atState(msgId: String): MutableLiveData<BaseResponse<String?>> {
        val response: MutableLiveData<BaseResponse<String?>> = MutableLiveData()
        initiateRequest({
            response.value = userRepo.atState(msgId)
        }, loadState)
        return response
    }
    /**
     * ????????????
     */
    fun wendaState(msgId: String): MutableLiveData<BaseResponse<String?>> {
        val response: MutableLiveData<BaseResponse<String?>> = MutableLiveData()
        initiateRequest({
            response.value = userRepo.wendaState(msgId)
        }, loadState)
        return response
    }

    /**
     * ??????????????????????????????at???
     */
    fun updateMsgState(pageType:Int, msgId: String): MutableLiveData<BaseResponse<String?>> {
        val response: MutableLiveData<BaseResponse<String?>> = MutableLiveData()
        initiateRequest({
            when (pageType) {
                Constants.Ucenter.PAGE_MSG_DYNAMIC -> response.value = userRepo.momentState(msgId)
                Constants.Ucenter.PAGE_MSG_AT -> response.value = userRepo.atState(msgId)
                Constants.Ucenter.PAGE_MSG_ARTICLE -> response.value = userRepo.articleState(msgId)
            }

        }, loadState)
        return response
    }


}