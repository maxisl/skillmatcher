package com.example.skillmatcher.di

import android.content.Context
import com.example.skillmatcher.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.logger.ChatLogLevel
import io.getstream.chat.android.offline.model.message.attachments.UploadAttachmentsNetworkType
import io.getstream.chat.android.offline.plugin.factory.StreamOfflinePluginFactory
import javax.inject.Singleton

/**@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideOfflinePluginFactory(@ApplicationContext context: Context)=
        StreamOfflinePluginFactory(
            config= io.getstream.chat.android.offline.plugin.configuration.Config(
                backgroundSyncEnabled= true,
                userPresence= true,
                persistenceEnabled= true,
                uploadAttachmentsNetworkType= UploadAttachmentsNetworkType.NOT_ROAMING

            ),
            appContext = context
        )
    //baut den Client hier
@Singleton
@Provides
    fun providedChatClient(@ApplicationContext context: Context, offlinePluginFactory: StreamOfflinePluginFactory )=
        ChatClient.Builder(context.getString(R.string.api_key), context).withPlugin(offlinePluginFactory)
            .logLevel(ChatLogLevel.ALL).build()
}
        */