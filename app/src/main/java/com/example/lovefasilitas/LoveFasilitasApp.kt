package com.example.lovefasilitas

import android.app.Application
import com.example.lovefasilitas.data.local.ChatLocalDataSource
import com.example.lovefasilitas.data.local.FacilityLocalDataSource
import com.example.lovefasilitas.data.local.OrderHistoryLocalDataSource
import com.example.lovefasilitas.data.local.UserLocalDataSource
import com.example.lovefasilitas.data.remote.RemoteFacilityDataSource
import com.example.lovefasilitas.data.remote.RemoteUserDataSource
import com.example.lovefasilitas.data.remote.RetrofitClient
import com.example.lovefasilitas.data.repository.ChatRepositoryImpl
import com.example.lovefasilitas.data.repository.FacilityRepositoryImpl
import com.example.lovefasilitas.data.repository.OrderHistoryRepositoryImpl
import com.example.lovefasilitas.data.repository.UserRepositoryImpl
import com.example.lovefasilitas.domain.repository.ChatRepository
import com.example.lovefasilitas.domain.repository.FacilityRepository
import com.example.lovefasilitas.domain.repository.OrderHistoryRepository
import com.example.lovefasilitas.domain.repository.UserRepository

class LoveFasilitasApp : Application() {

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppContainer(this)
    }
}

class AppContainer(private val app: Application) {
    private val apiService by lazy { RetrofitClient.apiService }

    val facilityRepository: FacilityRepository by lazy {
        FacilityRepositoryImpl(
            FacilityLocalDataSource(),
            RemoteFacilityDataSource(apiService)
        )
    }
    val userRepository: UserRepository by lazy {
        UserRepositoryImpl(
            UserLocalDataSource(),
            RemoteUserDataSource(apiService)
        )
    }
    val orderHistoryRepository: OrderHistoryRepository by lazy {
        OrderHistoryRepositoryImpl(OrderHistoryLocalDataSource())
    }
    val chatRepository: ChatRepository by lazy {
        ChatRepositoryImpl(ChatLocalDataSource())
    }
}
