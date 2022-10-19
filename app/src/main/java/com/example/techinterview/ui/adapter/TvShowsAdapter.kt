package com.example.techinterview.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SortedList
import androidx.recyclerview.widget.SortedListAdapterCallback
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.techinterview.R
import com.example.techinterview.databinding.ItemTvShowBinding
import com.example.techinterview.domain.model.TVShow
import com.example.techinterview.util.toHumanDate
import com.example.techinterview.util.toHumanDuration

class TvShowsAdapter : RecyclerView.Adapter<TvShowsAdapter.ViewHolder>() {

    private val internalComparator: ComparatorWrapper<TVShow> = ComparatorWrapperImpl(this)
    private val sortedList = SortedList(TVShow::class.java, SortedList.BatchedCallback<TVShow>(internalComparator))

    abstract class ComparatorWrapper<T>(adapter: RecyclerView.Adapter<*>, private var comparator: Comparator<T>): SortedListAdapterCallback<T>(adapter){
        fun setComparator(c: Comparator<T>){
            if(c == comparator) return
            comparator = c
        }

        override fun compare(o1: T, o2: T): Int {
            return comparator.compare(o1, o2)
        }
    }

    class ComparatorWrapperImpl(adapter: RecyclerView.Adapter<*>) : ComparatorWrapper<TVShow>(adapter, TVShowsComparator.Earliest){
        override fun areContentsTheSame(oldItem: TVShow?, newItem: TVShow?): Boolean {
            return oldItem?.imageUrl == newItem?.imageUrl &&
                    oldItem?.endTime == newItem?.endTime &&
                    oldItem?.startTime == newItem?.startTime &&
                    oldItem?.title == newItem?.title &&
                    oldItem?.episodeTitle == newItem?.episodeTitle

        }

        override fun areItemsTheSame(item1: TVShow?, item2: TVShow?): Boolean {
            return item1?.id == item2?.id
        }
    }

    sealed class TVShowsComparator: Comparator<TVShow> {
        object Earliest : TVShowsComparator() {
            override fun compare(o1: TVShow, o2: TVShow): Int {
                return o1.startTime.compareTo(o2.startTime)
            }
        }

        object Latest : TVShowsComparator() {
            override fun compare(o1: TVShow, o2: TVShow): Int {
                return o1.endTime.compareTo(o2.endTime)
            }
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemTvShowBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return sortedList.size()
    }

    fun setList(list: List<TVShow>){
        with(sortedList){
            beginBatchedUpdates()
            sortedList.clear()
            sortedList.addAll(list)
            endBatchedUpdates()
        }
    }

    fun setOrder(comparator: TVShowsComparator){
        with(sortedList){
            internalComparator.setComparator(comparator)
            beginBatchedUpdates()
            val shows = (0 until sortedList.size()).mapTo(ArrayList<TVShow>()){get(it)}
            clear()
            addAll(shows)
            shows.clear()
            endBatchedUpdates()
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(sortedList.get(position))
    }

    inner class ViewHolder(private val binding: ItemTvShowBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: TVShow){
            binding.itemTitle.text = item.episodeTitle
            binding.itemStartTime.text = item.startTime.toHumanDate()
            binding.itemEndTime.text = item.endTime.toHumanDate()
            binding.itemShowDuration.text = item.getDuration().toHumanDuration()

            Glide.with(binding.itemImage)
                .load(item.imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transform(RoundedCorners(itemView.context.resources.getDimensionPixelSize((R.dimen.image_corners))))
                .into(binding.itemImage)
        }
    }

    class DiffUtilCallback : DiffUtil.ItemCallback<TVShow>() {
        override fun areItemsTheSame(oldItem: TVShow, newItem: TVShow): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: TVShow, newItem: TVShow): Boolean {
            return oldItem.imageUrl == newItem.imageUrl &&
                    oldItem.endTime == newItem.endTime &&
                    oldItem.startTime == newItem.startTime &&
                    oldItem.title == newItem.title &&
                    oldItem.episodeTitle == newItem.episodeTitle
        }
    }
}