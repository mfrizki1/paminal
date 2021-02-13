package id.calocallo.sicape.network.response

import android.os.Parcelable
import id.calocallo.sicape.model.AllPersonelModel1
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AddPersonelResp(val personel: AllPersonelModel1) : Parcelable