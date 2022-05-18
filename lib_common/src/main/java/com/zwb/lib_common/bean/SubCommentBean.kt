package com.zwb.lib_common.bean

import android.os.Parcel
import android.os.Parcelable
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.zwb.lib_common.constant.Constants

/**
 * 文章和问答的子评论
 */
data class SubCommentBean(
    val _id: String?,
    val articleId: String?,
    val beNickname: String?,
    val beUid: String,
    val content: String?,
    val parentId: String?,
    val publishTime: String?,
    val vip: Boolean,
    val yourAvatar: String?,
    val yourNickname: String?,
    val yourRole: String?,
    val yourUid: String
) : MultiItemEntity, Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString() ?: "",
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readByte() != 0.toByte(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString() ?: "",
    )

    override fun getItemType(): Int {
        return Constants.MultiItemType.TYPE_SUB_COMMENT
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(_id)
        parcel.writeString(articleId)
        parcel.writeString(beNickname)
        parcel.writeString(beUid)
        parcel.writeString(content)
        parcel.writeString(parentId)
        parcel.writeString(publishTime)
        parcel.writeByte(if (vip) 1 else 0)
        parcel.writeString(yourAvatar)
        parcel.writeString(yourNickname)
        parcel.writeString(yourRole)
        parcel.writeString(yourUid)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SubCommentBean> {
        override fun createFromParcel(parcel: Parcel): SubCommentBean {
            return SubCommentBean(parcel)
        }

        override fun newArray(size: Int): Array<SubCommentBean?> {
            return arrayOfNulls(size)
        }
    }
}
