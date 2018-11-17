package huszti.martin.deadline.data

import android.os.AsyncTask
import huszti.martin.deadline.adapter.TaskAdapter

class LoadTasksAsync(
        private val database: TaskDatabase,
        private val adapter: TaskAdapter
) : AsyncTask<Unit, Unit, List<Task>>() {
    override fun doInBackground(vararg params: Unit?): List<Task> {
        return database.TaskDao().getAll()
    }

    override fun onPostExecute(result: List<Task>) {
        adapter.update(result)

    }
}