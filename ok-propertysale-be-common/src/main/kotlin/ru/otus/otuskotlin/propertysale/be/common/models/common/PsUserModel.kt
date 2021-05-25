package ru.otus.otuskotlin.propertysale.be.common.models.common

data class PsUserModel(
    val id: PsUserIdModel = PsUserIdModel.NONE,
    val fname: String = "",
    val mname: String = "",
    val lname: String = "",
) {
    companion object {
        val NONE = PsUserModel()
    }
}
