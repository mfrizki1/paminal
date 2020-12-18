package id.calocallo.sicape.model

import android.os.Parcel
import android.os.Parcelable

data class ListLidik(
    var nama_lidik: String?,
    var pangkat_lidik: String?,
    var nrp_lidik: String?
): Parcelable {
    constructor() : this("", "", "")

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nama_lidik)
        parcel.writeString(pangkat_lidik)
        parcel.writeString(nrp_lidik)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ListLidik> {
        override fun createFromParcel(parcel: Parcel): ListLidik {
            return ListLidik(parcel)
        }

        override fun newArray(size: Int): Array<ListLidik?> {
            return arrayOfNulls(size)
        }
    }
}
