package com.ecampus.bem.data.db_lokal
/*=================== T H A N K   Y O U ===================*/
/*============= TELAH MENGUNAKAN CODE SAYA ================*/
/* https://github.com/ringga-dev */
/*=========================================================*/
/*     R I N G G A   S E P T I A  P R I B A D I            */
/*=========================================================*/
import android.content.Context
import android.content.SharedPreferences
import com.ecampus.bem.data.model.UserRespon


class SharedPrefManager private constructor(mCtx: Context) {
    private val mCtx: Context
    fun saveUser(user: UserRespon) {
        val sharedPreferences: SharedPreferences = mCtx.getSharedPreferences(
            SHARED_PREF_NAME,
            Context.MODE_PRIVATE
        )
        val editor = sharedPreferences.edit()
        editor.putInt("id", user.id!!)
        editor.putString("name", user.name)
        editor.putString("nim", user.nim)
        editor.putString("email", user.email)
        editor.putString("no_phone", user.no_phone)
        editor.putString("prodi", user.prodi)
        editor.putString("image", user.image)
        editor.putString("created_by", user.created_by)
        editor.apply()
    }

    val isLoggedIn: Boolean
        get() {
            val sharedPreferences: SharedPreferences = mCtx.getSharedPreferences(
                SHARED_PREF_NAME,
                Context.MODE_PRIVATE
            )
            return sharedPreferences.getInt("id", -1) != -1
        }

    val user: UserRespon
        get() {
            val sharedPreferences: SharedPreferences = mCtx.getSharedPreferences(
                SHARED_PREF_NAME,
                Context.MODE_PRIVATE
            )
            return UserRespon(
                sharedPreferences.getInt("id", -1),
                sharedPreferences.getString("name", null)!!,
                sharedPreferences.getString("nim", null)!!,
                sharedPreferences.getString("email", null)!!,
                sharedPreferences.getString("no_phone", null)!!,
                sharedPreferences.getString("prodi", null)!!,
                sharedPreferences.getString("image", null)!!,
                sharedPreferences.getString("created_by", null)!!
            )
        }

    fun clear() {
        val sharedPreferences: SharedPreferences = mCtx.getSharedPreferences(
            SHARED_PREF_NAME,
            Context.MODE_PRIVATE
        )
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

    companion object {
        private const val SHARED_PREF_NAME = "my_shared_preff"
        private var mInstance: SharedPrefManager? = null

        @Synchronized
        fun getInstance(mCtx: Context): SharedPrefManager? {
            if (mInstance == null) {
                mInstance = SharedPrefManager(mCtx)
            }
            return mInstance as SharedPrefManager
        }
    }

    init {
        this.mCtx = mCtx
    }
}