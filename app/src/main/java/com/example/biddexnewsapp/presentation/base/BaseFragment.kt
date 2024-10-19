package com.example.biddexnewsapp.presentation.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.biddexnewsapp.R
import com.example.biddexnewsapp.domain.utils.DataState
import com.example.biddexnewsapp.domain.utils.NetworkException
import retrofit2.HttpException

typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

abstract class BaseFragment<VB : ViewBinding>(private val inflate: Inflate<VB>) : Fragment() {

    private var _binding: VB? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate.invoke(inflater, container, false)
        return binding.root
    }

    protected fun initToolbar(toolbar: Toolbar, title: String, @DrawableRes icon: Int? = R.drawable.ic_back_arrow) {
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.title = title
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(true)
        icon?.let {
            (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
            toolbar.setNavigationIcon(icon)
        }
    }

    fun <T> DataState<T>.applyCommonSideEffects() {
        if(this is DataState.Error) {
            Log.e("TAG", "applyCommonSideEffects: $throwable", )
            when(this.throwable) {
                is NetworkException.UnAuthorizedException -> {
                    Toast.makeText(requireContext(), this.throwable.msg, Toast.LENGTH_SHORT).show()
                }
                is NetworkException.BadRequestException -> {
                    Toast.makeText(requireContext(), this.throwable.msg, Toast.LENGTH_SHORT).show()
                }
                is NetworkException.TooManyRequestsException -> {
                    Toast.makeText(requireContext(), this.throwable.msg, Toast.LENGTH_SHORT).show()
                }
                is NetworkException.ServerErrorException -> {
                    Toast.makeText(requireContext(), this.throwable.msg, Toast.LENGTH_SHORT).show()
                }
                is NetworkException.UnknownException -> {
                    Toast.makeText(requireContext(), this.throwable.msg, Toast.LENGTH_SHORT).show()
                }
                is NetworkException.ConnectionException -> {
                    Toast.makeText(requireContext(),
                        getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Toast.makeText(requireContext(),
                        getString(R.string.something_went_wrong_please_try_again_later), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}