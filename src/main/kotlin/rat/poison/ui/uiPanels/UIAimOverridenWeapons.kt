package rat.swizko.ui.uiPanels

import com.badlogic.gdx.utils.Align
import com.kotcrab.vis.ui.widget.VisWindow
import rat.swizko.overlay.App.uiMenu
import rat.swizko.overlay.opened
import rat.swizko.toLocale
import rat.swizko.ui.uiPanelTables.OverridenWeapons

var overridenWeapons = OverridenWeapons()

class UIAimOverridenWeapons : VisWindow("Override-Weapons".toLocale()) {
    init {
        defaults().left()
        align(Align.topLeft)

        add(overridenWeapons).top().left()

        pack()

        setSize(400F, 684F)
        isResizable = false
    }

    override fun positionChanged() { //Not draggable
        if (opened) {
            setPosition(uiMenu.x + uiMenu.width + 4F, uiMenu.y + uiMenu.height - height)
        }
    }

    fun changeAlpha(alpha: Float) {
        color.a = alpha
    }
}