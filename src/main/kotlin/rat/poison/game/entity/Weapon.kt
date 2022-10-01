package rat.swizko.game.entity

import rat.swizko.game.CSGO.csgoEXE
import rat.swizko.game.Weapons
import rat.swizko.game.me
import rat.swizko.game.netvars.NetVarOffsets
import rat.swizko.game.netvars.NetVarOffsets.bInReload
import rat.swizko.game.netvars.NetVarOffsets.flNextPrimaryAttack
import rat.swizko.game.netvars.NetVarOffsets.iClip1
import rat.swizko.utils.extensions.uint

typealias Weapon = Long

internal fun Weapon.bullets() = csgoEXE.uint(this + iClip1)

internal fun Weapon.inReload() = csgoEXE.boolean(this + bInReload)

internal fun Weapon.nextPrimaryAttack() = csgoEXE.float(this + flNextPrimaryAttack).toDouble()

internal fun Weapon.canFire(): Boolean = if (bullets() > 0) {
	val nextAttack = nextPrimaryAttack()
	nextAttack <= 0 || nextAttack < me.time()
} else false

internal fun Weapon.type(): Weapons {
	var id = 42
	if (this > 0)
		id = csgoEXE.short(this + NetVarOffsets.iItemDefinitionIndex).toInt()

	return Weapons[id]
}

internal fun Weapon.index(): Int = csgoEXE.short(this + NetVarOffsets.iItemDefinitionIndex).toInt()