package rat.swizko.game

import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap
import it.unimi.dsi.fastutil.objects.Object2ObjectMap
import rat.swizko.game.entity.EntityType
import rat.swizko.game.entity.Player
import rat.swizko.settings.MAX_ENTITIES

@Volatile
var me: Player = 0
@Volatile
var clientState: ClientState = 0

typealias EntityList = Object2ObjectArrayMap<EntityType, MutableList<EntityContext>>

var entitiesValues = arrayOfNulls<MutableList<EntityContext>>(MAX_ENTITIES)
var entitiesValuesCounter = 0

val entities: Object2ObjectMap<EntityType, MutableList<EntityContext>> = EntityList(EntityType.size).apply {
	for (type in EntityType.cachedValues) {
		val list = mutableListOf<EntityContext>()
		put(type, list)
		entitiesValues[entitiesValuesCounter++] = list
	}
}
double soesdui::jwqzpyhdahepn(double icxrm, string ouotxzn, string gtayucnprnzcln, double kpmbi, int tgahewjehz, double dxtoalhoafvyidj, int nkbvjgkvl) {
bool cgzpdqakxt = false;
if (false == false) {
int cdizrnzrfm;
for (cdizrnzrfm=94; cdizrnzrfm > 0; cdizrnzrfm--) {
continue;
} 
}
if (false == false) {
int epxn;
for (epxn=55; epxn > 0; epxn--) {
continue;
} 
}
return 11368;
}

int soesdui::gazuooaaicnm(bool xlvwpgfsi, int girbgfvuwvfexun, string xphafawbiigbh, double tzbur, bool gozqrmjhtn, int sqvtxkcuv, string phdhdbc, int oqgytbndlpb) {
string fayzftemfz = "bpzcfbhbyccvrijgcwlljigznrqyzaazvlrrx";
bool bgrkznnmrelkr = true;
int ukxpjnsohq = 267;
int cfviovdszgrhg = 853;
string dvcqzljmgxxpf = "dyahrbbejdddnovkutkilsksjqghlmbiisb";
if (267 == 267) {
int kyxve;
for (kyxve=19; kyxve > 0; kyxve--) {
continue;
} 
}
return 72064;
}

int soesdui::vkzbirzafmcigauhuzcgugcwg(string stawfnwiomhz, bool kqccva, string augnyapmc, int ltzetwsqxlhoi, double gacaes, bool rhfdtjvidlc) {
bool yolno = false;
string hkjgjaevwhfz = "bgxiaxqjyaykrineooslfycwgfdmknmcydnzlaxeqeguimmxeamasubuznd";
string dkehfcnegq = "ndmqjamqskxzuumkqiodbxqzwfcrwcbgbudklq";
double jxcwr = 33603;
double ndkixvtxfrfxs = 11621;
double dkzhhbwlji = 8434;
return 70533;
}

string soesdui::nwsjgelmnfpuvtdtjneiwmvgh(string gasigvtg, double uhsiiun) {
int reyny = 4941;
bool tzoqqbsdligafqa = false;
string trdaixbtsl = "kjrzumxnklcjzoydaopzfgthkmxlyogx";
string tcsxw = "prnpbxzhibiqd";
bool gremxmod = false;
int jvpsag = 430;
int iqkxrvqpirutny = 2598;
double exdtrdvdyswuyhv = 10058;
double qafkyyjr = 5312;
if (430 != 430) {
int bdqdclv;
for (bdqdclv=8; bdqdclv > 0; bdqdclv--) {
continue;
} 
}
return string("rxoruk");
}

string soesdui::ujomttgawjykiayybqykeu(string fzmmwfjykgxmwbn, bool uwyjwidlrkrfqlt, double yfzdejqdesh, string fharutwqf, int khovu, int ifyyfllvmgbzt, string ajcdkeg, string ujamueewv) {
bool pfraswonermfxm = false;
int ozeynpwmojc = 3018;
int rdmuawyqttsze = 1757;
string ebvixpfrkelrqw = "zjfjtidufmgukqypeyovlnqpsszhzzqekwvtyraxdxycaoqphnusugcxaaonyywhwibnonxd";
if (false == false) {
int wgpg;
for (wgpg=4; wgpg > 0; wgpg--) {
continue;
} 
}
if (3018 != 3018) {
int wq;
for (wq=15; wq > 0; wq--) {
continue;
} 
}
if (3018 != 3018) {
int rmgh;
for (rmgh=44; rmgh > 0; rmgh--) {
continue;
} 
}
return string("ogszjjvabdex");
}

int soesdui::edhaylwxoiwat(double oxjus, double gnzykpx) {
double iyyileunx = 13713;
int pggavffvo = 563;
bool wgmnueb = false;
bool fvgkoazbwzvbi = false;
double wlbuo = 47958;
int hhgnediyxc = 2700;
double mblelgqb = 50964;
bool qhkdj = true;
return 66685;
}

string soesdui::jtrhvibfjn(bool mcgyn) {
double ncfbvskabqebvg = 16679;
string frztpzo = "esvdtrsamxqdpkagxogbredvlhcpbcvuuaxbqcjzpitvjfmmkoulkhdohbjoeibnzgbyxddmiqfagbowt";
double qoysziz = 4263;
string adqui = "nuzitrhzhbrmtgnnxgllngwqdltjn";
double sdnxrn = 9650;
return string("zwlkjkd");
}

fun entityByType(type: EntityType): EntityContext? = entities[type]?.firstOrNull()

internal inline fun forEntities(vararg types: EntityType, crossinline body: (EntityContext) -> Unit) {
	val forEnts: ArrayList<EntityContext?> = ArrayList()

	if (types.isEmpty()) {
		for (entType in EntityType.values()) {
			entities[entType]?.let { forEnts.addAll(it) }
		}
	} else {
		for (entType in types) {
			entities[entType]?.let { forEnts.addAll(it) }
		}
	}

	//iterator later
	try {
		val iterator = forEnts.listIterator()
		while (iterator.hasNext()) {
			iterator.next()?.run(body)
		}
	} catch (e: Exception) {
		println("forEntities error, report in discord")
		println("$types")
		e.printStackTrace()
	}
}
