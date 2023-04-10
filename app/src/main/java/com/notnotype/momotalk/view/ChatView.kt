package com.notnotype.momotalk.view

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.notnotype.momotalk.component.UserAndMessage
import com.notnotype.momotalk.component.MessageGroup
import com.notnotype.momotalk.component.getDefaultMessageGroup
import com.notnotype.momotalk.ui.theme.MomotalkTheme

fun getDefaultChatLog(): List<MessageGroup> {
    return listOf(getDefaultMessageGroup(), getDefaultMessageGroup())
}

@Composable
fun ChatView() {
    val defaultChatLog = getDefaultChatLog()
    LazyColumn {
        items(defaultChatLog.size) {

        }
    }
}

@Preview(name = "previewChatView")
@Composable
fun PreviewChatView() {
    MomotalkTheme {
        ChatView()
    }
}