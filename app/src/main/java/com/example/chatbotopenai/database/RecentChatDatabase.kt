
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.chatbotopenai.database.RecentChatDao

@Database(entities = [RecentChatModel::class], version = 1)
abstract class RecentChatDatabase:RoomDatabase() {

    abstract fun recentChatDao(): RecentChatDao

    companion object {
        private var instance: RecentChatDatabase? = null

        @Synchronized
        fun getInstance(ctx: Context): RecentChatDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    ctx.applicationContext,
                    RecentChatDatabase::class.java,
                    "recentchat_database.db"
                )

                    .build()
            }
            return instance as RecentChatDatabase
        }

     }
}