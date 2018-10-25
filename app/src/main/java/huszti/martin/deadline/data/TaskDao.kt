package huszti.martin.deadline.data

import android.arch.persistence.room.*


@Dao
interface TaskDao {
    @get:Query("SELECT * FROM shoppingitem")
    val all: List<Task>

    @Insert
    fun insert(shoppingItems: Task): Long

    @Update
    fun update(shoppingItem: Task)

    @Delete
    fun deleteItem(shoppingItem: Task)
}