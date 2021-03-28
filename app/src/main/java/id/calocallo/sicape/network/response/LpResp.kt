package id.calocallo.sicape.network.response

import android.os.Parcelable
import id.calocallo.sicape.model.DetLapPidanaModel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LpResp(
    var id: Int?,
    var no_lp: String?,
    var is_ada_lhp: Int?,
    var lhp: LhpMinResp?,
    var satuan_kerja: SatKerResp?,
    var jenis_pelanggaran: String?,
    var personel_terlapor: PersonelMinResp?,
    var personel_terlapor_lhp: PersonelPenyelidikResp?,
    var uraian_pelanggaran: String?,
    var kota_buat_laporan: String?,
    var tanggal_buat_laporan: String?,
    var is_melewati_proses_gelar: Int?,
    var status_kasus: String?,
    var status_pelapor: String?,
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
    var nama_pelapor: String?,
    var tempat_lahir_pelapor: String?,
    var tanggal_lahir_pelapor: String?,
    var agama_pelapor: String?,
    var pekerjaan_pelapor: String?,
    var kewarganegaraan_pelapor: String?,
    var alamat_pelapor: String?,
    var no_telp_pelapor: String?,
    var nik_ktp_pelapor: String?,
    var jenis_kelamin_pelapor: String?,
    var personel_pelapor: PersonelMinResp?,
    var nama_yang_mengetahui:String?,
    var pangkat_yang_mengetahui:String?,
    var nrp_yang_mengetahui:String?,
    var jabatan_yang_mengetahui:String?,
    var detail_laporan: DetLapPidanaModel?
    ) : Parcelable
@Parcelize
data class DokLpResp(
    var lp: LpResp?
) : Parcelable