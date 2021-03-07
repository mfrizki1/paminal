package id.calocallo.sicape.model

import android.os.Parcelable
import id.calocallo.sicape.network.response.PersonelMinResp
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DetLapKkeModel(
    var personel_pelapor: PersonelMinResp?,
    var isi_laporan: String?,
    var alat_bukti: String?,
    var jabatan_yang_mengetahui: String?,
    var nama_yang_mengetahui: String?,
    var pangkat_yang_mengetahui: String?,
    var nrp_yang_mengetahui: String?
) : Parcelable