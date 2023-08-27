package io.monever.quotes.ui.quotes

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import io.monever.quotes.R
import io.monever.quotes.share
import io.monever.quotes.storage.getFavoriteAuthors
import io.monever.quotes.storage.setFavoriteAuthor
import io.monever.quotes.ui.theme.Fonts
import io.monever.quotes.ui.theme.PlaceholderBackground
import io.monever.quotes.ui.theme.Secondary

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun QuotesScreen(
    state: QuotesState = QuotesState(),
    pageCount: Int = 0,
) {
    val context = LocalContext.current

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
    ) {

        val pagerState = rememberPagerState(pageCount = { pageCount })

        val (logoRef, indicatorRef, pageRef) = createRefs()

        Text(
            modifier = Modifier
                .constrainAs(logoRef) {
                    top.linkTo(parent.top, 32.dp)
                    start.linkTo(parent.start, 32.dp)
                },
            text = "Цитаты",
            fontFamily = Fonts.Inter,
            fontWeight = FontWeight.Normal,
            fontSize = 36.sp
        )

        Text(
            modifier = Modifier.constrainAs(indicatorRef) {
                top.linkTo(logoRef.top)
                bottom.linkTo(logoRef.bottom)
                end.linkTo(parent.end, 32.dp)
            },
            text = "${pagerState.currentPage + 1} из ${pagerState.pageCount}",
            fontFamily = Fonts.Inter,
            color = Secondary
        )

        HorizontalPager(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(pageRef) {
                    top.linkTo(logoRef.bottom, 42.dp)
                    bottom.linkTo(parent.bottom)
                    height = Dimension.fillToConstraints
                },
            state = pagerState
        ) {

            val quote = state.quotes[it]

            QuotePage(
                authorImage = quote.avatar,
                authorName = quote.author,
                text = quote.text,
                isFavorite = quote.isFavorite.value,
                onFavorite = { isFavorite ->
                    quote.isFavorite.value = isFavorite
                    setFavoriteAuthor(quote.id ?: 0, isFavorite, context)
                }
            )
        }
    }
}

@Composable
fun QuotePage(
    modifier: Modifier = Modifier,
    authorImage: String,
    authorName: String,
    text: String,
    isFavorite: Boolean,
    onFavorite: (Boolean) -> Unit
) {

    val context = LocalContext.current

    ConstraintLayout(
        modifier = modifier.fillMaxSize()
    ) {

        val (imageRef, authorRef, textRef, buttons) = createRefs()

        if (authorImage.isEmpty()) {
            Box(modifier = Modifier
                .size(124.dp)
                .clip(RoundedCornerShape(50))
                .background(PlaceholderBackground)
                .constrainAs(imageRef) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            )
        } else {
            AsyncImage(
                model = authorImage,
                contentDescription = "daily-quote-author",
                modifier = Modifier
                    .size(124.dp)
                    .clip(RoundedCornerShape(50))
                    .background(PlaceholderBackground)
                    .constrainAs(imageRef) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )
        }

        Text(
            text = authorName,
            textAlign = TextAlign.Center,
            fontFamily = Fonts.Inter,
            color = Secondary,
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(authorRef) {
                    top.linkTo(imageRef.bottom, 16.dp)
                },
        )

        Text(
            text = text,
            textAlign = TextAlign.Center,
            fontFamily = Fonts.Inter,
            fontSize = 20.sp,
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(textRef) {
                    width = Dimension.fillToConstraints
                    top.linkTo(authorRef.bottom, 50.dp)
                    start.linkTo(parent.start, 50.dp)
                    end.linkTo(parent.end, 50.dp)
                },
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .constrainAs(buttons) {
                    bottom.linkTo(parent.bottom)
                },
            horizontalArrangement = Arrangement.Center
        ) {
            IconButton(
                onClick = { onFavorite(!isFavorite) }) {
                Icon(
                    imageVector = if (isFavorite) {
                        Icons.Outlined.Favorite
                    } else {
                        Icons.Outlined.FavoriteBorder
                    },
                    contentDescription = "favorite-icon"
                )
            }
            IconButton(
                onClick = {
                    context.share("${text}\n${authorName}")
                }
            ) {
                Icon(imageVector = Icons.Outlined.Share, "favorite-icon")
            }
        }
    }
}


@Composable
@Preview(showBackground = true, backgroundColor = 0xfff)
private fun QuotesScreenPreview() {
    QuotesScreen()
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xfff)
private fun QuotePagePreview() {
    Box(modifier = Modifier.fillMaxSize()) {
        QuotePage(
            authorImage = "",
            authorName = "Эрих Мария Ремарк",
            text = "Погоня за прибылью - единственный способ, при помощи которого люди могут удовлетворять потребности тех, кого они вовсе не знают.",
            isFavorite = false
        ) {

        }
    }
}