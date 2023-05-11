package com.example.chatbotopenai.database
import RecentChatModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface RecentChatDao {

    @Insert
    fun insert(recentChat: RecentChatModel):Long

    @Delete
    fun delete(recentChat: RecentChatModel)

    @Query("delete from recent_history_table")
    fun deleteAllChat()

    @Query("select * from recent_history_table")
   fun getAllChat():MutableLiveData<ArrayList<RecentChatModel>>

}