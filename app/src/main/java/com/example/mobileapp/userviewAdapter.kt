package com.example.mobileapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class userviewAdapter(val context: Context, private val pd:List<product_data>) : RecyclerView.Adapter<user_view_holder>() {
    private lateinit var  mListener:onItemClickListener
    interface  onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener:onItemClickListener){
        mListener = listener
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): user_view_holder {

        val itemView =LayoutInflater.from(parent.context).inflate(R.layout.userviewlayout,parent,false)
        return user_view_holder(
            itemView,mListener
        )

    }

    override fun getItemCount(): Int {
        return pd.size
    }

    override fun onBindViewHolder(holder: user_view_holder, position: Int) {
        val product = pd[position]
        holder.itemName.text = product.itemName
        holder.price.text = product.Price

        Glide.with(context).load(product.itemImg).into(holder.image)
    }

}

class user_view_holder(itemView: View, listener:userviewAdapter.onItemClickListener):RecyclerView.ViewHolder(itemView){

    val itemName = itemView.findViewById<TextView>(R.id.tvpname)
    val price = itemView.findViewById<TextView>(R.id.tvprice)
    val image = itemView.findViewById<ImageView>(R.id.imgID)

    init{
        itemView.setOnClickListener{
            listener.onItemClick(adapterPosition)
        }

    }


}