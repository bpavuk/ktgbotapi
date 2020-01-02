package com.github.insanusmokrassar.TelegramBotAPI.types.MessageEntity

import com.github.insanusmokrassar.TelegramBotAPI.CommonAbstracts.TextSource
import com.github.insanusmokrassar.TelegramBotAPI.types.MessageEntity.textsources.URLTextSource
import com.github.insanusmokrassar.TelegramBotAPI.utils.linkHTML
import com.github.insanusmokrassar.TelegramBotAPI.utils.linkMarkdown

data class URLMessageEntity(
    override val offset: Int,
    override val length: Int,
    override val rawSource: String
) : MessageEntity, TextSource by URLTextSource(rawSource) {
    val url: String
        get() = rawSource
}
