package com.kobayashi.refrigeratormanager.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kobayashi.refrigeratormanager.R
import com.kobayashi.refrigeratormanager.entity.Items
import java.text.SimpleDateFormat
import java.util.*

class ItemListAdapter(private val genreMap: MutableMap<Int, Int>, private val itemList: MutableList<Items>): RecyclerView.Adapter<ItemListAdapter.ItemsViewHolder>() {

    lateinit var listener: ItemClickListener
    interface ItemClickListener{
        fun onItemClick(position: Int, item: Items)
    }

    class ItemsViewHolder(itemView : View): RecyclerView.ViewHolder(itemView){
        lateinit var item: Items
        val itemImage: ImageView = itemView.findViewById(R.id.itemImage)
        val itemName: TextView = itemView.findViewById(R.id.itemName)
        val insDate: TextView = itemView.findViewById(R.id.textInsDate)
        val expireDate: TextView = itemView.findViewById(R.id.itemExpireDate)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemsViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.item_list_item, parent,false)
        return ItemsViewHolder(item)
    }

    override fun onBindViewHolder(holder: ItemsViewHolder, position: Int) {
        val item = itemList[position]

        holder.item = item

        holder.itemName.text = item.itemName

        val imageId = checkNotNull(genreMap[item.genreId]){genreMap[0]!!}
        holder.itemImage.setImageResource(imageId)

        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.JAPAN)
        holder.insDate.text = sdf.format(item.insDate!!)
        holder.expireDate.text = sdf.format(item.expireDate!!)

        holder.itemView.setOnClickListener{
            listener.onItemClick(position, item)
        }
    }

    override fun getItemCount(): Int = itemList.size

    fun setOnClickListener(listener: ItemClickListener){
        this.listener = listener
    }
}