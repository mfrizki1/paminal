package id.calocallo.sicape.model

import android.os.Parcelable
import id.calocallo.sicape.network.response.PersonelMinResp
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DetLapDisiplinModel(
    var personel_pelapor: PersonelMinResp?,
    var macam_pelanggaran: String?,
    var keterangan_terlapor: String?,
    var kronologis_dari_pelapor: String?,
    var rincian_pelanggaran_disiplin: String?,
    var waktu_buat_laporan: String?,
    var jabatan_yang_mengetahui: String?,
    var nama_yang_mengetahui: String?,
    var pangkat_yang_mengetahui: String?,
    var nrp_yang_mengetahui: String?

) : Parcelable