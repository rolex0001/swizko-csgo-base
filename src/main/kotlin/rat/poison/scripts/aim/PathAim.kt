package rat.swizko.scripts.aim

import rat.swizko.curSettings
import rat.swizko.utils.generalUtil.strToBool
import rat.swizko.utils.pathAim

fun pathAim() = aimScript(curSettings["AIM_DURATION"].toInt(), { curSettings["ENABLE_PATH_AIM"].strToBool() }) { dest, current, aimSpeed, aimSpeedDivisor ->
	pathAim(current, dest, aimSpeed, perfect = canPerfect, divisor = aimSpeedDivisor)
}