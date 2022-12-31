package dev.inmo.tgbotapi.requests.chat.forum

import dev.inmo.tgbotapi.abstracts.types.ChatRequest
import dev.inmo.tgbotapi.requests.abstracts.SimpleRequest
import dev.inmo.tgbotapi.types.*
import dev.inmo.tgbotapi.utils.RGBColor
import kotlinx.serialization.*
import kotlinx.serialization.builtins.serializer

@Serializable
data class ReopenGeneralForumTopic (
    @SerialName(chatIdField)
    override val chatId: ChatIdentifier,
): ModifyForumRequest, GeneralForumRequest<Boolean> {
    override fun method(): String = "reopenGeneralForumTopic"
    override val requestSerializer: SerializationStrategy<*>
        get() = serializer()
}
