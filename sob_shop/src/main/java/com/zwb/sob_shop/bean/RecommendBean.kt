package com.zwb.sob_shop.bean

data class RecommendBean(
    val tbk_dg_optimus_material_response: TbkDgOptimusMaterialResponse
)

data class TbkDgOptimusMaterialResponse(
    val is_default: String,
    val result_list: Results,
    val total_count: Int,
    val request_id:String
)

data class Results(
    val map_data: List<RecommendGoodsBean>
)

