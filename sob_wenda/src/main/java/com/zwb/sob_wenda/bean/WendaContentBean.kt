package com.zwb.sob_wenda.bean

import android.os.Parcel
import android.os.Parcelable

/**
 * {
"id":"1177547881352044544",
"title":"程序员都喜欢哪些礼物呢？",
"userId":"1153952789488054272",
"avatar":"https://images.sunofbeaches.com/content/2022_01_04/927902852251123712.png",
"nickname":"拉大锯",
"description":"<p>程序员都喜欢哪些礼物呢？</p><p><br></p><p>问题如上：一般来说，是不是电子比较多呢？</p><p><br></p><p>比如说，机械键盘、耳机、Pad、手机之类的呢？</p><p><br></p><p><br></p>",
"answerCount":3,
"label":null,
"isVip":"0",
"createTime":"2019-09-28 03:39",
"isResolve":"1",
"viewCount":526,
"thumbUp":0,
"sob":30,
"categoryId":"1161217556019703808",
"categoryName":"猿(嫒)日常",
"labels":[
"程序员",
"机械键盘"
]
}
 */
data class WendaContentBean(
    val answerCount: Int,
    val avatar: String,
    val categoryId: String,
    val categoryName: String,
    val createTime: String,
    val description: String,
    val id: String,
    val isResolve: String,
    val isVip: String,
    val label: String?,
    val labels: List<String>,
    val nickname: String,
    val sob: Int,
    val thumbUp: Int,
    val title: String,
    val userId: String,
    val viewCount: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.createStringArrayList() ?: mutableListOf<String>(),
        parcel . readString () ?: "",
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(answerCount)
        parcel.writeString(avatar)
        parcel.writeString(categoryId)
        parcel.writeString(categoryName)
        parcel.writeString(createTime)
        parcel.writeString(description)
        parcel.writeString(id)
        parcel.writeString(isResolve)
        parcel.writeString(isVip)
        parcel.writeString(label)
        parcel.writeStringList(labels)
        parcel.writeString(nickname)
        parcel.writeInt(sob)
        parcel.writeInt(thumbUp)
        parcel.writeString(title)
        parcel.writeString(userId)
        parcel.writeInt(viewCount)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<WendaContentBean> {
        override fun createFromParcel(parcel: Parcel): WendaContentBean {
            return WendaContentBean(parcel)
        }

        override fun newArray(size: Int): Array<WendaContentBean?> {
            return arrayOfNulls(size)
        }
    }
}