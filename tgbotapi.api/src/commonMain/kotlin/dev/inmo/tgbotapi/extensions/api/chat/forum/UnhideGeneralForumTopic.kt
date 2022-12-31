package dev.inmo.tgbotapi.extensions.api.chat.forum

import dev.inmo.tgbotapi.bot.TelegramBot
import dev.inmo.tgbotapi.requests.chat.forum.CloseForumTopic
import dev.inmo.tgbotapi.requests.chat.forum.CloseGeneralForumTopic
import dev.inmo.tgbotapi.requests.chat.forum.HideGeneralForumTopic
import dev.inmo.tgbotapi.requests.chat.forum.UnhideGeneralForumTopic
import dev.inmo.tgbotapi.types.ChatIdentifier
import dev.inmo.tgbotapi.types.ForumTopic
import dev.inmo.tgbotapi.types.MessageThreadId
import dev.inmo.tgbotapi.types.chat.Chat

suspend fun TelegramBot.unhideGeneralForumTopic(
    chatId: ChatIdentifier
) = execute(
    UnhideGeneralForumTopic(chatId)
)

suspend fun TelegramBot.unhideGeneralForumTopic(
    chat: Chat
) = unhideGeneralForumTopic(chat.id)
