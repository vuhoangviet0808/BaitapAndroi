import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.android.material.snackbar.Snackbar
import vn.edu.hust.studentman.R
import vn.edu.hust.studentman.StudentModel

class StudentListAdapter(
    private val context: Context,
    private var students: MutableList<StudentModel>
) : BaseAdapter() {

    override fun getCount(): Int = students.size

    override fun getItem(position: Int): Any = students[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(
            R.layout.layout_student_item, parent, false
        )

        val textStudentName = view.findViewById<TextView>(R.id.text_student_name)
        val textStudentId = view.findViewById<TextView>(R.id.text_student_id)
        val imageEdit = view.findViewById<ImageView>(R.id.image_edit)
        val imageRemove = view.findViewById<ImageView>(R.id.image_remove)

        val student = students[position]
        textStudentName.text = student.studentName
        textStudentId.text = student.studentId

        // Xử lý chỉnh sửa sinh viên
        imageEdit.setOnClickListener {
            val dialog = Dialog(context)
            dialog.setContentView(R.layout.add_student_layout)

            val editName = dialog.findViewById<EditText>(R.id.s_name)
            val editID = dialog.findViewById<EditText>(R.id.id)

            // Hiển thị dữ liệu hiện tại
            editName.setText(student.studentName)
            editID.setText(student.studentId)

            dialog.findViewById<Button>(R.id.button_ok).setOnClickListener {
                val newName = editName.text.toString()
                val newID = editID.text.toString()

                if (newName.isNotBlank() && newID.isNotBlank()) {
                    student.studentName = newName
                    student.studentId = newID

                    notifyDataSetChanged() // Cập nhật dữ liệu trong ListView
                    dialog.dismiss()
                } else {
                    Toast.makeText(context, "Name and ID cannot be empty", Toast.LENGTH_SHORT).show()
                }
            }

            dialog.findViewById<Button>(R.id.button_cancel).setOnClickListener {
                dialog.dismiss()
            }

            dialog.window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            dialog.show()
        }

        // Xử lý xóa sinh viên
        imageRemove.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setMessage("Remove Student")
                .setTitle("Are you sure you want to remove this student?")
                .setPositiveButton("Yes") { dialog, _ ->
                    val removedStudent = students[position]
                    students.removeAt(position)

                    notifyDataSetChanged() // Cập nhật danh sách
                    Toast.makeText(context, "Student removed", Toast.LENGTH_SHORT).show()

                    Snackbar.make(view, "${removedStudent.studentName} removed", Snackbar.LENGTH_LONG)
                        .setAction("Undo") {
                            students.add(position, removedStudent)
                            notifyDataSetChanged()
                        }.show()

                    dialog.dismiss()
                }
                .setNegativeButton("No") { dialog, _ -> dialog.dismiss() }
                .create().show()
        }

        return view
    }
}
