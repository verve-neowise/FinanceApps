package io.monever.quotes.ui.authors

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import io.monever.quotes.models.Author
import io.monever.quotes.ui.theme.Fonts

@Composable
fun AuthorScreen(
    state: AuthorsState = AuthorsState(),
    showAuthor: (Int) -> Unit = {}
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
    ) {

        val (logoRef, listRef) = createRefs()

        Text(
            modifier = Modifier
                .constrainAs(logoRef) {
                    top.linkTo(parent.top, 32.dp)
                    start.linkTo(parent.start, 32.dp)
                },
            text = "Авторы",
            fontFamily = Fonts.Inter,
            fontWeight = FontWeight.Normal,
            fontSize = 36.sp
        )

        LazyColumn(
            modifier = Modifier
                .padding(horizontal = 32.dp)
                .constrainAs(listRef) {
                    height = Dimension.fillToConstraints
                    top.linkTo(logoRef.bottom, 32.dp)
                    bottom.linkTo(parent.bottom)
                }
        ) {
            items(state.authors) { author ->
                AuthorItem(
                    modifier =  Modifier.fillMaxWidth(),
                    name = author.name,
                    avatar = author.avatar
                ) {
                    showAuthor(author.id ?: 0)
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xfff)
private fun AuthorScreenPreview() {
    Box(modifier = Modifier.fillMaxSize()) {
        AuthorScreen(
            AuthorsState(
                authors = listOf(
                    Author(0, "Эрих Мария Ремарк", "https://www.finversia.ru/site/public/files/15/14452-erikh-mariya-remark.jpg"),
                    Author(1, "Унсур Аль-маали (Кей Кабус)", "https://www.finversia.ru/site/public/files/15/14453-unsur-al-maali.jpg"),
                    Author(2, "Пьер Декурсель", "https://www.finversia.ru/site/public/files/15/14454-per-dekursel.jpg"),
                ),
            )
        ) { id ->
        }
    }
}