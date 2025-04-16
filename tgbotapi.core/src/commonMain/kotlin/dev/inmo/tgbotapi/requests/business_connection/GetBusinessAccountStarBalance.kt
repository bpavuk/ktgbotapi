package dev.inmo.tgbotapi.requests.business_connection

import dev.inmo.tgbotapi.requests.abstracts.BusinessRequest
import dev.inmo.tgbotapi.requests.abstracts.SimpleRequest
import dev.inmo.tgbotapi.types.ChatId
import dev.inmo.tgbotapi.types.MessageId
import dev.inmo.tgbotapi.types.Username
import dev.inmo.tgbotapi.types.bioField
import dev.inmo.tgbotapi.types.businessConnectionIdField
import dev.inmo.tgbotapi.types.business_connection.BusinessConnectionId
import dev.inmo.tgbotapi.types.chatIdField
import dev.inmo.tgbotapi.types.firstNameField
import dev.inmo.tgbotapi.types.lastNameField
import dev.inmo.tgbotapi.types.message.RawMessage
import dev.inmo.tgbotapi.types.messageIdField
import dev.inmo.tgbotapi.types.messageIdsField
import dev.inmo.tgbotapi.types.payments.stars.StarAmount
import dev.inmo.tgbotapi.types.usernameField
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.builtins.serializer

@Serializable
data class GetBusinessAccountStarBalance(
    @SerialName(businessConnectionIdField)
    override val businessConnectionId: BusinessConnectionId,
) : BusinessRequest.Simple<StarAmount> {
    override fun method(): String = "getBusinessAccountStarBalance"

    override val resultDeserializer: DeserializationStrategy<StarAmount>
        get() = StarAmount.serializer()
    override val requestSerializer: SerializationStrategy<*>
        get() = serializer()
}