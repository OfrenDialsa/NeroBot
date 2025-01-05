package com.example.nerobot.data.local


import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.squareup.moshi.JsonAdapter
import java.lang.reflect.Type
import com.example.nerobot.domain.model.MessageDomainModel
import com.squareup.moshi.Types
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "message_prefs")

class MessageDataStore(context: Context) {

    private val dataStore: DataStore<Preferences> = context.dataStore

    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    private val jsonAdapter: JsonAdapter<List<MessageDomainModel>> = moshi.adapter(
        Types.newParameterizedType(List::class.java, MessageDomainModel::class.java)
    )

    private val messagesKey = stringPreferencesKey("messages")

    suspend fun saveMessages(messageList: List<MessageDomainModel>) {
        val json = jsonAdapter.toJson(messageList)
        dataStore.edit { preferences ->
            preferences[messagesKey] = json
        }
    }

    val getMessages: Flow<List<MessageDomainModel>> = dataStore.data
        .map { preferences ->
            preferences[messagesKey]?.let { json ->
                jsonAdapter.fromJson(json) ?: emptyList()
            } ?: emptyList()
        }
}