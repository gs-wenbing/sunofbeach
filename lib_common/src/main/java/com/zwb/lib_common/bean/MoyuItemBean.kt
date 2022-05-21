package com.zwb.lib_common.bean

import android.os.Parcel
import android.os.Parcelable

data class MoyuItemBean(
    var id: String,
    var userId: String,
    var nickname: String,
    var avatar: String,
    var company: String? = null,
    var position: String? = null,
    var content: String,
    var linkCover: String? = null,
    var linkTitle: String? = null,
    var linkUrl: String? = null,
    var commentCount: Int,
    var thumbUpCount: Int,
    var images: List<String>,
    var topicName: String,
    var topicId: String,
    var createTime: String,
    var hasThumbUp: Boolean,
    var thumbUpList: List<String>,
    var vip: Boolean
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()?:"",
        parcel.readString()?:"",
        parcel.readString()?:"",
        parcel.readString()?:"",
        parcel.readString(),
        parcel.readString(),
        parcel.readString()?:"",
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.createStringArrayList()?: mutableListOf(),
        parcel.readString()?:"",
        parcel.readString()?:"",
        parcel.readString()?:"",
        parcel.readByte() != 0.toByte(),
        parcel.createStringArrayList()?: mutableListOf(),
        parcel.readByte() != 0.toByte()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(userId)
        parcel.writeString(nickname)
        parcel.writeString(avatar)
        parcel.writeString(company)
        parcel.writeString(position)
        parcel.writeString(content)
        parcel.writeString(linkCover)
        parcel.writeString(linkTitle)
        parcel.writeString(linkUrl)
        parcel.writeInt(commentCount)
        parcel.writeInt(thumbUpCount)
        parcel.writeStringList(images)
        parcel.writeString(topicName)
        parcel.writeString(topicId)
        parcel.writeString(createTime)
        parcel.writeByte(if (hasThumbUp) 1 else 0)
        parcel.writeStringList(thumbUpList)
        parcel.writeByte(if (vip) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MoyuItemBean> {
        override fun createFromParcel(parcel: Parcel): MoyuItemBean {
            return MoyuItemBean(parcel)
        }

        override fun newArray(size: Int): Array<MoyuItemBean?> {
            return arrayOfNulls(size)
        }
    }

    override fun equals(other: Any?): Boolean {
        return this.id == (other as MoyuItemBean).id
    }
}