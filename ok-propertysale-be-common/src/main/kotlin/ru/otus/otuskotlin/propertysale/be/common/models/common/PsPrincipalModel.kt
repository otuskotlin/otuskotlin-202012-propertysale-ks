package ru.otus.otuskotlin.propertysale.be.common.models.common

data class PsPrincipalModel(
    val id: PsUserIdModel = PsUserIdModel.NONE,
    val fname: String = "",
    val mname: String = "",
    val lname: String = "",
    val groups: List<PsUserGroups> = emptyList()
) {
    companion object {
        val NONE = PsPrincipalModel()
    }
}
