# Keep osmdroid to avoid reflection/shading issues
-keep class org.osmdroid.** { *; }
-dontwarn org.osmdroid.**

# Keep Room generated classes
-keep class androidx.room.** { *; }
-dontwarn androidx.room.**

# Keep app classes
-keep class com.lavacrafter.maptimelinetool.** { *; }
