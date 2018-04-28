package pt.joaomneto.titancompanion.adventure.twofm

import org.junit.Assert
import org.junit.Test
import pt.joaomneto.titancompanion.adventure.impl.TWOFMAdventure
import pt.joaomneto.titancompanion.adventure.state.AdventureState
import pt.joaomneto.titancompanion.adventure.state.DefaultStateKey.CURRENT_LUCK
import pt.joaomneto.titancompanion.adventure.state.DefaultStateKey.CURRENT_SKILL
import pt.joaomneto.titancompanion.adventure.state.DefaultStateKey.CURRENT_STAMINA
import pt.joaomneto.titancompanion.adventure.state.DefaultStateKey.EQUIPMENT
import pt.joaomneto.titancompanion.adventure.state.DefaultStateKey.GOLD
import pt.joaomneto.titancompanion.adventure.state.DefaultStateKey.INITIAL_LUCK
import pt.joaomneto.titancompanion.adventure.state.DefaultStateKey.INITIAL_SKILL
import pt.joaomneto.titancompanion.adventure.state.DefaultStateKey.INITIAL_STAMINA
import pt.joaomneto.titancompanion.adventure.state.DefaultStateKey.NOTES
import pt.joaomneto.titancompanion.adventure.state.DefaultStateKey.PROVISIONS
import pt.joaomneto.titancompanion.adventure.state.DefaultStateKey.STANDARD_POTION_VALUE

class TWOFMAdventureStateTest {

    private var adventure = TWOFMAdventure(emptyArray())

    @Test
    fun `when increasing stamina it returns a state with the stamina incremented`() {
        with(adventure) {
            val state = AdventureState(mapOf(CURRENT_STAMINA to 5, INITIAL_STAMINA to 6))
            Assert.assertEquals(6, state.increaseStamina().currentStamina)
        }
    }

    @Test
    fun `when increasing stamina with value it returns a state with the stamina incremented`() {
        with(adventure) {
            val state = AdventureState(mapOf(CURRENT_STAMINA to 5, INITIAL_STAMINA to 10))
            Assert.assertEquals(10, state.increaseStamina(5).currentStamina)
        }
    }

    @Test
    fun `does nothing increasing stamina over the initial value`() {
        with(adventure) {
            val state = AdventureState(mapOf(CURRENT_STAMINA to 5, INITIAL_STAMINA to 5))
            Assert.assertEquals(5, state.increaseStamina().currentStamina)
        }
    }

    @Test
    fun `when decreasing stamina it returns a state with the stamina decremented`() {
        with(adventure) {
            val state = AdventureState(mapOf(CURRENT_STAMINA to 5))
            Assert.assertEquals(4, state.decreaseStamina().currentStamina)
        }
    }

    @Test
    fun `when decreasing stamina with value it returns a state with the stamina decremented`() {
        with(adventure) {
            val state = AdventureState(mapOf(CURRENT_STAMINA to 5))
            Assert.assertEquals(0, state.decreaseStamina(5).currentStamina)
        }
    }

    @Test
    fun `does nothing decreasing stamina under zero`() {
        with(adventure) {
            val state = AdventureState(mapOf(CURRENT_STAMINA to 0))
            Assert.assertEquals(0, state.decreaseStamina().currentStamina)
        }
    }

    @Test
    fun `when changing initial stamina it returns a state with the initial stamina with the new value`() {
        with(adventure) {
            val state = AdventureState(mapOf(INITIAL_STAMINA to 5))
            Assert.assertEquals(5, state.changeInitialStamina(5).initialStamina)
        }
    }

    @Test
    fun `when setting the initial stamina value under zero it sets to zero`() {
        with(adventure) {
            val state = AdventureState(mapOf(CURRENT_STAMINA to 0))
            Assert.assertEquals(0, state.changeInitialStamina(-5).initialStamina)
        }
    }

    @Test
    fun `when increasing skill it returns a state with the skill incremented`() {
        with(adventure) {
            val state = AdventureState(mapOf(CURRENT_SKILL to 5, INITIAL_SKILL to 6))
            Assert.assertEquals(6, state.increaseSkill().currentSkill)
        }
    }

    @Test
    fun `when increasing skill with value it returns a state with the skill incremented`() {
        with(adventure) {
            val state = AdventureState(mapOf(CURRENT_SKILL to 5, INITIAL_SKILL to 10))
            Assert.assertEquals(10, state.increaseSkill(5).currentSkill)
        }
    }

    @Test
    fun `does nothing increasing skill over the initial value`() {
        with(adventure) {
            val state = AdventureState(mapOf(CURRENT_SKILL to 5, INITIAL_SKILL to 5))
            Assert.assertEquals(5, state.increaseSkill().currentSkill)
        }
    }

    @Test
    fun `when decreasing skill it returns a state with the skill decremented`() {
        with(adventure) {
            val state = AdventureState(mapOf(CURRENT_SKILL to 5))
            Assert.assertEquals(4, state.decreaseSkill().currentSkill)
        }
    }

    @Test
    fun `when decreasing skill with value it returns a state with the skill decremented`() {
        with(adventure) {
            val state = AdventureState(mapOf(CURRENT_SKILL to 5))
            Assert.assertEquals(0, state.decreaseSkill(5).currentSkill)
        }
    }

    @Test
    fun `does nothing decreasing skill under zero`() {
        with(adventure) {
            val state = AdventureState(mapOf(CURRENT_SKILL to 0))
            Assert.assertEquals(0, state.decreaseSkill().currentSkill)
        }
    }

    @Test
    fun `when changing initial skill it returns a state with the initial skill with the new value`() {
        with(adventure) {
            val state = AdventureState(mapOf(INITIAL_SKILL to 5))
            Assert.assertEquals(5, state.changeInitialSkill(5).initialSkill)
        }
    }

    @Test
    fun `when setting the initial skill value under zero it sets to zero`() {
        with(adventure) {
            val state = AdventureState(mapOf(CURRENT_SKILL to 0))
            Assert.assertEquals(0, state.changeInitialSkill(-5).initialSkill)
        }
    }

    @Test
    fun `when increasing luck it returns a state with the luck incremented`() {
        with(adventure) {
            val state = AdventureState(mapOf(CURRENT_LUCK to 5, INITIAL_LUCK to 6))
            Assert.assertEquals(6, state.increaseLuck().currentLuck)
        }
    }

    @Test
    fun `when increasing luck with value it returns a state with the luck incremented`() {
        with(adventure) {
            val state = AdventureState(mapOf(CURRENT_LUCK to 5, INITIAL_LUCK to 10))
            Assert.assertEquals(10, state.increaseLuck(5).currentLuck)
        }
    }

    @Test
    fun `does nothing increasing luck over the initial value`() {
        with(adventure) {
            val state = AdventureState(mapOf(CURRENT_LUCK to 5, INITIAL_LUCK to 5))
            Assert.assertEquals(5, state.increaseLuck().currentLuck)
        }
    }

    @Test
    fun `when decreasing luck it returns a state with the luck decremented`() {
        with(adventure) {
            val state = AdventureState(mapOf(CURRENT_LUCK to 5))
            Assert.assertEquals(4, state.decreaseLuck().currentLuck)
        }
    }

    @Test
    fun `when decreasing luck with value it returns a state with the luck decremented`() {
        with(adventure) {
            val state = AdventureState(mapOf(CURRENT_LUCK to 5))
            Assert.assertEquals(0, state.decreaseLuck(5).currentLuck)
        }
    }

    @Test
    fun `does nothing decreasing luck under zero`() {
        with(adventure) {
            val state = AdventureState(mapOf(CURRENT_LUCK to 0))
            Assert.assertEquals(0, state.decreaseLuck().currentLuck)
        }
    }

    @Test
    fun `when changing initial luck it returns a state with the initial luck with the new value`() {
        with(adventure) {
            val state = AdventureState(mapOf(INITIAL_LUCK to 5))
            Assert.assertEquals(5, state.changeInitialLuck(5).initialLuck)
        }
    }

    @Test
    fun `when setting the initial luck value under zero it sets to zero`() {
        with(adventure) {
            val state = AdventureState(mapOf(CURRENT_LUCK to 0))
            Assert.assertEquals(0, state.changeInitialLuck(-5).initialLuck)
        }
    }

    @Test
    fun `when increasing provisions it returns a state with the provisions incremented`() {
        with(adventure) {
            val state = AdventureState(mapOf(PROVISIONS to 5))
            Assert.assertEquals(6, state.increaseProvisions().provisions)
        }
    }

    @Test
    fun `when increasing provisions with value it returns a state with the provisions incremented`() {
        with(adventure) {
            val state = AdventureState(mapOf(PROVISIONS to 5))
            Assert.assertEquals(10, state.increaseProvisions(5).provisions)
        }
    }

    @Test
    fun `when decreasing provisions it returns a state with the provisions decremented`() {
        with(adventure) {
            val state = AdventureState(mapOf(PROVISIONS to 5))
            Assert.assertEquals(4, state.decreaseProvisions().provisions)
        }
    }

    @Test
    fun `when decreasing provisions with value it returns a state with the provisions decremented`() {
        with(adventure) {
            val state = AdventureState(mapOf(PROVISIONS to 5))
            Assert.assertEquals(0, state.decreaseProvisions(5).provisions)
        }
    }

    @Test
    fun `does nothing decreasing provisions under zero`() {
        with(adventure) {
            val state = AdventureState(mapOf(PROVISIONS to 0))
            Assert.assertEquals(0, state.decreaseProvisions().provisions)
        }
    }

    @Test
    fun `when increasing gold it returns a state with the gold incremented`() {
        with(adventure) {
            val state = AdventureState(mapOf(GOLD to 5))
            Assert.assertEquals(6, state.increaseGold().gold)
        }
    }

    @Test
    fun `when increasing gold with value it returns a state with the gold incremented`() {
        with(adventure) {
            val state = AdventureState(mapOf(GOLD to 5))
            Assert.assertEquals(10, state.increaseGold(5).gold)
        }
    }

    @Test
    fun `when decreasing gold it returns a state with the gold decremented`() {
        with(adventure) {
            val state = AdventureState(mapOf(GOLD to 5))
            Assert.assertEquals(4, state.decreaseGold().gold)
        }
    }

    @Test
    fun `when decreasing gold with value it returns a state with the gold decremented`() {
        with(adventure) {
            val state = AdventureState(mapOf(GOLD to 5))
            Assert.assertEquals(0, state.decreaseGold(5).gold)
        }
    }

    @Test
    fun `does nothing decreasing gold under zero`() {
        with(adventure) {
            val state = AdventureState(mapOf(GOLD to 0))
            Assert.assertEquals(0, state.decreaseGold().gold)
        }
    }

    @Test
    fun `when decreasing potion it returns a state with the potion decremented`() {
        with(adventure) {
            val state = AdventureState(mapOf(STANDARD_POTION_VALUE to 5))
            Assert.assertEquals(4, state.decreasePotion().standardPotionValue)
        }
    }

    @Test
    fun `when decreasing potion with value it returns a state with the potion decremented`() {
        with(adventure) {
            val state = AdventureState(mapOf(STANDARD_POTION_VALUE to 5))
            Assert.assertEquals(0, state.decreasePotion(5).standardPotionValue)
        }
    }

    @Test
    fun `does nothing decreasing potion under zero`() {
        with(adventure) {
            val state = AdventureState(mapOf(STANDARD_POTION_VALUE to 0))
            Assert.assertEquals(0, state.decreasePotion().standardPotionValue)
        }
    }

    @Test
    fun `when resetting stamina current stamina is changed to initial value`() {
        with(adventure) {
            val state = AdventureState(mapOf(CURRENT_STAMINA to 5, INITIAL_STAMINA to 10)).resetStamina()
            Assert.assertEquals(state.initialStamina, state.currentStamina)
        }
    }

    @Test
    fun `when resetting skill current skill is changed to initial value`() {
        with(adventure) {
            val state = AdventureState(mapOf(CURRENT_SKILL to 5, INITIAL_SKILL to 10)).resetSkill()
            Assert.assertEquals(state.initialSkill, state.currentSkill)
        }
    }

    @Test
    fun `when resetting luck current luck is changed to initial value`() {
        with(adventure) {
            val state = AdventureState(mapOf(CURRENT_LUCK to 5, INITIAL_LUCK to 10)).resetLuck()
            Assert.assertEquals(state.initialLuck, state.currentLuck)
        }
    }

    @Test
    fun `when changing the gold value it returns a state with gold as the new value`() {
        with(adventure) {
            val state = AdventureState(mapOf(GOLD to 5))
            Assert.assertEquals(10, state.changeGold(10).gold)
        }
    }

    @Test
    fun `when setting the gold value value under zero it sets to zero`() {
        with(adventure) {
            val state = AdventureState(mapOf(GOLD to 0))
            Assert.assertEquals(0, state.changeGold(-5).gold)
        }
    }

    @Test
    fun `when adding equipment it returns a state with the equipment added`() {
        with(adventure) {
            val state = AdventureState(mapOf(EQUIPMENT to listOf("eq1")))
            Assert.assertEquals(listOf("eq1", "eq2"), state.addEquipment("eq2").equipment)
        }
    }

    @Test
    fun `does nothing when adding empty string equipment`() {
        with(adventure) {
            val state = AdventureState(mapOf(EQUIPMENT to listOf("eq1")))
            Assert.assertEquals(listOf("eq1"), state.addEquipment("").equipment)
        }
    }

    @Test
    fun `when removing equipment it returns a state with the equipment removed`() {
        with(adventure) {
            val state = AdventureState(mapOf(EQUIPMENT to listOf("eq1", "eq2", "eq3")))
            Assert.assertEquals(listOf("eq1", "eq3"), state.removeEquipment("eq2").equipment)
        }
    }

    @Test
    fun `does nothing when removing equipment that doesn't exist in the list`() {
        with(adventure) {
            val state = AdventureState(mapOf(EQUIPMENT to listOf("eq1", "eq2", "eq3")))
            Assert.assertEquals(listOf("eq1", "eq2", "eq3"), state.removeEquipment("eq4").equipment)
        }
    }

    @Test
    fun `when adding notes it returns a state with the note added`() {
        with(adventure) {
            val state = AdventureState(mapOf(NOTES to listOf("n1")))
            Assert.assertEquals(listOf("n1", "n2"), state.addNote("n2").notes)
        }
    }

    @Test
    fun `does nothing when adding empty string notes`() {
        with(adventure) {
            val state = AdventureState(mapOf(NOTES to listOf("n1")))
            Assert.assertEquals(listOf("n1"), state.addNote("").notes)
        }
    }

    @Test
    fun `when removing notes it returns a state with the notes removed`() {
        with(adventure) {
            val state = AdventureState(mapOf(NOTES to listOf("n1", "n2", "n3")))
            Assert.assertEquals(listOf("n1", "n3"), state.removeNote("n2").notes)
        }
    }

    @Test
    fun `does nothing when removing notes that doesn't exist in the list`() {
        with(adventure) {
            val state = AdventureState(mapOf(NOTES to listOf("n1", "n2", "n3")))
            Assert.assertEquals(listOf("n1", "n2", "n3"), state.removeNote("n4").notes)
        }
    }
}
