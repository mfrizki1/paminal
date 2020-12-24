package id.calocallo.sicape.model

import android.os.Parcel
import android.os.Parcelable

data class OrganisasiResp(
    var id: Int?,
    var id_personel: Int?,
    var organisasi: String?,
    var tahun_awal: String?,
    var tahun_akhir: String?,
    var tahun_bergabung: String?,
    var jabatan: String?,
    var alamat: String?,
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
        source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeValue(id)
        writeValue(id_personel)
        writeString(organisasi)
        writeString(tahun_awal)
        writeString(tahun_akhir)
        writeString(tahun_bergabung)
        writeString(jabatan)
        writeString(alamat)
        writeString(keterangan)
        writeString(created_at)
        writeString(updated_at)
        writeString(deleted_at)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<OrganisasiResp> =
            object : Parcelable.Creator<OrganisasiResp> {
                override fun createFromParcel(source: Parcel): OrganisasiResp =
                    OrganisasiResp(source)

                override fun newArray(size: Int): Array<OrganisasiResp?> = arrayOfNulls(size)
            }
    }
}
