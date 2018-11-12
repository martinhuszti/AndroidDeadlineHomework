package huszti.martin.deadline.data

import android.os.AsyncTask
import huszti.martin.deadline.adapter.TaskAdapter

class AddTaskAsync(
        val newTask: Task,
        val database: TaskDatabase,
        val adapter: TaskAdapter) : AsyncTask<Void, Void, Task>() {

    override fun doInBackground(vararg params: Void?): Task {
        newTask.taskId = database.TaskDao().insert(newTask)
        return newTask
    }

    override fun onPostExecute(task: Task) {
        adapter.addItem(task)
    }
}