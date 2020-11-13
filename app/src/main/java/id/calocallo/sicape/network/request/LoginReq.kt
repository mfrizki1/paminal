package id.calocallo.sicape.network.request

data class LoginReq (var nrp: String, var password: String) {
    constructor() : this("","")
}