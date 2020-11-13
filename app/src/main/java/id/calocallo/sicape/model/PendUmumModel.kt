package id.calocallo.sicape.model

data class PendUmumModel(
    var nama_pend: String,
    var thn_awal_pend: String,
    var thn_akhir_pend: String,
    var tmpt_pend: String,
    var org_membiayai: String,
    var ket_pend: String?
) {
    constructor() : this("", "", "", "", "", "")
}