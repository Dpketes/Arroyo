package net.iessochoa.dennisperezortiz.tareasv01.ui.screens.listatareas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import net.iessochoa.dennisperezortiz.tareasv01.data.db.entities.Tarea
import net.iessochoa.dennisperezortiz.tareasv01.data.repository.Repository

class ListaTareasViewModel() : ViewModel() {


    //Invoco al UiStateDialogo que se encuentra dentro de ListaUiState.
    private val _uiStateDialogo = MutableStateFlow(UiStateDialogo())

    val uiStateDialogo: StateFlow<UiStateDialogo> = _uiStateDialogo.asStateFlow()

    val listaTareasUiState: StateFlow<ListaUiState> =
        Repository.getAllTareas().map { ListaUiState(it ?: listOf()) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = ListaUiState()
            )

    //He creado todas las funciones para usar dialogos, aparte de conectar el delTarea con aceptar para que borre la tarea al clickar ok en el dialogo.

    fun onMostrarDialogoBorrar(tarea: Tarea) {
        _uiStateDialogo.value = UiStateDialogo(mostrarDialogoBorrar = true, tareaBorrar = tarea)
    }

    fun onCancelarDialogo() {
        _uiStateDialogo.value = UiStateDialogo()
    }

    fun onAceptarDialogo() {
        _uiStateDialogo.value.tareaBorrar?.let {
            delTarea(it)
        }
        onCancelarDialogo()
    }

    fun delTarea(tarea: Tarea) {
        viewModelScope.launch(Dispatchers.IO) {
            Repository.delTarea(tarea)
        }
    }

}