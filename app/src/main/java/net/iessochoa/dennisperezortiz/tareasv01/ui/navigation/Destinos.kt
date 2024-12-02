package net.iessochoa.dennisperezortiz.tareasv01.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
object ListaTareasDestination

@Serializable
data class TareaDestination(
    val posTarea: Long ?= null
)


