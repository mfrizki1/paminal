package id.calocallo.sicape.network.response

import android.os.Parcelable
import id.calocallo.sicape.model.PersonelLapor
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LpKkeResp(
    var id: Int?,
    var no_lp: String?,
    var uraian_pelanggaran: String?,
    var personel_terlapor: PersonelLapor?,
    var personel_pelapor: PersonelLapor?,
    var kota_buat_laporan: String?,
    var tanggal_buat_laporan: String?,
    var nama_yang_mengetahui: String?,
    var pangkat_yang_mengetahui: String?,
    var nrp_yang_mengetahui: String?,
    var jabatan_yang_mengetahui: String?,
    var kesatuan_yang_mengetahui: String?,
    var id_personel_operator: Int?,

    val alat_bukti: String?,
    val isi_laporan: String?,
    var pasal_dilanggar: ArrayList<LpPasalResp>?,
    var saksi_kode_etik: MutableList<LpSaksiResp>?,
    var created_at: String?,
    var updated_at: String?,
    var deleted_at: String?

) : Parcelable