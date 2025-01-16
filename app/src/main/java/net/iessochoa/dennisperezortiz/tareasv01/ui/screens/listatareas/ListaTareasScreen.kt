package net.iessochoa.dennisperezortiz.tareasv01.ui.screens.listatareas

import TopAppBarListas
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import net.iessochoa.dennisperezortiz.tareasv01.R
import net.iessochoa.dennisperezortiz.tareasv01.ui.components.DialogoDeConfirmacion
import net.iessochoa.dennisperezortiz.tareasv01.ui.theme.TareasV01Theme

@Composable
fun ListaTareasScreen(
    viewModel: ListaTareasViewModel = viewModel(),
    onClickNueva: () -> Unit = {},
    onItemModificarClick: (id: Long) -> Unit = {},
    modifier: Modifier = Modifier
) {

    val filtroEstado by viewModel.filtroEstado.collectAsState()
    val filtroSinPagar by viewModel.filtroSinPagar.collectAsState()
    val listaTareas by viewModel.listaTareasUiState.collectAsState()

    val uiState by viewModel.listaTareasUiState.collectAsState()
    val dialogoState by viewModel.uiStateDialogo.collectAsState()

    val context = LocalContext.current
    val listaCategorias = context.resources.getStringArray(R.array.categoria_tarea).toList()

    Scaffold(
        topBar = {
            //Llamamos a la funcion creada TopAppBarListas.
            TopAppBarListas()
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

            Column{

                Row {
                    //He creado un filtro de estados como pide el ejercicio, en el cual aparecerá solo las tareas que esten en el estado marcado en el radiobutton o que muestre todos si esta en el radiobutton general "Todos".
                    val estados = listOf("Abiertas", "En Curso", "Cerradas", "Todas")
                    estados.forEach { estado ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = (filtroEstado == estado),
                                onClick = { viewModel.onFiltroEstadoChanged(estado) }
                            )
                            Text(
                                text = estado,
                                style = TextStyle(fontSize = 12.sp), // Ajusta el tamaño del texto a 12sp
                                modifier = Modifier.padding(start = 4.dp) // Espacio pequeño entre el RadioButton y el texto
                            )
                        }
                    }
                }
                //He creado un filtro de sin pagar como pide el ejercicio, en el cual aparecerá solo las tareas que esten sin pagar al pulsar en el switch.
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Switch(
                        checked = filtroSinPagar,
                        onCheckedChange = { viewModel.onFiltroSinPagarChanged(it) },
                        modifier = Modifier.scale(0.8f)
                    )
                    Text(text = "Sin pagar", modifier = Modifier.padding(start = 8.dp))
                }
            }

            // Lista de Tareas
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
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
    }

    // Dialogo para Confirmar Borrado
    if (dialogoState.mostrarDialogoBorrar) {
        DialogoDeConfirmacion(
            onDismissRequest = { viewModel.onCancelarDialogo() },
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