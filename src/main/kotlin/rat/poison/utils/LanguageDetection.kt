package rat.swizko.utils

import rat.swizko.SETTINGS_DIRECTORY
import rat.swizko.curSettings
import rat.swizko.utils.generalUtil.loadLocale
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*

fun getSystemLocale(): Locale? {
    return Locale.getDefault()
}

fun detectLocale() {
    val locale = getSystemLocale()
    val dir = "$SETTINGS_DIRECTORY/Localizations/locale_${locale}.locale"
    if (locale != null && Files.exists(Paths.get(dir))) {
        loadLocale(dir)
    }
    else {
        loadLocale("$SETTINGS_DIRECTORY/Localizations/locale_en_US.locale")
        curSettings["CURRENT_LOCALE"] = "locale_en_US"
    }
}