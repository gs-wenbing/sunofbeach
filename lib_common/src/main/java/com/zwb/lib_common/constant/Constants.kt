package com.zwb.lib_common.constant

/**
 * 常量数据
 * @author: zwb
 * @date: 2022/4/30
 *
 */
object Constants {

    const val WEBSITE_URL = "https://www.sunofbeach.net/a/"
    const val UCENTER_URL = "https://www.sunofbeach.net/u/"
    const val WENDA_URL = "https://www.sunofbeach.net/qa/"
    const val WEBSITE_PREFIX = "http://www.sunofbeach"
    //未登录
    const val UN_LOGIN = 11126

    object URL {
        const val CHECK_TOKEN_URL = "uc/user/checkToken"
    }


    object MultiItemType {
        //标题
        const val TYPE_TITLE = 1

        //内容
        const val TYPE_CONTENT = 2

        //评论
        const val TYPE_COMMENT = 3

        //评论的评论
        const val TYPE_SUB_COMMENT = 4

        //推荐
        const val TYPE_RECOMMEND = 5
    }


    object Ucenter {

        const val PAGE_TYPE = "page_type"

        //收藏集
        const val PAGE_COLLOCATION = 1

        //关注列表
        const val PAGE_FOLLOW = 2

        //粉丝列表
        const val PAGE_FANS = 3

        //点赞列表
        const val PAGE_STARS = 4

        //富豪榜
        const val PAGE_RANKING = 5

        //Sob币
        const val PAGE_SOB = 6

        //消息-问答
        const val PAGE_MSG_WENDA = 7

        //消息-文章
        const val PAGE_MSG_ARTICLE = 8

        //消息-动态
        const val PAGE_MSG_DYNAMIC = 9

        //消息-给朕点赞
        const val PAGE_MSG_STAR = 10

        //消息-@朕
        const val PAGE_MSG_AT = 11

        //消息-系统消息
        const val PAGE_MSG_SYSTEM = 12
    }
}