package id.calocallo.sicape.model

import android.os.Parcelable
import id.calocallo.sicape.network.response.PersonelMinResp
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DetLapPidanaModel(
    var isi_laporan: String?,
    var waktu_buat_laporan: String?,

    /*KKE*/
    var personel_pelapor: PersonelMinResp?,
//    var isi_laporan: String?,
    var alat_bukti: String?,
    /*var jabatan_yang_mengetahui: String?,
    var nama_yang_mengetahui: String?,
    var pangkat_yang_mengetahui: String?,
    var nrp_yang_mengetahui: String?,*/

    /*DISIPLIN*/
    var macam_pelanggaran: String?,
    var keterangan_pelapor: String?,
    var kronologis_dari_pelapor: String?,
    var rincian_pelanggaran_disiplin: String?,

    var jabatan_kep_spkt:String?,
    var nama_kep_spkt:String?,
    var pangkat_kep_spkt:String?,
    var nrp_kep_spkt:String?,
    var nama_kabid_propam: String?,
    var pangkat_kabid_propam: String?,
    var nrp_kabid_propam: String?,
    var jabatan_kabid_propam: String?
//    var waktu_buat_laporan: String?,
//    var jabatan_yang_mengetahui: String?,
//    var nama_yang_mengetahui: String?,
//    var pangkat_yang_mengetahui: String?,
//    var nrp_yang_mengetahui: String?
) : Parcelable