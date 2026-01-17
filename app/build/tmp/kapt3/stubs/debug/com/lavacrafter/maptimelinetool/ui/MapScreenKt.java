package com.lavacrafter.maptimelinetool.ui;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000`\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u001aa\u0010\u0004\u001a\u00020\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00070\u00032\b\u0010\b\u001a\u0004\u0018\u00010\t2\u001e\u0010\n\u001a\u001a\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\u00050\u000b2\u0012\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00050\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0007\u00a2\u0006\u0002\u0010\u0011\u001a \u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\f2\u0006\u0010\u0017\u001a\u00020\u0001H\u0002\u001a0\u0010\u0018\u001a\u0004\u0018\u00010\u00072\u0006\u0010\u0019\u001a\u00020\u001a2\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00070\u00032\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001eH\u0002\u001a\u0012\u0010\u001f\u001a\u0004\u0018\u00010 2\u0006\u0010\u0014\u001a\u00020\u0015H\u0002\u001a\u0010\u0010!\u001a\u00020\u00012\u0006\u0010\"\u001a\u00020\u0001H\u0002\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082\u0004\u00a2\u0006\u0002\n\u0000\"\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006#"}, d2 = {"DEFAULT_MARKER_COLOR", "", "SPECTRUM_COLORS", "", "MapScreen", "", "points", "Lcom/lavacrafter/maptimelinetool/data/PointEntity;", "selectedPointId", "", "onUpdatePoint", "Lkotlin/Function3;", "", "onDeletePoint", "Lkotlin/Function1;", "isActive", "", "(Ljava/util/List;Ljava/lang/Long;Lkotlin/jvm/functions/Function3;Lkotlin/jvm/functions/Function1;Z)V", "createCounterIcon", "Landroid/graphics/drawable/BitmapDrawable;", "context", "Landroid/content/Context;", "text", "color", "findNearestPoint", "map", "Lorg/osmdroid/views/MapView;", "target", "Lorg/osmdroid/util/GeoPoint;", "maxDp", "", "readCachedLocation", "Landroid/location/Location;", "spectrumColor", "order", "app_debug"})
public final class MapScreenKt {
    @org.jetbrains.annotations.NotNull()
    private static final java.util.List<java.lang.Integer> SPECTRUM_COLORS = null;
    private static final int DEFAULT_MARKER_COLOR = 0;
    
    @android.annotation.SuppressLint(value = {"MissingPermission"})
    @androidx.compose.runtime.Composable()
    public static final void MapScreen(@org.jetbrains.annotations.NotNull()
    java.util.List<com.lavacrafter.maptimelinetool.data.PointEntity> points, @org.jetbrains.annotations.Nullable()
    java.lang.Long selectedPointId, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function3<? super com.lavacrafter.maptimelinetool.data.PointEntity, ? super java.lang.String, ? super java.lang.String, kotlin.Unit> onUpdatePoint, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.lavacrafter.maptimelinetool.data.PointEntity, kotlin.Unit> onDeletePoint, boolean isActive) {
    }
    
    private static final android.graphics.drawable.BitmapDrawable createCounterIcon(android.content.Context context, java.lang.String text, int color) {
        return null;
    }
    
    private static final int spectrumColor(int order) {
        return 0;
    }
    
    private static final android.location.Location readCachedLocation(android.content.Context context) {
        return null;
    }
    
    private static final com.lavacrafter.maptimelinetool.data.PointEntity findNearestPoint(org.osmdroid.views.MapView map, java.util.List<com.lavacrafter.maptimelinetool.data.PointEntity> points, org.osmdroid.util.GeoPoint target, float maxDp) {
        return null;
    }
}