package com.zwb.sob_wenda.bean

import android.os.Parcel
import android.os.Parcelable
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.zwb.lib_common.bean.SubCommentBean
import com.zwb.lib_common.constant.Constants

/**
 * {
"_id":"1178384579988320256",
"wendaId":"1177547881352044544",
"thumbUp":1,
"bestAs":"0",
"content":"<p>送个锤子</p>",
"nickname":"海饭",
"avatar":"http://imgs.sunofbeaches.com/group1/M00/00/02/rBPLFV1s1jWACj3bAABEjKosVos384.png",
"uid":"1162543794353192960",
"subCommentCount":0,
"publishTime":"2019-09-29 07:02:36",
"publishTimeText":"2019-09-29",
"thumbUps":[
"1153952789488054272"
],
"wendaSubComments":[

],
"isVip":true
}
 */
data class AnswerBean(
    val _id: String,
    val avatar: String,
    val bestAs: String,
    val content: String,
    val isVip: Boolean,
    val nickname: String,
    val publishTime: String,
    val publishTimeText: String,
    val subCommentCount: Int,
    val thumbUp: Int,
    val thumbUps: List<String>,
    val uid: String,
    val wendaId: String,
    val wendaSubComments: List<SubCommentBean>
) : MultiItemEntity, Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readByte() != 0.toByte(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readInt(),
        parcel.createStringArrayList() ?: mutableListOf(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.createTypedArrayList(SubCommentBean) ?: mutableListOf(),
    )

    override fun getItemType(): Int {
        return Constants.MultiItemType.TYPE_COMMENT
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(_id)
        parcel.writeString(avatar)
        parcel.writeString(bestAs)
        parcel.writeString(content)
        parcel.writeByte(if (isVip) 1 else 0)
        parcel.writeString(nickname)
        parcel.writeString(publishTime)
        parcel.writeString(publishTimeText)
        parcel.writeInt(subCommentCount)
        parcel.writeInt(thumbUp)
        parcel.writeStringList(thumbUps)
        parcel.writeString(uid)
        parcel.writeString(wendaId)
        parcel.writeTypedList(wendaSubComments)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AnswerBean> {
        override fun createFromParcel(parcel: Parcel): AnswerBean {
            return AnswerBean(parcel)
        }

        override fun newArray(size: Int): Array<AnswerBean?> {
            return arrayOfNulls(size)
        }
    }

    override fun equals(other: Any?): Boolean {
        return this._id == (other as AnswerBean)._id
    }
}