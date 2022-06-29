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
import com.example.test2.connections.DeletePics
import com.example.test2.connections.RetrieveBlopCount
import com.example.test2.connections.RetrieveBlopPost
import com.example.test2.databinding.FragmentAdminAppUpdateMenuBinding
import com.google.android.material.transition.MaterialElevationScale
import kotlinx.coroutines.*


class AdminPicDeleteMenu : Fragment() ,BlogRecyclerAdapter.OnItemClickListener{

    private lateinit var postDitail: List<String>
    private lateinit var binding: FragmentAdminAppUpdateMenuBinding
    private var blogAdapter = BlogRecyclerAdapter(this)
    var check:String=""
    var count:String=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAdminAppUpdateMenuBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        CoroutineScope(Dispatchers.Default).async {
            check = RetrieveBlopPost()
            count = RetrieveBlopCount()
            withContext(Dispatchers.Main) {
                postDitail = check.split("! ".toRegex())
                val data = DataSource.createDataSet(postDitail,count)
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
        binding.recyclerview.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = blogAdapter
        }
    }
    override fun onItemClick(view: View, itemBlog: BlogPost) {
        exitTransition = MaterialElevationScale(false).apply {
            duration = resources.getInteger(R.integer.reply_motion_duration_large).toLong()
        }
        reenterTransition = MaterialElevationScale(true).apply {
            duration = resources.getInteger(R.integer.reply_motion_duration_large).toLong()
        }
        val blogPost = DataSource.createDataSet(postDitail,count)[itemBlog.id.toInt()]
        val builder=AlertDialog.Builder(requireContext())
        builder.setTitle("Are you sure you want to delete?")
        builder.setNegativeButton("No") { dialogInterface: DialogInterface, i: Int -> }
        builder.setPositiveButton("Yes") { dialogInterface: DialogInterface, i: Int ->
            CoroutineScope(Dispatchers.Default).async {
                DeletePics(blogPost.title)
            }
        }
        builder.show()

    }
}


