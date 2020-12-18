package id.calocallo.sicape.model

data class PasanganReq(
    var status_pasangan: String?,
    var nama: String?,
    var nama_alias: String?,
    var tempat_lahir: String?,
    var tanggal_lahir: String?,
    var ras: String?,
    var kewarganegaraan: String?,
    var cara_peroleh_kewarganegaraan: String?,
    var agama_sekarang: String?,
    var agama_sebelumnya: String?,
    var aliran_kepercayaan_dianut: String?,
    var aliran_kepercayaan_diikuti: String?,
    var alamat_terakhir_sebelum_kawin: String?,
    var alamat_rumah: String?,
    var no_telp_rumah: String?,
    var pendidikan_terakhir: String?,
    var perkawinan_keberapa: String?,
    var pekerjaan: String?,
    var alamat_kantor: String?,
    var no_telp_kantor: String?,
    var pekerjaan_sebelumnya: String?,

    var kedudukan_organisasi_saat_ini: String?,
    var tahun_bergabung_organisasi_saat_ini: String?,
    var alasan_bergabung_organisasi_saat_ini: String?,
    var alamat_organisasi_saat_ini: String?,

    var kedudukan_organisasi_sebelumnya: String?,
    var tahun_bergabung_organisasi_sebelumnya: String?,
    var alasan_bergabung_organisasi_sebelumnya: String?,
    var alamat_organisasi_sebelumnya: String?

) {
    constructor() : this(
        "", "", "", "", "",
        "", "", "", "",
        "", "", "",
        "", "", "",
        "", "", "", "",
        "", "", "",
        "", "",
        "", "",
        "", "",
        ""
    )
}