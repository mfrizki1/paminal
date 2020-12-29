package id.calocallo.sicape.network.response

import android.os.Parcel
import android.os.Parcelable

data class AnakResp(
    var id: Int?,
    var id_personel: Int?,
    var status_ikatan: String?,
    var nama: String?,
    var jenis_kelamin: String?,
    var tempat_lahir: String?,
    var tanggal_lahir: String?,
    var pekerjaan_atau_sekolah: String?,
    var organisasi_yang_diikuti: String?,
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
        source.readString(),
        source.readString(),
        source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeValue(id)
        writeValue(id_personel)
        writeString(status_ikatan)
        writeString(nama)
        writeString(jenis_kelamin)
        writeString(tempat_lahir)
        writeString(tanggal_lahir)
        writeString(pekerjaan_atau_sekolah)
        writeString(organisasi_yang_diikuti)
        writeString(keterangan)
        writeString(created_at)
        writeString(updated_at)
        writeString(deleted_at)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<AnakResp> = object : Parcelable.Creator<AnakResp> {
            override fun createFromParcel(source: Parcel): AnakResp =
                AnakResp(source)
            override fun newArray(size: Int): Array<AnakResp?> = arrayOfNulls(size)
        }
    }
}