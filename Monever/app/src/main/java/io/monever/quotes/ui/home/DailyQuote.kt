package io.monever.quotes.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import io.monever.quotes.ui.theme.Fonts
import io.monever.quotes.ui.theme.ItemBackground
import io.monever.quotes.ui.theme.PlaceholderBackground
import io.monever.quotes.ui.theme.Purple40
import io.monever.quotes.ui.theme.Secondary

@Composable
fun DailyQuote(
    modifier: Modifier = Modifier,
    image: String,
    text: String,
    author: String
) {
    ConstraintLayout(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {

        val (imageRef, textRef, authorRef) = createRefs()

        if (image.isEmpty()) {
            Box(modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(50))
                .background(PlaceholderBackground)
                .constrainAs(imageRef) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
            )
        }
        else {
            AsyncImage(
                model = image,
                contentDescription = "daily-quote-author",
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(50))
                    .background(PlaceholderBackground)
                    .constrainAs(imageRef) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    }
            )
        }

        Text(
            modifier = Modifier.constrainAs(textRef) {
                width = Dimension.fillToConstraints
                top.linkTo(parent.top)
                start.linkTo(imageRef.end, 16.dp)
                end.linkTo(parent.end)
            },
            text = text,
            fontFamily = Fonts.Inter,
        )

        Text(
            modifier = Modifier.constrainAs(authorRef) {
                top.linkTo(textRef.bottom, 10.dp)
                end.linkTo(parent.end)
            },
            text = author,
            fontSize = 13.sp,
            color = Secondary,
            fontFamily = Fonts.Inter,
        )
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xfff)
private fun DailyQuotePreview() {
    Box(modifier = Modifier.fillMaxSize()) {
        DailyQuote(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            image = "",
            text = "Погоня за прибылью - единственный способ, при помощи которого люди могут удовлетворять потребности тех, кого они вовсе не знают.",
            author = "Эрих Мария Ремарк"
        )
    }
}