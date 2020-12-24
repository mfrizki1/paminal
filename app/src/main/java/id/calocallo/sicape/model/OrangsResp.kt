package id.calocallo.sicape.model

import android.os.Parcel
import android.os.Parcelable

data class OrangsResp(
    var id: Int?,
    var id_personel: Int?,
    var nama: String?,
    var jenis_kelamin: String?,
    var umur: String?,
    var alamat: String?,
    var pekerjaan: String?,
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
        source.readString(),
        source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeValue(id)
        writeValue(id_personel)
        writeString(nama)
        writeString(jenis_kelamin)
        writeString(umur)
        writeString(alamat)
        writeString(pekerjaan)
        writeString(keterangan)
        writeString(created_at)
        writeString(updated_at)
        writeString(deleted_at)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<OrangsResp> = object : Parcelable.Creator<OrangsResp> {
            override fun createFromParcel(source: Parcel): OrangsResp = OrangsResp(source)
            override fun newArray(size: Int): Array<OrangsResp?> = arrayOfNulls(size)
        }
    }
}