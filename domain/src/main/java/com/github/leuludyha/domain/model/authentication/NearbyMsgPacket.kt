package com.github.leuludyha.domain.model.authentication

private const val Header = "[NearbyMsg]"
private const val Delimiter = "/"

class NearbyMsgPacket(
    val descriptor: String,
) {

    sealed class Prefix(val id: String)

    object AddFriend : Prefix("AddFriend")
    object ShareWork : Prefix("ShareWork")

    constructor(prefix: Prefix, content: String) : this(
        Header + Delimiter + prefix.id + Delimiter + content
    )


    /** Prefix of the pack which identifies its type */
    val prefix get() = descriptor.split(Delimiter)[1]

    /** Content of the packet, the transmitted serialized information */
    val content get() = descriptor.split(Delimiter)[2]

    /** True if the packet is valid (Has the correct header), false otherwise */
    fun isValid() = descriptor.startsWith(Header)

}