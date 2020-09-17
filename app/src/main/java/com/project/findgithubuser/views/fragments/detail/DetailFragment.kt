package com.project.findgithubuser.views.fragments.detail

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.project.findgithubuser.R
import com.project.findgithubuser.adapters.SectionPagerAdapter
import com.project.findgithubuser.entity.FavoriteEntity
import com.project.findgithubuser.viewmodels.FavoriteViewModel
import com.project.findgithubuser.utils.log
import com.project.findgithubuser.utils.toast
import com.project.findgithubuser.viewmodels.DetailViewModel
import com.project.findgithubuser.viewmodels.SharedViewModel
import kotlinx.android.synthetic.main.include_detail.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DetailFragment : Fragment() {

    private lateinit var avatarUser: ImageView
    private lateinit var appBarLayout: AppBarLayout
    private lateinit var collapsingToolbarLayout: CollapsingToolbarLayout
    private lateinit var nestedScrollView: NestedScrollView
    private lateinit var linearLayout: LinearLayout
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager
    private lateinit var detailViewModel: DetailViewModel
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var progressBar: ProgressBar
    private lateinit var floatingActionButton: FloatingActionButton
    private lateinit var toolbar: Toolbar
    private lateinit var favoriteViewModel: FavoriteViewModel

    private var statusProgressBar = true
    private var state = false
    private var name = ""
    private var bitmap: Bitmap? = null
    private var url = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
        setupViewModel()
        tabLayout()
        appBarLayoutListener()
        detailViewModel()
    }

    private fun initView(view: View) {
        nestedScrollView = view.findViewById(R.id.nestedScrollView_detail)
        avatarUser = view.findViewById(R.id.img_detail)
        collapsingToolbarLayout = view.findViewById(R.id.collapsing_detail)
        appBarLayout = view.findViewById(R.id.appBar_detail)
        linearLayout = view.findViewById(R.id.linearLayout_detail)
        tabLayout = view.findViewById(R.id.tabLayout_detail)
        viewPager = view.findViewById(R.id.viewPager_detail)
        progressBar = view.findViewById(R.id.progressBar_detail)
        floatingActionButton = view.findViewById(R.id.floating_detail)
        toolbar = view.findViewById(R.id.toolbar_detail)

        nestedScrollView.isFillViewport = true
        toolbar.setNavigationIcon(R.drawable.ic_back)
        toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }

        floatingActionButton.setOnClickListener {
            if (state) {
                floatingActionButton.setImageResource(R.drawable.ic_unfavorite)
                state = false
                favoriteViewModel.deleteFavorite(name)
                log("delete")
            } else {
                val email = tv_detailEmailInformation.text.toString()
                val favorite = FavoriteEntity(0, bitmap!!, url, name, email)

                floatingActionButton.setImageResource(R.drawable.ic_favorite)
                state = true
                favoriteViewModel.addFavorite(favorite)
                log("add")
            }
        }
    }

    private fun setupViewModel() {
        favoriteViewModel = ViewModelProvider(requireActivity()).get(FavoriteViewModel::class.java)
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        detailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(DetailViewModel::class.java)

        sharedViewModel.getDataItem().observe(viewLifecycleOwner, { data ->
            detailViewModel.setRequestApi(data.login.toString())
            name = data.login.toString()
            url = data.avatarUrl.toString()
            Glide.with(this)
                .asBitmap()
                .load(data.avatarUrl.toString())
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        bitmap = resource
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {}
                })
            Glide.with(this)
                .load(data.avatarUrl.toString())
                .apply(RequestOptions().override(70, 70))
                .transform(CircleCrop())
                .into(avatarUser)
        })
    }

    private fun tabLayout() {
        val sectionPagerAdapter =
            SectionPagerAdapter(requireContext().applicationContext, childFragmentManager)
        viewPager.adapter = sectionPagerAdapter
        tabLayout.setupWithViewPager(viewPager)
    }

    private fun appBarLayoutListener() {
        val titleOff = ""
        val titleOn = resources.getString(R.string.titleDetail)
        context?.let { it ->
            ContextCompat.getColor(it, R.color.colorWhite).let {
                collapsingToolbarLayout.setCollapsedTitleTextColor(it)
                collapsingToolbarLayout.setExpandedTitleColor(it)
            }
        }
        appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener
        { appBarLayout, verticalOffset ->
            when {
                kotlin.math.abs(verticalOffset) - appBarLayout.totalScrollRange == 0 -> {
                    linearLayout.visibility = View.INVISIBLE
                    floatingActionButton.visibility = View.INVISIBLE
                    collapsingToolbarLayout.title = titleOn
                }
                verticalOffset == 0 -> {
                    linearLayout.visibility = View.VISIBLE
                    floatingActionButton.visibility = View.VISIBLE
                    collapsingToolbarLayout.title = titleOff
                }
                else -> {
                    linearLayout.visibility = View.VISIBLE
                    floatingActionButton.visibility = View.VISIBLE
                    collapsingToolbarLayout.title = titleOff
                }
            }
        })
    }

    private fun detailViewModel() {
        detailViewModel.getDataApi().observe(viewLifecycleOwner, { detailUser ->
            if (detailUser != null) {
                tv_detailNameInformation.text = checkNull(detailUser.name)
                tv_detailEmailInformation.text = checkNull(detailUser.email)
                tv_detailLocationInformation.text = checkNull(detailUser.location)
                tv_detailFollowersInformation.text = checkNull(detailUser.followers)
                tv_detailFollowingInformation.text = checkNull(detailUser.following)
                tv_detailBioInformation.text = checkNull(detailUser.bio)

                favoriteViewModel.readAllData.observe(viewLifecycleOwner, { data ->
                    if (!state) {
                        if (data != null) {
                            for (i in data.indices) {
                                if (data[i].username == name) {
                                    state = true
                                    floatingActionButton.setImageResource(R.drawable.ic_favorite)
                                }
                            }
                        }
                    }
                })
                if (statusProgressBar) {
                    GlobalScope.launch(Dispatchers.Main) {
                        delay(2000)
                        progressBar.visibility = View.GONE
                        appBarLayout.visibility = View.VISIBLE
                        nestedScrollView.visibility = View.VISIBLE
                    }
                }

            } else {
                toast(activity, resources.getString(R.string.failedToLoad))
            }
        })
    }

    private fun checkNull(text: Any?): String {
        return text?.toString() ?: "-"
    }
}