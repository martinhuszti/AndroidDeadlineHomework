package huszti.martin.deadline.data

import android.arch.persistence.room.*


@Dao
interface TaskDao {

    @Query("SELECT * FROM taskitem")
    fun getAll(): List<Task>

    @Insert
    fun insert(task: Task): Long

    @Update
    fun update(task: Task)

    @Delete
    fun deleteItem(task: Task?)
}