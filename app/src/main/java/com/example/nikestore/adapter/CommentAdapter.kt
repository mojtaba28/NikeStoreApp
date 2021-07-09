package com.example.nikestore.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nikestore.R
import com.example.nikestore.model.Comment
import kotlinx.android.synthetic.main.item_comment.view.*

class CommentAdapter(val showAll:Boolean=false): RecyclerView.Adapter<CommentAdapter.ViewHolder>() {
    var comments = ArrayList<Comment>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {

        val titleTv = itemView.commentTitleTv
        val dateTv = itemView.commentDateTv
        val authorTv = itemView.commentAuthor
        val contentTv = itemView.commentContentTv
        fun bindComment(comment: Comment) {
            titleTv.text = comment.title
            dateTv.text = comment.date
            authorTv.text = comment.author.email
            contentTv.text = comment.content
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_comment,parent,false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindComment(comments[position])
    }

    override fun getItemCount(): Int {
        return if (comments.size > 5 && !showAll) 5 else comments.size
    }
}