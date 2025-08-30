package com.example.quiz.data.sp

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class InnerStorageImpl(context: Context) : BaseDataStore(context), InnerStorage {

    private val preferencesDataStore: DataStore<Preferences> by lazy {
        getDataStore()
    }

    private fun getIntKey(keyName: SharedPreferencesKeyNames): Preferences.Key<Int> {
        return intPreferencesKey(keyName.keyName)
    }

    private fun getStringKey(keyName: SharedPreferencesKeyNames): Preferences.Key<String> {
        return stringPreferencesKey(keyName.keyName)
    }

    private fun getBooleanKey(keyName: SharedPreferencesKeyNames): Preferences.Key<Boolean> {
        return booleanPreferencesKey(keyName.keyName)
    }

    override suspend fun setInt(sharedPreferencesKeyName: SharedPreferencesKeyNames, value: Int) {
        try {
            preferencesDataStore.edit { preferences ->
                preferences[getIntKey(sharedPreferencesKeyName)] = value
            }
        } catch (_: Exception) {

        }
    }

    override suspend fun getInt(
        sharedPreferencesKeyName: SharedPreferencesKeyNames,
        defaultValue: Int
    ): Int {
        return try {
            preferencesDataStore.data.map { preferences ->
                preferences[getIntKey(sharedPreferencesKeyName)] ?: defaultValue
            }.first()
        } catch (_: Exception) {
            defaultValue
        }
    }

    override suspend fun setString(
        sharedPreferencesKeyNames: SharedPreferencesKeyNames,
        value: String
    ) {
        try {
            preferencesDataStore.edit { preferences ->
                preferences[getStringKey(sharedPreferencesKeyNames)] = value
            }
        } catch (_: Exception) {
        }
    }

    override suspend fun getString(
        sharedPreferencesKeyNames: SharedPreferencesKeyNames,
        defaultValue: String
    ): String {
        return try {
            preferencesDataStore.data.map { preferences ->
                preferences[getStringKey(sharedPreferencesKeyNames)] ?: defaultValue
            }.first()
        } catch (_: Exception) {
            defaultValue
        }
    }

    override suspend fun setBoolean(
        sharedPreferencesKeyNames: SharedPreferencesKeyNames,
        value: Boolean
    ) {
        try {
            preferencesDataStore.edit { preferences ->
                preferences[getBooleanKey(sharedPreferencesKeyNames)] = value
            }
        } catch (_: Exception) {
        }
    }

    override suspend fun getBoolean(
        sharedPreferencesKeyNames: SharedPreferencesKeyNames,
        defaultValue: Boolean
    ): Boolean {
        return try {
            preferencesDataStore.data.map { preferences ->
                preferences[getBooleanKey(sharedPreferencesKeyNames)] ?: defaultValue
            }.first()
        } catch (_: Exception) {
            defaultValue
        }
    }
}