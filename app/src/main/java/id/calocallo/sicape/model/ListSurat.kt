package id.calocallo.sicape.model

import android.os.Parcel
import android.os.Parcelable

data class ListSurat(
    var surat: String?
): Parcelable {
    constructor(parcel: Parcel) : this(parcel.readString())

    constructor() : this("")

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(surat)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ListSurat> {
        override fun createFromParcel(parcel: Parcel): ListSurat {
            return ListSurat(parcel)
        }

        override fun newArray(size: Int): Array<ListSurat?> {
            return arrayOfNulls(size)
        }
    }
}
