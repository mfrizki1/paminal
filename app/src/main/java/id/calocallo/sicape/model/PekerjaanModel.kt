package id.calocallo.sicape.model

import android.os.Parcel
import android.os.Parcelable

data class PekerjaanModel(
    val id: Int?,
    val id_personel: Int?,
    val pekerjaan: String?,
    val golongan: String?,
    val instansi: String?,
    val berapa_tahun: Int?,
    val keterangan: String?,
    val created_at: String?,
    val updated_at: String?,
    val deleted_at: String?
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeValue(id_personel)
        parcel.writeString(pekerjaan)
        parcel.writeString(golongan)
        parcel.writeString(instansi)
        parcel.writeValue(berapa_tahun)
        parcel.writeString(keterangan)
        parcel.writeString(created_at)
        parcel.writeString(updated_at)
        parcel.writeString(deleted_at)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PekerjaanModel> {
        override fun createFromParcel(parcel: Parcel): PekerjaanModel {
            return PekerjaanModel(parcel)
        }

        override fun newArray(size: Int): Array<PekerjaanModel?> {
            return arrayOfNulls(size)
        }
    }
}