package dev.inmo.tgbotapi.extensions.utils.formatting

import dev.inmo.tgbotapi.types.*
import dev.inmo.tgbotapi.types.chat.*
import dev.inmo.tgbotapi.types.message.abstracts.Message
import dev.inmo.tgbotapi.types.message.textsources.link


fun makeUsernameLink(username: String) = "$internalLinkBeginning/$username"
fun makeUsernameDeepLinkPrefix(username: String) = "${makeUsernameLink(username)}?start="
fun makeUsernameStartattachPrefix(username: String) = "$internalLinkBeginning/$username?startattach"
fun makeUsernameStartattachLink(username: String, data: String? = null) = "${makeUsernameStartattachPrefix(username)}${data?.let { "=$it" } ?: ""}"
inline val Username.link
    get() = makeUsernameLink(usernameWithoutAt)
inline val Username.deepLinkPrefix
    get() = makeUsernameDeepLinkPrefix(usernameWithoutAt)
inline val Username.startattachPrefix
    get() = makeUsernameStartattachPrefix(usernameWithoutAt)
inline fun makeLink(username: Username) = username.link
inline fun makeTelegramDeepLink(username: String, startParameter: String) = "${makeUsernameDeepLinkPrefix(username)}$startParameter"
inline fun makeTelegramStartattach(username: String, data: String? = null) = makeUsernameStartattachLink(username, data)
inline fun makeDeepLink(username: Username, startParameter: String) = "${username.deepLinkPrefix}$startParameter"
inline fun makeTelegramDeepLink(username: Username, startParameter: String) = makeDeepLink(username, startParameter)
inline fun makeTelegramStartattach(username: Username, data: String? = null) = makeTelegramStartattach(username.username, data)

fun makeLinkToMessage(
    username: String,
    messageId: MessageIdentifier
): String = "$internalLinkBeginning/$username/$messageId"
fun makeLinkToMessage(
    username: Username,
    messageId: MessageIdentifier
): String = makeLinkToMessage(username.username, messageId)
fun makeLinkToMessage(
    chat: UsernameChat,
    messageId: MessageIdentifier
): String? = chat.username ?.let { makeLinkToMessage(it, messageId) }

private val linkIdRedundantPartRegex = Regex("^-100")
private val usernameBeginSymbolRegex = Regex("^@")

/**
 * Link which can be used as by any user to get access to [Message]. Returns null in case when there are no
 * known way to build link (for [PrivateChat]s, for example)
 */
fun makeLinkToMessage(
    chat: Chat,
    messageId: MessageIdentifier
): String? {
    return when {
        chat is UsernameChat && chat.username != null -> {
            "$internalLinkBeginning/${chat.username ?.username ?.replace(
                usernameBeginSymbolRegex, "")}/$messageId"
        }
        chat !is PrivateChat -> chat.id.chatId.toString().replace(
            linkIdRedundantPartRegex,
            ""
        ).let { bareId ->
            "$internalLinkBeginning/c/$bareId/$messageId"
        }
        else -> return null
    }
}

/**
 * @see makeLinkToMessage
 */
val Message.link: String?
    get() = makeLinkToMessage(
        chat,
        messageId
    )

/**
 * Link which can be used as by any user to get access to [Chat]. Returns null in case when there are no
 * known way to build link
 */
val Chat.link: String?
    get() {
        if (this is UsernameChat) {
            username ?.link
        }
        if (this is ExtendedPublicChat) {
            inviteLink ?.let { return it }
        }
        if (this is PrivateChat) {
            return id.userLink
        }
        return null
    }

private const val stickerSetAddingLinkPrefix = "$internalLinkBeginning/addstickers"

val StickerSetName.stickerSetLink
    get() = link(this, "$stickerSetAddingLinkPrefix/$this")
