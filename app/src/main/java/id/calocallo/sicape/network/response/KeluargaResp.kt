package id.calocallo.sicape.network.response

import android.os.Parcel
import android.os.Parcelable

data class KeluargaResp(
    var id: Int?,
    var id_personel: Int?,
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
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readValue(Int::class.java.classLoader) as Int?,
        source.readValue(Int::class.java.classLoader) as Int?,
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readValue(Int::class.java.classLoader) as Int?,
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readValue(Int::class.java.classLoader) as Int?,
        source.readString(),
        source.readString(),
        source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeValue(id)
        writeValue(id_personel)
        writeString(nama)
        writeString(nama_alias)
        writeString(tempat_lahir)
        writeString(tanggal_lahir)
        writeString(agama)
        writeString(ras)
        writeString(kewarganegaraan)
        writeString(cara_peroleh_kewarganegaraan)
        writeString(aliran_kepercayaan_dianut)
        writeString(alamat_rumah)
        writeString(no_telp_rumah)
        writeString(alamat_rumah_sebelumnya)
        writeValue(status_kerja)
        writeString(pekerjaan_terakhir)
        writeString(tahun_pensiun)
        writeString(alasan_pensiun)
        writeString(nama_kantor)
        writeString(alamat_kantor)
        writeString(no_telp_kantor)
        writeString(pekerjaan_sebelumnya)
        writeString(pendidikan_terakhir)
        writeString(kedudukan_organisasi_saat_ini)
        writeString(tahun_bergabung_organisasi_saat_ini)
        writeString(alasan_bergabung_organisasi_saat_ini)
        writeString(alamat_organisasi_saat_ini)
        writeString(kedudukan_organisasi_sebelumnya)
        writeString(tahun_bergabung_organisasi_sebelumnya)
        writeString(alasan_bergabung_organisasi_sebelumnya)
        writeString(alamat_organisasi_sebelumnya)
        writeValue(status_kehidupan)
        writeString(tahun_kematian)
        writeString(lokasi_kematian)
        writeString(sebab_kematian)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<KeluargaResp> = object : Parcelable.Creator<KeluargaResp> {
            override fun createFromParcel(source: Parcel): KeluargaResp =
                KeluargaResp(source)
            override fun newArray(size: Int): Array<KeluargaResp?> = arrayOfNulls(size)
        }
    }
}