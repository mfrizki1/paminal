package id.calocallo.sicape.model

import android.os.Parcel
import android.os.Parcelable


data class ListPetunjuk(
    var petunjuk: String?
): Parcelable {
    constructor(parcel: Parcel) : this(parcel.readString()) {
    }

    constructor() : this("")

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(petunjuk)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ListPetunjuk> {
        override fun createFromParcel(parcel: Parcel): ListPetunjuk {
            return ListPetunjuk(parcel)
        }

        override fun newArray(size: Int): Array<ListPetunjuk?> {
            return arrayOfNulls(size)
        }
    }
}