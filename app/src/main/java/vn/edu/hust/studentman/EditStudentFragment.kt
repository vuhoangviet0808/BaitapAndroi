package vn.edu.hust.studentman

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController



private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EditStudentFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditStudentFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private var position: Int? = null

    private lateinit var nameEditText: EditText
    private lateinit var idEditText:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString("name")
            param2 = it.getString("id")
            position = it.getInt("position")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_edit_student, container, false)

        nameEditText = view.findViewById(R.id.edit_name)
        idEditText = view.findViewById(R.id.edit_id)
        nameEditText.setText(param1)
        idEditText.setText(param2)
        val saveButton = view.findViewById<Button>(R.id.btn_save)
        val cancelButton = view.findViewById<Button>(R.id.btn_cancel)

        saveButton.setOnClickListener{
            val name = nameEditText.text?.toString()
            val id = idEditText.text?.toString()
            if(!name.isNullOrEmpty() && !id.isNullOrEmpty()){
                val bundle = Bundle().apply{
                    putString("newName", name)
                    putString("newId", id)
                    putInt("position", position!!)
                }

                parentFragmentManager.setFragmentResult("editStudentRequest", bundle)
                findNavController().navigateUp()
            } else {
                Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment EditStudentFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            EditStudentFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}