package net.iessochoa.dennisperezortiz.tareasv01.ui.screens.tarea

import androidx.compose.ui.graphics.Color

//Este es el UiState, para facilitar el manejo y la depuración de los estados, basicamente facilita las actualizaciones reactivas y manteniendo el código claro y organizado.
data class UiStateTarea(
    val categoria: String = "",
    val prioridad: String = "",
    val pagado: Boolean = false,
    val estado: String = "",
    val valoracion: Int = 0,
    val tecnico: String = "",
    val descripcion: String = "",
    val colorFondo: Color = Color.Transparent,
    val esFormularioValido: Boolean = false,
    val mostrarDialogo: Boolean = false,
    val esTareaNueva: Boolean = false
) {
}