package dev.inmo.tgbotapi.types.chat

import dev.inmo.tgbotapi.types.*
import dev.inmo.tgbotapi.types.chat.abstracts.Chat
import dev.inmo.tgbotapi.types.chat.abstracts.UnknownChatType
import dev.inmo.tgbotapi.types.chat.abstracts.extended.ExtendedChat
import dev.inmo.tgbotapi.types.chat.abstracts.extended.UnknownExtendedChat
import dev.inmo.tgbotapi.types.chat.extended.*
import dev.inmo.tgbotapi.utils.RiskFeature
import dev.inmo.tgbotapi.utils.nonstrictJsonFormat
import kotlinx.serialization.*
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.*

private val formatter
    get() = nonstrictJsonFormat

@Serializable(ChatTypeSerializer::class)
sealed class ChatType {
    abstract val stringified: String
    @Serializable(ChatTypeSerializer::class)
    object PrivateChatType : ChatType() { override val stringified = "private" }
    @Serializable(ChatTypeSerializer::class)
    object GroupChatType : ChatType() { override val stringified = "group" }
    @Serializable(ChatTypeSerializer::class)
    object SupergroupChatType : ChatType() { override val stringified = "supergroup" }
    @Serializable(ChatTypeSerializer::class)
    object ChannelChatType : ChatType() { override val stringified = "channel" }
    @Serializable(ChatTypeSerializer::class)
    class UnknownChatType(override val stringified: String) : ChatType()
}
val String.asChatType
    get() = when (this) {
        ChatType.PrivateChatType.stringified -> ChatType.PrivateChatType
        ChatType.GroupChatType.stringified -> ChatType.GroupChatType
        ChatType.SupergroupChatType.stringified -> ChatType.SupergroupChatType
        ChatType.ChannelChatType.stringified -> ChatType.ChannelChatType
        else -> ChatType.UnknownChatType(this)
    }
object ChatTypeSerializer : KSerializer<ChatType> {
    override val descriptor: SerialDescriptor = String.serializer().descriptor
    override fun deserialize(decoder: Decoder): ChatType {
        return decoder.decodeString().asChatType
    }

    override fun serialize(encoder: Encoder, value: ChatType) {
        encoder.encodeString(value.stringified)
    }
}

@RiskFeature
object PreviewChatSerializer : KSerializer<Chat> {
    @InternalSerializationApi
    override val descriptor: SerialDescriptor = buildSerialDescriptor("PreviewChatSerializer", PolymorphicKind.OPEN)

    override fun deserialize(decoder: Decoder): Chat {
        val decodedJson = JsonObject.serializer().deserialize(decoder)

        val type = decodedJson[typeField] ?.jsonPrimitive ?.content ?.asChatType ?: error("Field $typeField must be presented, but absent in $decodedJson")

        return when (type) {
            ChatType.PrivateChatType -> formatter.decodeFromJsonElement(PrivateChatImpl.serializer(), decodedJson)
            ChatType.GroupChatType -> formatter.decodeFromJsonElement(GroupChatImpl.serializer(), decodedJson)
            ChatType.SupergroupChatType -> formatter.decodeFromJsonElement(SupergroupChatImpl.serializer(), decodedJson)
            ChatType.ChannelChatType -> formatter.decodeFromJsonElement(ChannelChatImpl.serializer(), decodedJson)
            is ChatType.UnknownChatType -> UnknownChatType(
                formatter.decodeFromJsonElement(Long.serializer(), decodedJson[chatIdField] ?: JsonPrimitive(-1)).toChatId(),
                decodedJson.toString()
            )
        }
    }

    override fun serialize(encoder: Encoder, value: Chat) {
        when (value) {
            is ExtendedChat -> ExtendedChatSerializer.serialize(encoder, value)
            is PrivateChatImpl -> PrivateChatImpl.serializer().serialize(encoder, value)
            is GroupChatImpl -> GroupChatImpl.serializer().serialize(encoder, value)
            is SupergroupChatImpl -> SupergroupChatImpl.serializer().serialize(encoder, value)
            is ChannelChatImpl -> ChannelChatImpl.serializer().serialize(encoder, value)
        }
    }
}

@RiskFeature
object ExtendedChatSerializer : KSerializer<ExtendedChat> {
    @InternalSerializationApi
    override val descriptor: SerialDescriptor = buildSerialDescriptor("PreviewChatSerializer", PolymorphicKind.OPEN)

    override fun deserialize(decoder: Decoder): ExtendedChat {
        val decodedJson = JsonObject.serializer().deserialize(decoder)

        val type = decodedJson[typeField] ?.jsonPrimitive ?.content ?.asChatType ?: error("Field $typeField must be presented, but absent in $decodedJson")

        return when (type) {
//            else -> throw IllegalArgumentException("Unknown type of chat")
            ChatType.PrivateChatType -> formatter.decodeFromJsonElement(ExtendedPrivateChatImpl.serializer(), decodedJson)
            ChatType.GroupChatType -> formatter.decodeFromJsonElement(ExtendedGroupChatImpl.serializer(), decodedJson)
            ChatType.SupergroupChatType -> formatter.decodeFromJsonElement(ExtendedSupergroupChatImpl.serializer(), decodedJson)
            ChatType.ChannelChatType -> formatter.decodeFromJsonElement(ExtendedChannelChatImpl.serializer(), decodedJson)
            is ChatType.UnknownChatType -> UnknownExtendedChat(
                formatter.decodeFromJsonElement(Long.serializer(), decodedJson[chatIdField] ?: JsonPrimitive(-1)).toChatId(),
                decodedJson.toString()
            )
        }
    }

    override fun serialize(encoder: Encoder, value: ExtendedChat) {
        when (value) {
            is ExtendedPrivateChatImpl -> ExtendedPrivateChatImpl.serializer().serialize(encoder, value)
            is ExtendedGroupChatImpl -> ExtendedGroupChatImpl.serializer().serialize(encoder, value)
            is ExtendedSupergroupChatImpl -> ExtendedSupergroupChatImpl.serializer().serialize(encoder, value)
            is ExtendedChannelChatImpl -> ExtendedChannelChatImpl.serializer().serialize(encoder, value)
        }
    }
}



