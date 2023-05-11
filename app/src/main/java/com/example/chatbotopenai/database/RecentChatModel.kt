
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recent_history_table")
data class RecentChatModel (
    @PrimaryKey(autoGenerate = true)
     var id: Int = 0,

    var message:String?=""
)