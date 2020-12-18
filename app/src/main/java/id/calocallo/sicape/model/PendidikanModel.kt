package id.calocallo.sicape.model

import android.os.Parcel
import android.os.Parcelable


data class PendidikanModel(
    val id: Int,
    val id_personel: Int,
    val pendidikan: String?,
    val jenis: String?,
    val tahun_awal: String?,
    val tahun_akhir: String?,
    val kota: String?,
    val yang_membiayai: String?,
    val keterangan: String?,
    val created_at: String?,
    val updated_at: String?,
    val deleted_at: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeInt(id_personel)
        parcel.writeString(pendidikan)
        parcel.writeString(jenis)
        parcel.writeString(tahun_awal)
        parcel.writeString(tahun_akhir)
        parcel.writeString(kota)
        parcel.writeString(yang_membiayai)
        parcel.writeString(keterangan)
        parcel.writeString(created_at)
        parcel.writeString(updated_at)
        parcel.writeString(deleted_at)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PendidikanModel> {
        override fun createFromParcel(parcel: Parcel): PendidikanModel {
            return PendidikanModel(parcel)
        }

        override fun newArray(size: Int): Array<PendidikanModel?> {
            return arrayOfNulls(size)
        }
    }
}