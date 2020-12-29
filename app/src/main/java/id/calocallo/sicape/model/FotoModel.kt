package id.calocallo.sicape.model

import android.os.Parcel
import android.os.Parcelable

data class FotoModel(
    val id_foto_kanan: String?,
    val id_foto_muka: String?,
    val id_foto_kiri: String?
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readString(),
        source.readString(),
        source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(id_foto_kanan)
        writeString(id_foto_muka)
        writeString(id_foto_kiri)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<FotoModel> = object : Parcelable.Creator<FotoModel> {
            override fun createFromParcel(source: Parcel): FotoModel = FotoModel(source)
            override fun newArray(size: Int): Array<FotoModel?> = arrayOfNulls(size)
        }
    }
}