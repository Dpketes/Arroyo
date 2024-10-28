package net.iessochoa.dennisperezortiz.tareasv01.ui.screens.tarea

import androidx.compose.foundation.Image
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
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import net.iessochoa.dennisperezortiz.tareasv01.R
import net.iessochoa.dennisperezortiz.tareasv01.ui.components.BasicRadioButton
import net.iessochoa.dennisperezortiz.tareasv01.ui.components.DropdownMenu
import net.iessochoa.dennisperezortiz.tareasv01.ui.components.RatingBar
import net.iessochoa.dennisperezortiz.tareasv01.ui.theme.TareasV01Theme

@Composable
fun TareaScreen(viewModel: TareaViewModel = viewModel(), modifier: Modifier = Modifier) {

    val uiStateTarea by viewModel.uiStateTarea.collectAsState()

    //Aqui almacenamos el estado actual de los campos
    //var categoriaSeleccionada by remember { mutableStateOf("") }
    //var prioridadSeleccionada by remember { mutableStateOf("") }
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
    

    //Inicializamos el esqueleto de la aplicación con scaffold, donde importaremos varias 4 cosas de el archivo components, como los dropdowns/selects, el radiobutton con las tres opciones definidas en strings.xml y el ratingBar
    Scaffold(
        containerColor = uiStateTarea.colorFondo,
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
                        DropdownMenu(viewModel.listaCategoria, stringResource(R.string.categoria), onSelectionChanged = { viewModel.onCategoriaChange(it) })
                        DropdownMenu(viewModel.listaPrioridad, stringResource(R.string.prioridad), onSelectionChanged = { viewModel.onValueChangePrioridad(it)})
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Image(
                        painter = painterResource(id = R.drawable.foto_tarea),
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

                        //He utilizado el let este que aunque no sabia para que servia la propia aplicación para cachear un error de implementación me lo ha metido por medio
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
            }
        }
    )
}

//Inicializo preview para que veamos la aplicacion graficamente mientras la desarrollamos, junto con su import arriba
@Preview(showBackground = true)
@Composable
fun TareaScreenPreview() {
    TareasV01Theme {
        TareaScreen()
    }
}
