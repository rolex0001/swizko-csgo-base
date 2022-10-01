package rat.swizko.ui.uiHelpers.tables

import com.badlogic.gdx.utils.Array
import com.kotcrab.vis.ui.widget.VisLabel
import com.kotcrab.vis.ui.widget.VisSelectBox
import com.kotcrab.vis.ui.widget.VisTable
import rat.swizko.curLocale
import rat.swizko.curSettings
import rat.swizko.dbg
import rat.swizko.toLocale
import rat.swizko.ui.changed
import rat.swizko.ui.tabs.categorySelected
import rat.swizko.ui.tabs.gunCategories
import rat.swizko.ui.uiHelpers.VisCheckBoxCustom
import rat.swizko.ui.uiHelpers.VisInputFieldCustom
import rat.swizko.ui.uiHelpers.aimTab.ATabVisCheckBox
import rat.swizko.ui.uiHelpers.aimTab.ATabVisSlider
import rat.swizko.ui.uiPanels.aimTab
import rat.swizko.ui.uiUpdate

class AimTriggerTable: VisTable(false) {
    //Init labels/sliders/boxes that show values here
    val enableTrig = VisCheckBoxCustom("Enable Trigger", "ENABLE_TRIGGER") //Master switch

    val boneTriggerEnableKey = VisCheckBoxCustom("Trigger On Key", "TRIGGER_ENABLE_KEY")
    val boneTriggerKey = VisInputFieldCustom("Trigger Key", "TRIGGER_KEY")

    val trigEnable = ATabVisCheckBox("Enable", "_TRIGGER") //Per weapon category

    val trigAimbot = ATabVisCheckBox("Aimbot", "_TRIGGER_AIMBOT")
    val trigInCross = ATabVisCheckBox("InCross", "_TRIGGER_INCROSS")
    val trigInFov = ATabVisCheckBox("InFov", "_TRIGGER_INFOV")
    val trigFov = ATabVisSlider("FOV", "_TRIGGER_FOV", .5F, 90F, .5F, false)
    val trigShootBacktrack = ATabVisCheckBox("Shoot Backtrack", "_TRIGGER_BACKTRACK")
    val initTrigDelay = ATabVisSlider("First Shot Delay", "_TRIGGER_INIT_SHOT_DELAY", 0F, 500F, 10F, true)
    val perShotTrigDelay = ATabVisSlider("Per Shot Delay", "_TRIGGER_PER_SHOT_DELAY", 0F, 500F, 10F, true)

    //Override Weapon Checkbox & Selection Box
    private val categorySelection = VisTable()
    val categorySelectionBox = VisSelectBox<String>()
    val categorySelectLabel = VisLabel("${"Weapon-Category".toLocale()}:")

    init {
        //Create Category Selector Box
        val itemsArray = Array<String>()
        for (i in gunCategories) {
            if (dbg && curLocale[i].isBlank()) {
                println("[DEBUG] ${curSettings["CURRENT_LOCALE"]} $i is missing!")
            }

            itemsArray.add(curLocale[i])
        }

        categorySelectionBox.items = itemsArray
        categorySelectionBox.selectedIndex = 0

        categorySelected = gunCategories[categorySelectionBox.selectedIndex]
        categorySelection.add(categorySelectLabel).padRight(200F-categorySelectLabel.width)
        categorySelection.add(categorySelectionBox)

        categorySelectionBox.changed { _, _ ->
            categorySelected = gunCategories[categorySelectionBox.selectedIndex]
            aimTab.tAim.categorySelectionBox.selectedIndex = gunCategories.indexOf(categorySelected)
            aimTab.tBacktrack.categorySelectionBox.selectedIndex = gunCategories.indexOf(categorySelected)
            uiUpdate()
            true
        }

        //Default menu size width is 565
        //Default menu size heght is 535
        //Texts are 200
        //Sliders are 250
        //Leaves 25 for left and right side to center
        apply {
            padLeft(25F)
            padRight(25F)

            //Add all items to label for tabbed pane content

            add(enableTrig).left().row()
            add(boneTriggerEnableKey).left().row()
            add(boneTriggerKey).left().row()
            add(categorySelection).left().row()
            add(trigEnable).left().row()

            add(trigAimbot).left().row()
            add(trigInCross).left().row()
            add(trigInFov).left().row()
            add(trigShootBacktrack).left().row()
            add(trigFov).left().row()
            add(initTrigDelay).left().row()
            add(perShotTrigDelay).left().row()

            addSeparator()
        }
    }
}