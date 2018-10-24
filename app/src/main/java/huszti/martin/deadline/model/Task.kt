package huszti.martin.deadline.model

import android.renderscript.RenderScript

class Task(
        var title: String,
        var priority: Priority,
        var dueDate: String,
        var description: String
) {

    enum class Priority {
        LOW, MEDIUM, HIGH
    }

}
