## Plan: Backup feature for the app

Use the existing ZIP export/import pipeline as the backup carrier, but extend it into a true backup system: add a versioned manifest, include app settings in the archive, support manual and scheduled full backups, and define merge-safe restore rules so backups can be re-applied without forcing a wipe.

**Steps**
1. Define the backup contract first, so the rest of the work is aligned.
   - Keep ZIP as the container format.
   - Add a manifest file that declares backup version, created-at timestamp, app version, and which sections are present.
   - Keep backward compatibility with the current ZIP layout so old exports still import.
   - Scope the backup content to points, tags, point-tag relations, point photos, and app settings.
   - Exclude offline map tiles/cache and downloaded area caches for now, because those can become very large and are better treated as separate storage.

2. Extend the export/import layer to carry settings in addition to the existing data.
   - Reuse `ZipExporter` and `ZipImporter` instead of creating a second backup format.
   - Add a settings snapshot representation that serializes the values currently stored in `SettingsStore` and exposed through `SettingsRepository`.
   - Add restore logic that can write those settings back through the existing settings gateway rather than mutating SharedPreferences ad hoc.
   - Preserve the current point/tag/photo CSV layout so the existing importer keeps working.

3. Make restore merge-safe instead of pure overwrite.
   - Add a deterministic deduplication rule for imported points, ideally based on a stable fingerprint of timestamp, coordinates, title, note, photo hash/path, and sensor fields.
   - Merge tags by normalized name, as the current importer already does, but extend it so repeated restores do not create duplicates.
   - Rebuild point-tag relations against the merged point and tag identities.
   - Decide how settings should merge: backup values should overwrite included settings, while omitted settings should leave current values intact.

4. Add periodic backup execution on Android.
   - Introduce WorkManager for scheduled backup jobs, because the project currently has no background scheduler dependency.
   - Create a worker that reads the current data snapshot, runs the ZIP export, and writes it to a user-chosen URI or app-managed backup location.
   - Keep a manual “Back up now” action so the user can trigger a full backup on demand.
   - Surface clear success/failure feedback, especially when storage access or permissions block the write.

5. Expose backup controls in the settings UI.
   - Add a backup section to `SettingsScreen` for manual backup, restore, and schedule controls.
   - Wire `MainActivity` to launch the file picker for restore and backup destination selection.
   - Add settings for backup cadence and, if needed, the chosen destination URI or folder.
   - Keep the current CSV and ZIP export/import actions available, but label them clearly so users can distinguish ad hoc export from backup/restore.

6. Push the state and data plumbing through the app layers.
   - Extend `AppViewModel` with backup and restore entry points, including a restore flow that can load a ZIP, apply settings, import data, and report any skipped duplicates.
   - Add any missing repository/use-case methods needed to export settings cleanly and to restore them in one place.
   - If the backup UI needs a confirmation step, keep it in the UI layer and leave the actual merge logic in the view model or use case layer.

7. Cover the new behavior with tests.
   - Add round-trip tests for backup export/import with points, tags, photos, and settings.
   - Add merge tests that verify duplicate points and tags are not duplicated after re-import.
   - Add compatibility tests to ensure the current ZIP format still imports after the backup changes.
   - Add one test for scheduled backup configuration or worker wiring if WorkManager is introduced in the app module.

**Relevant files**
- `/home/lava/code/map_timeline_tool/app/src/main/java/com/lavacrafter/maptimelinetool/export/ZipExporter.kt` and `/home/lava/code/map_timeline_tool/app/src/main/java/com/lavacrafter/maptimelinetool/export/ZipImporter.kt` — primary backup archive format and import/export logic.
- `/home/lava/code/map_timeline_tool/app/src/main/java/com/lavacrafter/maptimelinetool/ui/SettingsStore.kt` and `/home/lava/code/map_timeline_tool/app/src/main/java/com/lavacrafter/maptimelinetool/data/SettingsRepository.kt` — source of truth for backed-up app settings.
- `/home/lava/code/map_timeline_tool/app/src/main/java/com/lavacrafter/maptimelinetool/ui/SettingsScreen.kt` — backup UI entry points.
- `/home/lava/code/map_timeline_tool/app/src/main/java/com/lavacrafter/maptimelinetool/MainActivity.kt` — file picker launchers and current export/import wiring.
- `/home/lava/code/map_timeline_tool/app/src/main/java/com/lavacrafter/maptimelinetool/ui/AppViewModel.kt` — import/restore orchestration and merge behavior.
- `/home/lava/code/map_timeline_tool/app/src/main/java/com/lavacrafter/maptimelinetool/data/AppDatabase.kt` — database scope and data model boundaries.
- `/home/lava/code/map_timeline_tool/app/src/test/java/com/lavacrafter/maptimelinetool/export/ZipExportImportTest.kt` — current ZIP test baseline to extend.
- `/home/lava/code/map_timeline_tool/app/build.gradle.kts` — add WorkManager if scheduled backups are implemented.

**Verification**
1. Run the ZIP export/import tests and extend them until the backup archive round-trips points, photos, tags, and settings.
2. Verify a current non-backup ZIP still imports successfully to confirm backward compatibility.
3. Manually test a restore into a non-empty database to confirm merge behavior does not duplicate points or tags.
4. If scheduled backups are added, verify the worker can run from WorkManager and that cancellation/retry behaves correctly.
5. Check the settings screen flows on device or emulator: manual backup, restore picker, and schedule toggle.

**Decisions**
- Use full scheduled backups first, not incremental backups. Incremental backup would need change tracking and a more complex restore path, so it should be a later enhancement.
- Reuse the existing ZIP format rather than inventing a second backup file type.
- Treat settings as part of backup scope, but keep map tile caches and downloaded area caches out of scope for now.
- Keep restore merge-based, with settings overwritten only for included keys and data deduplicated by stable identity rules.

**Further Considerations**
1. Cloud backup can be done in two ways: save the ZIP into a cloud provider folder through the system file picker/Storage Access Framework, or add direct provider integration for true background upload.
2. For OneDrive and Google Drive, the lowest-risk first step is SAF-based destination selection; this works if the provider is installed and exposed by the system picker.
3. If you want downloaded areas included later, it should probably be a separate opt-in section because that data can dominate backup size.