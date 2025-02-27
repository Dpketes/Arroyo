package net.iessochoa.dennisperezortiz.tareasv01.ui.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import net.iessochoa.dennisperezortiz.tareasv01.ui.screens.listatareas.ListaTareasScreen
import net.iessochoa.dennisperezortiz.tareasv01.ui.screens.tarea.ImagenScreen
import net.iessochoa.dennisperezortiz.tareasv01.ui.screens.tarea.TareaScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = ListaTareasDestination

    ){

        composable<ListaTareasDestination>{
            ListaTareasScreen(
                onClickNueva = {
                    navController.navigate(TareaDestination())
                },
                onItemModificarClick = { posTarea: Long ->
                    navController.navigate(TareaDestination(posTarea))
                }
            )
        }
        composable<TareaDestination> { backStackEntry ->
            val tarea: TareaDestination = backStackEntry.toRoute()
            TareaScreen(
                idTarea = tarea.posTarea,
                onVolver = { navController.navigateUp() },
                navController = navController
            )
        }

        composable("image_screen/{uriImagen}") { backStackEntry ->
            val uriImagenEncoded = backStackEntry.arguments?.getString("uriImagen") ?: ""
            val uriImagen = Uri.decode(uriImagenEncoded)
            ImagenScreen(
                uriImagen = uriImagen,
                onVolver = { navController.navigateUp() },
                navController = navController
            )
        }
    }
}