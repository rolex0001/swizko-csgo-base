package rat.swizko.game

import com.sun.jna.Pointer
import com.sun.jna.platform.win32.WinDef
import org.jire.arrowhead.Module
import org.jire.arrowhead.Process
import org.jire.arrowhead.processByName
import rat.swizko.DEFAULT_MENU_APP
import rat.swizko.dbg
import rat.swizko.game.hooks.constructEntities
import rat.swizko.game.hooks.updateCursorEnable
import rat.swizko.game.netvars.NetVars
import rat.swizko.settings.CLIENT_MODULE_NAME
import rat.swizko.settings.ENGINE_MODULE_NAME
import rat.swizko.settings.PROCESS_ACCESS_FLAGS
import rat.swizko.settings.PROCESS_NAME
import rat.swizko.utils.every
import rat.swizko.utils.inBackground
import rat.swizko.utils.natives.CUser32
import rat.swizko.utils.retry
import kotlin.system.exitProcess

object CSGO {
	const val ENTITY_SIZE = 16
	const val GLOW_OBJECT_SIZE = 56

	lateinit var csgoEXE: Process
		private set

	lateinit var clientDLL: Module
		private set
	lateinit var engineDLL: Module
		private set

	var gameHeight: Int = 0
		private set

	var gameX: Int = 0
		private set

	var gameWidth: Int = 0
		private set

	var gameY: Int = 0
		private set

	fun initialize() {
		if (dbg) println("[DEBUG] Searching for and initializing CSGO")

		retry(128) {
			csgoEXE = processByName(PROCESS_NAME, PROCESS_ACCESS_FLAGS)!!
		}

		retry(128) {
			csgoEXE.loadModules()
			engineDLL = csgoEXE.modules[ENGINE_MODULE_NAME]!!
			clientDLL = csgoEXE.modules[CLIENT_MODULE_NAME]!!
		}

		val rect = WinDef.RECT()
		val hwd = CUser32.FindWindowA(null, DEFAULT_MENU_APP)

		//Get initially
		if (!CUser32.GetClientRect(hwd, rect)) exitProcess(2)
		gameWidth = rect.right - rect.left
		gameHeight = rect.bottom - rect.top

		if (!CUser32.GetWindowRect(hwd, rect)) exitProcess(3)
		gameX = rect.left + (((rect.right - rect.left) - gameWidth) / 2)
		gameY = rect.top + ((rect.bottom - rect.top) - gameHeight)

		every(1000) {
			if (!CUser32.GetClientRect(hwd, rect)) exitProcess(2)
			gameWidth = rect.right - rect.left
			gameHeight = rect.bottom - rect.top

			if (!CUser32.GetWindowRect(hwd, rect)) exitProcess(3)
			gameX = rect.left + (((rect.right - rect.left) - gameWidth) / 2)
			gameY = rect.top + ((rect.bottom - rect.top) - gameHeight)
		}

		every(1000, continuous = true) {
			inBackground = Pointer.nativeValue(hwd.pointer) != CUser32.GetForegroundWindow()
		}

		NetVars.load()

		constructEntities()
		updateCursorEnable()

		if (dbg) println("[DEBUG] CSGO initialized")
	}
}