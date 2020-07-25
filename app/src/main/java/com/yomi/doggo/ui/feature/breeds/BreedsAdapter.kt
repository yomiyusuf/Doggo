package com.yomi.doggo.ui.feature.breeds

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yomi.doggo.R
import com.yomi.doggo.ui.model.BreedDetail
import com.yomi.doggo.util.capitalizeWords
import com.yomi.doggo.util.getProgressDrawable
import com.yomi.doggo.util.loadImage
import kotlinx.android.synthetic.main.item_breed.view.*

/**
 * Created by Yomi Joseph on 2020-07-22.
 */
class BreedsAdapter(private val breedList: ArrayList<BreedDetail>):
    RecyclerView.Adapter<BreedsAdapter.BreedViewHolder>() {

    fun updateData(newBreedsList: List<BreedDetail>){
        breedList.clear()
        breedList.addAll(newBreedsList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BreedViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_breed, parent, false)
        return BreedViewHolder(
            view
        )
    }

    override fun getItemCount() = breedList.size

    override fun onBindViewHolder(holder: BreedViewHolder, position: Int) {
        val item = breedList[position]
        holder.view.txt_item_breed_name.text = breedList[position].name.capitalizeWords()
        holder.view.image_item_breed.loadImage(item.imageUrl, getProgressDrawable(holder.view.context))
    }

    class BreedViewHolder(var view: View): RecyclerView.ViewHolder(view)
}