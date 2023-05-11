
import android.app.Activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.chatbotopenai.database.RecentChatDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RecentChatRepository(var application:Activity) {
    private var noteDao: RecentChatDao
    private var allNotes: MutableLiveData<ArrayList<RecentChatModel>>

    private val database = RecentChatDatabase.getInstance(application)

    init {

        noteDao = database.recentChatDao()
        allNotes = noteDao.getAllChat()
        allNotes.value?.let {

        }
    }

   suspend fun insert(note: RecentChatModel) {
       withContext(Dispatchers.IO) {

          val i= noteDao.insert(note)
        }
    }



   suspend fun delete(note: RecentChatModel) {
       withContext(Dispatchers.IO) {

            noteDao.delete(note)
        }
    }

   suspend fun deleteAllNotes() {
        withContext(Dispatchers.IO) {
            noteDao.deleteAllChat()
        }
    }

    fun getAllNotes(): MutableLiveData<ArrayList<RecentChatModel>> {
        return allNotes
    }

}