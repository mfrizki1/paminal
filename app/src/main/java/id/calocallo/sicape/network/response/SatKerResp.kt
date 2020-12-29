package id.calocallo.sicape.network.response

import android.os.Parcel
import android.os.Parcelable

data class SatKerResp(
    var id: Int?,
    var kesatuan: String?,
    var alamat_kantor: String?,
    var no_telp_kantor: String?,
    var tingkat: String?,
    var created_at: String?,
    var updated_at: String?,
    var deleted_at: String?
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readValue(Int::class.java.classLoader) as Int?,
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeValue(id)
        writeString(kesatuan)
        writeString(alamat_kantor)
        writeString(no_telp_kantor)
        writeString(tingkat)
        writeString(created_at)
        writeString(updated_at)
        writeString(deleted_at)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<SatKerResp> = object : Parcelable.Creator<SatKerResp> {
            override fun createFromParcel(source: Parcel): SatKerResp =
                SatKerResp(source)
            override fun newArray(size: Int): Array<SatKerResp?> = arrayOfNulls(size)
        }
    }
}