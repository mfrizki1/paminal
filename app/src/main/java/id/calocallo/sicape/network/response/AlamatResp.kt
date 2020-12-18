package id.calocallo.sicape.network.response

import android.os.Parcel
import android.os.Parcelable

data class AlamatResp(
    val id: Int?,
    val id_personel: Int?,
    val alamat: String?,
    val tahun_awal: String?,
    val tahun_akhir: String?,
    val dalam_rangka: String?,
    val keterangan: String?,
    val created_at: String?,
    val updated_at: String?,
    val deleted_at: String?

) : Parcelable {
    constructor() : this(0, 0, "", "", "", "", "", "", "", "")
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
        writeString(alamat)
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
        val CREATOR: Parcelable.Creator<AlamatResp> = object : Parcelable.Creator<AlamatResp> {
            override fun createFromParcel(source: Parcel): AlamatResp = AlamatResp(source)
            override fun newArray(size: Int): Array<AlamatResp?> = arrayOfNulls(size)
        }
    }
}
