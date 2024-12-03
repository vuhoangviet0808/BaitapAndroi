package vn.edu.hust.studentman

import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class StudentListFragment : Fragment() {
    private val studentList = mutableListOf<StudentModel>()
    private lateinit var adapter: StudentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        studentList.addAll(
            listOf(
                StudentModel("Nguyễn Văn An", "SV001"),
                StudentModel("Trần Thị Bảo", "SV002"),
                StudentModel("Lê Hoàng Cường", "SV003"),
                StudentModel("Phạm Thị Dung", "SV004"),
                StudentModel("Đỗ Minh Đức", "SV005"),
                StudentModel("Vũ Thị Hoa", "SV006"),
                StudentModel("Hoàng Văn Hải", "SV007"),
                StudentModel("Bùi Thị Hạnh", "SV008"),
                StudentModel("Đinh Văn Hùng", "SV009"),
                StudentModel("Nguyễn Thị Linh", "SV010"),
                StudentModel("Phạm Văn Long", "SV011"),
                StudentModel("Trần Thị Mai", "SV012"),
                StudentModel("Lê Thị Ngọc", "SV013"),
                StudentModel("Vũ Văn Nam", "SV014"),
                StudentModel("Hoàng Thị Phương", "SV015"),
                StudentModel("Đỗ Văn Quân", "SV016"),
                StudentModel("Nguyễn Thị Thu", "SV017"),
                StudentModel("Trần Văn Tài", "SV018"),
                StudentModel("Phạm Thị Tuyết", "SV019"),
                StudentModel("Lê Văn Vũ", "SV020")
            )
        )
        parentFragmentManager.setFragmentResultListener("addStudentRequest", this) { _, bundle ->
            val newName = bundle.getString("newName")
            val newId = bundle.getString("newId")

            if (!newName.isNullOrEmpty() && !newId.isNullOrEmpty()) {
                // Thêm sinh viên mới vào danh sách
                studentList.add(StudentModel(newName, newId))
                adapter.notifyDataSetChanged()
            }
        }

        parentFragmentManager.setFragmentResultListener("editStudentRequest", this) { _, bundle ->
            val newName = bundle.getString("newName")
            val newId = bundle.getString("newId")
            val position = bundle.getInt("position")
            if (newName != null && newId != null) {
                studentList[position].studentName = newName
                studentList[position].studentId = newId
                adapter.notifyDataSetChanged()
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_student_list, container, false)

        val listView = view.findViewById<ListView>(R.id.studentListView)
        adapter = StudentAdapter(requireContext(), studentList)
        listView.adapter = adapter

        registerForContextMenu(listView)
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.addNewStudent) {
            findNavController().navigate(R.id.action_studentListFragment_to_studentFormFragment)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        requireActivity().menuInflater.inflate(R.menu.context_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        val position = info.position
        val selectedStudent = studentList[position]

        when (item.itemId) {
            R.id.editStudent -> {
                // Chuyển tới StudentFormFragment để chỉnh sửa
                val bundle = Bundle().apply {
                    putString("name", selectedStudent.studentName)
                    putString("id", selectedStudent.studentId)
                    putInt("position", position)
                }
                findNavController().navigate(R.id.action_studentListFragment_to_editStudentFragment, bundle)
                return true
            }
            R.id.removeStudent -> {
                studentList.removeAt(position)
                adapter.notifyDataSetChanged()
                return true
            }
        }
        return super.onContextItemSelected(item)
    }




}
