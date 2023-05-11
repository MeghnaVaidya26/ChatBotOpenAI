
import android.app.Activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class RecentChatViewModel(activity: Activity) : ViewModel() {

    private val repository = RecentChatRepository(activity)
    private val allNotes = repository.getAllNotes()

    fun insert(note: RecentChatModel) {
        viewModelScope.launch {
            repository.insert(note)
        }
    }

    fun delete(note: RecentChatModel) {
        viewModelScope.launch {
            repository.delete(note)
        }
    }

    fun deleteAllNotes() {
        viewModelScope.launch {
            repository.deleteAllNotes()
        }
    }

    fun getAllNotes(): MutableLiveData<ArrayList<RecentChatModel>> {

        return allNotes


    }


}