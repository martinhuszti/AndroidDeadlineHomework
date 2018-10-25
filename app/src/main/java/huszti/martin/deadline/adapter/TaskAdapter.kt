
import android.support.v7.widget.RecyclerView

import android.view.*
import android.widget.*
import huszti.martin.deadline.R
import huszti.martin.deadline.data.Task

class TaskAdapter(private val listener: taskItemClickListener) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    private var items: ArrayList <Task> = ArrayList ()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val itemView = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_task_list, parent, false)

        return TaskViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val item = items.get(position);
        holder.nameTextView.setText(item.title);
        holder.descriptionTextView.setText(item.description);
        holder.categoryTextView.setText(item.priority.name);

        holder.item = item;
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

    fun update(shoppingItems: List<Task>) {
        items.clear()
        items.addAll(shoppingItems)
        notifyDataSetChanged()
    }

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var iconImageView: ImageView
        var nameTextView: TextView
        var descriptionTextView: TextView
        var categoryTextView: TextView
        var priceTextView: TextView
        var isBoughtCheckBox: CheckBox
        var removeButton: ImageButton

        var item: Task? = null

        init {
            iconImageView = itemView.findViewById(R.id.ShoppingItemIconImageView)
            nameTextView = itemView.findViewById(R.id.ShoppingItemNameTextView)
            descriptionTextView = itemView.findViewById(R.id.ShoppingItemDescriptionTextView)
            categoryTextView = itemView.findViewById(R.id.ShoppingItemCategoryTextView)
            priceTextView = itemView.findViewById(R.id.ShoppingItemPriceTextView)
            isBoughtCheckBox = itemView.findViewById(R.id.ShoppingItemIsBoughtCheckBox)
            removeButton = itemView.findViewById(R.id.ShoppingItemRemoveButton)

        }
    }
}