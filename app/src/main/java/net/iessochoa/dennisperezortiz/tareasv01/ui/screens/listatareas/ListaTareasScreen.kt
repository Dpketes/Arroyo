package net.iessochoa.dennisperezortiz.tareasv01.ui.screens.listatareas

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import net.iessochoa.dennisperezortiz.tareasv01.R
import net.iessochoa.dennisperezortiz.tareasv01.R.string.titulo_lista_tarea
import net.iessochoa.dennisperezortiz.tareasv01.ui.components.AppBar
import net.iessochoa.dennisperezortiz.tareasv01.ui.components.DialogoDeConfirmacion
import net.iessochoa.dennisperezortiz.tareasv01.ui.theme.TareasV01Theme

@Composable
fun ListaTareasScreen(
    viewModel: ListaTareasViewModel = viewModel(),
    onClickNueva: () -> Unit = {},
    onItemModificarClick: (id: Long) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.listaTareasUiState.collectAsState()
    val dialogoState by viewModel.uiStateDialogo.collectAsState()

    val context = LocalContext.current
    val listaCategorias = context.resources.getStringArray(R.array.categoria_tarea).toList()

    Scaffold(
        topBar = {
            AppBar(
                tituloPantallaActual = stringResource(titulo_lista_tarea),
                puedeNavegarAtras = false,
                navegaAtras = {}
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onClickNueva) {
                Icon(Icons.Filled.Add, contentDescription = "Nueva Tarea")
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            items(uiState.listaTareas) { tarea ->
                ItemCard(
                    tarea = tarea,
                    listaCategorias = listaCategorias,
                    onItemModificarClick = onItemModificarClick,
                    onClickBorrar = { viewModel.onMostrarDialogoBorrar(tarea) }
                )
            }
        }
    }
    if (dialogoState.mostrarDialogoBorrar) {
        DialogoDeConfirmacion(
            onDismissRequest = {

                viewModel.onCancelarDialogo()
            },
            onConfirmation = {

                viewModel.onAceptarDialogo()
                dialogoState.scope?.launch {
                    dialogoState.snackbarHostState?.showSnackbar(
                        message = "Tarea eliminada correctamente",
                        duration = SnackbarDuration.Short
                    )
                } ?: run {

                    Log.e("DialogoDeConfirmacion", "Scope o SnackbarHostState no está configurado")
                }
            },
            dialogTitle = stringResource(R.string.dialogo_title),
            dialogText = stringResource(R.string.dialogo_context),
            icon = Icons.Default.Info
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ListaTareasScreenPreview() {
    TareasV01Theme {
        ListaTareasScreen(
            onClickNueva = {}
        )
    }
}