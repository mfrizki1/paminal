package id.calocallo.sicape.network.response

import android.os.Parcelable
import id.calocallo.sicape.model.PersonelLapor
import kotlinx.android.parcel.Parcelize


@Parcelize
data class LpDisiplinResp(
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
//    var id_personel_operator: Int?,

    var macam_pelanggaran: String?,
    var keterangan_terlapor: String?,
    var kronologis_dari_pelapor: String?,
    var rincian_pelanggaran_disiplin: String?,
    var pasal_dilanggar: ArrayList<LpPasalResp>?,
    var satuan_kerja: SatKerResp?,
    var created_at: String?,
    var updated_at: String?
) : Parcelable