package ru.otus.otuskotlin.propertysale.be.common.repositories

object EmptyUserSession : IUserSession<Any> {
    override val fwSession = object {}
}
