package id.calocallo.sicape.network.response

import android.os.Parcel
import android.os.Parcelable


data class SipilProfilResp(
    var operator_sipil: SipilOperatorResp?,
    var id: Int?,
    var hak_akses: String?,
    var satuan_kerja: SatKerResp?,
    var is_aktif: String?,
    var created_at: String?,
    var updated_at: String?
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readParcelable<SipilOperatorResp>(SipilOperatorResp::class.java.classLoader),
        source.readValue(Int::class.java.classLoader) as Int?,
        source.readString(),
        source.readParcelable<SatKerResp>(SatKerResp::class.java.classLoader),
        source.readString(),
        source.readString(),
        source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeParcelable(operator_sipil, 0)
        writeValue(id)
        writeString(hak_akses)
        writeParcelable(satuan_kerja, 0)
        writeString(is_aktif)
        writeString(created_at)
        writeString(updated_at)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<SipilProfilResp> = object : Parcelable.Creator<SipilProfilResp> {
            override fun createFromParcel(source: Parcel): SipilProfilResp = SipilProfilResp(source)
            override fun newArray(size: Int): Array<SipilProfilResp?> = arrayOfNulls(size)
        }
    }
}