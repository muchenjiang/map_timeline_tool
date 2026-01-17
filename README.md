# Map Timeline Tool

手动打点记录工具（本地存储 + 地图查看 + CSV 导出）

## 功能
- 一键记录当前点（时间/经纬度/备注）
- 列表查看打点
- 地图查看打点（OSMDroid）
- CSV 导出（使用系统文件保存对话框）
- 通知栏快速打点
- 支持深色模式
- 时间统一使用 UTC（导出/地图详情）
- 界面支持中文/英文

## 运行
1. Android Studio 打开工程
2. 运行到真机（需要定位权限）
3. 中间「打点」按钮记录当前位置
4. 设置页「导出 CSV」导出

## 注意
- CSV 文件通过系统文件保存对话框导出
- OSMDroid 使用 OSM 在线瓦片
- 仅地图瓦片加载需要联网，其余功能均为本地

## 开源与署名
应用内「设置」和「关于」页面已列出开源项目与署名信息。

依赖与署名要求（摘要）：
- AndroidX / Jetpack Compose / Room / Material Components / Kotlin（Apache-2.0）
- osmdroid（Apache-2.0）
- OpenStreetMap 数据（ODbL，需署名）

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
				export/
					GpxExporter.kt
					CsvExporter.kt
			ui/
				AddPointDialog.kt
				AppViewModel.kt
				ListScreen.kt
				MapScreen.kt
				SettingsScreen.kt
				AboutScreen.kt
				DayOrderUtils.kt
				theme/Theme.kt
		res/
			values/strings.xml
			values/themes.xml
			xml/provider_paths.xml
```

