package huszti.martin.deadline.adapter

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import huszti.martin.deadline.R
import huszti.martin.deadline.data.Task

import kotlinx.android.synthetic.main.item_task_list.view.*
import java.text.SimpleDateFormat
import java.util.*
import org.joda.time.format.DateTimeFormat



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
        holder.remainingDays.text = item.calculateRemaningDays(item.dueDate!!)
                .toString()
        holder.item = item
    }

    @SuppressLint("SimpleDateFormat")
    private fun reconvertDatePicker(d: String): Date {
        val df = SimpleDateFormat("yyyy-MM-dd")
        return df.parse(d)
    }

    override fun getItemCount(): Int {
        return items.size
    }


    fun addItem(item: Task) {
        items.add(item)
        items.sortBy { t -> t.dueDate }
        notifyDataSetChanged()
    }


    fun deleteItem(item: Task?) {
        items.remove(item)
        notifyDataSetChanged()
    }


    fun sortItems (items : ArrayList<Task>){

    }

    fun update(taskItems: List<Task>) {
        items.clear()
        for (item in taskItems)
            items.add(item)
        items.sortBy { t -> t.dueDate }
        notifyDataSetChanged()
    }

    interface taskItemClickListener {
        fun onItemChanged(item: Task)
        fun onItemDeleted(item: Task?)
        fun onItemDetailsClicked(item: Task)
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