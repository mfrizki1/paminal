package id.calocallo.sicape.model

import android.os.Parcel
import android.os.Parcelable

data class ListTerlapor(
    var nama_terlapor: String?,
    var uraian_terlapor: String?
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    ) {
    }

    constructor() : this("", "")

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nama_terlapor)
        parcel.writeString(uraian_terlapor)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ListTerlapor> {
        override fun createFromParcel(parcel: Parcel): ListTerlapor {
            return ListTerlapor(parcel)
        }

        override fun newArray(size: Int): Array<ListTerlapor?> {
            return arrayOfNulls(size)
        }
    }
}