package com.zwb.lib_common.constant

/**
 * 路由地址
 * @author: zwb
 * @date: 2022/4/30
 *
 */
object RoutePath{

    const val PATH = "path"

    const val PAGE_PHONE = "/common/PhotoBrowse"

    object Login{
        private const val LOGIN = "/login"
        const val PAGE_LOGIN = "$LOGIN/LoginActivity"
    }

    object Home{
        private const val HOME = "/home"
        const val SERVICE_HOME = "${HOME}/home_service"
        const val FRAGMENT_HOME = "$HOME/HomeMainFragment"
        const val PAGE_ARTICLE = "${HOME}/ArticleDetailActivity"
        const val PAGE_WEBVIEW = "${HOME}/WebViewActivity"
    }

    object Moyu{
        private const val MOYU = "/moyu"
        const val FRAGMENT_MOYU = "$MOYU/MoyuMainFragment"
        const val SERVICE_MOYU = "${MOYU}/moyu_service"
        const val PAGE_DETAIL = "${MOYU}/MoyuDetailActivity"
        const val PARAMS_MOYU_ID = "moyu_id"
    }

    object Wenda{
        private const val WENDA = "/wenda"
        const val FRAGMENT_WENDA = "$WENDA/WendaMainFragment"
        const val SERVICE_WENDA = "${WENDA}/wenda_service"
        const val PAGE_DETAIL = "${WENDA}/WendaDetailActivity"
        const val PAGE_ANSWER_DETAIL = "${WENDA}/WendaAnswerActivity"
        const val PARAMS_WENDA_ID = "wendaId"
        const val PARAMS_WENDA_CONTENT = "wenda"
        const val PARAMS_ANSWER = "answer"
    }

    object Ucenter{
        private const val UCENTER = "/ucenter"
        const val FRAGMENT_UCENTER = "$UCENTER/UcenterFragment"
        const val SERVICE_UCENTER = "${UCENTER}/ucenter_service"

        const val PAGE_UCENTER = "${UCENTER}/UcenterActivity"
        const val PARAMS_USER_ID = "userId"

        const val PAGE_MESSAGE = "${UCENTER}/MsgCenterActivity"
        const val PAGE_UCENTER_LIST = "${UCENTER}/UcenterListActivity"
        const val PAGE_MSG_LIST = "${UCENTER}/MessageListActivity"
        const val PAGE_FAVORITE_LIST = "${UCENTER}/FavoriteListActivity"

    }

    object Shop{
        private const val SHOP = "/shop"
        const val FRAGMENT_SHOP = "${SHOP}/ShopMainFragment"
        const val SERVICE_SHOP = "${SHOP}/shop_service"
    }

}