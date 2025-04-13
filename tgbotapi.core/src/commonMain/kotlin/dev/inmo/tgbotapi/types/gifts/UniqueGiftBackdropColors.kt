package dev.inmo.tgbotapi.types.gifts

import dev.inmo.tgbotapi.types.centerColorField
import dev.inmo.tgbotapi.types.edgeColorField
import dev.inmo.tgbotapi.types.symbolColorField
import dev.inmo.tgbotapi.types.textColorField
import dev.inmo.tgbotapi.utils.RGBColor
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * This object describes the colors of the backdrop of a unique gift.
 *
 * @param centerColor The color in the center of the backdrop in RGB format
 * @param edgeColor The color on the edges of the backdrop in RGB format
 * @param symbolColor The color to be applied to the symbol in RGB format
 * @param textColor The color for the text on the backdrop in RGB format
 */
@Serializable
data class UniqueGiftBackdropColors(
    @SerialName(centerColorField)
    val centerColor: RGBColor,
    @SerialName(edgeColorField)
    val edgeColor: RGBColor,
    @SerialName(symbolColorField)
    val symbolColor: RGBColor,
    @SerialName(textColorField)
    val textColor: RGBColor
)
