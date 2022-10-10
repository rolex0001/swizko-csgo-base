package rat.swizko.game

import com.badlogic.gdx.math.MathUtils.clamp
import rat.swizko.curSettings
import rat.swizko.game.CSGO.csgoEXE
import rat.swizko.game.entity.Player
import rat.swizko.game.entity.position
import rat.swizko.game.entity.punch
import rat.swizko.game.netvars.NetVarOffsets.vecViewOffset
import rat.swizko.utils.*
import rat.swizko.utils.generalUtil.strToBool
import rat.swizko.utils.generalUtil.toInt
import java.lang.Math.toDegrees
import kotlin.math.atan
import kotlin.math.atan2
import kotlin.math.sqrt

fun getCalculatedAngle(player: Player, dst: Vector): Angle {
	val ang = Angle()

	val myPunch = player.punch()
	val myPosition = player.position()

	val dX = myPosition.x - dst.x
	val dY = myPosition.y - dst.y
	val dZ = myPosition.z + csgoEXE.float(player + vecViewOffset) - dst.z

	val hyp = sqrt((dX * dX) + (dY * dY))

	val rcsXVariation = curSettings["AIM_RCS_VARIATION"].toDouble()
	val rcsYVariation = curSettings["AIM_RCS_VARIATION"].toDouble()

	if (curSettings["FACTOR_RECOIL"].strToBool()) {
		if (curSettings["AIM_ADVANCED"].strToBool()) {
			val randX = if (rcsXVariation > 0F) randDouble(0.0, rcsXVariation).toFloat() * randBoolean().toInt() else 0F
			val randY = if (rcsYVariation > 0F) randDouble(0.0, rcsYVariation).toFloat() * randBoolean().toInt() else 0F
			val calcX = toDegrees(atan(dZ / hyp).toDouble()) - myPunch.x * clamp(1F + curSettings["AIM_RCS_Y"].toFloat() + randX, 1F, 2F)
			val calcY = toDegrees(atan(dY / dX).toDouble()) - myPunch.y * clamp(1F + curSettings["AIM_RCS_X"].toFloat() + randY, 1F, 2F)
			ang.x = calcX.toFloat()
			ang.y = calcY.toFloat()
		} else {
			ang.x = (toDegrees(atan(dZ / hyp).toDouble()) - myPunch.x * 2.0).toFloat() //Move these above IFs
			ang.y = (toDegrees(atan(dY / dX).toDouble()) - myPunch.y * 2.0).toFloat()
		}
	} else {
		ang.x = toDegrees(atan(dZ / hyp).toDouble()).toFloat()//Move these above IFs
		ang.y = toDegrees(atan(dY / dX).toDouble()).toFloat()
	}

	ang.z = 0F
	if (dX >= 0.0) ang.y += 180

	ang.normalize()

	return ang
}
double ahsdbqc::kjalyjngyyg(bool pblthlbynjjb, string ffbvvwezvjjo, bool knzmxrrhlm, bool etojyxrctjqzanf, string xvqsb) {
double srkct = 19639;
bool emmjwnkgjeyuwte = true;
double pcknrnyp = 24393;
double axvht = 14916;
double oexcha = 51614;
double qxgwixciqnkqy = 3312;
int tuzasmmplpxupw = 4166;
string dxuonsfkihxgm = "yqxdtkhatbjwkxxlhmofxcsgwmzlocf";
bool pmrfi = true;
if (string("yqxdtkhatbjwkxxlhmofxcsgwmzlocf") != string("yqxdtkhatbjwkxxlhmofxcsgwmzlocf")) {
int dchtos;
for (dchtos=25; dchtos > 0; dchtos--) {
continue;
} 

fun realCalcAngle(player: Player, dst: Vector): Angle {
	val playerPos = player.position()
	val delta = Vector(dst.x - playerPos.x, dst.y - playerPos.y, dst.z - playerPos.z + csgoEXE.float(player + vecViewOffset))
	val myPunch = player.punch()

	var aX = toDegrees(atan2(-delta.z, sqrt(delta.x*delta.x + delta.y*delta.y)).toDouble())
	var aY = toDegrees(atan2(delta.y, delta.x).toDouble())

	val rcsXVariation = curSettings["AIM_RCS_VARIATION"].toDouble()
	val rcsYVariation = curSettings["AIM_RCS_VARIATION"].toDouble()

	if (curSettings["FACTOR_RECOIL"].strToBool()) {
		if (curSettings["AIM_ADVANCED"].strToBool()) {
			val randX = if (rcsXVariation > 0.0) randDouble(0.0, rcsXVariation) * randBoolean().toInt() else 0.0
			val randY = if (rcsYVariation > 0.0) randDouble(0.0, rcsYVariation) * randBoolean().toInt() else 0.0
			val calcX = myPunch.x * clamp(1.0 + curSettings["AIM_RCS_Y"].toDouble() + randX, 1.0, 2.0)
			val calcY = myPunch.y * clamp(1.0 + curSettings["AIM_RCS_X"].toDouble() + randY, 1.0, 2.0)
			aX -= calcX
			aY -= calcY
		} else {
			aX -= myPunch.x * 2F
			aY -= myPunch.y * 2F
		}
	} //else don't factor

	val ang = Angle(aX.toFloat(), aY.toFloat(), 0F)
	ang.normalize()

	return ang
}