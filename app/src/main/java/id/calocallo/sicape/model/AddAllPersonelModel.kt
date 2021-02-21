package id.calocallo.sicape.model

import id.calocallo.sicape.network.request.*
import kotlinx.android.parcel.Parcelize

data class AddAllPersonelModel(
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
    var perkawinan_keberapa: Int?,
    var jumlah_anak: Int?,
    var alamat_sesuai_ktp: String?,
    var no_telp: String?,
    var no_ktp: String?,
    var hobi: String?,
    var kebiasaan: String?,
    var bahasa: String?,
    var signalement: SignalementModel,
    var foto: FotoReq,
    var relasi: ArrayList<RelasiReq>,
    var pernah_dihukum: ArrayList<HukumanReq>,
//    var catatan_personel:CatatanPersReq,
    var riwayat_pendidikan: ArrayList<AddPendidikanModel>,
//    var riwayat_pendidikan_kedinasan: ArrayList<AddPendidikanModel>,
//    var riwayat_pendidikan_lain_lain: ArrayList<AddPendidikanModel>,
    var riwayat_pekerjaan: ArrayList<AddSinglePekerjaanReq>,
    var pekerjaan_diluar_dinas: ArrayList<PekerjaanODinasReq>,
    var riwayat_alamat: ArrayList<AlamatReq>,
    var riwayat_organisasi: ArrayList<OrganisasiReq>,
    var riwayat_penghargaan: ArrayList<PenghargaanReq>,
    var riwayat_perjuangan: ArrayList<PerjuanganCitaReq>,
    var pasangan: ArrayList<PasanganReq>,
    var keluarga :ArrayList<KeluargaReq>,
//    var ayah_kandung: AyahReq,
//    var ayah_tiri: AyahTiriReq,
//    var ibu_kandung: IbuReq,
//    var ibu_tiri: IbuTiriReq,
//    var mertua_laki: MertuaLakiReq,
//    var mertua_perempuan: MertuaPerempuanReq,
    var anak: ArrayList<AnakReq>,
    var saudara: ArrayList<SaudaraReq>,
    var orang_berjasa: ArrayList<OrangsReq>,
    var orang_disegani: ArrayList<OrangsReq>,
    var tokoh_dikagumi: ArrayList<TokohReq>,
    var sahabat: ArrayList<SahabatReq>,
    var media_disenangi: ArrayList<MedInfoReq>,
    var media_sosial: ArrayList<MedSosReq>

) {
    constructor() : this(
        null, null, null, null, null, null,
        null, null, null, null, null,
        null, null, null, null,
        null, null, null, null,
        null, null, null, null,
        null, null, null, null, null, SignalementModel(), FotoReq(),
        ArrayList(), ArrayList(), ArrayList(), ArrayList(),
        ArrayList(), ArrayList(), ArrayList(), ArrayList(), ArrayList(), ArrayList(),  ArrayList(),ArrayList(),
        ArrayList(), ArrayList(), ArrayList(), ArrayList(), ArrayList(), ArrayList(), ArrayList()
    )
}