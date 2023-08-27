package io.monever.quotes.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.monever.quotes.R
import io.monever.quotes.ui.Destinations
import io.monever.quotes.ui.common.ActionButton
import io.monever.quotes.ui.theme.Secondary

enum class Menu(val title: String, val route: String) {
    AllQuotes("Цитаты", Destinations.Quotes),
    Authors("Авторы", Destinations.Authors),
    About("О приложении", Destinations.About)
}

@Composable
fun HomeScreen(
    state: HomeState = HomeState(),
    navigateTo: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = 48.dp,
                start = 32.dp,
                end = 32.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .width(150.dp),
            painter = painterResource(id = R.drawable.monever),
            contentDescription = "logo"
        )
        Spacer(modifier = Modifier.height(60.dp))

        if (state.dailyQuote != null) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = "Цитата дня",
                color = Secondary,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(10.dp))

            DailyQuote(
                modifier = Modifier.fillMaxWidth(),
                image = state.dailyQuote.avatar,
                text = state.dailyQuote.text,
                author = state.dailyQuote.author
            )
        }

        Spacer(modifier = Modifier.height(80.dp))

        Column(
            modifier = Modifier.width(200.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Menu.values().forEach { menu ->
                ActionButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = menu.title
                ) {
                    navigateTo(menu.route)
                }
            }
        }
    }
}

@Composable
@Preview(backgroundColor = 0xfff, showBackground = true)
private fun HomeScreenPreview() {
    HomeScreen(
        state = HomeState(),
        navigateTo = { }
    )
}