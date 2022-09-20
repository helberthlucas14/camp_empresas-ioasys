package com.example.empresas.ui

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.empresas.R

class DetailsFragment : Fragment() {

    private val args: DetailsFragmentArgs by navArgs()

    private lateinit var toolbar: Toolbar
    private lateinit var companyName: AppCompatTextView
    private lateinit var descriptionCompany: AppCompatTextView
    private lateinit var imageDetailsCompany: AppCompatImageView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar = view.findViewById(R.id.toolbar)
        imageDetailsCompany = view.findViewById(R.id.imageDetailsCompany)
        companyName = view.findViewById(R.id.companyName)
        descriptionCompany = view.findViewById(R.id.descriptionCompany)
        setupToolbar()
        configureView()

    }

    private fun setupToolbar() {
        with(toolbar)
        {
            (activity as AppCompatActivity).run {
                setSupportActionBar(toolbar)
                supportActionBar?.setDisplayShowTitleEnabled(false)
            }
        }
    }

    private fun configureView() {
        companyName.text = args.companyName
        descriptionCompany.text = args.description
        descriptionCompany.movementMethod = ScrollingMovementMethod()
        setImageContent()
    }

    private fun setImageContent() {
        Glide
            .with(this)
            .setDefaultRequestOptions(
                RequestOptions().placeholder(R.drawable.img_logo_company)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
            )
            .load(args.imageUrl)
            .into(imageDetailsCompany)
    }
}