package id.calocallo.sicape.model

import android.os.Parcel
import android.os.Parcelable

data class ListBukti(
    var bukti: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(parcel.readString()) {
    }

    constructor() : this("")

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(bukti)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ListBukti> {
        override fun createFromParcel(parcel: Parcel): ListBukti {
            return ListBukti(parcel)
        }

        override fun newArray(size: Int): Array<ListBukti?> {
            return arrayOfNulls(size)
        }
    }
}
