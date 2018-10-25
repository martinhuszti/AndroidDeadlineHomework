package huszti.martin.deadline.data

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverter


@Entity(tableName = "shoppingitem")
class Task(
        @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) val ID: Int,
        @ColumnInfo(name = "title") var title: String,
        @ColumnInfo(name = "priority") var priority: Priority,
        @ColumnInfo(name = "dueDate") var dueDate: String,
        @ColumnInfo(name = "description") var description: String

) {

    enum class Priority {
        LOW, MEDIUM, HIGH;

        @TypeConverter
        fun getByOrdinal(ordinal: Int): Priority? {
            var ret: Priority? = null

            for (pri in Priority.values()) {
                if (pri.ordinal == ordinal)
                    ret = pri
                break
            }
            return ret;
        }

        @TypeConverter
        fun toInt(priority: Priority): Int {
            return priority.ordinal
        }
    }
}






