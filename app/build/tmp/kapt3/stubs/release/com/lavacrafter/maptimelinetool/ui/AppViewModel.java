package com.lavacrafter.maptimelinetool.ui;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J(\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00102\b\u0010\u0012\u001a\u0004\u0018\u00010\u00132\u0006\u0010\u0014\u001a\u00020\u0015J\u000e\u0010\u0016\u001a\u00020\u000e2\u0006\u0010\u0017\u001a\u00020\bJ\u0014\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\b0\u0007H\u0086@\u00a2\u0006\u0002\u0010\u0019J\b\u0010\u001a\u001a\u0004\u0018\u00010\u0013J\u001e\u0010\u001b\u001a\u00020\u000e2\u0006\u0010\u0017\u001a\u00020\b2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0010R\u001d\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001c"}, d2 = {"Lcom/lavacrafter/maptimelinetool/ui/AppViewModel;", "Landroidx/lifecycle/AndroidViewModel;", "app", "Landroid/app/Application;", "(Landroid/app/Application;)V", "points", "Lkotlinx/coroutines/flow/StateFlow;", "", "Lcom/lavacrafter/maptimelinetool/data/PointEntity;", "getPoints", "()Lkotlinx/coroutines/flow/StateFlow;", "repo", "Lcom/lavacrafter/maptimelinetool/data/PointRepository;", "addPoint", "", "title", "", "note", "location", "Landroid/location/Location;", "timestamp", "", "deletePoint", "point", "getAllPoints", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getLastKnownLocation", "updatePoint", "app_release"})
public final class AppViewModel extends androidx.lifecycle.AndroidViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.lavacrafter.maptimelinetool.data.PointRepository repo = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.lavacrafter.maptimelinetool.data.PointEntity>> points = null;
    
    public AppViewModel(@org.jetbrains.annotations.NotNull()
    android.app.Application app) {
        super(null);
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.lavacrafter.maptimelinetool.data.PointEntity>> getPoints() {
        return null;
    }
    
    public final void addPoint(@org.jetbrains.annotations.NotNull()
    java.lang.String title, @org.jetbrains.annotations.NotNull()
    java.lang.String note, @org.jetbrains.annotations.Nullable()
    android.location.Location location, long timestamp) {
    }
    
    public final void updatePoint(@org.jetbrains.annotations.NotNull()
    com.lavacrafter.maptimelinetool.data.PointEntity point, @org.jetbrains.annotations.NotNull()
    java.lang.String title, @org.jetbrains.annotations.NotNull()
    java.lang.String note) {
    }
    
    public final void deletePoint(@org.jetbrains.annotations.NotNull()
    com.lavacrafter.maptimelinetool.data.PointEntity point) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final android.location.Location getLastKnownLocation() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getAllPoints(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.lavacrafter.maptimelinetool.data.PointEntity>> $completion) {
        return null;
    }
}