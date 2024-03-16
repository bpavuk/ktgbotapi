package dev.inmo.tgbotapi.types

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@Serializable
@JvmInline
value class TgFileUniqueId(
    val string: String
)
@Deprecated(
    "Renamed",
    ReplaceWith("TgFileUniqueId", "dev.inmo.tgbotapi.types.TgFileUniqueId")
)
typealias FileUniqueId = TgFileUniqueId