package io.monever.quotes.ui.authors

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import io.monever.quotes.ui.theme.Fonts
import io.monever.quotes.ui.theme.PlaceholderBackground

@Composable
fun AuthorItem(
    modifier: Modifier = Modifier,
    avatar: String = "",
    name: String = "",
    onClick: () -> Unit = {}
) {
    ConstraintLayout(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .clickable { onClick() }
            .padding(16.dp)
    ) {

        val (imageRef, textRef) = createRefs()

        if (avatar.isEmpty()) {
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
                model = avatar,
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
                bottom.linkTo(parent.bottom)
                start.linkTo(imageRef.end, 16.dp)
                end.linkTo(parent.end)
            },
            text = name,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = Fonts.Inter,
        )
    }
}

@Composable
@Preview(backgroundColor = 0xfff, showBackground = true)
private fun AuthorItemPreview() {
    AuthorItem(
        modifier = Modifier.fillMaxWidth(),
        avatar = "https://www.finversia.ru/site/public/files/15/14452-erikh-mariya-remark.jpg",
        name = "Эрих Мария Ремарк"
    )
}