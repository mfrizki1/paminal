package id.calocallo.sicape.network.response

import android.os.Parcelable
import id.calocallo.sicape.model.DetLapDisiplinModel
import id.calocallo.sicape.model.DetLapPidanaModel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LpPidanaResp(
    var id: Int?,
    var no_lp: String?,
    var satuan_kerja: SatKerResp?,
    var jenis_pelanggaran: String?,
    var detail_laporan: DetLapPidanaModel?,
    var personel_terlapor: PersonelMinResp?,
    var uraian_pelanggaran: String?,
    var pasal_dilanggar: ArrayList<PasalDilanggarResp>?,
    var kota_buat_laporan: String?,
    var tanggal_buat_laporan: String?,
    var jabatan_kep_spkt:String?,
    var nama_kep_spkt:String?,
    var pangkat_kep_spkt:String?,
    var nrp_kep_spkt:String?,
    var status_terlapor: String?,
    var is_ada_dokumen: Int?,
    var dokumen: DokResp?,
    var user_creator: UserCreatorResp?,
    var user_updater: UserCreatorResp?,
    var saksi_kode_etik: ArrayList<LpSaksiResp>?,
    var created_at: String?,
    var updated_at: String?


) : Parcelable
@Parcelize
data class DokLpResp(
    var lp: LpPidanaResp?
) : Parcelable