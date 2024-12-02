package net.iessochoa.dennisperezortiz.tareasv01.ui.screens.listatareas

import net.iessochoa.dennisperezortiz.tareasv01.data.db.entities.Tarea

data class ListaUiState(
    val listaTareas: List<Tarea> = listOf()
)

