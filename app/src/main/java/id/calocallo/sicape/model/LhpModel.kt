package id.calocallo.sicape.model

import android.os.Parcel
import android.os.Parcelable
import id.calocallo.sicape.network.response.LpResp
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LhpModel(
    var no_lhp: String?,

    var no_sp: String?,
    var isi_pengaduan: String?,
//    var lp: LpResp,
    var id_lp: Int?,
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

//    var nama_ketua_tim: String?,
//    var pangkat_ketua_tim: String?,
//    var nrp_ketua_tim: String?,
    var isTerbukti: Int?

) : Parcelable {
    constructor() : this(
        "", "", "", 0, ArrayList(), "", "",
        "", "", "", "", ArrayList(),
        ArrayList(), "", ArrayList(), ArrayList(), ArrayList(), ArrayList(), "",
        "",
//        "", "", "",
        0
    )

}







