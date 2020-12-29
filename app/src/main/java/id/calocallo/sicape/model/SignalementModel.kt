package id.calocallo.sicape.model

data class SignalementModel(
    var tinggi: String?,
    var rambut: String?,
    var muka: String?,
    var mata: String?,
    var sidik_jari: String?,
    var cacat: String?,
    var kesenangan: String?,
    var kelemahan: String?,
    var yang_mempengaruhi: String?,
    var keluarga_dekat: String,
    var lain_lainnya: String?
){
    constructor():this(
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        ""
    )
}