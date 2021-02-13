package id.calocallo.sicape.network.response

import android.os.Parcel
import android.os.Parcelable
import id.calocallo.sicape.model.AllPersonelModel1
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LoginPersonelResp(
    val status: Int,
    val token: String,
    val hak_akses: HakAksesPersonel1
) : Parcelable

@Parcelize
data class PersonelModelMax2(
    var user: HakAksesPersonel1
) : Parcelable

@Parcelize
data class PersonelModelMax1(
    var personel: AllPersonelModel1
) : Parcelable


@Parcelize
data class LoginSuperAdminResp(
    val status: Int,
    val token: String,
    val user: HakAksesPersonel1
) : Parcelable

data class HakAksesPersonel1(
    var personel: AllPersonelModel1?,
    var id: Int?,
    var hak_akses: String?,
    var satuan_kerja: SatKerResp?,
    var is_aktif: Int?,
    var created_at: String?,
    var updated_at: String?
) : Parcelable {
    constructor() : this(null, null, null, null, null, null, null)
    constructor(parcel: Parcel) : this(
        parcel.readParcelable(AllPersonelModel1::class.java.classLoader),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readParcelable(SatKerResp::class.java.classLoader),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(personel, flags)
        parcel.writeValue(id)
        parcel.writeString(hak_akses)
        parcel.writeParcelable(satuan_kerja, flags)
        parcel.writeValue(is_aktif)
        parcel.writeString(created_at)
        parcel.writeString(updated_at)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<HakAksesPersonel1> {
        override fun createFromParcel(parcel: Parcel): HakAksesPersonel1 {
            return HakAksesPersonel1(parcel)
        }

        override fun newArray(size: Int): Array<HakAksesPersonel1?> {
            return arrayOfNulls(size)
        }
    }
}
