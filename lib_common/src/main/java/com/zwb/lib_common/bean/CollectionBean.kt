package com.zwb.lib_common.bean

import android.os.Parcel
import android.os.Parcelable

data class CollectionBean(
    val _id: String,
    val avatar: String?,
    val cover: String?,
    val createTime: String?,
    val description: String?,
    val favoriteCount: Int,
    val followCount: Int,
    val name: String?,
    val permission: String?,
    val userId: String?,
    val userName: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()?:"",
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(_id)
        parcel.writeString(avatar)
        parcel.writeString(cover)
        parcel.writeString(createTime)
        parcel.writeString(description)
        parcel.writeInt(favoriteCount)
        parcel.writeInt(followCount)
        parcel.writeString(name)
        parcel.writeString(permission)
        parcel.writeString(userId)
        parcel.writeString(userName)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CollectionBean> {
        override fun createFromParcel(parcel: Parcel): CollectionBean {
            return CollectionBean(parcel)
        }

        override fun newArray(size: Int): Array<CollectionBean?> {
            return arrayOfNulls(size)
        }
    }
}