package id.calocallo.sicape.network.request

import android.os.Parcelable
import id.calocallo.sicape.network.response.KetTerlaporLhpResp
import id.calocallo.sicape.network.response.PersonelPenyelidikResp
import id.calocallo.sicape.network.response.RefPenyelidikanResp
import id.calocallo.sicape.network.response.SaksiLhpResp
import kotlinx.android.parcel.Parcelize

@Parcelize

data class LhpReq(
    var no_lhp: String?,
    var referensi_penyelidikan: ArrayList<RefPenyelidikanReq>?,
    var personel_penyelidik: ArrayList<PersonelPenyelidikReq>?,
    var saksi: ArrayList<SaksiLhpReq>?,
    var keterangan_terlapor: ArrayList<KetTerlaporReq>?,
    var tentang: String?,
    var no_surat_perintah_penyelidikan: String?,

    var tugas_pokok: String?,
    var pokok_permasalahan: String?,
    var keterangan_ahli: String?,
    var kesimpulan: String?,
    var rekomendasi: String?,
    var kota_buat_laporan: String?,
    var tanggal_buat_laporan: String?,

    //textmultiline
    var surat: String?,
    var petunjuk: String?,
    var barang_bukti: String?,
    var analisa: String?,
    var isTerbukti: Int?,
    var tanggal_mulai_penyelidikan: String?,
    var wilayah_hukum_penyelidikan: String?
) : Parcelable {
    constructor() : this(
        "",
        ArrayList(), ArrayList(), ArrayList(),
        ArrayList(), "", "",
        "", "", "",
        "", "", "",
        "", "", "",
        "",
        "", 0, "", ""
    )
}