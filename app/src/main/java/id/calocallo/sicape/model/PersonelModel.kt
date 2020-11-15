package id.calocallo.sicape.model

import android.os.Parcel
import android.os.Parcelable
import java.util.*

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
    val kesatuan: String?,
    val alamat_kantor: String?,
    val no_telp_kantor: String?,
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
    val bahasa: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(nama)
        parcel.writeString(nama_alias)
        parcel.writeString(jenis_kelamin)
        parcel.writeString(tempat_lahir)
        parcel.writeString(tanggal_lahir)
        parcel.writeString(ras)
        parcel.writeString(jabatan)
        parcel.writeString(pangkat)
        parcel.writeString(nrp)
        parcel.writeString(kesatuan)
        parcel.writeString(alamat_kantor)
        parcel.writeString(no_telp_kantor)
        parcel.writeString(alamat_rumah)
        parcel.writeString(no_telp_rumah)
        parcel.writeString(kewarganegaraan)
        parcel.writeString(cara_peroleh_kewarganegaraan)
        parcel.writeString(agama_sekarang)
        parcel.writeString(agama_sebelumnya)
        parcel.writeString(aliran_kepercayaan)
        parcel.writeString(status_perkawinan)
        parcel.writeString(tempat_perkawinan)
        parcel.writeString(tanggal_perkawinan)
        parcel.writeString(perkawinan_keberapa)
        parcel.writeString(jumlah_anak)
        parcel.writeString(alamat_sesuai_ktp)
        parcel.writeString(no_telp)
        parcel.writeString(no_ktp)
        parcel.writeString(hobi)
        parcel.writeString(kebiasaan)
        parcel.writeString(bahasa)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PersonelModel> {
        override fun createFromParcel(parcel: Parcel): PersonelModel {
            return PersonelModel(parcel)
        }

        override fun newArray(size: Int): Array<PersonelModel?> {
            return arrayOfNulls(size)
        }
    }

}