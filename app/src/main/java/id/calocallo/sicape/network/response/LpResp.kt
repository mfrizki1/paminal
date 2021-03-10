package id.calocallo.sicape.network.response

import android.os.Parcelable
import id.calocallo.sicape.model.DetLapPidanaModel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LpResp(
    var id: Int?,
    var no_lp: String?,
    var satuan_kerja: SatKerResp?,
    var jenis_pelanggaran: String?,
    var personel_terlapor: PersonelMinResp?,
    var uraian_pelanggaran: String?,
    var kota_buat_laporan: String?,
    var tanggal_buat_laporan: String?,
    var is_melewati_proses_gelar: Int?,
    var status_kasus: String?,
    var status_terlapor: String?,
    var detail_keterangan_terlapor: String?,
    var is_ada_dokumen: Int?,
    var dokumen: DokResp?,
    var user_creator: UserResp?,
    var user_updater: UserResp?,
    var user_deleter: UserResp?,
    var created_at: String?,
    var updated_at: String?,
    var deleted_at: String?,
    var pasal_dilanggar: ArrayList<PasalDilanggarResp>?,
    var saksi: ArrayList<LpSaksiResp>?,
    var detail_laporan: DetLapPidanaModel?
    ) : Parcelable
@Parcelize
data class DokLpResp(
    var lp: LpResp?
) : Parcelable