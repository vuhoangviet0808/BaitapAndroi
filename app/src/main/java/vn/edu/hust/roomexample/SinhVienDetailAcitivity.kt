package vn.edu.hust.roomexample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import vn.edu.hust.roomexample.databinding.ActivitySinhVienDetailBinding

class SinhVienDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySinhVienDetailBinding
    private lateinit var database: SinhVienDatabase
    private var sinhVien: SinhVien? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySinhVienDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = SinhVienDatabase.getDatabase(this)
        val mssv = intent.getStringExtra("mssv")

        CoroutineScope(Dispatchers.IO).launch {
            sinhVien = database.sinhVienDao().getAllSinhVien().value?.find { it.mssv == mssv }
            runOnUiThread {
                binding.etMssv.setText(sinhVien?.mssv)
                binding.etHoTen.setText(sinhVien?.hoTen)
                binding.etNgaySinh.setText(sinhVien?.ngaySinh)
                binding.etEmail.setText(sinhVien?.email)
            }
        }

        binding.btnUpdate.setOnClickListener {
            val updatedSinhVien = SinhVien(
                mssv = binding.etMssv.text.toString(),
                hoTen = binding.etHoTen.text.toString(),
                ngaySinh = binding.etNgaySinh.text.toString(),
                email = binding.etEmail.text.toString()
            )

            CoroutineScope(Dispatchers.IO).launch {
                database.sinhVienDao().update(updatedSinhVien)
                finish()
            }
        }

        binding.btnDelete.setOnClickListener {
            sinhVien?.let {
                CoroutineScope(Dispatchers.IO).launch {
                    database.sinhVienDao().delete(it)
                    finish()
                }
            }
        }
    }
}
