package org.example.flikrphotosearch.core.presentation.content

import org.example.flikrphotosearch.core.presentation.UiText
import org.jetbrains.compose.resources.StringResource


data class ContentErrorConfig(
    val errorTitleRes: ErrorMessage,
    val errorSubTitleRes: ErrorMessage,
    val onRetry: (() -> Unit)? = null
) {
sealed interface ErrorMessage {
        data class Resource(val stringResource: StringResource) : ErrorMessage
        data class Text(val text: UiText) : ErrorMessage
    }
}

