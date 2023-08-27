package io.monever.quotes.ui.common

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.monever.quotes.ui.theme.Fonts
import io.monever.quotes.ui.theme.ItemBackground
import io.monever.quotes.ui.theme.Secondary

@Composable
fun ActionButton(
    modifier: Modifier = Modifier,
    text: String = "",
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
             containerColor = ItemBackground,
            contentColor = Color.Black,
        ),
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        Text(
            text = text,
            fontSize = 14.sp,
            fontFamily = Fonts.Inter,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
@Preview
private fun ActionButtonPreview() {
    ActionButton(
        modifier = Modifier.width(200.dp),
        text = "Все цитаты") {
    }
}