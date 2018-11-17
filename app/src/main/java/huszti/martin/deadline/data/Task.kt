package huszti.martin.deadline.data

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverter
import org.joda.time.DateTime
import org.joda.time.Duration


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
    var dueDate: DateTime = DateTime()


    var remanindays: Int = 0

    fun calculateRemainingDays(dateSelected: DateTime): Int {
        return Duration(DateTime(), dateSelected).standardDays.toInt()
    }

}

class TaskEnumConverters {

    @TypeConverter
    fun getByOrdinal(str: String): DateTime {
        return DateTime(str)
    }

    @TypeConverter
    fun toString(dt: DateTime): String {
        return dt.toString()
    }

}








