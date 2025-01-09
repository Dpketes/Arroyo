package net.iessochoa.dennisperezortiz.tareasv01.ui.screens.listatareas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import net.iessochoa.dennisperezortiz.tareasv01.data.db.entities.Tarea
import net.iessochoa.dennisperezortiz.tareasv01.data.repository.Repository

class ListaTareasViewModel() : ViewModel() {

    private val _filtroEstado = MutableStateFlow("Todas")
    private val _filtroSinPagar = MutableStateFlow(false)

    val filtroEstado: StateFlow<String> get() = _filtroEstado
    val filtroSinPagar: StateFlow<Boolean> get() = _filtroSinPagar

    val listaTareasUiState: StateFlow<ListaUiState> =
        _filtroEstado
            .flatMapLatest { estado ->
                _filtroSinPagar.flatMapLatest { sinPagar ->
                    when {
                        estado == "Todas" && !sinPagar -> Repository.getAllTareas()
                        estado == "Todas" && sinPagar -> Repository.getTareasSinPagar()
                        estado != "Todas" && !sinPagar -> {
                            val estadoIndex = getEstadoIndex(estado)
                            Repository.getTareasByEstado(estadoIndex)
                        }
                        else -> {
                            val estadoIndex = getEstadoIndex(estado)
                            Repository.getTareasByEstadoYNoPagadas(estadoIndex)
                        }
                    }
                }
            }
            .map { ListaUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = ListaUiState()
            )

    //Invoco al UiStateDialogo que se encuentra dentro de ListaUiState.
    private val _uiStateDialogo = MutableStateFlow(UiStateDialogo())

    val uiStateDialogo: StateFlow<UiStateDialogo> = _uiStateDialogo.asStateFlow()

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

    fun onFiltroEstadoChanged(nuevoEstado: String) {
        _filtroEstado.value = nuevoEstado
    }

    fun onFiltroSinPagarChanged(sinPagar: Boolean) {
        _filtroSinPagar.value = sinPagar
    }

    private fun getEstadoIndex(estado: String): Int {
        val estados = listOf("Abiertas", "En Curso", "Cerradas", "Todas")
        return estados.indexOf(estado).takeIf { it != -1 } ?: 3
    }

}