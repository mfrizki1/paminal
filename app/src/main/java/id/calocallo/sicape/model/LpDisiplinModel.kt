package id.calocallo.sicape.model

import android.os.Parcel
import android.os.Parcelable

data class LpDisiplinModel(
    var no_lp: String?,
    var hukuman: String?,
    var pasal: String?,
    var nama_personel: String?,
    var pangkat_personel: String?,
    var nrp_personel: String?,
    var kesatuan: String?,
    var jabatan: String?,
    var keterangan: String?,
    var jenis_pelanggaran: String?
) : Parcelable {
    constructor() : this("", "", "", "", "", "", "", "","","")

    constructor(source: Parcel) : this(
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
        writeString(no_lp)
        writeString(hukuman)
        writeString(pasal)
        writeString(nama_personel)
        writeString(pangkat_personel)
        writeString(nrp_personel)
        writeString(kesatuan)
        writeString(jabatan)
        writeString(keterangan)
        writeString(jenis_pelanggaran)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<LpDisiplinModel> = object : Parcelable.Creator<LpDisiplinModel> {
            override fun createFromParcel(source: Parcel): LpDisiplinModel = LpDisiplinModel(source)
            override fun newArray(size: Int): Array<LpDisiplinModel?> = arrayOfNulls(size)
        }
    }
}