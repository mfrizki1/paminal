package id.calocallo.sicape.network.response

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class LoginResp(
    val status: Int?,
    val token: String?,
    val user: UserResp?
) : Parcelable


@Parcelize
data class LoginSipilResp(
    val status: Int,
    val token: String,
    val hak_akses: HakAksesSipil
) : Parcelable

@Parcelize
data class SipilMaxModel(
    var user: HakAksesSipil
) : Parcelable

data class HakAksesSipil(
    var operator_sipil: SipilOperatorResp?,
    var id: Int?,
    var hak_akses: String?,
    var satuan_kerja: SatKerResp?,
    var is_aktif: Int?,
    var created_at: String?,
    var updated_at: String?
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readParcelable<SipilOperatorResp>(SipilOperatorResp::class.java.classLoader),
        source.readValue(Int::class.java.classLoader) as Int?,
        source.readString(),
        source.readParcelable<SatKerResp>(SatKerResp::class.java.classLoader),
        source.readValue(Int::class.java.classLoader) as Int?,
        source.readString(),
        source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeParcelable(operator_sipil, 0)
        writeValue(id)
        writeString(hak_akses)
        writeParcelable(satuan_kerja, 0)
        writeValue(is_aktif)
        writeString(created_at)
        writeString(updated_at)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<HakAksesSipil> =
            object : Parcelable.Creator<HakAksesSipil> {
                override fun createFromParcel(source: Parcel): HakAksesSipil = HakAksesSipil(source)
                override fun newArray(size: Int): Array<HakAksesSipil?> = arrayOfNulls(size)
            }
    }
}