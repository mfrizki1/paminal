package id.calocallo.sicape.model

import android.os.Parcel
import android.os.Parcelable

data class LhpModel(
    var no_lhp: String?,

    var no_sp: String?,
    var isi_pengaduan: String?,

    var listLidik: ArrayList<ListLidik>?,
    var waktu_penugasan: String?,
    var daerah_penyelidikan: String?,
    var tugas_pokok: String?,
    var rencana_pelaksanaan_penyelidikan: String?,
    var pelaksanan: String?,
    var pokok_permasalahan: String?,
    var listSaksi: ArrayList<ListSaksi>?,
    var listSurat: ArrayList<ListSurat>?,
    var keterangan_ahli: String?,
    var listPetunjuk: ArrayList<ListPetunjuk>?,
    var listBukti: ArrayList<ListBukti>?,
    var listTerlapor: ArrayList<ListTerlapor>?,
    var listAnalisa: ArrayList<ListAnalisa>?,
    var kesimpulan: String?,
    var rekomendasi: String?,

    var nama_ketua_tim: String?,
    var pangkat_ketua_tim: String?,
    var nrp_ketua_tim: String?,
    var isTerbukti: Int?

) : Parcelable {
    constructor() : this(
        "", "", "", ArrayList(), "", "",
        "", "", "", "", ArrayList(),
        ArrayList(), "", ArrayList(), ArrayList(), ArrayList(), ArrayList(), "",
        "", "", "", "", 0
    )

    constructor(source: Parcel) : this(
        source.readString(),
        source.readString(),
        source.readString(),
        source.createTypedArrayList(ListLidik.CREATOR),
//        ArrayList<ListLidik>().apply { source.readList(this, ListLidik::class.java.classLoader) },
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.createTypedArrayList(ListSaksi.CREATOR),
//        ArrayList<ListSaksi>().apply { source.readList(this, ListSaksi::class.java.classLoader) },
        source.createTypedArrayList(ListSurat.CREATOR),
//        ArrayList<ListSurat>().apply { source.readList(this, ListSurat::class.java.classLoader) },
        source.readString(),
        source.createTypedArrayList(ListPetunjuk.CREATOR),
//        ArrayList<ListPetunjuk>().apply {source.readList(this,ListPetunjuk::class.java.classLoader)},
        source.createTypedArrayList(ListBukti.CREATOR),
//        ArrayList<ListBukti>().apply { source.readList(this, ListBukti::class.java.classLoader) },
        source.createTypedArrayList(ListTerlapor.CREATOR),
//        ArrayList<ListTerlapor>().apply {source.readList(this,ListTerlapor::class.java.classLoader)},
        source.createTypedArrayList(ListAnalisa.CREATOR),
//        ArrayList<ListAnalisa>().apply {source.readList(this,ListAnalisa::class.java.classLoader)},
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readValue(Int::class.java.classLoader) as Int?
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(no_lhp)
        writeString(no_sp)
        writeString(isi_pengaduan)
        writeTypedList(listLidik)
        writeString(waktu_penugasan)
        writeString(daerah_penyelidikan)
        writeString(tugas_pokok)
        writeString(rencana_pelaksanaan_penyelidikan)
        writeString(pelaksanan)
        writeString(pokok_permasalahan)
        writeTypedList(listSaksi)
        writeTypedList(listSurat)
        writeString(keterangan_ahli)
        writeTypedList(listPetunjuk)
        writeTypedList(listBukti)
        writeTypedList(listTerlapor)
        writeTypedList(listAnalisa)
        writeString(kesimpulan)
        writeString(rekomendasi)
        writeString(nama_ketua_tim)
        writeString(pangkat_ketua_tim)
        writeString(nrp_ketua_tim)
        writeValue(isTerbukti)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<LhpModel> = object : Parcelable.Creator<LhpModel> {
            override fun createFromParcel(source: Parcel): LhpModel = LhpModel(source)
            override fun newArray(size: Int): Array<LhpModel?> = arrayOfNulls(size)
        }
    }
}







