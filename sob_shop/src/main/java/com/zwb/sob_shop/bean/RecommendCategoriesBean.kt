package com.zwb.sob_shop.bean

data class RecommendCategoriesBean(
  var type: Int,
  var favorites_id: Long,
  var favorites_title:String
): ShopCategoryBean()
