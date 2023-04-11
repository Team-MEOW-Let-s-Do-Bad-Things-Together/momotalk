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
import androidx.compose.ui.graphics.Color
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
import com.notnotype.momotalk.model.*
import com.notnotype.momotalk.ui.theme.MomotalkTheme


enum class ClickableBubbleSender {
    Left, Right
}

@Composable
fun UserAndMessage(
    user: User,
    withAvatar: Boolean,
    message: Message,
    withTriangle: Boolean
) {
    Row {
        if (!isMe(user)) {
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
                Spacer(modifier = Modifier.width(avatarWidth))
            }
        }
        MessageBubble(user, withAvatar, message, withTriangle)
    }
}

@Composable
fun MessageBubble(
    user: User,
    withAvatar: Boolean,
    message: Message,
    withTriangle: Boolean
) {
    val backgroundBubbleColor = if (isMe(user))
        MaterialTheme.colorScheme.primary else
        MaterialTheme.colorScheme.surfaceVariant

    val pxValue = with(LocalDensity.current) { 22.dp.toPx() } / 2

    Column(horizontalAlignment = if (isMe(user)) Alignment.End else Alignment.Start) {
        if (withAvatar) {
            Row(modifier = Modifier
                .padding(start = triangleSize, top = 2.dp, bottom = 2.dp, end = triangleSize)
                .semantics(mergeDescendants = true) {}) {
                Text(
                    text = user.nickname,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.alignBy(LastBaseline)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "20:18",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.alignBy(LastBaseline),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Row(Modifier.height(IntrinsicSize.Max)) {
            if (withTriangle && !isMe(user)) {
                Box(
                    modifier = Modifier
                        .background(
                            color = backgroundBubbleColor, shape = TriangleEdgeShape(true, pxValue)
                        )
                        .width(triangleSize)
                        .fillMaxHeight()
                ) {}
            } else {
                Spacer(modifier = Modifier.width(if (!isMe(user)) triangleSize else triangleSize + avatarWidth))
            }

            val shape =
                if (isMe(user)) RoundedCornerShape(borderSize0, borderSize, borderSize, borderSize)
                else RoundedCornerShape(borderSize, borderSize0, borderSize, borderSize)

            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = if (isMe(user)) Arrangement.End else Arrangement.Start
            ) {
                Surface(
                    shape = shape, color = backgroundBubbleColor
                ) {
                    Text(
                        text = message.text,
                        style = MaterialTheme.typography.bodyLarge.copy(color = LocalContentColor.current),
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    )
                }
            }

            if (withTriangle && isMe(user)) {
                Box(
                    modifier = Modifier
                        .background(
                            color = backgroundBubbleColor, shape = TriangleEdgeShape(false, pxValue)
                        )
                        .width(triangleSize)
                        .fillMaxHeight()
                ) {}
            } else {
                Spacer(modifier = Modifier.width(if (isMe(user)) triangleSize else triangleSize + avatarWidth))
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
                moveTo(x = 0f, y = offsetY)
                lineTo(x = 0f, y = offsetY + offset)
                lineTo(x = 0f + offset, y = offsetY + offset / 2)
            }
        }
        return Outline.Generic(path = trianglePath)
    }
}

@Composable
@Preview()
fun PreviewUserAndMessage() {
    val messageGroup = getDefaultMessageGroup(getDefaultMe())
    val messageGroup2 = getDefaultMessageGroup(getDefaultUser())
    MomotalkTheme {
        Surface {
            Column {
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
                        Spacer(modifier = Modifier.height(bubbleSpace))
                    }
                }
                Column(horizontalAlignment = if (isMe(messageGroup2.user)) Alignment.End else Alignment.Start) {
                    for (i in 0 until messageGroup2.messages.size) {
                        var withAvatar = false
                        var withTriangle = false
                        if (i == 0) {
                            withAvatar = true
                            withTriangle = true
                        }
                        UserAndMessage(
                            withAvatar = withAvatar,
                            user = messageGroup2.user,
                            message = messageGroup2.messages[i],
                            withTriangle = withTriangle
                        )
                        Spacer(modifier = Modifier.height(bubbleSpace))
                    }
                }
            }
        }
    }
}

private val avatarWidth = 68.dp
private val bubbleSpace = 2.dp
private val triangleSize = 8.dp
private val borderSize = 12.dp
private val borderSize0 = 12.dp

