package io.monever.quotes.ui.about

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import io.monever.quotes.share
import io.monever.quotes.ui.common.ActionButton
import io.monever.quotes.ui.common.HtmlText
import io.monever.quotes.ui.theme.Fonts

@Composable
fun AboutScreen(
    aboutText: String = "",
    shareLink: String = ""
) {
    val context = LocalContext.current

    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {

        val (logoRef, textRef, shareRef) = createRefs()

        Text(
            modifier = Modifier
                .constrainAs(logoRef) {
                    top.linkTo(parent.top, 32.dp)
                    start.linkTo(parent.start, 32.dp)
                },
            text = "О приложении",
            fontFamily = Fonts.Inter,
            fontWeight = FontWeight.Normal,
            fontSize = 36.sp
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp)
                .verticalScroll(rememberScrollState())
                .constrainAs(textRef) {
                    height = Dimension.fillToConstraints
                    top.linkTo(logoRef.bottom, 16.dp)
                    bottom.linkTo(shareRef.top, 32.dp)
                }
        ) {
            HtmlText(
                html = aboutText,
                modifier = Modifier.fillMaxWidth()
            )
        }
        ActionButton(
            text = "Поделиться приложением",
            modifier = Modifier.constrainAs(shareRef) {
                bottom.linkTo(parent.bottom, 32.dp)
                start.linkTo(parent.start, 32.dp)
                end.linkTo(parent.end, 32.dp)
                width = Dimension.fillToConstraints
            }
        ) {
            context.share(shareLink)
        }
    }
}

@Composable
@Preview
private fun AboutScreenPreview() {
    AboutScreen(
        aboutText = "<h2>Accompanist Pager Migration</h2> <p>The Accompanist version of the Pager is now marked as deprecated. Google has provided a guide on migrating your codebase from the existing com.google.accompanist.pager.HorizontalPager to androidx.compose.foundation.pager.HorizontalPager, and from com.google.accompanist.pager.VerticalPager to androidx.compose.foundation.pager.VerticalPager</p>",
        shareLink = ""
    )
}