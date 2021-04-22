package id.calocallo.sicape.network.request

import id.calocallo.sicape.model.PutKkeOnRpphModel
import id.calocallo.sicape.model.SkhdOnRpsModel

data class Sp4Req(
    var id_lp: Int?,/*ADD*/
    var no_sp4: String?,
    var no_surat_perintah_kapolri: String?,
    var tanggal_surat_perintah_kapolri: String?,
    var no_hasil_audit: String?,
    var tanggal_hasil_audit: String?,
    var tanggal_gelar_audit: String?,
    var kota_penetapan: String?,
    var tanggal_penetapan: String?,
    var nama_akreditor: String?,
    var pangkat_akreditor: String?,
    var nrp_akreditor: String?
    /*  var id_sktb: Int?,
      var id_sktt: Int?,
      var no_sp4: String?,
      var mengingat_p4: String?,
      var mengingat_p5: String?,
      var menetapkan_p1: String?,
      var kota_keluar: String?,
      var tanggal_keluar: String?,
      var tembusan: String?,
      var nama_akreditor: String?,
      var pangkat_akreditor: String?,
      var nrp_akreditor: String?*/

) {
    constructor() : this(
        null,null, null, null, null, null, null, null, null, null,
        null, null
    )
}