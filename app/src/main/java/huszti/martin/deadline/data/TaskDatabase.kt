package huszti.martin.deadline.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters


@Database(entities = [Task::class], version = 1, exportSchema = false)

@TypeConverters(TaskEnumConverters::class)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun TaskDao(): TaskDao
}