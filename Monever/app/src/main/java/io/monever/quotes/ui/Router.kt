package io.monever.quotes.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import io.monever.quotes.models.Author
import io.monever.quotes.storage.loadAuthors
import io.monever.quotes.storage.setFavoriteAuthor
import io.monever.quotes.ui.about.AboutScreen
import io.monever.quotes.ui.authors.AuthorPreview
import io.monever.quotes.ui.authors.AuthorScreen
import io.monever.quotes.ui.authors.AuthorsViewModel
import io.monever.quotes.ui.home.HomeScreen
import io.monever.quotes.ui.home.HomeViewModel
import io.monever.quotes.ui.quotes.QuotesScreen
import io.monever.quotes.ui.quotes.QuotesViewModel

object Destinations {
    const val Home = "home"
    const val Quotes = "quotes"
    const val Authors = "authors"
    const val AuthorPreview = "authors/{id}"
    const val About = "about"
}

@Composable
fun Router() {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Destinations.Home) {
        composable(Destinations.Home) {
            val viewModel: HomeViewModel = viewModel()
            HomeScreen(
                state = viewModel.state,
                navigateTo = {
                    navController.navigate(it)
                }
            )
        }
        composable(Destinations.Quotes) {
            val viewModel: QuotesViewModel = viewModel()
            QuotesScreen(
                state = viewModel.state,
                pageCount = viewModel.state.quotes.size
            )
        }
        composable(Destinations.Authors) {
            val viewModel: AuthorsViewModel = viewModel()
            AuthorScreen(
                state = viewModel.state,
                showAuthor = { id -> navController.navigate("${Destinations.Authors}/${id}") }
            )
        }
        composable(
            route = Destinations.AuthorPreview,
            arguments = listOf(
                navArgument("id") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val context = LocalContext.current
            val id = backStackEntry.arguments?.getInt("id")
            val author = loadAuthors(LocalContext.current)?.find { it.id == id }
            AuthorPreview(
                author = author ?: Author()
            ) { favorite ->
                author?.isFavorite?.value = favorite
                setFavoriteAuthor(id ?: 0, favorite, context)
            }
        }
        composable(Destinations.About) {
            val assets = LocalContext.current.assets
            val aboutText = assets.open("about.html").bufferedReader().readText()
            AboutScreen(aboutText, "https://google.com")
        }
    }
}