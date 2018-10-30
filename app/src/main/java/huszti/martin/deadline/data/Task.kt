package huszti.martin.deadline.data

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverter

@Entity(tableName = "taskitem")
class Task {
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    var taskId: Long = 0

    @ColumnInfo(name = "title")
    var title: String = ""

    @ColumnInfo(name = "description")
    var description: String = ""

    @ColumnInfo(name = "dueDate")
    var dueDate: String = ""

    @ColumnInfo(name = "priority") //később lehet hasznos lesz
    var priority: Priority? = Priority.HIGH


    enum class Priority {
        LOW, MEDIUM, HIGH;
    }

    var remanindays: Int = 0
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






