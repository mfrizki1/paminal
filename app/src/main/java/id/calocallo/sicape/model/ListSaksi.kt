package id.calocallo.sicape.model

import android.os.Parcel
import android.os.Parcelable

data class ListSaksi(
    var nama_saksi: String?,
    var uraian_saksi: String?
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    ) {
    }

    constructor() : this("", "")

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nama_saksi)
        parcel.writeString(uraian_saksi)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ListSaksi> {
        override fun createFromParcel(parcel: Parcel): ListSaksi {
            return ListSaksi(parcel)
        }

        override fun newArray(size: Int): Array<ListSaksi?> {
            return arrayOfNulls(size)
        }
    }

}
