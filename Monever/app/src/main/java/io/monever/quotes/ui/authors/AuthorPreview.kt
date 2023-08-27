package io.monever.quotes.ui.authors

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import io.monever.quotes.models.Author
import io.monever.quotes.openLink
import io.monever.quotes.share
import io.monever.quotes.ui.common.ActionButton
import io.monever.quotes.ui.theme.Fonts
import io.monever.quotes.ui.theme.PlaceholderBackground
import io.monever.quotes.ui.theme.Secondary

@Composable
fun AuthorPreview(
    author: Author,
    onFavorite: (Boolean) -> Unit = { }
) {

    val context = LocalContext.current

    ConstraintLayout(
        modifier = Modifier
            .padding(start = 32.dp, end = 32.dp, top = 32.dp)
            .fillMaxSize()
    ) {

        val (imageRef, authorRef, textRef, datesRef, buttons) = createRefs()

        if (author.avatar.isEmpty()) {
            Box(modifier = Modifier
                .size(72.dp)
                .clip(RoundedCornerShape(50))
                .background(PlaceholderBackground)
                .constrainAs(imageRef) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
            )
        } else {
            AsyncImage(
                model = author.avatar,
                contentDescription = "daily-quote-author",
                modifier = Modifier
                    .size(72.dp)
                    .clip(RoundedCornerShape(50))
                    .background(PlaceholderBackground)
                    .constrainAs(imageRef) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    }
            )
        }

        Text(
            text = author.name,
            textAlign = TextAlign.Center,
            fontFamily = Fonts.Inter,
            fontWeight = FontWeight.Medium,
            color = Color.Black,
            fontSize = 22.sp,
            modifier = Modifier
                .constrainAs(authorRef) {
                    start.linkTo(imageRef.end, 16.dp)
                    top.linkTo(imageRef.top)
                },
        )

        Text(
            text = author.dates,
            textAlign = TextAlign.Center,
            fontFamily = Fonts.Inter,
            color = Secondary,
            fontSize = 18.sp,
            modifier = Modifier
                .constrainAs(datesRef) {
                    top.linkTo(authorRef.bottom, 4.dp)
                    start.linkTo(authorRef.start)
                }
        )

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .constrainAs(textRef) {
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                    top.linkTo(authorRef.bottom, 72.dp)
                    bottom.linkTo(buttons.top, 8.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        ) {
            Text(
                text = author.text,
                fontFamily = Fonts.Inter,
                fontSize = 20.sp,
                modifier = Modifier.fillMaxWidth(),
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .constrainAs(buttons) {
                    bottom.linkTo(parent.bottom)
                },
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            ActionButton(
                modifier = Modifier.weight(1f),
                text = "Открыть Wikipedia"
            ) {
                context.openLink(author.link)
            }
            IconButton(
                onClick = { onFavorite(!author.isFavorite.value) }
            ) {
                Icon(
                    imageVector = if (author.isFavorite.value) {
                        Icons.Outlined.Favorite
                    } else {
                        Icons.Outlined.FavoriteBorder
                    },
                    contentDescription = "favorite-icon"
                )
            }
            IconButton(
                onClick = {
                    context.share("${author.name}\n${author.dates}\n${author.text}")
                }
            ) {
                Icon(imageVector = Icons.Outlined.Share, "favorite-icon")
            }
        }
    }
}

@Composable
@Preview(backgroundColor = 0xfff, showBackground = true)
private fun AuthorPreviewPreview() {
    AuthorPreview(author = Author(
        1,
        name = "Уинстон Черчилль",
        text = "Британский политик и писатель, дважды становившийся премьер-министром Великобритании. Один из ключевых лидеров союзников во время Второй мировой войны.",
        dates = "1874 г.–1965 г."
    ))
}