package id.calocallo.sicape.network.response

import android.os.Parcel
import android.os.Parcelable

data class PerjuanganResp(
    var id: Int?,
    var peristiwa: String?,
    var lokasi: String?,
    var tahun_awal: String?,
    var tahun_akhir: String?,
    var dalam_rangka: String?,
    var keterangan: String?,
    var created_at: String?,
    var updated_at: String?,
    var deleted_at: String?
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readValue(Int::class.java.classLoader) as Int?,
        source.readString(),
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
        writeString(peristiwa)
        writeString(lokasi)
        writeString(tahun_awal)
        writeString(tahun_akhir)
        writeString(dalam_rangka)
        writeString(keterangan)
        writeString(created_at)
        writeString(updated_at)
        writeString(deleted_at)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<PerjuanganResp> =
            object : Parcelable.Creator<PerjuanganResp> {
                override fun createFromParcel(source: Parcel): PerjuanganResp =
                    PerjuanganResp(source)

                override fun newArray(size: Int): Array<PerjuanganResp?> = arrayOfNulls(size)
            }
    }
}