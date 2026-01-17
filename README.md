# Map Timeline Tool

**Language**: English | [中文](#中文说明)

Map Timeline Tool is an offline-first, manual point logging app for Android. It stores points locally, visualizes them on a map and in a list, and supports CSV export for external tools.

## Features
- **One-tap point logging**: record timestamp, latitude/longitude, title, and optional note.
- **Map view (osmdroid + OSM tiles)**: markers show your points with detail info; today’s points are color-coded by order (red → violet).
- **List view**: shows all points with coordinates and the **n-th point of today** displayed on the right.
- **Quick Add notification**: add a point directly from a persistent notification.
- **UTC in exports & map details**: timestamps are standardized to UTC for interoperability.
- **CSV export via system picker**: saves to user-selected location.
- **Dark theme toggle**.
- **Bilingual UI**: English/Chinese.

## How to Run
1. Open in Android Studio.
2. Run on a device and grant location permission.
3. Tap the center **Add** button to log a point.
4. Go to **Settings → Export CSV** to export.

## Notes
- CSV is exported via the system document picker.
- Map tiles are loaded from OpenStreetMap; network is only required for tiles.
- Location and data remain on-device.

## Open Source & Attribution
The app’s **Settings** and **About** screens list all open-source components and required attributions.

Summary:
- AndroidX / Jetpack Compose / Room / Material Components / Kotlin (Apache-2.0)
- osmdroid (Apache-2.0)
- OpenStreetMap data (ODbL, attribution required)

---

# 中文说明

Map Timeline Tool 是一款离线优先的 Android 手动打点应用，所有数据本地存储，可在地图和列表中查看，并支持 CSV 导出。

## 功能
- **一键打点**：记录时间、经纬度、标题和备注。
- **地图视图（osmdroid + OSM 瓦片）**：点位支持详情显示，当日点位按顺序颜色区分（红→紫）。
- **列表视图**：显示所有点位和坐标，右侧标注“当日第 n 个点”。
- **通知栏快速打点**：常驻通知一键打点。
- **导出/地图详情使用 UTC 时间**。
- **CSV 导出（系统文件选择器）**。
- **深色模式开关**。
- **中英文界面**。

## 运行
1. 使用 Android Studio 打开工程。
2. 运行到真机并授予定位权限。
3. 点击底部中间 **打点** 按钮记录。
4. 设置页点击 **导出 CSV**。

## 说明
- CSV 通过系统文件选择器导出。
- 地图瓦片来源于 OpenStreetMap，仅加载瓦片需要联网。
- 位置与数据均保存在本地。

## 开源与署名
应用内「设置」与「关于」页面已列出所有开源项目与署名要求。

摘要：
- AndroidX / Jetpack Compose / Room / Material Components / Kotlin（Apache-2.0）
- osmdroid（Apache-2.0）
- OpenStreetMap 数据（ODbL，需要署名）

