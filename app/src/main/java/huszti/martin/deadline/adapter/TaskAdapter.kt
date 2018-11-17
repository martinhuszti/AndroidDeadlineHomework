package huszti.martin.deadline.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import huszti.martin.deadline.R
import huszti.martin.deadline.data.Task
import kotlinx.android.synthetic.main.item_task_list.view.*
import org.joda.time.format.DateTimeFormat
import java.util.*


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
        val fmt = DateTimeFormat.forPattern("yyyy. MM. dd.")
        holder.dueDate.text = fmt.print(item.dueDate)
        holder.remainingDays.text = item.calculateRemainingDays(item.dueDate)
                .toString()
        holder.item = item
    }


    override fun getItemCount(): Int {
        return items.size
    }


    fun addItem(item: Task) {
        items.add(item)
        items.sortBy { t -> t.dueDate }
        notifyDataSetChanged()
        listener.checkAdapter()
    }


    fun deleteItem(item: Task?) {
        items.remove(item)
        notifyDataSetChanged()
        listener.checkAdapter()
    }


    fun update(taskItems: List<Task>) {
        items.clear()
        items.addAll(taskItems)
        items.sortBy { t -> t.dueDate }
        notifyDataSetChanged()
        listener.checkAdapter()
    }

    fun isEmpty(): Boolean {
        return items.isEmpty()

    }

    interface taskItemClickListener {
        fun onItemDeleted(item: Task?)
        fun onItemDetailsClicked(item: Task)
        fun checkAdapter()
    }


    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val remainingDays = itemView.RemainingDaysTextView
        val title = itemView.TaskTitleTextView
        val dueDate = itemView.DueDateTextView
        val completedButton = itemView.TaskCompletedButton
        val detailsButton = itemView.TaskInfoButton
        var item: Task? = null

        init {
            completedButton.setOnClickListener {
                deleteItem(item)
                listener.onItemDeleted(item)
            }
            detailsButton.setOnClickListener {
                item?.let { item -> listener.onItemDetailsClicked(item) }
            }


        }


    }
}