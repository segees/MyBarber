package com.example.test2
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test2.connections.*
import com.example.test2.databinding.FragmentAdminPicsUpdateBinding
import com.google.android.material.transition.MaterialElevationScale
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class AdminAppDelete : Fragment() ,ItemRecyclerAdapter.OnItemClickListener {
    private lateinit var appDitail: List<String>
    private lateinit var binding: FragmentAdminPicsUpdateBinding
    private var blogAdapter = ItemRecyclerAdapter(this)
    var check:String=""
    var count:String=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAdminPicsUpdateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        CoroutineScope(Dispatchers.Default).async {
            check = adminShowApp()
            count = adminAppCount()
            withContext(Dispatchers.Main) {
                appDitail = check.split("! ".toRegex())
                val data = ItemSource.createDataSet(appDitail,count)
                blogAdapter.submitList(data)
                initRecycleView()
                postponeEnterTransition()
                view.doOnPreDraw {
                    startPostponedEnterTransition()
                }
            }
        }
    }
    private fun initRecycleView() {
        binding.recyclerview2.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = blogAdapter
        }
    }
    override fun onItemClick(view: View, itemBlog: ItemsViewModel) {
        exitTransition = MaterialElevationScale(false).apply {
            duration = resources.getInteger(R.integer.reply_motion_duration_large).toLong()
        }
        reenterTransition = MaterialElevationScale(true).apply {
            duration = resources.getInteger(R.integer.reply_motion_duration_large).toLong()
        }
        val itemSource = ItemSource.createDataSet(appDitail,count)[itemBlog.id.toInt()]
        val builder= AlertDialog.Builder(requireContext())
        builder.setTitle("are you sure you want to delete?")
        builder.setNegativeButton("No") { dialogInterface: DialogInterface, i: Int -> }
        builder.setPositiveButton("Yes") { dialogInterface: DialogInterface, i: Int ->
            CoroutineScope(Dispatchers.Default).async {
                DeleteAppData(itemSource.name)
            }
        }
        builder.show()
    }
}