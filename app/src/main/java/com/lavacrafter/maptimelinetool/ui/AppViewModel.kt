package com.lavacrafter.maptimelinetool.ui

import android.app.Application
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.CancellationSignal
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.lavacrafter.maptimelinetool.data.AppDatabase
import com.lavacrafter.maptimelinetool.data.PointEntity
import com.lavacrafter.maptimelinetool.data.PointRepository
import com.lavacrafter.maptimelinetool.data.TagEntity
import com.lavacrafter.maptimelinetool.ui.HeadingLocationOverlay
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeoutOrNull
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class AppViewModel(app: Application) : AndroidViewModel(app) {
    private val repo = PointRepository(AppDatabase.get(app).pointDao())
    val points = repo.observeAll().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val tags = repo.observeTags().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    private var autoAddJob: kotlinx.coroutines.Job? = null
    private val _autoAdded = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
    val autoAdded = _autoAdded

    fun addPoint(title: String, note: String, location: Location?, timestamp: Long) {
        if (location == null) return
        viewModelScope.launch {
            repo.insert(
                PointEntity(
                    timestamp = timestamp,
                    latitude = location.latitude,
                    longitude = location.longitude,
                    title = title,
                    note = note
                )
            )
        }
    }

    fun updatePoint(point: PointEntity, title: String, note: String) {
        viewModelScope.launch {
            repo.update(point.copy(title = title, note = note))
        }
    }

    fun deletePoint(point: PointEntity) {
        viewModelScope.launch {
            repo.delete(point)
        }
    }

    fun addTag(name: String, onResult: (Long) -> Unit = {}) {
        viewModelScope.launch {
            val id = repo.insertTag(TagEntity(name = name))
            onResult(id)
        }
    }

    fun renameTag(tag: TagEntity, name: String) {
        viewModelScope.launch {
            repo.updateTag(tag.copy(name = name))
        }
    }

    fun deleteTag(tagId: Long) {
        viewModelScope.launch {
            repo.deleteTag(tagId)
        }
    }

    fun setTagForPoint(pointId: Long, tagId: Long, enabled: Boolean) {
        viewModelScope.launch {
            if (enabled) repo.insertPointTag(pointId, tagId) else repo.deletePointTag(pointId, tagId)
        }
    }

    suspend fun getTagIdsForPoint(pointId: Long): List<Long> = repo.getTagIdsForPoint(pointId)

    fun observePointsForTag(tagId: Long) = repo.observePointsForTag(tagId)

    fun getLastKnownLocation(): Location? {
        val lm = getApplication<Application>().getSystemService(LocationManager::class.java)
        val providers = lm.getProviders(true)
        return providers
            .mapNotNull { lm.getLastKnownLocation(it) }
            .maxByOrNull { it.time }
    }

    suspend fun getFreshLocation(timeoutMs: Long): Location? {
        return withTimeoutOrNull(timeoutMs) {
            try {
                getCurrentLocationOnce()
            } catch (_: CancellationException) {
                null
            }
        } ?: getLastKnownLocation()
    }

    private suspend fun getCurrentLocationOnce(): Location = suspendCancellableCoroutine { cont ->
        val lm = getApplication<Application>().getSystemService(LocationManager::class.java)
        val provider = lm.getProviders(true).firstOrNull()
            ?: run {
                cont.resumeWithException(IllegalStateException("No location provider"))
                return@suspendCancellableCoroutine
            }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val signal = CancellationSignal()
            cont.invokeOnCancellation { signal.cancel() }
            lm.getCurrentLocation(provider, signal, { runnable -> runnable.run() }) { location ->
                if (location != null) {
                    cont.resume(location)
                } else {
                    cont.resumeWithException(IllegalStateException("Location unavailable"))
                }
            }
            return@suspendCancellableCoroutine
        }

        lateinit var listener: android.location.LocationListener
        listener = android.location.LocationListener { location ->
            lm.removeUpdates(listener)
            cont.resume(location)
        }
        cont.invokeOnCancellation { lm.removeUpdates(listener) }
        lm.requestSingleUpdate(provider, listener, null)
    }

    fun scheduleAutoAdd(createdAt: Long, timeoutSeconds: Int) {
        autoAddJob?.cancel()
        autoAddJob = viewModelScope.launch {
            kotlinx.coroutines.delay(timeoutSeconds * 1000L)
            val timestamp = createdAt
            val title = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date(timestamp))
            val location = getLastKnownLocation() ?: readCachedLocation()
            if (location != null) {
                repo.insert(
                    PointEntity(
                        timestamp = timestamp,
                        latitude = location.latitude,
                        longitude = location.longitude,
                        title = title,
                        note = ""
                    )
                )
                _autoAdded.tryEmit(Unit)
            }
        }
    }

    private fun readCachedLocation(): Location? {
        val prefs = getApplication<Application>().getSharedPreferences(HeadingLocationOverlay.LOCATION_PREFS, Application.MODE_PRIVATE)
        if (!prefs.contains(HeadingLocationOverlay.KEY_LAT) || !prefs.contains(HeadingLocationOverlay.KEY_LON)) {
            return null
        }
        return Location("cached").apply {
            latitude = prefs.getFloat(HeadingLocationOverlay.KEY_LAT, 0f).toDouble()
            longitude = prefs.getFloat(HeadingLocationOverlay.KEY_LON, 0f).toDouble()
            time = prefs.getLong(HeadingLocationOverlay.KEY_TIME, System.currentTimeMillis())
        }
    }

    fun cancelAutoAdd() {
        autoAddJob?.cancel()
        autoAddJob = null
    }

    suspend fun getAllPoints(): List<PointEntity> = repo.getAll()
}