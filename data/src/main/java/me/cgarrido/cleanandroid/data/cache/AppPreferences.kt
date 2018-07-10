package me.cgarrido.cleanandroid.data.cache

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Class that manage android's [SharedPreferences] allowing the access using [ ] operator as keys. It supports only primitives types
 *
 * @property context the context for access to the preference file
 * @property sharedPreferenceName the name to retrieve a specific sharedPreference file. It uses [PreferenceManager.getDefaultSharedPreferences] if **`null`**.
 *
 * @author Cristian Garrido
 */
class AppPreferences(private val context: Context,
                     private val sharedPreferenceName: String? = null) {

    private inline val preferences: SharedPreferences
        get() = sharedPreferenceName?.let { context.getSharedPreferences(it, Context.MODE_PRIVATE) }
                ?: PreferenceManager.getDefaultSharedPreferences(context)

    private inline val editor: SharedPreferences.Editor
        @SuppressLint("CommitPrefEdits")
        get() = preferences.edit()


    /**
     * Set the value for a given key of [T] type.
     */
    operator fun <T : Any> set(key: String, value: T) {
        when (value) {
            is Int -> editor.putInt(key, value)
            is Boolean -> editor.putBoolean(key, value)
            is Float -> editor.putFloat(key, value)
            is Long -> editor.putLong(key, value)
            is String -> editor.putString(key, value)
            else -> throw IllegalArgumentException(value::class.toString() + " type isn't supported")
        }.apply()
    }

    /**
     * Get the value for a given key of [T] type or [default] if the preference does not exist.
     */
    operator fun <T : Any> get(key: String, default: T): T {
        @Suppress("UNCHECKED_CAST", "IMPLICIT_CAST_TO_ANY")
        return when (default) {
            is Int -> preferences.getInt(key, default)
            is Boolean -> preferences.getBoolean(key, default)
            is Float -> preferences.getFloat(key, default)
            is Long -> preferences.getLong(key, default)
            is String -> preferences.getString(key, default)
            else -> throw IllegalArgumentException(default::class.toString() + " type isn't supported")
        } as T
    }

}

/**
 * Delegate class to read and write easily on [AppPreferences]
 **/
class AppPreference<T: Any>(
        private val key: String,
        private val defaultValue: T,
        private val preferences: AppPreferences) : ReadWriteProperty<Any, T> {

    override fun getValue(thisRef: Any, property: KProperty<*>): T {
        return preferences[key, defaultValue]
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        preferences[key] = value
    }
}

typealias pref<T> = AppPreference<T>