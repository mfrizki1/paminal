package id.calocallo.sicape.network.request

import android.os.Parcelable
import id.calocallo.sicape.network.response.PesertaLhgResp

data class LhgReq(
    var id_lp: Int?,/*ADD*/
    var dugaan: String?,
    /**/ var pangkat_yang_menangani: String?,
    /**/var nama_yang_menangani: String?,
    var dasar: String?,
    var tanggal: String?,
    /**/  var waktu_mulai: String?,
    /**/var waktu_selesai: String?,
    var tempat: String?,
    var pangkat_pimpinan: String?,
    var nama_pimpinan: String?,
    var nrp_pimpinan: String?,
    var pangkat_pemapar: String?,
    var nama_pemapar: String?,
    var kronologis_kasus: String?,
    var no_surat_perintah_penyidikan: String?,
    var nama_notulen: String?,
    var pangkat_notulen: String?,
    var nrp_notulen: String?,
    var pasal_kuh_pidana: String?,
    var peserta_gelar: ArrayList<PesertaLhgResp>?/*ADD*/
) {
    constructor() : this(
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null
    )
}
