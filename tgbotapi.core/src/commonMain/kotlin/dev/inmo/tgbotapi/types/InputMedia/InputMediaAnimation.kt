package dev.inmo.tgbotapi.types.InputMedia

import dev.inmo.tgbotapi.CommonAbstracts.TextedOutput
import dev.inmo.tgbotapi.CommonAbstracts.makeString
import dev.inmo.tgbotapi.requests.abstracts.InputFile
import dev.inmo.tgbotapi.requests.abstracts.fileIdToSend
import dev.inmo.tgbotapi.types.*
import dev.inmo.tgbotapi.types.MessageEntity.*
import dev.inmo.tgbotapi.types.MessageEntity.textsources.TextSource
import dev.inmo.tgbotapi.types.MessageEntity.textsources.TextSourcesList
import dev.inmo.tgbotapi.types.ParseMode.ParseMode
import dev.inmo.tgbotapi.types.ParseMode.parseModeField
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

fun InputMediaAnimation(
    file: InputFile,
    text: String? = null,
    parseMode: ParseMode? = null,
    width: Int? = null,
    height: Int? = null,
    duration: Long? = null,
    thumb: InputFile? = null
) = InputMediaAnimation(file, text, parseMode, null, width, height, duration, thumb)

fun InputMediaAnimation(
    file: InputFile,
    entities: TextSourcesList,
    width: Int? = null,
    height: Int? = null,
    duration: Long? = null,
    thumb: InputFile? = null
) = InputMediaAnimation(file, entities.makeString(), null, entities.toRawMessageEntities(), width, height, duration, thumb)

@Serializable
data class InputMediaAnimation internal constructor(
    override val file: InputFile,
    @SerialName(captionField)
    override val text: String? = null,
    @SerialName(parseModeField)
    override val parseMode: ParseMode? = null,
    @SerialName(captionEntitiesField)
    private val rawEntities: List<RawMessageEntity>? = null,
    override val width: Int? = null,
    override val height: Int? = null,
    override val duration: Long? = null,
    override val thumb: InputFile? = null
) : InputMedia, SizedInputMedia, DuratedInputMedia, ThumbedInputMedia, TextedOutput {
    override val type: String = "animation"
    override val textSources: TextSourcesList? by lazy {
        rawEntities ?.asTextSources(text ?: return@lazy null)
    }

    @SerialName(mediaField)
    override val media: String
    init { media = file.fileIdToSend } // crutch until js compiling will be fixed
}
