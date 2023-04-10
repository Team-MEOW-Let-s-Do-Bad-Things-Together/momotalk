package com.notnotype.momotalk.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.notnotype.momotalk.ui.theme.MomotalkTheme


data class User(val username: String, val nickname: String, val uid: String, val avatar: String) {}

data class MessageGroup(val user: User, val messages: List<Message>) {}

data class Message(val text: String) {}

fun getDefaultMessages(): List<Message> {
    return listOf(
        Message("Hello, World"),
        Message("Make the world better."),
        Message("Can can need."),
        Message("qpweoirhjnkasdbnfzxcloiyhjosdfjkesja;ladksjfkljasdokfhjl;kasjd;lfj中文输入从屙屎")
    )
}

fun getDefaultUser(): User {
    return User("Notnotype", "notype", "114514", "https://www.sdfsdf.dev/100x100.png")
}

fun getDefaultMessageGroup(): MessageGroup {
    return MessageGroup(
        user = getDefaultUser(),
        messages = getDefaultMessages(),
    )
}

enum class ClickableBubbleSender {
    Left, Right
}

@Composable
fun UserAndMessage(
    user: User,
    sender: ClickableBubbleSender,
    withAvatar: Boolean,
    message: Message,
    withTriangle: Boolean
) {

    val backgroundBubbleColor = when (sender) {
        ClickableBubbleSender.Left -> MaterialTheme.colorScheme.primary
        ClickableBubbleSender.Right -> MaterialTheme.colorScheme.surfaceVariant
    }

    Row() {
        if (withAvatar) {
            AsyncImage(
                model = user.avatar,
                modifier = Modifier
//                .clickable(onClick = { onAuthorClick(msg.author) })
                    .padding(end = 8.dp)
                    .size(60.dp)
                    .border(1.5.dp, MaterialTheme.colorScheme.primary, CircleShape)
                    .border(3.dp, MaterialTheme.colorScheme.surface, CircleShape)
                    .clip(CircleShape)
                    .align(Alignment.Top),
                contentScale = ContentScale.Crop,
                contentDescription = null,
            )
        } else {
            Spacer(modifier = Modifier.width(68.dp))
        }

        val pxValue = with(LocalDensity.current) { 25.dp.toPx() } / 2

        Column {
            if (withAvatar) {
                Row(modifier = Modifier
                    .padding(start = triangleSize, top = 2.dp, bottom = 2.dp)
                    .semantics(mergeDescendants = true) {}
                ) {
                    Text(

                        text = user.nickname,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier
                            .alignBy(LastBaseline)
//                            .paddingFrom(LastBaseline, after = 8.dp) // Space to 1st bubble
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "msg.timestamp",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.alignBy(LastBaseline),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Row(Modifier.height(IntrinsicSize.Max)) {
                if (withTriangle && sender == ClickableBubbleSender.Left) {
                    Box(
                        modifier = Modifier
                            .background(
                                color = backgroundBubbleColor,
                                shape = TriangleEdgeShape(true, pxValue)
                            )
                            .width(triangleSize)
                            .fillMaxHeight()
                    ) {}
                } else {
                    Spacer(modifier = Modifier.width(8.dp).fillMaxHeight())
                }

                val shape = if (sender == ClickableBubbleSender.Left)
                    RoundedCornerShape(borderSize0, borderSize, borderSize, borderSize)
                else
                    RoundedCornerShape(borderSize, borderSize0, borderSize, borderSize)

                Surface(
                    shape = shape, color = backgroundBubbleColor
                ) {
                    Text(
                        text = message.text,
                        style = MaterialTheme.typography.bodyLarge.copy(color = LocalContentColor.current),
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    )
                }
                if (withTriangle && sender == ClickableBubbleSender.Right) {
                    Box(
                        modifier = Modifier
                            .background(
                                color = backgroundBubbleColor,
                                shape = TriangleEdgeShape(false, pxValue)
                            )
                            .width(8.dp)
                            .fillMaxHeight()
                    ) {}
                } else {
                    Spacer(modifier = Modifier.width(triangleSize))
                }
            }
        }
    }
}

@Composable
fun SendMessage(
    withAvatar: Boolean,
    message: Message,
    withTriangle: Boolean
) {

}


@Composable
@Preview
fun PreviewUserAndMessage() {
    val messageGroup = getDefaultMessageGroup()
    MomotalkTheme {
        Column {
            for (i in 0 until messageGroup.messages.size) {
                var withAvatar = false
                var withTriangle = false
                if (i == 0) {
                    withAvatar = true
                    withTriangle = true
                }
                UserAndMessage(
                    withAvatar = withAvatar,
                    sender = ClickableBubbleSender.Left,
                    user = messageGroup.user,
                    message = messageGroup.messages[i],
                    withTriangle = withTriangle
                )
                Spacer(modifier = Modifier.height(bubbleSpace))
            }
        }
    }
}


class TriangleEdgeShape(private val left: Boolean, private val offsetY: Float) : Shape {
    private val offset = 20

    override fun createOutline(
        size: Size, layoutDirection: LayoutDirection, density: Density
    ): Outline {
        val trianglePath: Path = if (left) {
            Path().apply {
                moveTo(x = size.width, y = offsetY)
                lineTo(x = size.width, y = offsetY + offset)
                lineTo(x = 0f, y = offsetY + offset / 2)
            }
        } else {
            Path().apply {
                moveTo(x = 0f, y = size.height - offsetY - offset)
                lineTo(x = 0f, y = size.height - offsetY)
                lineTo(x = 0f + offset, y = size.height - offsetY - offset / 2)
            }
        }
        return Outline.Generic(path = trianglePath)
    }
}

private val bubbleSpace = 4.dp
private val triangleSize = 8.dp
private val borderSize = 12.dp
private val borderSize0 = 12.dp

