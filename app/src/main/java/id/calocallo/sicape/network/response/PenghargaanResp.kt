package id.calocallo.sicape.network.response

import android.os.Parcel
import android.os.Parcelable

data class PenghargaanResp(
    var id: Int?,
    var id_personel: Int?,
    var penghargaan: String?,
    var diterima_dari: String?,
    var dalam_rangka: String?,
    var tahun: String?,
    var keterangan: String?,
    var created_at: String?,
    var updated_at: String?,
    var deleted_at: String?
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readValue(Int::class.java.classLoader) as Int?,
        source.readValue(Int::class.java.classLoader) as Int?,
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeValue(id)
        writeValue(id_personel)
        writeString(penghargaan)
        writeString(diterima_dari)
        writeString(dalam_rangka)
        writeString(tahun)
        writeString(keterangan)
        writeString(created_at)
        writeString(updated_at)
        writeString(deleted_at)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<PenghargaanResp> =
            object : Parcelable.Creator<PenghargaanResp> {
                override fun createFromParcel(source: Parcel): PenghargaanResp =
                    PenghargaanResp(source)

                override fun newArray(size: Int): Array<PenghargaanResp?> = arrayOfNulls(size)
            }
    }
}