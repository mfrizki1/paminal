package id.calocallo.sicape.model

import android.os.Parcel
import android.os.Parcelable

data class SkhdModel(
    var no_skhd: String?,
    var no_lp: String?,
    var nama_personel: String?,
    var pangkat_personel: String?,
    var nrp_personel: String?,
    var jabatan: String?,
    var kesatuan: String?,
    var listHukuman: ArrayList<ListHukumanSkhd>?,
    var satker: String?,
    var detail_skhd: String?,
    var tanggal_disampaikan: String?,
    var pukul_disampaikan: String?,
    var tanggal_dibuat: String?,
    var kepala_bidang: String?,
    var nama_bidang: String?,
    var pangkat_bidang: String?,
    var nrp_bidang: String?,
    var jenis: String?
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.createTypedArrayList(ListHukumanSkhd.CREATOR),
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
        writeString(no_skhd)
        writeString(no_lp)
        writeString(nama_personel)
        writeString(pangkat_personel)
        writeString(nrp_personel)
        writeString(jabatan)
        writeString(kesatuan)
        writeTypedList(listHukuman)
        writeString(satker)
        writeString(detail_skhd)
        writeString(tanggal_disampaikan)
        writeString(pukul_disampaikan)
        writeString(tanggal_dibuat)
        writeString(kepala_bidang)
        writeString(nama_bidang)
        writeString(pangkat_bidang)
        writeString(nrp_bidang)
        writeString(jenis)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<SkhdModel> = object : Parcelable.Creator<SkhdModel> {
            override fun createFromParcel(source: Parcel): SkhdModel = SkhdModel(source)
            override fun newArray(size: Int): Array<SkhdModel?> = arrayOfNulls(size)
        }
    }
}

data class ListHukumanSkhd(
    var hukuman: String?
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(hukuman)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<ListHukumanSkhd> =
            object : Parcelable.Creator<ListHukumanSkhd> {
                override fun createFromParcel(source: Parcel): ListHukumanSkhd =
                    ListHukumanSkhd(source)

                override fun newArray(size: Int): Array<ListHukumanSkhd?> = arrayOfNulls(size)
            }
    }
}