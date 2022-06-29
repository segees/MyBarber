package com.example.test2

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.test2.connections.*
import com.example.test2.databinding.FragmentPostBinding
import com.google.android.material.transition.MaterialContainerTransform
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*


class PostFragment : Fragment() {

    private val args:PostFragmentArgs by navArgs()
    private val postId: Long by lazy(LazyThreadSafetyMode.NONE) { args.blogPost }
    var option:String=""
    var msg:String=""
    private lateinit var binding: FragmentPostBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = MaterialContainerTransform().apply {
            drawingViewId = R.id.nav_host_fragment
            duration = resources.getInteger((R.integer.reply_motion_duration_large)).toLong()
            scrimColor = Color.TRANSPARENT
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPostBinding.inflate(inflater, container, false)
        var check:String=""
        var count:String=""
        CoroutineScope(Dispatchers.Default).async {
            check = RetrieveBlopPost()
            count = RetrieveBlopCount()
            withContext(Dispatchers.Main) {
                var postDitail: List<String> = check.split("! ".toRegex())
                val blogPost = DataSource.createDataSet(postDitail,count)[postId.toInt()]
                if (blogPost != null) {
                    binding.run { blogTitle.text = blogPost.title }
                }
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.navigationBackIcon.setOnClickListener {
            findNavController().navigateUp()
        }
        val dateSelect=view.findViewById<DatePicker>(R.id.calenderPicker)
        val timeSpinner=view.findViewById<Spinner>(R.id.timeSpinner)
        val timeBtn=view.findViewById<Button>(R.id.timeBtn)
        val timeCalendar=Calendar.getInstance()
        var check:String=""
        var check2:String=""
        val myPreference=MyPreference(requireContext())
        binding.run {
            dateSelect.init(
                timeCalendar.get(Calendar.YEAR),
                timeCalendar.get(Calendar.MONTH),
                timeCalendar.get(Calendar.DAY_OF_MONTH),
            )
            { view, year, month, day ->
                val month = month + 1
                msg = "$day/$month/$year"
                val simpleDateFormat = SimpleDateFormat("EEEE")
                val date = Date(year, month-1, day -1)
                val dayString = simpleDateFormat.format(date)
                val timeOptions= arrayListOf<String>("")
                CoroutineScope(Dispatchers.IO).async {
                    check = RetrieveDate(msg)
                    check2=AvailableHours()
                    withContext(Dispatchers.Main) {
                        var i:Int
                        var j:Int
                        var k:Int
                        var l:Int
                        var notAvailableHour: List<String> = check.split("\\s".toRegex())
                        var notAvailableHour2: List<String> = check2.split("\\s".toRegex())
                        var notAvailableHour3: List<String> = notAvailableHour2[0].split(":".toRegex())
                        var notAvailableHour4: List<String> = notAvailableHour2[1].split(":".toRegex())

                        i=notAvailableHour3[0].toInt()
                        k=notAvailableHour4[0].toInt()

                        while(i <=k){
                            timeOptions.add("$i:00")
                            timeOptions.add("$i:30")
                            i++
                        }
                        for (item: String in notAvailableHour) timeOptions.remove(item)
                        if(dayString=="Saturday"||timeOptions.isEmpty())
                        {
                            timeOptions.clear()
                            timeOptions.add("no appointment")
                        }
                        timeSpinner.adapter = ArrayAdapter(
                            requireContext(),
                            android.R.layout.simple_spinner_item,
                            timeOptions
                        )
                        timeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long)
                            { option = timeOptions[p2]}
                            override fun onNothingSelected(p0: AdapterView<*>?) {}
                        }
                    }
                }
                timeBtn.setOnClickListener {
                    if (option == ""||option=="no appointment")
                        Toast.makeText(activity, "not selected date/hour", Toast.LENGTH_LONG).show()
                    else {
                        CoroutineScope(Dispatchers.IO).async {
                            check = SendDate(myPreference.getLoginC(),msg,option)
                        }
                        val builder= AlertDialog.Builder(requireContext())
                        builder.setTitle("do you want to add to calender?")
                        builder.setNegativeButton("No") { dialogInterface: DialogInterface, i: Int ->
                            Navigation.findNavController(view).navigate(R.id.action_postFragment_to_menu)
                        }
                        builder.setPositiveButton("Yes") { dialogInterface: DialogInterface, i: Int ->
                            addCalendarEvent(view)
                        }
                        builder.show()
                    }
                }
            }
        }
    }

    fun addCalendarEvent(view:View) {
        val calendarEvent: Calendar = Calendar.getInstance()
        val intent = Intent(Intent.ACTION_EDIT)
        intent.type = "vnd.android.cursor.item/event"
        var check:String=""
        var time1:String=""
        var time2:String=""
        var date1:String=""
        var date2:String=""
        var date3:String=""
        var notAvailableHour3: List<String>
        var notAvailableHour4: List<String>
        var notAvailableHour5: List<String>
        val myPreference=MyPreference(requireContext())
        CoroutineScope(Dispatchers.Default).async {
            check = ReceiveAppData(myPreference.getLoginC())
            withContext(Dispatchers.Main) {
                notAvailableHour3=check.split("! ".toRegex())
                notAvailableHour4=notAvailableHour3[0].split("/".toRegex())
                date1=notAvailableHour4[0]
                date2=notAvailableHour4[1]
                date3=notAvailableHour4[2]
                notAvailableHour5=notAvailableHour3[1].split(":".toRegex())
                time1=notAvailableHour5[0]
                time2=notAvailableHour5[1]

                val simpp= SimpleDateFormat("yyyy MM dd HH:mm", Locale.ENGLISH)
                intent.putExtra("beginTime",simpp.parse(date3+" "+date2+" "+date1+" "+time1+":"+time2).time)
                intent.putExtra("allDay", false)
                intent.putExtra("beginDate", date1.toInt())
                intent.putExtra("beginDate", date2.toInt())
                intent.putExtra("beginYear", date3.toInt())
                intent.putExtra("endTime", simpp.parse(date3+" "+date2+" "+date1+" "+time1+":"+time2).time+30 * 60 * 1000)
                intent.putExtra("title", "Barbara")
                startActivity(intent)
            }
        }
    }
}