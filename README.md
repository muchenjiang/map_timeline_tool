# Map Timeline Tool

手动打点记录工具（本地存储 + 地图查看 + GPX 导出）

## 功能
- 一键记录当前点（时间/经纬度/备注）
- 列表查看打点
- 地图查看打点（OSMDroid）
- GPX 导出（导出到外部文件目录并可分享）
- 时间统一使用 UTC
- 地图支持预下载（离线查看）
- 界面支持中文/英文

## 运行
1. Android Studio 打开工程
2. 运行到真机（需要定位权限）
3. 顶部「打点」按钮记录当前位置
4. 顶部「导出」按钮导出 GPX

## 注意
- GPX 文件导出在应用外部文件目录，可通过分享/文件管理器获取
- OSMDroid 使用 OSM 在线瓦片；后续可以改为离线瓦片
- 仅地图瓦片下载需要联网，其余功能均为本地

## 工程结构（实际）
```
settings.gradle.kts
build.gradle.kts
app/
	build.gradle.kts
	src/main/
		AndroidManifest.xml
		java/com/lavacrafter/maptimelinetool/
			MainActivity.kt
			MapTimelineApp.kt
			data/
				AppDatabase.kt
				PointEntity.kt
				PointDao.kt
				PointRepository.kt
			export/
				GpxExporter.kt
			ui/
				AddPointDialog.kt
				AppViewModel.kt
				ListScreen.kt
				MapScreen.kt
				theme/Theme.kt
		res/
			values/strings.xml
			values/themes.xml
			xml/provider_paths.xml
```

