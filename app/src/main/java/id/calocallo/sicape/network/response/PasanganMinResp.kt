package id.calocallo.sicape.network.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PasanganMinResp(
    var id: Int?,
    var id_personel: Int?,
    var status_pasangan: String?,
    var nama: String?,
    var pendidikan_terakhir: String?
) : Parcelable


@Parcelize
data class PasanganResp(
    val id: Int?,
    val personel: PersonelMinResp?,
    val status_pasangan: String?,
    val nama: String?,
    val nama_alias: String?,
    val tempat_lahir: String?,
    val tanggal_lahir: String?,
    val ras: String?,
    val kewarganegaraan: String?,
    val cara_peroleh_kewarganegaraan: String?,
    val agama_sekarang: String?,
    val agama_sebelumnya: String?,
    val aliran_kepercayaan_dianut: String?,
    val aliran_kepercayaan_diikuti: String?,
    val alamat_terakhir_sebelum_kawin: String?,
    val alamat_rumah: String?,
    val no_telp_rumah: String?,
    val pendidikan_terakhir: String?,
    val perkawinan_keberapa: String?,
    val pekerjaan: String?,
    val alamat_kantor: String?,
    val no_telp_kantor: String?,
    val pekerjaan_sebelumnya: String?,
    val kedudukan_organisasi_saat_ini: String?,
    val tahun_bergabung_organisasi_saat_ini: Int?,
    val alasan_bergabung_organisasi_saat_ini: String?,
    val alamat_organisasi_saat_ini: String?,
    val kedudukan_organisasi_sebelumnya: String?,
    val tahun_bergabung_organisasi_sebelumnya: Int?,
    val alasan_bergabung_organisasi_sebelumnya: String?,
    val alamat_organisasi_sebelumnya: String?
) : Parcelable