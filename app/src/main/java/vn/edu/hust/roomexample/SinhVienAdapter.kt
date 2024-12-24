package vn.edu.hust.roomexample


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import vn.edu.hust.roomexample.databinding.ItemSinhVienBinding

class SinhVienAdapter(
    private val onItemClicked: (SinhVien) -> Unit,
    private val onItemChecked: (SinhVien, Boolean) -> Unit
) : RecyclerView.Adapter<SinhVienAdapter.SinhVienViewHolder>() {

    private var sinhVienList = listOf<SinhVien>()
    private val selectedItems = mutableSetOf<SinhVien>()

    fun submitList(list: List<SinhVien>) {
        sinhVienList = list
        notifyDataSetChanged()
    }

    fun getSelectedItems(): List<SinhVien> = selectedItems.toList()

    inner class SinhVienViewHolder(private val binding: ItemSinhVienBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(sinhVien: SinhVien) {
            binding.tvMssv.text = sinhVien.mssv
            binding.tvHoTen.text = sinhVien.hoTen

            // Xử lý click vào item
            binding.root.setOnClickListener {
                onItemClicked(sinhVien)
            }

            // Xử lý checkbox
            binding.checkbox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) selectedItems.add(sinhVien) else selectedItems.remove(sinhVien)
                onItemChecked(sinhVien, isChecked)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SinhVienViewHolder {
        val binding = ItemSinhVienBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SinhVienViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SinhVienViewHolder, position: Int) {
        holder.bind(sinhVienList[position])
    }

    override fun getItemCount(): Int = sinhVienList.size
}
