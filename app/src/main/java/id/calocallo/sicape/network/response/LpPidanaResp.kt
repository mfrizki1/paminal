package id.calocallo.sicape.network.response

import android.os.Parcelable
import id.calocallo.sicape.model.PersonelLapor
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LpPidanaResp(
    var id: Int?,
    var no_lp: String?,
    var satuan_kerja: SatKerResp?,
    var personel_terlapor: PersonelLapor?,
    var uraian_pelanggaran: String?,
    var kota_buat_laporan: String?,
    var tanggal_buat_laporan: String?,
    var nama_yang_mengetahui: String?,
    var pangkat_yang_mengetahui: String?,
    var nrp_yang_mengetahui: String?,
    var jabatan_yang_mengetahui: String?,

    var status_pelapor: String?,
    var nama_pelapor: String?,
    var agama_pelapor: String?,
    var pekerjaan_pelapor: String?,
    var kewarganegaraan_pelapor: String?,
    var alamat_pelapor: String?,
    var no_telp_pelapor: String?,
    var nik_ktp_pelapor: String?,
    var personel_pelapor: PersonelLapor?,

    var isi_laporan: String?,
    var pembukaan_laporan: String?,
    var pasal_dilanggar: ArrayList<LpPasalResp>?,
    var created_at: String?,
    var updated_at: String?,
    var deleted_at: String?
) : Parcelable {
    constructor() : this(
        0, "",SatKerResp(), PersonelLapor(), "", "", "",
        "", "", "",
        "",  "", "", "",
        "", "", "", "", "",
        PersonelLapor(), "", "", ArrayList(), "", "", ""
    )
}