package net.iessochoa.dennisperezortiz.tareasv01.ui.screens.tarea

import android.app.Application
import androidx.compose.material3.SnackbarHostState
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import net.iessochoa.dennisperezortiz.tareasv01.R
import net.iessochoa.dennisperezortiz.tareasv01.data.db.entities.Tarea
import net.iessochoa.dennisperezortiz.tareasv01.data.repository.Repository
import net.iessochoa.dennisperezortiz.tareasv01.ui.theme.ColorPrioridadAlta

//clase ViewModel donde definimos las acciones que van a realizar los objetos de tarea.
class TareaViewModel(application: Application) : AndroidViewModel(application) {

    private val context = application.applicationContext

    var cargado: Boolean = false

    val listaPrioridad = context.resources.getStringArray(R.array.prioridad_tarea).toList()

    val listaCategoria = context.resources.getStringArray(R.array.categoria_tarea).toList()

    private val PRIORIDAD_ALTA = listaPrioridad[2]

    val listaEstado = context.resources.getStringArray(R.array.estado_tarea).toList()

    var tarea: Tarea? = null
    var datosCargados = false

    private val _uiStateTarea = MutableStateFlow(
        UiStateTarea(
            prioridad = listaPrioridad[0],
            categoria = listaCategoria[0],
            pagado = false,
            estado = listaEstado[0],
            valoracion = 0,
            tecnico = "",
            descripcion = "",
            esFormularioValido = false,
            mostrarDialogo = false,
            snackbarHostState = SnackbarHostState(),
            scope = viewModelScope
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

    fun onCategoriaChange(nuevaCategoria: String) {
        _uiStateTarea.value = _uiStateTarea.value.copy(categoria = nuevaCategoria)
    }

    fun onPagadoChange(isPagado: Boolean) {
        _uiStateTarea.value = _uiStateTarea.value.copy(pagado = isPagado)
    }

    fun onEstadoChange(nuevoEstado: String) {
        _uiStateTarea.value = _uiStateTarea.value.copy(estado = nuevoEstado)
    }

    fun onValoracionChange(nuevaValoracion: Int) {
        _uiStateTarea.value = _uiStateTarea.value.copy(valoracion = nuevaValoracion)
    }

    fun onTecnicoChange(tecnico: String) {
        _uiStateTarea.value = _uiStateTarea.value.copy(
            tecnico = tecnico,
            esFormularioValido = tecnico.isNotBlank() && _uiStateTarea.value.tecnico.isNotBlank()
        )
    }

    fun onDescripcionChange(descripcion: String) {
        _uiStateTarea.value = _uiStateTarea.value.copy(
            descripcion = descripcion,
            esFormularioValido = descripcion.isNotBlank() && uiStateTarea.value.descripcion.isNotBlank()
        )
    }

    fun onGuardar() {
        _uiStateTarea.value = _uiStateTarea.value.copy(
            mostrarDialogo = true
        )
    }

    fun onConfirmarDialogoGuardar() {
        //cierra el dialogo
        _uiStateTarea.value = _uiStateTarea.value.copy(
            mostrarDialogo = false
        )
        //lanzamos la corrutina para guardar la tarea
        viewModelScope.launch(Dispatchers.IO) {
            Repository.addTarea(uiStateToTarea())
        }

    }

    fun onCancelarDialogoGuardar() {
        _uiStateTarea.value = _uiStateTarea.value.copy(
            mostrarDialogo = false
        )
    }

    /**
     *Carga los datos de la tarea en UiState,
     * que a su vez actualiza la interfaz de usuario *
     */
    fun tareaToUiState(tarea: Tarea) {
        _uiStateTarea.value = _uiStateTarea.value.copy(
            categoria = listaCategoria[tarea.categoria],
            prioridad = listaPrioridad[tarea.prioridad],
            pagado = tarea.pagado,
            estado = listaEstado[tarea.estado],
            valoracion = tarea.valoracionCliente,
            tecnico = tarea.tecnico,
            descripcion = tarea.descripcion,
            esFormularioValido = tarea.tecnico.isNotBlank() &&
                    tarea.descripcion.isNotBlank(),
            esTareaNueva = false,
            colorFondo = if (PRIORIDAD_ALTA == listaPrioridad[tarea.prioridad])
                ColorPrioridadAlta else Color.Transparent
        )
    }

    /**
     * Extrae los datos de la interfaz de usuario y los convierte en un objeto Tarea.
     */
    fun uiStateToTarea(): Tarea {
        return if (uiStateTarea.value.esTareaNueva)
        //si es nueva, le asigna un id
            Tarea(
                categoria = listaCategoria.indexOf(uiStateTarea.value.categoria),
                prioridad = listaPrioridad.indexOf(uiStateTarea.value.prioridad),
                img = R.drawable.foto3.toString(),
                pagado = uiStateTarea.value.pagado,
                estado = listaEstado.indexOf(uiStateTarea.value.estado),
                valoracionCliente = uiStateTarea.value.valoracion,
                tecnico = uiStateTarea.value.tecnico,
                descripcion = uiStateTarea.value.descripcion
            ) //si no es nueva, actualiza la tarea
        else Tarea(
            tarea!!.id,
            categoria = listaCategoria.indexOf(uiStateTarea.value.categoria),
            prioridad = listaPrioridad.indexOf(uiStateTarea.value.prioridad),
            img = tarea!!.img,
            pagado = uiStateTarea.value.pagado,
            estado = listaEstado.indexOf(uiStateTarea.value.estado),
            valoracionCliente = uiStateTarea.value.valoracion,
            tecnico = uiStateTarea.value.tecnico,
            descripcion = uiStateTarea.value.descripcion
        )
    }

    fun getTarea(id: Long) {
        //lanzamos una corrutina que nos devuelve la tarea de la bd

        viewModelScope.launch(Dispatchers.IO) {
            tarea = Repository.getTarea(id)
            if (tarea != null) tareaToUiState(tarea!!)
        }
    }


}