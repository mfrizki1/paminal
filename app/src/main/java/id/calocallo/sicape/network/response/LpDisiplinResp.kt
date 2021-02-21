package id.calocallo.sicape.network.response

import android.os.Parcelable
import id.calocallo.sicape.model.DetLapDisiplinModel
import kotlinx.android.parcel.Parcelize


@Parcelize
data class LpDisiplinResp(
    var id: Int?,
    var no_lp: String?,
    var satuan_kerja: SatKerResp?,
    var jenis_pelanggaran: SatKerResp?,
    var detail_laporan: DetLapDisiplinModel?,
    var personel_terlapor: PersonelMinResp?,
    var uraian_pelanggaran: String?,
    var pasal_dilanggar: ArrayList<PasalDilanggarResp>?,
    var kota_buat_laporan: String?,
    var tanggal_buat_laporan: String?,
    var status_terlapor: String?,
    var is_ada_dokumen: Int?,
    var dokumen: DokResp?,
    var user_creator: UserCreatorResp?,
    var user_updater: UserCreatorResp?,
    var created_at: String?,
    var updated_at: String?
) : Parcelable