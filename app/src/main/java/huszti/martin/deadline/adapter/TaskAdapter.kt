
package huszti.martin.deadline.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import huszti.martin.deadline.R
import huszti.martin.deadline.data.Task
import kotlinx.android.synthetic.main.item_task_list.*


class TaskAdapter(private val listener: taskItemClickListener) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    private var items: ArrayList <Task> = ArrayList ()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val itemView = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_task_list, parent, false)

        return TaskViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val item = items[position]
        holder.remainingDays.text=item.dueDate
    }

    override fun getItemCount(): Int {
        return items.size
    }

    interface taskItemClickListener {
        fun onItemChanged(item: Task)
    }

    fun addItem (item : Task){
        items.add(item)
        notifyDataSetChanged()
    }

    fun update(taskItems: List<Task>) {
        items.clear()
        items.addAll(taskItems)
        notifyDataSetChanged()
    }

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val remainingDays = itemView.findViewById<TextView>(R.id.RemainingDaysTextView)

    }
}