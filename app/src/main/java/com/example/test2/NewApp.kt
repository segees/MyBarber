package com.example.test2
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test2.connections.RetrieveBlopCount
import com.example.test2.connections.RetrieveBlopPost
import com.example.test2.databinding.FragmentNewAppBinding
import com.google.android.material.transition.MaterialElevationScale
import com.google.android.material.transition.MaterialContainerTransform
import kotlinx.coroutines.*

class NewApp : Fragment() ,BlogRecyclerAdapter.OnItemClickListener{

    private lateinit var binding: FragmentNewAppBinding
    private var blogAdapter = BlogRecyclerAdapter(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val postFragment = PostFragment()
        postFragment.sharedElementEnterTransition = MaterialContainerTransform()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentNewAppBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var check:String=""
        var count:String=""
        CoroutineScope(Dispatchers.Default).async {
            check = RetrieveBlopPost()
            count = RetrieveBlopCount()
            withContext(Dispatchers.Main) {
                var picsDitail: List<String> = check.split("! ".toRegex())
                val data = DataSource.createDataSet(picsDitail,count)
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
        binding.recycleView.apply {
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
        val postCardDetailTransitionName = getString(R.string.post_card_detail_transition_name)
        val extras = FragmentNavigatorExtras(view to postCardDetailTransitionName)
        val directions = NewAppDirections.actionNewAppToPostFragment(itemBlog.id)
        findNavController().navigate(directions, extras)
    }
}