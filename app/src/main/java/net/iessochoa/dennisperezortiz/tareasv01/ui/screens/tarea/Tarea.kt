package net.iessochoa.dennisperezortiz.tareasv01.ui.screens.tarea

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import kotlinx.coroutines.launch
import net.iessochoa.dennisperezortiz.tareasv01.R
import net.iessochoa.dennisperezortiz.tareasv01.ui.components.AppBar
import net.iessochoa.dennisperezortiz.tareasv01.ui.components.BasicRadioButton
import net.iessochoa.dennisperezortiz.tareasv01.ui.components.DialogoDeConfirmacion
import net.iessochoa.dennisperezortiz.tareasv01.ui.components.DropdownMenu
import net.iessochoa.dennisperezortiz.tareasv01.ui.components.RatingBar

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun TareaScreen(
    idTarea: Long? = null,
    onVolver: () -> Unit = {},
    viewModel: TareaViewModel = viewModel(),
    modifier: Modifier = Modifier
) {

    
//creamos el estado de uiStateTarea, el estado de SnackBar y el de CoroutineScope. Aparte de comentar todos los demas que son inutiles al usar UiState

    idTarea?.let { if(!viewModel.cargado){
        viewModel.getTarea(it)
        viewModel.cargado = true
    } }

    val uiStateTarea by viewModel.uiStateTarea.collectAsState()

    //Aqui almacenamos el estado actual de los campos
    //var categoriaSeleccionada by remember { mutableStateOf("") }
    //
    //var isPagado by remember { mutableStateOf(false) }
    //val estadoTarea = remember { mutableStateOf("") }
    //var valoracionCliente by remember { mutableIntStateOf(0) }
    //var tecnico by remember { mutableStateOf("") }
    //var descripcion by remember { mutableStateOf("") }
    val context = LocalContext.current
    //val categorias = context.resources.getStringArray(R.array.categoria_tarea)
    //val prioridades = context.resources.getStringArray(R.array.prioridad_tarea)
    val estadosTarea = context.resources.getStringArray(R.array.estado_tarea)
    //val colorFondo = if (prioridadSeleccionada == prioridades[2]) ColorPrioridadAlta else Color.Transparent

    //Como dices que hay usar strings.xml para lo que sea texto, me he creado mis valores, ya que no me dejaba hacerlo dentro del message directamente.
    val mensajeFaltaCampos = stringResource(R.string.message_falta_campos)
    val tareaGuardada = stringResource(R.string.tarea_guardada)

    /*
Permisos:
 Petición de permisos múltiples condicionales según la versión de Android
*/
    val permissionState = rememberMultiplePermissionsState(
        permissions = mutableListOf(//permiso para hacer fotos
            android.Manifest.permission.CAMERA
        ).apply {//Permisos para la galería
            //Si es Android menor de 10
            if (android.os.Build.VERSION.SDK_INT <= android.os.Build.VERSION_CODES.P) {
                add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                add(android.Manifest.permission.READ_EXTERNAL_STORAGE)
            }//si es Android igual o superior a 13
            if (android.os.Build.VERSION.SDK_INT >=
                android.os.Build.VERSION_CODES.TIRAMISU) {
                add(android.Manifest.permission.READ_MEDIA_IMAGES)
            }//si es Android igual o superior a 14. Este permiso no lo tengo claro si es necesario
            //podéis probar en el dispositivo real si tenéis Android 14
            /*if (android.os.Build.VERSION.SDK_INT >=
           android.os.Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            add(android.Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED)
            }*/
        }
    )

    //Inicializamos el esqueleto de la aplicación con scaffold, donde importaremos varias 4 cosas de el archivo components, como los dropdowns/selects, el radiobutton con las tres opciones definidas en strings.xml y el ratingBar
    Scaffold(
        containerColor = uiStateTarea.colorFondo,
        snackbarHost = { SnackbarHost(hostState = uiStateTarea.snackbarHostState) },
        topBar = {
            AppBar(
                tituloPantallaActual = if (idTarea == null) stringResource(R.string.titulo_pantalla_nueva) else stringResource(R.string.titulo_pantalla, idTarea),
                puedeNavegarAtras = true,
                navegaAtras = onVolver
            )
        },
        //Creamos el floatingbutton como explica la tarea difiniendo sus acciones y haciendo uso de el uistate creado si esta bien o un mensaje snackbar si falta por rellenar tecnico y/o descripción.
        floatingActionButton = {
            FloatingActionButton(onClick = {
                if (uiStateTarea.esFormularioValido) {
                    viewModel.onGuardar()

                } else {
                    uiStateTarea.scope.launch {
                        uiStateTarea.snackbarHostState.showSnackbar(
                            message = mensajeFaltaCampos,
                            duration = SnackbarDuration.Short
                        )
                    }
                }
            }
            ) {
                Icon(
                    painter = painterResource(android.R.drawable.ic_menu_save),
                    contentDescription = "guardar"
                )
            }
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState()),
            ) {
                //Inicio un row que contendra una columna con los selects y una imagen, aparte he usado padding para darle separacion con los bordes de la pantalla y despues entre medio de los 2 row he metido un Spacer para dar espacio tambien entre los items dentro del row.
                Row(
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                    ) {
                    Column(modifier = Modifier.weight(1f)) {
                        DropdownMenu(viewModel.listaCategoria, stringResource(R.string.categoria), onSelectionChanged = { viewModel.onCategoriaChange(it) }, selectedValue = uiStateTarea.categoria)
                        DropdownMenu(viewModel.listaPrioridad, stringResource(R.string.prioridad), onSelectionChanged = { viewModel.onValueChangePrioridad(it)}, selectedValue = uiStateTarea.prioridad)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    AsyncImage(
                        model = R.drawable.foto_tarea,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .aspectRatio(2f)
                            .clip(RoundedCornerShape(16.dp)),
                        contentScale = ContentScale.Crop
                    )
                }

                //Aqui iniciamos otro row que contendrá la funcionalidad del switch que basicamente es el cambio entre los iconos, segun este switcheado o no, el texto importado de stringresourcdes y el switchbutton
                Row(modifier = Modifier.padding(8.dp, 0.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(painter = if (uiStateTarea.pagado) painterResource(R.drawable.ic_pagado) else painterResource(R.drawable.ic_no_pagado), contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(stringResource(R.string.pagado))
                    Spacer(modifier = Modifier.width(8.dp))
                    Switch(checked = uiStateTarea.pagado, onCheckedChange = { viewModel.onPagadoChange(it)})

                    IconButton(
                        onClick = {
                            if (!permissionState.allPermissionsGranted)
                                permissionState.launchMultiplePermissionRequest()
                        }) {
                        Icon(painterResource(R.drawable.ic_image_search),
                            contentDescription = "abrir galeria"
                        )
                    }
                    IconButton(
                        onClick = {
                            if (!permissionState.allPermissionsGranted)
                                permissionState.launchMultiplePermissionRequest()
                        }) {
                        Icon(painterResource(R.drawable.ic_camera),
                            contentDescription = "abrir galeria"
                        )
                    }
                }

                //Aqui veremos segun seleccionemos en el radiobutton un icono u otro
                Row(modifier = Modifier.padding(8.dp, 0.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text(text = stringResource(R.string.estado_tarea_titulo))
                    Spacer(modifier = Modifier.width(8.dp))

                    uiStateTarea.estado.let { estado ->
                        val iconId = when (estado) {
                            "Abierta" -> R.drawable.ic_abierto
                            "En curso" -> R.drawable.ic_en_curso
                            "Cerrada" -> R.drawable.ic_cerrado
                            else -> null
                        }

                        iconId?.let { id ->
                            Icon(
                                painter = painterResource(id = id),
                                contentDescription = estado
                            )
                        }
                    }
                }

                BasicRadioButton(selectedOption = uiStateTarea.estado, onOptionSelected = { viewModel.onEstadoChange(it)}, options = estadosTarea)

                //El rating bar no he sabido darle menos espacio entre los iconos o mejor dicho imagenes vectoriales.
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = stringResource(R.string.valoracion_cliente))
                    RatingBar(rating = uiStateTarea.valoracion, onRatingChanged = { viewModel.onValoracionChange(it) })
                }

                OutlinedTextField(
                    value = uiStateTarea.tecnico,
                    onValueChange = { viewModel.onTecnicoChange(it) },
                    label = { Text(stringResource(R.string.tecnico)) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
                )
                //Como dijiste en clase he metido la descripcion en un box para que se extienda al escribir, o eso tenia entendido
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)) {
                    OutlinedTextField(
                        value = uiStateTarea.descripcion,
                        onValueChange = { viewModel.onDescripcionChange(it) },
                        label = { Text(stringResource(R.string.descripcion)) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .verticalScroll(rememberScrollState()),
                        keyboardOptions = KeyboardOptions.Default.copy(),
                        singleLine = false
                    )
                }
                //Como dice la tarea hacemos un if de los dialogos para definir su uso y cierre.
                if (uiStateTarea.mostrarDialogo) {
                    DialogoDeConfirmacion(
                        onDismissRequest = {
                            //cancela el dialogo
                            viewModel.onCancelarDialogoGuardar()
                        },
                        onConfirmation = {
                            //guardaría los cambios
                            viewModel.onConfirmarDialogoGuardar()
                            uiStateTarea.scope.launch {
                                uiStateTarea.snackbarHostState.showSnackbar(
                                    message = tareaGuardada,
                                    duration = SnackbarDuration.Short
                                )
                            }
                            onVolver()
                        },
                        dialogTitle = stringResource(R.string.dialogo_title),
                        dialogText = stringResource(R.string.dialogo_context),
                        icon = Icons.Default.Info
                    )

                }
            }
        }
    )
}