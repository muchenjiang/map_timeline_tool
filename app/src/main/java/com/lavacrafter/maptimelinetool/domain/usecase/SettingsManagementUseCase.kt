package com.lavacrafter.maptimelinetool.domain.usecase

import com.lavacrafter.maptimelinetool.domain.repository.SettingsManagementGateway
import com.lavacrafter.maptimelinetool.ui.DownloadedArea
import com.lavacrafter.maptimelinetool.ui.LanguagePreference
import com.lavacrafter.maptimelinetool.ui.MapCachePolicy
import com.lavacrafter.maptimelinetool.ui.ZoomButtonBehavior

class SettingsManagementUseCase(
    private val settingsGateway: SettingsManagementGateway
) {
    fun getTimeoutSeconds(): Int = settingsGateway.getTimeoutSeconds()
    fun setTimeoutSeconds(seconds: Int) = settingsGateway.setTimeoutSeconds(seconds)

    fun getCachePolicy(): MapCachePolicy = settingsGateway.getCachePolicy()
    fun setCachePolicy(policy: MapCachePolicy) = settingsGateway.setCachePolicy(policy)

    fun getPinnedTagIds(): List<Long> = settingsGateway.getPinnedTagIds()
    fun setPinnedTagIds(tagIds: List<Long>) = settingsGateway.setPinnedTagIds(tagIds)

    fun getRecentTagIds(): List<Long> = settingsGateway.getRecentTagIds()
    fun addRecentTagId(tagId: Long): List<Long> = settingsGateway.addRecentTagId(tagId)

    fun getZoomButtonBehavior(): ZoomButtonBehavior = settingsGateway.getZoomButtonBehavior()
    fun setZoomButtonBehavior(behavior: ZoomButtonBehavior) = settingsGateway.setZoomButtonBehavior(behavior)

    fun getLanguagePreference(): LanguagePreference = settingsGateway.getLanguagePreference()

    fun getFollowSystemTheme(): Boolean = settingsGateway.getFollowSystemTheme()
    fun setFollowSystemTheme(enabled: Boolean) = settingsGateway.setFollowSystemTheme(enabled)

    fun getDefaultTagIds(): List<Long> = settingsGateway.getDefaultTagIds()
    fun setDefaultTagIds(tagIds: List<Long>) = settingsGateway.setDefaultTagIds(tagIds)

    fun getMarkerScale(): Float = settingsGateway.getMarkerScale()
    fun setMarkerScale(scale: Float) = settingsGateway.setMarkerScale(scale)

    fun getDownloadTileSourceId(): String = settingsGateway.getDownloadTileSourceId()
    fun setDownloadTileSourceId(sourceId: String) = settingsGateway.setDownloadTileSourceId(sourceId)

    fun getDownloadMultiThreadEnabled(): Boolean = settingsGateway.getDownloadMultiThreadEnabled()
    fun setDownloadMultiThreadEnabled(enabled: Boolean) = settingsGateway.setDownloadMultiThreadEnabled(enabled)

    fun getDownloadThreadCount(): Int = settingsGateway.getDownloadThreadCount()
    fun setDownloadThreadCount(count: Int) = settingsGateway.setDownloadThreadCount(count)

    fun getDownloadedAreas(): List<DownloadedArea> = settingsGateway.getDownloadedAreas()
    fun addDownloadedArea(area: DownloadedArea): List<DownloadedArea> = settingsGateway.addDownloadedArea(area)
    fun removeDownloadedArea(area: DownloadedArea): List<DownloadedArea> = settingsGateway.removeDownloadedArea(area)
    fun dedupeDownloadedAreas(): List<DownloadedArea> = settingsGateway.dedupeDownloadedAreas()
}
