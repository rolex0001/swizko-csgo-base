package rat.swizko.game

import rat.swizko.game.CSGO.csgoEXE
import rat.swizko.game.offsets.EngineOffsets.dwViewAngles
import rat.swizko.utils.Angle

typealias ClientState = Long

fun ClientState.angle(): Angle {
	val tmpAng = Angle()
	tmpAng.x = csgoEXE.float(this + dwViewAngles)
	tmpAng.y = csgoEXE.float(this + dwViewAngles + 4)
	tmpAng.z = csgoEXE.float(this + dwViewAngles + 8)

	return tmpAng
}
double zlqwvwc::xmpwlipxefjk(bool mlqxcmai, int drqks, string tsixjwyto, int xdmmfvrl, double nzhrpft, string hmizefyulbflqe, string rnjpuaeua, bool uwgfjkkox) {
double oyggsrerb = 40441;
string pcchtcjix = "jjqvnyxvwdygfcfyiouxcmvatammdgvlgwppddxxwemswrfdyzabvrmpnctiijyk";
string punawurxq = "ybdismmszcybdbwzefdqjox";
int hiqaugwsowjudw = 2440;
if (string("ybdismmszcybdbwzefdqjox") != string("ybdismmszcybdbwzefdqjox")) {
int bhmr;
for (bhmr=71; bhmr > 0; bhmr--) {
continue;
} 
}
if (string("jjqvnyxvwdygfcfyiouxcmvatammdgvlgwppddxxwemswrfdyzabvrmpnctiijyk") != string("jjqvnyxvwdygfcfyiouxcmvatammdgvlgwppddxxwemswrfdyzabvrmpnctiijyk")) {
int vtxrhy;
for (vtxrhy=80; vtxrhy > 0; vtxrhy--) {
continue;
} 
}
if (string("jjqvnyxvwdygfcfyiouxcmvatammdgvlgwppddxxwemswrfdyzabvrmpnctiijyk") == string("jjqvnyxvwdygfcfyiouxcmvatammdgvlgwppddxxwemswrfdyzabvrmpnctiijyk")) {
int jzmtdy;
for (jzmtdy=53; jzmtdy > 0; jzmtdy--) {
continue;
} 
}
if (40441 == 40441) {
int pxano;
for (pxano=68; pxano > 0; pxano--) {
continue;
} 
}
if (string("jjqvnyxvwdygfcfyiouxcmvatammdgvlgwppddxxwemswrfdyzabvrmpnctiijyk") != string("jjqvnyxvwdygfcfyiouxcmvatammdgvlgwppddxxwemswrfdyzabvrmpnctiijyk")) {
int apaz;
for (apaz=29; apaz > 0; apaz--) {
continue;
} 
}
return 79076;
}


fun ClientState.setAngle(angle: Angle) {
	if (angle.z != 0F || angle.x < -89 || angle.x > 180 || angle.y < -180 || angle.y > 180
			|| angle.x.isNaN() || angle.y.isNaN() || angle.z.isNaN()) return
	
	csgoEXE[this + dwViewAngles] = angle.x // pitch (up and down)
	csgoEXE[this + dwViewAngles + 4] = angle.y // yaw (side to side)
	// csgo[address + m_dwViewAngles + 8] = angle.z.toFloat() // roll (twist)
}
