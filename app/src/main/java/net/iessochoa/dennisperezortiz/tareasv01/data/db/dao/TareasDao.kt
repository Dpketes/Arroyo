package net.iessochoa.dennisperezortiz.tareasv01.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import net.iessochoa.dennisperezortiz.tareasv01.data.db.entities.Tarea

@Dao
interface TareasDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTarea(tarea: Tarea)
    @Delete
    suspend fun delTarea(tarea: Tarea)

    @Query("SELECT * FROM tareas")
    fun getTareas(): Flow<List<Tarea>>
    @Query("SELECT * FROM tareas WHERE id = :id")
    suspend fun getTarea(id: Long): Tarea
    @Query("SELECT * FROM tareas WHERE estado = :estado")
    fun getTareasByEstado(estado: Int): Flow<List<Tarea>>

    @Query("SELECT * FROM tareas WHERE pagado = 0")
    fun getTareasSinPagar(): Flow<List<Tarea>>

    @Query("SELECT * FROM tareas WHERE estado = :estado AND pagado = 0")
    fun getTareasByEstadoYNoPagadas(estado: Int): Flow<List<Tarea>>
}