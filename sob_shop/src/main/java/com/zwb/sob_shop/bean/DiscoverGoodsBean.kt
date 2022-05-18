package com.zwb.sob_shop.bean

data class DiscoverGoodsBean(
    var category_id: Long,
    var category_name: String?,
    var click_url: String?,//s.click.taobao.com/t?e=m%3D2%26s%3DN8yGtdbUXiVw4vFB6t2Z2ueEDrYVVa64Dne87AjQPk9yINtkUhsv0BcvKH%2BTjMa38aSx7PqjIIZm5nS0VR3oT0KAZCke%2BMGJxC%2FP4%2FZfPFbcQmwDRwHnn1oN8CPq4PKMZiqtwk9j5QNerBM5mSXVLZIQa6qg9eLV0a3u46RXbtekOrGae4DS5oO2CiNcVz0KPqWqRPDXyNNpqoxKosq%2FMbtdhRLMp6nxY4S2k1mJ%2BZU%3D&scm=1007.19011.125573.0_9660&pvid=9e141d3e-367c-445e-b2dd-164409bc01e7&app_pvid=59590_11.8.57.194_76981_1575265017272&ptl=floorId:9660;originalFloorId:9660;pvid:9e141d3e-367c-445e-b2dd-164409bc01e7;app_pvid:59590_11.8.57.194_76981_1575265017272&union_lens=lensId%3AOPT%401575265017%400b0839c2_0d6c_16ec51c9e3f_ba08%4001,
    var commission_rate: Double,
    var coupon_amount: Int,
    var coupon_click_url: String?,//uland.taobao.com/coupon/edetail?e=QQzniHg2%2BooNfLV8niU3R5TgU2jJNKOfU1BGIDN741G0%2Bc5Gzwi6geKSfLX1rbqQLGbJes29FQq2QeBl4W2u7xytS5OGw29olRKZ8DI0LPZKuy%2B0WAVbvPskU%2Fn7vZ%2Fe3KE7Ta9aMuRNo1KEDehNsJ4dcb7gMzr8o%2Be84lu5p3EGQASttHIRqWOUJeY8%2FPSU5VEyZiiJcVy4Q6eh0tBC8a5OaBGcL9GSTA0FRtOwCuw%3D&&app_pvid=59590_11.8.57.194_76981_1575265017272&ptl=floorId:9660;app_pvid:59590_11.8.57.194_76981_1575265017272;tpp_pvid:9e141d3e-367c-445e-b2dd-164409bc01e7&union_lens=lensId%3AOPT%401575265017%400b0839c2_0d6c_16ec51c9e3f_ba08%4001,
    var coupon_end_time: Long,
    var coupon_remain_count: Int,
    var coupon_share_url: String?,//uland.taobao.com/coupon/edetail?e=QQzniHg2%2BooNfLV8niU3R5TgU2jJNKOfU1BGIDN741G0%2Bc5Gzwi6geKSfLX1rbqQLGbJes29FQq2QeBl4W2u7xytS5OGw29olRKZ8DI0LPZKuy%2B0WAVbvPskU%2Fn7vZ%2Fe3KE7Ta9aMuRNo1KEDehNsJ4dcb7gMzr8o%2Be84lu5p3EGQASttHIRqWOUJeY8%2FPSU5VEyZiiJcVy4Q6eh0tBC8a5OaBGcL9GSTA0FRtOwCuw%3D&&app_pvid=59590_11.8.57.194_76981_1575265017272&ptl=floorId:9660;app_pvid:59590_11.8.57.194_76981_1575265017272;tpp_pvid:9e141d3e-367c-445e-b2dd-164409bc01e7&union_lens=lensId%3AOPT%401575265017%400b0839c2_0d6c_16ec51c9e3f_ba08%4001,
    var coupon_start_fee: Double,
    var coupon_start_time: Long,
    var coupon_total_count: Int,
    var item_description: String?,//买1送1 买2送3,
    var item_id: Long,
    var level_one_category_id: Long,
    var level_one_category_name: String?,//洗护清洁剂/卫生巾/纸/香薰,
    var nick: String?,//大末居家日用专营店,
    var pict_url: String?,////gw.alicdn.com/bao/uploaded/i2/1881526428/O1CN01wUGVGL1xM4SAcwL9t_!!0-item_pic.jpg,
    var seller_id: Long,
    var shop_title: String?,//大末居家日用专营店,
    var small_images: ImageStringBean?,
    var title: String?,//活氧彩漂白剂彩色衣物还原衣服通用彩票粉家用彩漂粉去渍去黄增白,
    var user_type: Int,
    var volume: Int,
    var zk_final_price: Double
):ShopItemGoodsBean()

data class ImageStringBean(
    var string:List<String>
)