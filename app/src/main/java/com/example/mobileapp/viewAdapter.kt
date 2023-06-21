package com.example.mobileapp

import android.content.Context
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class viewAdapter(val context: Context, private val pd:List<product_data>) : RecyclerView.Adapter<product_view_holder>() {
    private lateinit var  mListener:onItemClickListener
    interface  onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener:onItemClickListener){
        mListener = listener
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): product_view_holder {

        val itemView =LayoutInflater.from(parent.context).inflate(R.layout.viewpd,parent,false)
        return product_view_holder(
            itemView,mListener
        )

    }

    override fun getItemCount(): Int {
        return pd.size
    }

    override fun onBindViewHolder(holder: product_view_holder, position: Int) {
        val product = pd[position]
        holder.itemName.text = product.itemName
        holder.price.text = product.Price

        Glide.with(context).load(product.itemImg).into(holder.image)
    }

}

class product_view_holder(itemView: View, listener:viewAdapter.onItemClickListener):RecyclerView.ViewHolder(itemView){

    val itemName = itemView.findViewById<TextView>(R.id.productname)
    val price = itemView.findViewById<TextView>(R.id.pdprice)
    val image = itemView.findViewById<ImageView>(R.id.imageView3)

    init{
        itemView.setOnClickListener{
            listener.onItemClick(adapterPosition)
        }

    }


}