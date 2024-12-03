package vn.edu.hust.studentman

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import vn.edu.hust.studentman.StudentModel

class StudentAdapter(context: Context, students: List<StudentModel>) :
  ArrayAdapter<StudentModel>(context, 0, students) {

  override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
    val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.layout_student_item, parent, false)
    val student = getItem(position)

    val nameTextView = view.findViewById<TextView>(R.id.text_student_name)
    val idTextView = view.findViewById<TextView>(R.id.text_student_id)

    nameTextView.text = student?.studentName
    idTextView.text = student?.studentId

    return view
  }
}
