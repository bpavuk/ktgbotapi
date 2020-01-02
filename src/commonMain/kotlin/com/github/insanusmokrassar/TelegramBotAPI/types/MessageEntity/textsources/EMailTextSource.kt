package com.github.insanusmokrassar.TelegramBotAPI.types.MessageEntity.textsources

import com.github.insanusmokrassar.TelegramBotAPI.CommonAbstracts.TextSource
import com.github.insanusmokrassar.TelegramBotAPI.utils.emailHTML
import com.github.insanusmokrassar.TelegramBotAPI.utils.emailMarkdown

class EMailTextSource(
    override val rawSource: String
) : TextSource {
    override val asMarkdownSource: String
        get() = rawSource.emailMarkdown()
    override val asHtmlSource: String
        get() = rawSource.emailHTML()
}
