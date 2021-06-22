package com.example.corakshak

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.app.ActivityCompat.recreate
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SettingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SettingFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var LanguagemBtn : Button
    var VacinationStatus: String = ""
    var statuts = arrayOf("English", "French", "Spanish")
   // var spinner:Spinner? = null
   // var textView_msg: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment

        var view = inflater.inflate(R.layout.fragment_setting, container, false);
        LanguagemBtn = view.findViewById(R.id.language_change_btn)
        loadLocate()

        LanguagemBtn.setOnClickListener{
            val listItmes = arrayOf( "English", "हिंदी")

            val mBuilder = AlertDialog.Builder(activity)
            mBuilder.setTitle("Choose Language")
            mBuilder.setSingleChoiceItems(listItmes, -1) { dialog, which ->
                if (which == 0) {
                    setLocate("en")
                    activity?.recreate()
//                    LanguagemBtn.text = "English"

                } else if (which == 1) {
                    setLocate("hi")
                    activity?.recreate()
//                    LanguagemBtn.text = "हिंदी"
                }

                dialog.dismiss()
            }
            val mDialog = mBuilder.create()

            mDialog.show()
        }
            return view
    }



    private fun setLocate(Lang: String) {

        val locale = Locale(Lang)

        Locale.setDefault(locale)

        val config = Configuration()

        config.locale = locale
        activity?.baseContext?.resources?.updateConfiguration(config,
            activity?.baseContext?.resources?.displayMetrics
        )

        val editor = activity?.getSharedPreferences("Settings", Context.MODE_PRIVATE)?.edit()
        editor?.putString("My_Lang", Lang)
        editor?.apply()

    }

    private fun loadLocate() {
        val sharedPreferences = activity?.getSharedPreferences("Settings", Activity.MODE_PRIVATE)
        val language = sharedPreferences?.getString("My_Lang", "")
        language?.let { setLocate(it)
            if (it == "en") {
                LanguagemBtn.text = "English"

            } else if (it == "hi") {
                LanguagemBtn.text = "हिंदी"
            }
        }


    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SettingFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SettingFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}