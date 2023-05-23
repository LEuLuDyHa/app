package com.github.leuludyha.domain.model.authentication

import com.google.common.truth.Truth
import org.junit.Test

class NearbyMsgPacketTest {
    @Test
    fun fieldsAreProperlyAccessed() {
        val msg = NearbyMsgPacket("desc/prefix/content")
        Truth.assertThat(msg.descriptor).isEqualTo("desc/prefix/content")
        Truth.assertThat(msg.prefix).isEqualTo("prefix")
        Truth.assertThat(msg.content).isEqualTo("content")
    }

    @Test
    fun validPacketIsValid() {
        val msg = NearbyMsgPacket("[NearbyMsg]/prefix/content")
        Truth.assertThat(msg.isValid()).isTrue()
    }


    @Test
    fun invalidPacketIsNotValid() {
        val msg = NearbyMsgPacket("header/prefix/content")
        Truth.assertThat(msg.isValid()).isFalse()
    }

    @Test
    fun prefixContentConstructorWithAddFriendWorks() {
        val msg = NearbyMsgPacket(NearbyMsgPacket.AddFriend, "content")
        Truth.assertThat(msg.prefix).isEqualTo(NearbyMsgPacket.AddFriend.id)
        Truth.assertThat(msg.content).isEqualTo("content")
    }

    @Test
    fun prefixContentConstructorWithShareWorkWorks() {
        val msg = NearbyMsgPacket(NearbyMsgPacket.ShareWork, "content")
        Truth.assertThat(msg.prefix).isEqualTo(NearbyMsgPacket.ShareWork.id)
        Truth.assertThat(msg.content).isEqualTo("content")
    }
}
