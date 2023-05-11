
import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import com.app.utils.Constant

open class Pref {

    companion object {

        private val TAG: String = "Pref"
        private var sharedPreferences: SharedPreferences? = null

        fun openPref(context: Context) {
            sharedPreferences = context.getSharedPreferences(Constant.pref, Context.MODE_PRIVATE)
        }

        fun getValue(context: Context, key: String, defaultValue: String?): String? {
            try {
                openPref(context)
                val result = sharedPreferences!!.getString(key, defaultValue)
                return result
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return defaultValue
        }

        fun setValue(context: Context, key: String, value: Int) {
            openPref(context)
            var prefsPrivateEditor: Editor? = sharedPreferences!!.edit()
            prefsPrivateEditor!!.putInt(key, value)
            prefsPrivateEditor.commit()
        }

        fun setValue(context: Context, key: String, value: Long) {
            openPref(context)
            var prefsPrivateEditor: Editor? = sharedPreferences!!.edit()
            prefsPrivateEditor!!.putLong(key, value)
            prefsPrivateEditor.commit()
            sharedPreferences = null
        }

        fun getValue(context: Context, key: String, defaultValue: Int): Int {
            openPref(context)
            val result = sharedPreferences!!.getInt(key, defaultValue)
            sharedPreferences = null
            return result
        }

        fun getValue(context: Context, key: String, defaultValue: Long): Long {
            openPref(context)
            val result = sharedPreferences!!.getLong(key, defaultValue)
            sharedPreferences = null
            return result
        }

        fun setValue(context: Context, key: String, value: String) {
            openPref(context)
            var prefsPrivateEditor: Editor? = sharedPreferences!!.edit()
            prefsPrivateEditor!!.putString(key, value)
            prefsPrivateEditor.commit()
            sharedPreferences = null
        }

        fun getValue(context: Context, key: String, defaultValue: Boolean): Boolean {
            openPref(context)
            val result = sharedPreferences!!.getBoolean(key, defaultValue)
            sharedPreferences = null
            return result
        }

        fun setValue(context: Context, key: String, value: Boolean) {
            openPref(context)
            val prefsPrivateEditor = sharedPreferences!!.edit()
            prefsPrivateEditor.putBoolean(key, value)
            prefsPrivateEditor.commit()
            sharedPreferences = null
        }

        fun setPrefSubscription(context: Context,is_sub:Boolean){
            Pref.setValue(context,Constant.is_subscribed,is_sub)
        }

        fun getPrefSubscription(context: Context):Boolean{
           return Pref.getValue(context,Constant.is_subscribed,false)
        }

        fun setMessageCount(context: Context,no:Int){
            Pref.setValue(context,Constant.getMessageCount,no)
        }

        fun getMessageCount(context: Context):Int{
            return Pref.getValue(context,Constant.getMessageCount,0)
        }
    }
}
