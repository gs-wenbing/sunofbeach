package com.zwb.sob_shop.bean

import android.os.Parcel
import android.os.Parcelable

open class ShopItemGoodsBean() : Parcelable {
    constructor(parcel: Parcel) : this() {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ShopItemGoodsBean> {
        override fun createFromParcel(parcel: Parcel): ShopItemGoodsBean {
            return ShopItemGoodsBean(parcel)
        }

        override fun newArray(size: Int): Array<ShopItemGoodsBean?> {
            return arrayOfNulls(size)
        }
    }
}