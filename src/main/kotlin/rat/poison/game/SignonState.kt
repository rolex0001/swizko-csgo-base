package rat.swizko.game

import rat.swizko.utils.extensions.EnumLookUpWithDefault

@Suppress("unused")
enum class SignOnState {
    MAIN_MENU,
    CHALLENGE,
    CONNECTED,
    NEW,
    PRE_SPAWN,
    SPAWN,
    IN_GAME,
    CHANGE_LEVEL;

    companion object : EnumLookUpWithDefault<SignOnState>(values().associateBy(SignOnState::ordinal), MAIN_MENU)
}