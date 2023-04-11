package com.notnotype.momotalk.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.notnotype.momotalk.component.*
import com.notnotype.momotalk.model.MessageGroup
import com.notnotype.momotalk.model.getDefaultMe
import com.notnotype.momotalk.model.getDefaultMessageGroup
import com.notnotype.momotalk.model.isMe
import com.notnotype.momotalk.ui.theme.MomotalkTheme

fun getDefaultChatLog(): List<MessageGroup> {
    return listOf(
        getDefaultMessageGroup(),
        getDefaultMessageGroup(user = getDefaultMe()),
        getDefaultMessageGroup()
    )
}

@Composable
fun ChatView(
    modifier: Modifier = Modifier,
    chatLog: List<MessageGroup>
) {
    val lazyListState = rememberLazyListState()

    LazyColumn(
        modifier = modifier.padding(8.dp),
        state = lazyListState
    ) {
        items(chatLog.size) {
            val messageGroup = chatLog[it]

            Column(horizontalAlignment = if (isMe(messageGroup.user)) Alignment.End else Alignment.Start) {
                for (i in 0 until messageGroup.messages.size) {
                    var withAvatar = false
                    var withTriangle = false
                    if (i == 0) {
                        withAvatar = true
                        withTriangle = true
                    }
                    UserAndMessage(
                        withAvatar = withAvatar,
                        user = messageGroup.user,
                        message = messageGroup.messages[i],
                        withTriangle = withTriangle
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                }
            }
        }
    }
}

@Preview(name = "previewChatView")
@Composable
fun PreviewChatView() {
    MomotalkTheme {
        Surface {
            val defaultChatLog = getDefaultChatLog()
            ChatView(chatLog = defaultChatLog)
        }
    }
}