package id.calocallo.sicape.network.response

import android.os.Parcel
import android.os.Parcelable

class MedSosResp(
    var id: Int?,
    var id_personel: Int?,
    var nama_medsos: String?,
    var nama_akun: String?,
    var alasan: String?,
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
        source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeValue(id)
        writeValue(id_personel)
        writeString(nama_medsos)
        writeString(nama_akun)
        writeString(alasan)
        writeString(keterangan)
        writeString(created_at)
        writeString(updated_at)
        writeString(deleted_at)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<MedSosResp> = object : Parcelable.Creator<MedSosResp> {
            override fun createFromParcel(source: Parcel): MedSosResp =
                MedSosResp(source)
            override fun newArray(size: Int): Array<MedSosResp?> = arrayOfNulls(size)
        }
    }
}