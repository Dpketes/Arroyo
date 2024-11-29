package net.iessochoa.dennisperezortiz.tareasv01.ui.screens.listatareas

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import net.iessochoa.dennisperezortiz.tareasv01.R.string.titulo_lista_tarea
import net.iessochoa.dennisperezortiz.tareasv01.ui.components.AppBar
import net.iessochoa.dennisperezortiz.tareasv01.ui.theme.TareasV01Theme

@Composable
fun ListaTareasScreen(
    viewModel: ListaTareasViewModel = viewModel(),
    onClickNueva: () -> Unit = {},
    onItemModificarClick: (id: Long) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.listaTareasUiState.collectAsState()

    Scaffold(
        topBar = {
            AppBar(
                tituloPantallaActual = stringResource(titulo_lista_tarea),
                puedeNavegarAtras = false
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onClickNueva) {
                Icon(Icons.Filled.Add, contentDescription = "Nueva Tarea")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            uiState.listaPalabras.forEachIndexed { pos, item ->
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onItemModificarClick(item.id !!) }
                        .padding(8.dp)
                ) {
                    Text(
                        text = "${item.id} - ${item.prioridad} - ${item.estado} - ${item.tecnico}",
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                HorizontalDivider(color = Color.Blue, thickness = 1.dp)

            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun TareaScreenPreview() {
    TareasV01Theme {
        ListaTareasScreen()
    }
}