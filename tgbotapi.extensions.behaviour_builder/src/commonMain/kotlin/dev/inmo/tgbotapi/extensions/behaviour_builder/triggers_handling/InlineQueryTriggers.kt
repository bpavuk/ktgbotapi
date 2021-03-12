package dev.inmo.tgbotapi.extensions.behaviour_builder.triggers_handling

import dev.inmo.micro_utils.coroutines.subscribeSafelyWithoutExceptions
import dev.inmo.tgbotapi.extensions.behaviour_builder.*
import dev.inmo.tgbotapi.extensions.behaviour_builder.expectations.expectFlow
import dev.inmo.tgbotapi.extensions.utils.asInlineQueryUpdate
import dev.inmo.tgbotapi.extensions.utils.extensions.sourceChat
import dev.inmo.tgbotapi.types.InlineQueries.abstracts.InlineQuery
import dev.inmo.tgbotapi.types.InlineQueries.query.BaseInlineQuery
import dev.inmo.tgbotapi.types.InlineQueries.query.LocationInlineQuery

internal suspend inline fun <reified T : InlineQuery> BehaviourContext.onInlineQuery(
    includeFilterByChatInBehaviourSubContext: Boolean = true,
    noinline additionalFilter: (suspend (T) -> Boolean)? = null,
    noinline scenarioReceiver: BehaviourContextAndTypeReceiver<Unit, T>
) = flowsUpdatesFilter.expectFlow(bot) {
    it.asInlineQueryUpdate() ?.data ?.let { query ->
        if (query is T) {
            if (additionalFilter == null || additionalFilter(query)) query else null
        } else {
            null
        }
    }.let(::listOfNotNull)
}.subscribeSafelyWithoutExceptions(scope) { triggerQuery ->
    doInSubContextWithUpdatesFilter(
        updatesFilter = if (includeFilterByChatInBehaviourSubContext) {
            { it.sourceChat() ?.id ?.chatId == triggerQuery.from.id.chatId }
        } else {
            null
        }
    ) {
        scenarioReceiver(triggerQuery)
    }
}


suspend fun BehaviourContext.onAnyInlineQuery(
    includeFilterByChatInBehaviourSubContext: Boolean = true,
    additionalFilter: (suspend (InlineQuery) -> Boolean)? = null,
    scenarioReceiver: BehaviourContextAndTypeReceiver<Unit, InlineQuery>
) = onInlineQuery(includeFilterByChatInBehaviourSubContext, additionalFilter, scenarioReceiver)


suspend fun BehaviourContext.onBaseInlineQuery(
    includeFilterByChatInBehaviourSubContext: Boolean = true,
    additionalFilter: (suspend (BaseInlineQuery) -> Boolean)? = null,
    scenarioReceiver: BehaviourContextAndTypeReceiver<Unit, BaseInlineQuery>
) = onInlineQuery(includeFilterByChatInBehaviourSubContext, additionalFilter, scenarioReceiver)


suspend fun BehaviourContext.onLocationInlineQuery(
    includeFilterByChatInBehaviourSubContext: Boolean = true,
    additionalFilter: (suspend (LocationInlineQuery) -> Boolean)? = null,
    scenarioReceiver: BehaviourContextAndTypeReceiver<Unit, LocationInlineQuery>
) = onInlineQuery(includeFilterByChatInBehaviourSubContext, additionalFilter, scenarioReceiver)
