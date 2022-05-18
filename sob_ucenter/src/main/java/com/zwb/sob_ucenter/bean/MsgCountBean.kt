package com.zwb.sob_ucenter.bean

import android.os.Parcel
import android.os.Parcelable

/**
 * {
"systemMsgCount":1,
"thumbUpMsgCount":0,
"articleMsgCount":0,
"wendaMsgCount":0,
"momentCommentCount":0,
"atMsgCount":0,
"shareMsgCount":0
}
 */
data class MsgCountBean(
    val articleMsgCount: Int,
    val atMsgCount: Int,
    val momentCommentCount: Int,
    val shareMsgCount: Int,
    val systemMsgCount: Int,
    val thumbUpMsgCount: Int,
    val wendaMsgCount: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(articleMsgCount)
        parcel.writeInt(atMsgCount)
        parcel.writeInt(momentCommentCount)
        parcel.writeInt(shareMsgCount)
        parcel.writeInt(systemMsgCount)
        parcel.writeInt(thumbUpMsgCount)
        parcel.writeInt(wendaMsgCount)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MsgCountBean> {
        override fun createFromParcel(parcel: Parcel): MsgCountBean {
            return MsgCountBean(parcel)
        }

        override fun newArray(size: Int): Array<MsgCountBean?> {
            return arrayOfNulls(size)
        }
    }
}