package com.zwb.sob_shop.bean

import android.os.Parcel
import android.os.Parcelable

data class SmallImages(
    var string: List<String>?
) : Parcelable {
    constructor(parcel: Parcel) : this(parcel.createStringArrayList()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeStringList(string)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SmallImages> {
        override fun createFromParcel(parcel: Parcel): SmallImages {
            return SmallImages(parcel)
        }

        override fun newArray(size: Int): Array<SmallImages?> {
            return arrayOfNulls(size)
        }
    }
}