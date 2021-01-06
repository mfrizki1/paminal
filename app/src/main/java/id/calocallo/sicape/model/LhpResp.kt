package id.calocallo.sicape.model

import android.os.Parcelable
import id.calocallo.sicape.network.request.SaksiReq
import id.calocallo.sicape.network.response.KetTerlaporLhpResp
import id.calocallo.sicape.network.response.PersonelPenyelidikResp
import id.calocallo.sicape.network.response.RefPenyelidikanResp
import id.calocallo.sicape.network.response.SaksiLhpResp
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LhpResp(
    var id: Int?,
    var no_lhp: String?,
    var referensi_penyelidikan: ArrayList<RefPenyelidikanResp>?,
    var personel_penyelidik: ArrayList<PersonelPenyelidikResp>?,
    var saksi: ArrayList<SaksiLhpResp>?,
    var keterangan_terlapor: ArrayList<KetTerlaporLhpResp>?,
    var tentang: String?,
    var no_surat_perintah_penyelidikan: String?,

    var tugas_pokok: String?,
    var pokok_permasalahan: String?,
    var keterangan_ahli: String?,
    var kesimpulan: String?,
    var rekomendasi: String?,
    var kota_buat_laporan: String?,
    var tanggal_buat_laporan: String?,
    var created_at: String?,
    var updated_at: String?,
    //textmultiline
    var surat: String?,
    var petunjuk: String?,
    var barang_bukti: String?,
    var analisa: String?,
    var isTerbukti: Int?,
    var tanggal_mulai_penyelidikan: String?,
    var daerah_penyelidikan: String?


//    var lp: LpResp,
//    var id_lp: Int?,
//    var listLidik: ArrayList<ListLidik>?,
//    var waktu_penugasan: String?,
//    var daerah_penyelidikan: String?,
//    var tugas_pokok: String?,
//    var rencana_pelaksanaan_penyelidikan: String?,
//    var pelaksanan: String?,
//    var pokok_permasalahan: String?,
//    var listSaksi: ArrayList<ListSaksi>?,
//    var listSurat: ArrayList<ListSurat>?,
//    var keterangan_ahli: String?,
//    var listPetunjuk: ArrayList<ListPetunjuk>?,
//    var listBukti: ArrayList<ListBukti>?,
//    var listTerlapor: ArrayList<ListTerlapor>?,
//    var listAnalisa: ArrayList<ListAnalisa>?,
//    var kesimpulan: String?,
//    var rekomendasi: String?,
//
//    var nama_ketua_tim: String?,
//    var pangkat_ketua_tim: String?,
//    var nrp_ketua_tim: String?,
):Parcelable {
    constructor() : this(
        0, "",
        ArrayList(), ArrayList(), ArrayList(),
        ArrayList(), "", "",
        "", "", "",
        "", "", "",
        "", "", "",
        "", "", "",
        "",0,"",""
    )
}







