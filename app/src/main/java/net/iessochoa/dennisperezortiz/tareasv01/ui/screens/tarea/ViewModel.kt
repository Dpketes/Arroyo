package net.iessochoa.dennisperezortiz.tareasv01.ui.screens.tarea

import android.app.Application
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import net.iessochoa.dennisperezortiz.tareasv01.R
import net.iessochoa.dennisperezortiz.tareasv01.ui.theme.ColorPrioridadAlta

class TareaViewModel(application: Application) : AndroidViewModel(application) {

    private val context = application.applicationContext

    val listaPrioridad = context.resources.getStringArray(R.array.prioridad_tarea).toList()

    private val PRIORIDAD_ALTA = listaPrioridad[2]

    private val _uiStateTarea = MutableStateFlow(
        UiStateTarea(
            prioridad = listaPrioridad[0]
        )
    )
    val uiStateTarea: StateFlow<UiStateTarea> = _uiStateTarea.asStateFlow()

    fun onValueChangePrioridad(nuevaPrioridad: String) {
        val colorFondo: Color = if (PRIORIDAD_ALTA == nuevaPrioridad) {
            ColorPrioridadAlta
        } else {
            Color.Transparent
        }

        _uiStateTarea.value = _uiStateTarea.value.copy(
            prioridad = nuevaPrioridad,
            colorFondo = colorFondo
        )
    }
}
