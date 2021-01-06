package id.calocallo.sicape.network.request

import android.os.Parcel
import android.os.Parcelable
import id.calocallo.sicape.model.PersonelModel
import kotlinx.android.parcel.Parceler
import kotlinx.android.parcel.Parcelize

data class RefPenyelidikanReq (
    var id_lp: Int?,
    var no_lp: String?
):Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString()
    ) {
    }

    constructor():this(null,null)

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id_lp)
        parcel.writeString(no_lp)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RefPenyelidikanReq> {
        override fun createFromParcel(parcel: Parcel): RefPenyelidikanReq {
            return RefPenyelidikanReq(parcel)
        }

        override fun newArray(size: Int): Array<RefPenyelidikanReq?> {
            return arrayOfNulls(size)
        }
    }
}