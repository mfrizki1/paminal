package id.calocallo.sicape.network.response

import android.os.Parcel
import android.os.Parcelable

data class PekerjaanLuarResp(
    val id: Int,
    val id_personel: Int,
    val pekerjaan: String?,
    val instansi: String?,
    val tahun_awal: String?,
    val tahun_akhir: String?,
    val dalam_rangka: String?,
    val keterangan: String?,
    val created_at: String?,
    val updated_at: String?,
    val deleted_at: String?
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readInt(),
        source.readInt(),
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
        writeInt(id)
        writeInt(id_personel)
        writeString(pekerjaan)
        writeString(instansi)
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
        val CREATOR: Parcelable.Creator<PekerjaanLuarResp> =
            object : Parcelable.Creator<PekerjaanLuarResp> {
                override fun createFromParcel(source: Parcel): PekerjaanLuarResp =
                    PekerjaanLuarResp(source)

                override fun newArray(size: Int): Array<PekerjaanLuarResp?> = arrayOfNulls(size)
            }
    }
}