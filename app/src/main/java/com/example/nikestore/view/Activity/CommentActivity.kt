package com.example.nikestore.view.Activity

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nikestore.R
import com.example.nikestore.adapter.CommentAdapter
import com.example.nikestore.common.EXTRA_KEY_ID
import com.example.nikestore.common.NikeActivity
import com.example.nikestore.model.Comment
import com.example.nikestore.viewmodel.CommentViewModel
import kotlinx.android.synthetic.main.activity_product_detail.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class CommentActivity : NikeActivity() {
    val commentViewModel:CommentViewModel by viewModel{ parametersOf(
        intent.extras!!.getInt(
        EXTRA_KEY_ID))}

    lateinit var commentAdapter: CommentAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment)

        getComment()
        setProgressBar()


    }

    private fun setProgressBar() {
        commentViewModel.progressBarLiveData.observe(this,{
            setProgressIndicator(it)
        })
    }

    private fun getComment() {
//        commentViewModel.progressBarLiveData.observe(this){
//            setProgressIndicator(it)
//        }
        commentViewModel.commentsLiveData.observe(this){
            commentAdapter = CommentAdapter(true)
            commentsRv.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
            commentAdapter.comments = it as ArrayList<Comment>
            commentsRv.adapter = commentAdapter
        }
    }
}