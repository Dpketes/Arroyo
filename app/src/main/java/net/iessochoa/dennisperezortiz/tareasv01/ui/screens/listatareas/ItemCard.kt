package net.iessochoa.dennisperezortiz.tareasv01.ui.screens.listatareas

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import net.iessochoa.dennisperezortiz.tareasv01.R.drawable.ic_abierto
import net.iessochoa.dennisperezortiz.tareasv01.R.drawable.ic_cerrado
import net.iessochoa.dennisperezortiz.tareasv01.R.drawable.ic_en_curso
import net.iessochoa.dennisperezortiz.tareasv01.data.db.entities.Tarea
import net.iessochoa.dennisperezortiz.tareasv01.ui.theme.ColorPrioridadAlta

@Composable
fun ItemCard(
    tarea: Tarea,
    listaCategorias: List<String>,
    onItemModificarClick: (id: Long) -> Unit,
    modifier: Modifier = Modifier
) {
    val cardBackgroundColor = if (tarea.prioridad == 2) {
        ColorPrioridadAlta
    } else {
        Color.Transparent
    }

    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { tarea.id?.let { onItemModificarClick(it) } },
        elevation = androidx.compose.material3.CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .background(cardBackgroundColor)
        ) {
            Image(
                painter = painterResource(tarea.img.toInt()),
                contentDescription = "Imagen de la tarea",
                modifier = Modifier
                    .width(100.dp)
                    .fillMaxHeight(),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(8.dp))

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .padding(vertical = 8.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        painter = painterResource(
                            id = when (tarea.estado) {
                                0 -> ic_abierto
                                1 -> ic_en_curso
                                2 -> ic_cerrado
                                else -> ic_abierto
                            }
                        ),
                        contentDescription = "Estado de la tarea"
                    )
                    Text(
                        text = listaCategorias.getOrNull(tarea.categoria) ?: "Sin categoría",
                        style = MaterialTheme.typography.labelMedium,
                        color = Color.Gray
                    )
                }

                Text(
                    text = tarea.tecnico,
                    style = MaterialTheme.typography.labelLarge,
                    color = Color.Blue
                )

                Text(
                    text = tarea.descripcion,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(4.dp),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    text = tarea.id.toString(),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }
        }
    }
}
