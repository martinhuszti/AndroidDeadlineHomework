package huszti.martin.deadline.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import huszti.martin.deadline.R
import huszti.martin.deadline.data.Task


class TaskAdapter(private val listener: taskItemClickListener) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    private var items: ArrayList<Task> = ArrayList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val itemView = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_task_list, parent, false)

        return TaskViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val item = items[position]
        holder.title.text = item.title
        holder.remainingDays.text = "12"
        holder.dueDate.text = item.dueDate
        holder.item=item

    }

    override fun getItemCount(): Int {
        return items.size
    }


    fun addItem(item: Task) {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }


    fun deleteItem(item: Task?) {
        items.remove(item)
        notifyDataSetChanged()
    }


    fun update(taskItems: List<Task>) {
        items.clear()
        items.addAll(taskItems)
        notifyDataSetChanged()
    }

    interface taskItemClickListener {
        fun onItemChanged(item: Task)
        fun onItemDeleted(item: Task?)
    }



    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val remainingDays = itemView.findViewById<TextView>(R.id.RemainingDaysTextView)!!
        val title = itemView.findViewById<TextView>(R.id.TaskTitleTextView)!!
        val dueDate = itemView.findViewById<TextView>(R.id.DueDateTextView)!!
        val completedButton = itemView.findViewById<ImageButton>(R.id.TaskCompletedButton)!!
        var item: Task? = null
        init {
            completedButton.setOnClickListener {
                deleteItem(item)
                listener.onItemDeleted(item)
            }
        }



    }
}