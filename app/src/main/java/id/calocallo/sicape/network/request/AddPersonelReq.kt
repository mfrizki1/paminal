package id.calocallo.sicape.network.request

data class AddPersonelReq(
    var nama: String?,
    var nama_alias: String?,
    var jenis_kelamin: String?,
    var tempat_lahir: String?,
    var tanggal_lahir: String?,
    var ras: String?,
    var jabatan: String?,
    var pangkat: String?,
    var nrp: String?,
    var id_satuan_kerja: Int?,
//    var alamat_kantor: String?,
//    var no_telp_kantor: String?,
    var alamat_rumah: String?,
    var no_telp_rumah: String?,
    var kewarganegaraan: String?,
    var cara_peroleh_kewarganegaraan: String?,
    var agama_sekarang: String?,
    var agama_sebelumnya: String?,
    var aliran_kepercayaan: String?,
    var status_perkawinan: Int?,
    var tempat_perkawinan: String?,
    var tanggal_perkawinan: String?,
    var perkawinan_keberapa: String?,
    var jumlah_anak: Int?,
    var alamat_sesuai_ktp: String?,
    var no_telp: String?,
    var no_ktp: String?,
    var hobi: String?,
    var kebiasaan: String?,
    var bahasa: String?
) {
    constructor() : this(
        "", "", "", "", "", "",
        "", "", "", 0,"", "",
        "", "", "", "",
        "", 0, "","",
        "",0,"","",
        "","","",""
    )
}