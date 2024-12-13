package net.iessochoa.dennisperezortiz.tareasv01.ui.screens.listatareas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import net.iessochoa.dennisperezortiz.tareasv01.data.db.entities.Tarea
import net.iessochoa.dennisperezortiz.tareasv01.data.repository.Repository

class ListaTareasViewModel() : ViewModel() {

    val listaTareasUiState: StateFlow<ListaUiState> =
        Repository.getAllTareas().map { ListaUiState(it ?: listOf()) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = ListaUiState()
            )

    fun delTarea(tarea: Tarea) {
        viewModelScope.launch(Dispatchers.IO) {
            Repository.delTarea(tarea)
        }
    }

}