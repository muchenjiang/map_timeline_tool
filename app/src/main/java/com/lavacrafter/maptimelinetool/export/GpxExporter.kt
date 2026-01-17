package com.lavacrafter.maptimelinetool.export

import com.lavacrafter.maptimelinetool.data.PointEntity
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object GpxExporter {
    fun exportToFile(points: List<PointEntity>, file: File) {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
        val content = buildString {
            appendLine(<?xml version=\"1.0\" encoding=\"UTF-8\"?>)
            appendLine(<gpx version=\"1.1\" creator=\"map-timeline-tool\" xmlns=\"http://www.topografix.com/GPX/1/1\">)
            points.forEach { p ->
                appendLine(  <wpt lat=\"${'$'}{p.latitude}\" lon=\"${'$'}{p.longitude}\">)
                appendLine(    <time>${'$'}{sdf.format(Date(p.timestamp))}</time>)
                appendLine(    <name>${'$'}{escape(p.note)}</name>)
                appendLine(  </wpt>)
            }
            appendLine(</gpx>)
        }
        file.writeText(content)
    }

    private fun escape(text: String): String =
        text.replace("&", "&amp;")
            .replace("<", "&lt;")
            .replace(">", "&gt;")
}