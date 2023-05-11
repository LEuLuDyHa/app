package com.github.leuludyha.domain.model.authentication

const val Header = "[NearbyMsg]/"

class NearbyMsgPacket(
    val descriptor: String,
) {
    sealed class Prefix(val id: String)

    object AddFriend : Prefix("AddFriend/")
    object ShareWork : Prefix("ShareWork/")

    constructor(prefix: Prefix, content: String) : this(Header + prefix.id + content)

    val prefix get() = descriptor.split("/")[1]

    val content get() = descriptor.split("/")[2]

    /** True if the packet is valid (Has the correct header), false otherwise */
    fun isValid() = descriptor.startsWith(Header)

}