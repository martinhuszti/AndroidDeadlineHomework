package huszti.martin.deadline.data

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverter

@Entity(tableName = "shoppingitem")
class Task(
        @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) val ID: Int,
        @ColumnInfo(name = "title") var title: String,
        @ColumnInfo(name = "priority")var priority: Priority,
        @ColumnInfo(name = "dueDate") var dueDate: String,
        @ColumnInfo(name = "description") var description: String

) {

    enum class Priority {
        LOW, MEDIUM, HIGH;


    }
}

class TaskEnumConverters {

        @TypeConverter
        fun getByOrdinal(ordinal: Int): Task.Priority? {
            var ret: Task.Priority? = null

            for (pri in Task.Priority.values()) {
                if (pri.ordinal == ordinal)
                    ret = pri
                break
            }
            return ret
        }

        @TypeConverter
        fun toInt(priority: Task.Priority): Int {
            return priority.ordinal
        }

}






