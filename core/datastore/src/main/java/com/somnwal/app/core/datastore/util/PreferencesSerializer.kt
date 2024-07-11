package com.somnwal.app.core.datastore.util

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import com.somnwal.app.core.datastore.Preferences
import java.io.InputStream
import java.io.OutputStream

object PreferencesSerializer : Serializer<Preferences> {
    override val defaultValue: Preferences
        get() = Preferences.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): Preferences =
        try {
            Preferences.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }

    override suspend fun writeTo(t: Preferences, output: OutputStream) =
        t.writeTo(output)

}