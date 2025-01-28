package net.iessochoa.dennisperezortiz.tareasv01.ui.screens.listatareas

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import net.iessochoa.dennisperezortiz.tareasv01.R
import net.iessochoa.dennisperezortiz.tareasv01.R.drawable.ic_abierto
import net.iessochoa.dennisperezortiz.tareasv01.R.drawable.ic_cerrado
import net.iessochoa.dennisperezortiz.tareasv01.R.drawable.ic_en_curso
import net.iessochoa.dennisperezortiz.tareasv01.data.db.entities.Tarea
import net.iessochoa.dennisperezortiz.tareasv01.ui.theme.ColorPrioridadAlta

@Composable
fun ItemCard(
    tarea: Tarea,
    listaCategorias: List<String> = emptyList(),
    onItemModificarClick: (id: Long) -> Unit,
    onClickBorrar: (Long) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val cardBackgroundColor = if (tarea.prioridad == 2) {
        ColorPrioridadAlta
    } else {
        Color.Transparent
    }

    var expanded by remember { mutableStateOf(false) }

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
            AsyncImage(
                model = if (tarea.img.isEmpty())
                    R.drawable.sinimagen
                else
                    tarea.img,
                contentDescription = null,
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
                    .padding(top = 4.dp, bottom = 8.dp, end = 8.dp),
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
                        contentDescription = "Estado de la tarea",
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = listaCategorias.getOrNull(tarea.categoria) ?: "Sin categoría",
                        style = MaterialTheme.typography.labelMedium,
                        color = Color.Gray,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }

                Text(
                    text = tarea.tecnico,
                    style = MaterialTheme.typography.labelLarge,
                    color = Color.Blue
                )

                Row(modifier = modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically){
                    if(!expanded){

                        Text(
                            text = tarea.descripcion,
                            modifier = Modifier.padding(top = 2.dp)
                                .weight(1f),
                            style = MaterialTheme.typography.bodySmall,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    } else {
                        Text(
                            text = tarea.descripcion,
                            modifier = Modifier.padding(top = 2.dp)
                                .weight(1f),
                            style = MaterialTheme.typography.bodySmall,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    ExpandableCardIcon(
                        expanded = expanded,
                        onIconClick = { expanded = !expanded },
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                }

            }
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(4.dp),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Center
            ) {
                IconButton(
                    onClick = { tarea.id?.let { onClickBorrar(it) } }
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Borrar tarea",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
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

@Composable
fun ExpandableCardIcon(
    expanded: Boolean,
    onIconClick: () -> Unit,
    modifier: Modifier
) {
    IconButton(onClick = onIconClick, modifier = modifier) {
        Icon(
            Icons.Filled.KeyboardArrowDown,
            "Expandir tarjeta",
            Modifier.rotate(
                if (expanded)
                    180f
                else
                    360f
            )
        )
    }
}