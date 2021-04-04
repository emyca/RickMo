package ua.kh.em.rickmo.ui.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ua.kh.em.rickmo.R
import ua.kh.em.rickmo.data.model.Character
import ua.kh.em.rickmo.databinding.ItemMainBinding
import ua.kh.em.rickmo.ui.adapter.MainAdapter.MainViewHolder
import java.util.*


class MainAdapter(
        private var list: ArrayList<Character>
) : RecyclerView.Adapter<MainViewHolder>() {

    private lateinit var binding: ItemMainBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = ItemMainBinding.inflate(inflater, parent, false)
        return MainViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val itemData = list[position]
        Picasso.get()
            .load(itemData.image)
            .error(R.drawable.ic_emoji)
            .placeholder(R.drawable.ic_emoji)
            .fit()
            .into(holder.binding?.itemImage)
        holder.binding?.itemName?.text = itemData.name
        holder.binding?.itemContainer?.setOnClickListener {
            val bundle = Bundle()
            bundle.putParcelable("detail", itemData)
            it.findNavController().navigate(R.id.action_mainFragment_to_detailFragment, bundle)
        }
    }

    class MainViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        var binding: ItemMainBinding? = DataBindingUtil.bind(itemView!!)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun addListData(listData: ArrayList<Character>) {
        list = listData
        notifyDataSetChanged()
    }
}