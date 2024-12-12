package net.iessochoa.dennisperezortiz.tareasv01.data.repository

import net.iessochoa.dennisperezortiz.tareasv01.TareasApplication
import net.iessochoa.dennisperezortiz.tareasv01.data.db.dao.TareasDao
import net.iessochoa.dennisperezortiz.tareasv01.data.db.database.TareasDataBase
import net.iessochoa.dennisperezortiz.tareasv01.data.db.entities.Tarea

object Repository {
    //modelo de datos
    private lateinit var modelTareas: TareasDao
    //inicio del objeto singleton
    operator fun invoke() {

        //iniciamos el modelo con la base de datos
        modelTareas = TareasDataBase
            .getDatabase(TareasApplication.application.applicationContext)
            .tareasDao()
    }
    //Métodos CRUD a la base de datos
    suspend fun addTarea(tarea: Tarea)= modelTareas.addTarea(tarea)
    suspend fun delTarea(tarea: Tarea)= modelTareas.delTarea(tarea)
    suspend fun getTarea(id:Long)= modelTareas.getTarea(id)
    fun getAllTareas()= modelTareas.getTareas()
}

