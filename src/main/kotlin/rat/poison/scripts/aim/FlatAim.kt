package rat.swizko.scripts.aim

import rat.swizko.curSettings
import rat.swizko.utils.generalUtil.strToBool
import rat.swizko.utils.writeAim

fun flatAim() = aimScript(curSettings["AIM_DURATION"].toInt(), { curSettings["ENABLE_FLAT_AIM"].strToBool() }) { dest, current, aimSpeed, aimSpeedDivisor ->
	writeAim(current, dest, aimSpeed.toFloat(), divisor = aimSpeedDivisor)
}