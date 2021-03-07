package id.calocallo.sicape.model

import android.os.Parcelable
import id.calocallo.sicape.network.response.PersonelMinResp
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DetLapPidanaModel(
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
    var isi_laporan: String?,
    var waktu_buat_laporan: String?,

    /*KKE*/
    var personel_pelapor: PersonelMinResp?,
//    var isi_laporan: String?,
    var alat_bukti: String?,
    var jabatan_yang_mengetahui: String?,
    var nama_yang_mengetahui: String?,
    var pangkat_yang_mengetahui: String?,
    var nrp_yang_mengetahui: String?,

    /*DISIPLIN*/
    var macam_pelanggaran: String?,
    var keterangan_pelapor: String?,
    var kronologis_dari_pelapor: String?,
    var rincian_pelanggaran_disiplin: String?
//    var waktu_buat_laporan: String?,
//    var jabatan_yang_mengetahui: String?,
//    var nama_yang_mengetahui: String?,
//    var pangkat_yang_mengetahui: String?,
//    var nrp_yang_mengetahui: String?
) : Parcelable