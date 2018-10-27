package huszti.martin.deadline.data

import android.os.AsyncTask

class DeleteTaskAsync(
        val item: Task?,
        val database: TaskDatabase) : AsyncTask<Void, Void, Unit>() {

    override fun doInBackground(vararg params: Void?) {
        database.TaskDao().deleteItem(item)

    }

}