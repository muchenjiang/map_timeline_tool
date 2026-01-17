package com.lavacrafter.maptimelinetool.ui;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u001c2\u00020\u00012\u00020\u00022\u00020\u0003:\u0001\u001cB\r\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J \u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0016J\u001c\u0010\u0016\u001a\u00020\u000f2\b\u0010\f\u001a\u0004\u0018\u00010\r2\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018H\u0016J\u001a\u0010\u0019\u001a\u00020\u000f2\u0006\u0010\u001a\u001a\u00020\n2\b\u0010\u0017\u001a\u0004\u0018\u00010\u001bH\u0016R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u0004\u0018\u00010\rX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001d"}, d2 = {"Lcom/lavacrafter/maptimelinetool/ui/HeadingLocationOverlay;", "Lorg/osmdroid/views/overlay/Overlay;", "Lorg/osmdroid/views/overlay/mylocation/IMyLocationConsumer;", "Lorg/osmdroid/views/overlay/compass/IOrientationConsumer;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "arrowPaint", "Landroid/graphics/Paint;", "bearing", "", "circlePaint", "location", "Landroid/location/Location;", "draw", "", "canvas", "Landroid/graphics/Canvas;", "mapView", "Lorg/osmdroid/views/MapView;", "shadow", "", "onLocationChanged", "provider", "Lorg/osmdroid/views/overlay/mylocation/IMyLocationProvider;", "onOrientationChanged", "orientation", "Lorg/osmdroid/views/overlay/compass/IOrientationProvider;", "Companion", "app_release"})
public final class HeadingLocationOverlay extends org.osmdroid.views.overlay.Overlay implements org.osmdroid.views.overlay.mylocation.IMyLocationConsumer, org.osmdroid.views.overlay.compass.IOrientationConsumer {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.Nullable()
    private android.location.Location location;
    private float bearing = 0.0F;
    @org.jetbrains.annotations.NotNull()
    private final android.graphics.Paint circlePaint = null;
    @org.jetbrains.annotations.NotNull()
    private final android.graphics.Paint arrowPaint = null;
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String LOCATION_PREFS = "location_cache";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String KEY_LAT = "lat";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String KEY_LON = "lon";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String KEY_TIME = "time";
    @org.jetbrains.annotations.NotNull()
    public static final com.lavacrafter.maptimelinetool.ui.HeadingLocationOverlay.Companion Companion = null;
    
    public HeadingLocationOverlay(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super(null);
    }
    
    @java.lang.Override()
    public void onLocationChanged(@org.jetbrains.annotations.Nullable()
    android.location.Location location, @org.jetbrains.annotations.Nullable()
    org.osmdroid.views.overlay.mylocation.IMyLocationProvider provider) {
    }
    
    @java.lang.Override()
    public void onOrientationChanged(float orientation, @org.jetbrains.annotations.Nullable()
    org.osmdroid.views.overlay.compass.IOrientationProvider provider) {
    }
    
    @java.lang.Override()
    public void draw(@org.jetbrains.annotations.NotNull()
    android.graphics.Canvas canvas, @org.jetbrains.annotations.NotNull()
    org.osmdroid.views.MapView mapView, boolean shadow) {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\b"}, d2 = {"Lcom/lavacrafter/maptimelinetool/ui/HeadingLocationOverlay$Companion;", "", "()V", "KEY_LAT", "", "KEY_LON", "KEY_TIME", "LOCATION_PREFS", "app_release"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}