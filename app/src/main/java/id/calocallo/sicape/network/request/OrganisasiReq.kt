package id.calocallo.sicape.network.request

data class OrganisasiReq(
    var organisasi: String?,
    var tahun_awal: String?,
    var tahun_akhir: String?,
    var jabatan: String?,
    var tahun_bergabung: String?,
    var alamat: String?,
    var keterangan: String?
){
    constructor():this(null,null,null,null,null,null,null)
}