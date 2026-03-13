package com.lavacrafter.maptimelinetool.data

import android.content.Context
import com.lavacrafter.maptimelinetool.domain.repository.SettingsManagementGateway
import com.lavacrafter.maptimelinetool.ui.DownloadedArea
import com.lavacrafter.maptimelinetool.ui.LanguagePreference
import com.lavacrafter.maptimelinetool.ui.MapCachePolicy
import com.lavacrafter.maptimelinetool.ui.SettingsStore
import com.lavacrafter.maptimelinetool.ui.ZoomButtonBehavior

class SettingsRepository(context: Context) : SettingsManagementGateway {
    private val appContext = context.applicationContext

    override fun getTimeoutSeconds(): Int = SettingsStore.getTimeoutSeconds(appContext)
    override fun setTimeoutSeconds(seconds: Int) = SettingsStore.setTimeoutSeconds(appContext, seconds)

    override fun getCachePolicy(): MapCachePolicy = SettingsStore.getCachePolicy(appContext)
    override fun setCachePolicy(policy: MapCachePolicy) = SettingsStore.setCachePolicy(appContext, policy)

    override fun getPinnedTagIds(): List<Long> = SettingsStore.getPinnedTagIds(appContext)
    override fun setPinnedTagIds(tagIds: List<Long>) = SettingsStore.setPinnedTagIds(appContext, tagIds)

    override fun getRecentTagIds(): List<Long> = SettingsStore.getRecentTagIds(appContext)
    override fun addRecentTagId(tagId: Long): List<Long> = SettingsStore.addRecentTagId(appContext, tagId)

    override fun getZoomButtonBehavior(): ZoomButtonBehavior = SettingsStore.getZoomButtonBehavior(appContext)
    override fun setZoomButtonBehavior(behavior: ZoomButtonBehavior) =
        SettingsStore.setZoomButtonBehavior(appContext, behavior)

    override fun getLanguagePreference(): LanguagePreference = SettingsStore.getLanguagePreference(appContext)

    override fun getFollowSystemTheme(): Boolean = SettingsStore.getFollowSystemTheme(appContext)
    override fun setFollowSystemTheme(enabled: Boolean) = SettingsStore.setFollowSystemTheme(appContext, enabled)

    override fun getDefaultTagIds(): List<Long> = SettingsStore.getDefaultTagIds(appContext)
    override fun setDefaultTagIds(tagIds: List<Long>) = SettingsStore.setDefaultTagIds(appContext, tagIds)

    override fun getMarkerScale(): Float = SettingsStore.getMarkerScale(appContext)
    override fun setMarkerScale(scale: Float) = SettingsStore.setMarkerScale(appContext, scale)

    override fun getDownloadTileSourceId(): String = SettingsStore.getDownloadTileSourceId(appContext)
    override fun setDownloadTileSourceId(sourceId: String) = SettingsStore.setDownloadTileSourceId(appContext, sourceId)

    override fun getDownloadMultiThreadEnabled(): Boolean = SettingsStore.getDownloadMultiThreadEnabled(appContext)
    override fun setDownloadMultiThreadEnabled(enabled: Boolean) =
        SettingsStore.setDownloadMultiThreadEnabled(appContext, enabled)

    override fun getDownloadThreadCount(): Int = SettingsStore.getDownloadThreadCount(appContext)
    override fun setDownloadThreadCount(count: Int) = SettingsStore.setDownloadThreadCount(appContext, count)

    override fun getDownloadedAreas(): List<DownloadedArea> = SettingsStore.getDownloadedAreas(appContext)
    override fun addDownloadedArea(area: DownloadedArea): List<DownloadedArea> =
        SettingsStore.addDownloadedArea(appContext, area)

    override fun removeDownloadedArea(area: DownloadedArea): List<DownloadedArea> =
        SettingsStore.removeDownloadedArea(appContext, area)

    override fun dedupeDownloadedAreas(): List<DownloadedArea> = SettingsStore.dedupeDownloadedAreas(appContext)
}
