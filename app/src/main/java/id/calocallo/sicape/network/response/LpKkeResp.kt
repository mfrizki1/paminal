package id.calocallo.sicape.network.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LpKkeResp(
    var id: Int?,
    var no_lp: String?,
    var kategori: String?,
    var id_personel_terlapor: Int?,
    var id_personel_pelapor: Int?,
    var kota_buat_laporan: String?,
    var tanggal_buat_laporan: String?,
    var nama_yang_mengetahui: String?,
    var pangkat_yang_mengetahui: String?,
    var nrp_yang_mengetahui: String?,
    var jabatan_yang_mengetahui: String?,
    var id_personel_operator: Int?,

    val alat_bukti: String?,
    val isi_laporan: String?,
    var listPasal: ArrayList<LpPasalResp>?,
    var listSaksi: MutableList<LpSaksiResp>?


) : Parcelable