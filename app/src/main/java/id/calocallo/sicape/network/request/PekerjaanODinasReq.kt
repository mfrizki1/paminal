package id.calocallo.sicape.network.request

import android.os.Parcel
import android.os.Parcelable

data class PekerjaanODinasReq(
    var pekerjaan: String?,
    var instansi: String?,
//    var thnLama: String?,
    var tahun_awal: String?,
    var tahun_akhir: String?,
    var dalam_rangka: String?,
    var keterangan: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(pekerjaan)
        parcel.writeString(instansi)
        parcel.writeString(tahun_awal)
        parcel.writeString(tahun_akhir)
        parcel.writeString(dalam_rangka)
        parcel.writeString(keterangan)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PekerjaanODinasReq> {
        override fun createFromParcel(parcel: Parcel): PekerjaanODinasReq {
            return PekerjaanODinasReq(parcel)
        }

        override fun newArray(size: Int): Array<PekerjaanODinasReq?> {
            return arrayOfNulls(size)
        }
    }
}