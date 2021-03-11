package id.calocallo.sicape.network.request

data class AnakReq(
    var status_ikatan: String?,
    var nama: String?,
    var jenis_kelamin: String?,
    var tempat_lahir: String?,
    var tanggal_lahir: String?,
    var pekerjaan_atau_sekolah: String?,
    var organisasi_yang_diikuti: String?,
    var keterangan: String?
) {
    constructor() : this(
        null, null, null, null, null, null, null, null
    )
}