package id.calocallo.sicape.network.request

data class LoginReq (var username: String, var password: String) {
    constructor() : this("","")
}