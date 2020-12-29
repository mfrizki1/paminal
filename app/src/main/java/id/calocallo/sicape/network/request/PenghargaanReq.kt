package id.calocallo.sicape.network.request

import android.os.Parcel
import android.os.Parcelable

data class PenghargaanReq(
    var penghargaan: String?,
    var diterima_dari: String?,
    var dalam_rangka: String?,
    var tahun: String?,
    var keterangan: String?
) : Parcelable {
    constructor() : this("", "", "", "", "")

    constructor(source: Parcel) : this(
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(penghargaan)
        writeString(diterima_dari)
        writeString(dalam_rangka)
        writeString(tahun)
        writeString(keterangan)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<PenghargaanReq> =
            object : Parcelable.Creator<PenghargaanReq> {
                override fun createFromParcel(source: Parcel): PenghargaanReq =
                    PenghargaanReq(source)

                override fun newArray(size: Int): Array<PenghargaanReq?> = arrayOfNulls(size)
            }
    }
}