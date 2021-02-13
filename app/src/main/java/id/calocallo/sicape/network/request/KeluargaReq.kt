package id.calocallo.sicape.network.request

data class KeluargaReq(
    var status_hubungan: String?,
    var nama: String?,
    var nama_alias: String?,
    var tempat_lahir: String?,
    var tanggal_lahir: String?,
    var agama: String?,
    var ras: String?,
    var kewarganegaraan: String?,
    var cara_peroleh_kewarganegaraan: String?,
    var aliran_kepercayaan_dianut: String?,
    var alamat_rumah: String?,
    var no_telp_rumah: String?,
    var alamat_rumah_sebelumnya: String?,

    var status_kerja: Int?,
    var pekerjaan_terakhir: String?,
    var tahun_pensiun: String?,
    var alasan_pensiun: String?,

    var nama_kantor: String?,
    var alamat_kantor: String?,
    var no_telp_kantor: String?,
    var pekerjaan_sebelumnya: String?,
    var pendidikan_terakhir: String?,

    var kedudukan_organisasi_saat_ini: String?,
    var tahun_bergabung_organisasi_saat_ini: String?,
    var alasan_bergabung_organisasi_saat_ini: String?,
    var alamat_organisasi_saat_ini: String?,

    var kedudukan_organisasi_sebelumnya: String?,
    var tahun_bergabung_organisasi_sebelumnya: String?,
    var alasan_bergabung_organisasi_sebelumnya: String?,
    var alamat_organisasi_sebelumnya: String?,

    var status_kehidupan: Int?,
    var tahun_kematian: String?,
    var lokasi_kematian: String?,
    var sebab_kematian: String?
) {
    constructor() : this(
        null,null, null, null, null, null, null, null,
        null, null, null, null,
        null, 0, null, null, null,
        null, null, null, null, null,
        null, null, null,
        null,  null,
        null, null,
        null, 0, null, null, null
    )
}