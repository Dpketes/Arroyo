package net.iessochoa.dennisperezortiz.tareasv01.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

//Creo la función de RadioButton ademas de insertarlo en un row y añadirle mas modificaciones de estilo para que se vea como la imagen del ejercicio
@Composable
fun BasicRadioButton(
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
    options: Array<String>
) {
    Row {
        options.forEach { option ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 8.dp)
            ) {
                RadioButton(
                    selected = selectedOption == option,
                    onClick = { onOptionSelected(option) }
                )
                Text(text = option)
            }
        }
    }
}

