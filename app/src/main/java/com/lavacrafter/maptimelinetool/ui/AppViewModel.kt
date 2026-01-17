package com.lavacrafter.maptimelinetool.ui

import android.app.Application
import android.location.Location
import android.location.LocationManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.lavacrafter.maptimelinetool.data.AppDatabase
import com.lavacrafter.maptimelinetool.data.PointEntity
import com.lavacrafter.maptimelinetool.data.PointRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AppViewModel(app: Application) : AndroidViewModel(app) {
    private val repo = PointRepository(AppDatabase.get(app).pointDao())
    val points = repo.observeAll().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun addPoint(note: String, location: Location?) {
        if (location == null) return
        viewModelScope.launch {
            repo.insert(
                PointEntity(
                    timestamp = System.currentTimeMillis(),
                    latitude = location.latitude,
                    longitude = location.longitude,
                    note = note.ifBlank { "打点" }
                )
            )
        }
    }

    fun getLastKnownLocation(): Location? {
        val lm = getApplication<Application>().getSystemService(LocationManager::class.java)
        val providers = lm.getProviders(true)
        return providers
            .mapNotNull { lm.getLastKnownLocation(it) }
            .maxByOrNull { it.time }
    }

    suspend fun getAllPoints(): List<PointEntity> = repo.getAll()
}