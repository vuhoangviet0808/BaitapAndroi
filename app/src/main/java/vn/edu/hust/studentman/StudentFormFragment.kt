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

private const val ARG_NAME = "param1"
private const val ARG_ID = "param2"

class StudentFormFragment : Fragment() {
    private var name: String? = null
    private var studentId: String? = null

    private lateinit var nameEditText: EditText
    private lateinit var idEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            name = it.getString(ARG_NAME)
            studentId = it.getString(ARG_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_student_form, container, false)

        nameEditText = view.findViewById(R.id.editName)
        idEditText = view.findViewById(R.id.editId)
        val saveButton = view.findViewById<Button>(R.id.saveButton)

        saveButton.setOnClickListener {
            val name = nameEditText.text?.toString()
            val id = idEditText.text?.toString()

            if (!name.isNullOrEmpty() && !id.isNullOrEmpty()) {
                // Tạo Bundle chứa thông tin sinh viên mới
                val bundle = Bundle().apply {
                    putString("newName", name)
                    putString("newId", id)
                }

                // Gửi dữ liệu qua FragmentResultListener
                parentFragmentManager.setFragmentResult("addStudentRequest", bundle)

                // Quay lại danh sách
                findNavController().navigateUp()
            } else {
                // Thông báo nếu nhập thiếu dữ liệu
                Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }


    companion object {
        @JvmStatic
        fun newInstance(name: String?, studentId: String?) =
            StudentFormFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_NAME, name)
                    putString(ARG_ID, studentId)
                }
            }
    }
}
