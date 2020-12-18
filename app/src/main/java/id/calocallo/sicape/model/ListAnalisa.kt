package id.calocallo.sicape.model

import android.os.Parcel
import android.os.Parcelable

data class ListAnalisa(
    var analisa: String?
): Parcelable {
    constructor(parcel: Parcel) : this(parcel.readString()) {
    }

    constructor() : this("")

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(analisa)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ListAnalisa> {
        override fun createFromParcel(parcel: Parcel): ListAnalisa {
            return ListAnalisa(parcel)
        }

        override fun newArray(size: Int): Array<ListAnalisa?> {
            return arrayOfNulls(size)
        }
    }
}
