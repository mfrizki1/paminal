package id.calocallo.sicape.network.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LpPidanaResp(
    var id: Int?,
    var no_lp: String?,
    var kategori: String?,
    var id_personel_terlapor: Int?,
    var kota_buat_laporan: String?,
    var tanggal_buat_laporan: String?,
    var nama_yang_mengetahui: String?,
    var pangkat_yang_mengetahui: String?,
    var nrp_yang_mengetahui: String?,
    var jabatan_yang_mengetahui: String?,
    var id_personel_operator: Int?,

    var status_pelapor: String?,
    var nama_pelapor: String?,
    var agama_pelapor: String?,
    var pekerjaan_pelapor: String?,
    var kewarganegaraan_pelapor: String?,
    var alamat_pelapor: String?,
    var no_telp_pelapor: String?,
    var nik_ktp_pelapor: String?,
    var id_personel_pelapor: Int?,

    var isi_laporan: String?,
    var pembukaan_laporan: String?,
    var listPasal: ArrayList<LpPasalResp>?
):Parcelable {
    constructor() : this(
        0, "", "", 0, "", "",
        "", "", "",
        "", 0, "", "", "",
        "", "", "","","",
        0,"","",ArrayList()
    )
}