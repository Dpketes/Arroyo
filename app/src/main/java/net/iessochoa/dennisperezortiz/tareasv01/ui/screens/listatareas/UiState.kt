package net.iessochoa.dennisperezortiz.tareasv01.ui.screens.listatareas

import androidx.compose.material3.SnackbarHostState
import kotlinx.coroutines.CoroutineScope
import net.iessochoa.dennisperezortiz.tareasv01.data.db.entities.Tarea

data class ListaUiState(
    val listaTareas: List<Tarea> = listOf(),
)

data class UiStateDialogo(
    val mostrarDialogoBorrar: Boolean = false,
    val tareaBorrar: Tarea? = null,
    val scope: CoroutineScope? = null,
    val snackbarHostState: SnackbarHostState? = null
) {

}