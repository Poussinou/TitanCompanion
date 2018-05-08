package pt.joaomneto.titancompanion.adventure.state

import pt.joaomneto.titancompanion.adventure.state.DefaultStateKey.*
import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook

open class AdventureState(
        open val values: Map<out StateKey, Any>
) {

    val gamebook: FightingFantasyGamebook
        get() = values[GAMEBOOK] as FightingFantasyGamebook
    val name: String
        get() = values[NAME] as String
    val initialSkill: Int
        get() = values[INITIAL_SKILL] as Int
    val initialLuck: Int
        get() = values[INITIAL_LUCK] as Int
    val initialStamina: Int
        get() = values[INITIAL_STAMINA] as Int
    val currentSkill: Int
        get() = values[CURRENT_SKILL] as Int
    val currentLuck: Int
        get() = values[CURRENT_LUCK] as Int
    val currentStamina: Int
        get() = values[CURRENT_STAMINA] as Int
    val equipment: List<String>
        get() = values[EQUIPMENT] as List<String>
    val notes: List<String>
        get() = values[NOTES] as List<String>
    val gold: Int
        get() = values[GOLD] as Int
    val provisions: Int
        get() = values[PROVISIONS] as Int
    val provisionsValue: Int
        get() = values[PROVISIONS_VALUE] as Int
    val currentReference: Int
        get() = values[CURRENT_REFERENCE] as Int
    val standardPotion: Int
        get() = values[STANDARD_POTION] as Int
    val standardPotionValue: Int
        get() = values[STANDARD_POTION_VALUE] as Int

    constructor(state: AdventureState) : this(
            state.values
    )

    fun checkSkill(): Boolean = pt.joaomneto.titancompanion.util.DiceRoller.roll2D6().sum <= currentSkill

    fun checkLuck(): Boolean = pt.joaomneto.titancompanion.util.DiceRoller.roll2D6().sum <= currentLuck

    fun isMaxStamina(): Boolean = currentStamina == initialStamina

    fun notesAsString() = pt.joaomneto.titancompanion.adventure.state.AdventureState.stringListToText(notes)

    fun toSavegameString(): String {
        return values
                .map {
                    "${it.key.saveFileKey}=${it.key.serializer?.invoke(it.value) ?: it.value}"
                }
                .sorted()
                .joinToString(separator = "\n")
    }

    open fun storeAdventureSpecificValuesInFile(): String = ""

    companion object {
        fun toValueMap(
                gamebook: FightingFantasyGamebook,
                name: String,
                initialSkill: Int,
                initialLuck: Int,
                initialStamina: Int,
                currentSkill: Int,
                currentLuck: Int,
                currentStamina: Int,
                equipment: List<String>,
                notes: List<String>,
                gold: Int,
                provisions: Int,
                provisionsValue: Int,
                currentReference: Int,
                standardPotion: Int,
                standardPotionValue: Int
        ) =
                mapOf(
                        GAMEBOOK to gamebook,
                        NAME to name,
                        INITIAL_SKILL to initialSkill,
                        INITIAL_LUCK to initialLuck,
                        INITIAL_STAMINA to initialStamina,
                        CURRENT_SKILL to currentSkill,
                        CURRENT_LUCK to currentLuck,
                        CURRENT_STAMINA to currentStamina,
                        EQUIPMENT to equipment,
                        NOTES to notes,
                        GOLD to gold,
                        PROVISIONS to provisions,
                        PROVISIONS_VALUE to provisionsValue,
                        CURRENT_REFERENCE to currentReference,
                        STANDARD_POTION to standardPotion,
                        STANDARD_POTION_VALUE to standardPotionValue
                )

        fun arrayToString(elements: Collection<Any>): String {
            var _string = ""

            if (!elements.isEmpty()) {
                for (value in elements) {
                    _string += value.toString() + "#"
                }
                _string = _string.substring(0, _string.length - 1)
            }
            return _string
        }

        fun <Y : Enum<Y>> enumListToText(list: List<Y>): String {
            var text = ""

            if (!list.isEmpty()) {
                for (note in list) {
                    text += note.name + "#"
                }
                text = text.substring(0, text.length - 1)
            }
            return text
        }

        fun <Y : Enum<Y>> enumMapToText(map: Map<Y, Int>): String {
            var text = ""

            if (!map.isEmpty()) {
                for (enum in map.keys) {
                    text += enum.name + "$" + map[enum] + "#"
                }
                text = text.substring(0, text.length - 1)
            }
            return text
        }

        fun stringListToText(list: List<String>): String {
            var text = ""

            if (!list.isEmpty()) {
                for (note in list) {
                    text += note + "#"
                }
                text = text.substring(0, text.length - 1)
            }
            return text
        }

        fun stringToStringList(text: String) = text.split("#".toRegex()).filter { it.isNotEmpty() }

        inline fun <reified Y : kotlin.Enum<Y>> stringToEnumList(equipmentS: String): List<Y> {
            return stringToStringList(equipmentS).map {
                safeValueOf<Y>(
                        it
                )!!
            }
        }

        inline fun <reified Y : kotlin.Enum<Y>> stringToEnumMap(equipmentS: String): Map<Y, Int> {
            return stringToStringList(equipmentS).map {
                val tokens = it.split("§")
                safeValueOf<Y>(tokens[0])!! to tokens[1].toInt()
            }.toMap()
        }

        inline fun <reified T : kotlin.Enum<T>> safeValueOf(type: String?): T? {
            return java.lang.Enum.valueOf(T::class.java, type)
        }
    }
}

enum class DefaultStateKey(
        override val saveFileKey: String,
        override val serializer: ((Any) -> String)? = null
) : StateKey {
    GAMEBOOK("gamebook"),
    NAME("name"),
    INITIAL_SKILL("initialSkill"),
    INITIAL_LUCK("initialLuck"),
    INITIAL_STAMINA("initialStamina"),
    CURRENT_SKILL("currentSkill"),
    CURRENT_LUCK("currentLuck"),
    CURRENT_STAMINA("currentStamina"),
    EQUIPMENT("equipment", {
        AdventureState.stringListToText(
                it as List<String>
        )
    }),
    NOTES("notes", {
        AdventureState.stringListToText(
                it as List<String>
        )
    }),
    GOLD("gold"),
    PROVISIONS("provisions"),
    PROVISIONS_VALUE("provisionsValue"),
    CURRENT_REFERENCE("currentReference"),
    STANDARD_POTION("standardPotion"),
    STANDARD_POTION_VALUE("standardPotionValue")
}
