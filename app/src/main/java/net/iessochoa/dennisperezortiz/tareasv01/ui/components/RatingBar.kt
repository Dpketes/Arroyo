package net.iessochoa.dennisperezortiz.tareasv01.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import net.iessochoa.dennisperezortiz.tareasv01.R

//Un rating bar normal lo unico que en vez de con estrellas le he metido los iconos de emoji tanto outline como filleds, para que si no se marca o selecciona se vea la diferencia de los marcados y los que no estan marcados, por ejemplo si el cliente selecciona la tercera cara, la primera la segunda y la tercera se volveran rellenados basicamente filled, y la cuarta y quinta se quedarán outline.
@Composable
fun RatingBar(rating: Int, onRatingChanged: (Int) -> Unit) {
    val emojiOutlines = listOf(
        R.drawable.emoji_1_outline,
        R.drawable.emoji_2_outline,
        R.drawable.emoji_3_outline,
        R.drawable.emoji_4_outline,
        R.drawable.emoji_5_outline
    )
    val emojiFilled = listOf(
        R.drawable.emoji_1_filled,
        R.drawable.emoji_2_filled,
        R.drawable.emoji_3_filled,
        R.drawable.emoji_4_filled,
        R.drawable.emoji_5_filled
    )

    //Aqui es donde defino el rating bar
    Row {
        for (i in 1..5) {
            IconButton(onClick = { onRatingChanged(i) }) {
                val emojiPainter: Painter = painterResource(
                    id = if (i <= rating) emojiFilled[i - 1] else emojiOutlines[i - 1]
                )
                Image(painter = emojiPainter, contentDescription = null)
            }
        }
    }
}
