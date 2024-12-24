package vn.edu.hust.roomexample

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [SinhVien::class], version = 1, exportSchema = false)
abstract class SinhVienDatabase : RoomDatabase() {
  abstract fun sinhVienDao(): SinhVienDao

  companion object {
    @Volatile
    private var INSTANCE: SinhVienDatabase? = null

    fun getDatabase(context: Context): SinhVienDatabase {
      return INSTANCE ?: synchronized(this) {
        val instance = Room.databaseBuilder(
          context.applicationContext,
          SinhVienDatabase::class.java,
          "sinh_vien_database"
        ).build()
        INSTANCE = instance
        instance
      }
    }
  }
}