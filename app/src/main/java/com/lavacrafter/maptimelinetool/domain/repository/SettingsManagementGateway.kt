package com.lavacrafter.maptimelinetool.domain.repository

import com.lavacrafter.maptimelinetool.ui.DownloadedArea
import com.lavacrafter.maptimelinetool.ui.LanguagePreference
import com.lavacrafter.maptimelinetool.ui.MapCachePolicy
import com.lavacrafter.maptimelinetool.ui.ZoomButtonBehavior

interface SettingsManagementGateway {
    fun getTimeoutSeconds(): Int
    fun setTimeoutSeconds(seconds: Int)

    fun getCachePolicy(): MapCachePolicy
    fun setCachePolicy(policy: MapCachePolicy)

    fun getPinnedTagIds(): List<Long>
    fun setPinnedTagIds(tagIds: List<Long>)

    fun getRecentTagIds(): List<Long>
    fun addRecentTagId(tagId: Long): List<Long>

    fun getZoomButtonBehavior(): ZoomButtonBehavior
    fun setZoomButtonBehavior(behavior: ZoomButtonBehavior)

    fun getLanguagePreference(): LanguagePreference

    fun getFollowSystemTheme(): Boolean
    fun setFollowSystemTheme(enabled: Boolean)

    fun getDefaultTagIds(): List<Long>
    fun setDefaultTagIds(tagIds: List<Long>)

    fun getMarkerScale(): Float
    fun setMarkerScale(scale: Float)

    fun getDownloadTileSourceId(): String
    fun setDownloadTileSourceId(sourceId: String)

    fun getDownloadMultiThreadEnabled(): Boolean
    fun setDownloadMultiThreadEnabled(enabled: Boolean)

    fun getDownloadThreadCount(): Int
    fun setDownloadThreadCount(count: Int)

    fun getDownloadedAreas(): List<DownloadedArea>
    fun addDownloadedArea(area: DownloadedArea): List<DownloadedArea>
    fun removeDownloadedArea(area: DownloadedArea): List<DownloadedArea>
    fun dedupeDownloadedAreas(): List<DownloadedArea>
}
