package com.example.biddexnewsapp.presentation.new_details

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.example.biddexnewsapp.R
import com.example.biddexnewsapp.presentation.utils.loadImageFromUrl
import com.example.biddexnewsapp.databinding.FragmentNewDetailsBinding
import com.example.biddexnewsapp.domain.entity.NewEntity
import com.example.biddexnewsapp.presentation.base.BaseFragment
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewDetailsFragment : BaseFragment<FragmentNewDetailsBinding>(FragmentNewDetailsBinding::inflate) {

    private val args: NewDetailsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbar(binding.toolbar.layoutToolbar, getString(R.string.details))
        fillNewData()
    }

    private fun fillNewData() {
        val newDetails = Gson().fromJson(args.newDetails, NewEntity::class.java)

        with(binding){
            ivImage.loadImageFromUrl(newDetails.image)
            tvTitle.text  = newDetails.title
            tvAuthor.text  =  newDetails.author
            tvDescription.text = newDetails.description
        }

    }
}