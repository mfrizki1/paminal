package id.calocallo.sicape.model

import android.os.Parcel
import android.os.Parcelable

data class PersonelModel(
    val id: Int?,
    val nama: String?,
    val nama_alias: String?,
    val jenis_kelamin: String?,
    val tempat_lahir: String?,
    val tanggal_lahir: String?,
    val ras: String?,
    val jabatan: String?,
    val pangkat: String?,
    val nrp: String?,
    val id_satuan_kerja: Int?,
    val alamat_rumah: String?,
    val no_telp_rumah: String?,
    val kewarganegaraan: String?,
    val cara_peroleh_kewarganegaraan: String?,
    val agama_sekarang: String?,
    val agama_sebelumnya: String?,
    val aliran_kepercayaan: String?,
    val status_perkawinan: String?,
    val tempat_perkawinan: String?,
    val tanggal_perkawinan: String?,
    val perkawinan_keberapa: String?,
    val jumlah_anak: String?,
    val alamat_sesuai_ktp: String?,
    val no_telp: String?,
    val no_ktp: String?,
    val hobi: String?,
    val kebiasaan: String?,
    val bahasa: String?,
    val satuan_kerja: SatKerResp?
) : Parcelable {
    constructor(source: Parcel) : this(
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
        source.readString(),
        source.readString(),
        source.readParcelable<SatKerResp>(SatKerResp::class.java.classLoader)
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeValue(id)
        writeString(nama)
        writeString(nama_alias)
        writeString(jenis_kelamin)
        writeString(tempat_lahir)
        writeString(tanggal_lahir)
        writeString(ras)
        writeString(jabatan)
        writeString(pangkat)
        writeString(nrp)
        writeValue(id_satuan_kerja)
        writeString(alamat_rumah)
        writeString(no_telp_rumah)
        writeString(kewarganegaraan)
        writeString(cara_peroleh_kewarganegaraan)
        writeString(agama_sekarang)
        writeString(agama_sebelumnya)
        writeString(aliran_kepercayaan)
        writeString(status_perkawinan)
        writeString(tempat_perkawinan)
        writeString(tanggal_perkawinan)
        writeString(perkawinan_keberapa)
        writeString(jumlah_anak)
        writeString(alamat_sesuai_ktp)
        writeString(no_telp)
        writeString(no_ktp)
        writeString(hobi)
        writeString(kebiasaan)
        writeString(bahasa)
        writeParcelable(satuan_kerja, 0)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<PersonelModel> =
            object : Parcelable.Creator<PersonelModel> {
                override fun createFromParcel(source: Parcel): PersonelModel = PersonelModel(source)
                override fun newArray(size: Int): Array<PersonelModel?> = arrayOfNulls(size)
            }
    }
}