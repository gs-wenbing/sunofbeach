package com.zwb.sob_home.adapter

import android.widget.ImageView
import com.allen.library.SuperTextView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zwb.lib_base.utils.UIUtils
import com.zwb.lib_common.bean.CollectionBean
import com.zwb.sob_home.R

class CollectFolderAdapter(list:MutableList<CollectionBean?>):
    BaseQuickAdapter<CollectionBean, BaseViewHolder>(R.layout.home_dialog_adapter_collection_folder,list) {

    override fun convert(helper: BaseViewHolder, item: CollectionBean?) {
        item?.let { folderItem ->
            val tvCover = helper.getView<ImageView>(R.id.iv_cover)
            Glide.with(tvCover.context)
                .load(folderItem.cover)
                .placeholder(R.drawable.shape_grey_background)
                .into(tvCover)
            helper.setText(R.id.tv_title,folderItem.name)
            helper.setText(R.id.tv_desc,"${folderItem.favoriteCount}篇文章 • ${folderItem.followCount}订阅")

            helper.addOnClickListener(R.id.btn_collect)
        }
    }
}