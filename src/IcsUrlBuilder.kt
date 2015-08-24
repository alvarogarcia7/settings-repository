package org.jetbrains.settingsRepository

import com.intellij.openapi.components.RoamingType
import com.intellij.openapi.components.StoragePathMacros
import com.intellij.openapi.util.SystemInfo

val PROJECTS_DIR_NAME: String = "_projects/"

private fun getOsFolderName() = when {
  SystemInfo.isMac -> "_mac"
  SystemInfo.isWindows -> "_windows"
  SystemInfo.isLinux -> "_linux"
  SystemInfo.isFreeBSD -> "_freebsd"
  SystemInfo.isUnix -> "_unix"
  else -> "_unknown"
}

fun buildPath(path: String, roamingType: RoamingType, projectKey: String? = null): String {
  var fixedPath = if (path.startsWith(StoragePathMacros.ROOT_CONFIG)) {
    path.substring(StoragePathMacros.ROOT_CONFIG.length() + 1)
  }
  else if (path.startsWith(StoragePathMacros.APP_CONFIG)) {
    path.substring(StoragePathMacros.APP_CONFIG.length() + 1)
  }
  else {
    path
  }

  fun String.osIfNeed() = if (roamingType == RoamingType.PER_PLATFORM) "${getOsFolderName()}/$this" else this

  return if (projectKey == null) fixedPath.osIfNeed() else "$PROJECTS_DIR_NAME$projectKey/$fixedPath"
}