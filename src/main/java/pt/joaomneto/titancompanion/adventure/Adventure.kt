package pt.joaomneto.titancompanion.adventure

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.text.InputType
import android.text.method.DigitsKeyListener
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import pt.joaomneto.titancompanion.BaseFragmentActivity
import pt.joaomneto.titancompanion.LoadAdventureActivity
import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventure.state.AdventureState
import pt.joaomneto.titancompanion.adventure.state.DefaultStateKey
import pt.joaomneto.titancompanion.adventure.state.StateKey
import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook
import pt.joaomneto.titancompanion.util.AdventureFragmentRunner
import pt.joaomneto.titancompanion.util.DiceRoller
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.FileReader
import java.io.FileWriter
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.io.PrintWriter
import java.io.StringReader
import java.util.ArrayList
import java.util.Arrays
import java.util.Date
import java.util.HashSet
import java.util.Properties
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor

abstract class Adventure<T : AdventureState>(
    override val fragmentConfiguration: Array<AdventureFragmentRunner>,
    private val kClass: KClass<T>
) : BaseFragmentActivity(
    fragmentConfiguration,
    R.layout.activity_adventure
) {

    lateinit var state: T

    lateinit var name: String

    private var dir: File? = null

    open val consumeProvisionText: Int = R.string.consumeProvisions

    open val provisionsText: Int = R.string.provisions

    open val currencyName: Int = R.string.gold

    open val combatSkillValue: Int
        get() = state.currentSkill

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val savedGame = loadSavegameProperties()

        loadSavegame(savedGame)
    }

    fun copyState(key: StateKey, value: Any): T {
        return state.copy(key, value)
    }

    private fun loadSavegameProperties(): Properties {
        val savedGame = Properties()

        if (intent.hasExtra(LoadAdventureActivity.ADVENTURE_FILE)) {
            val fileName = intent.getStringExtra(LoadAdventureActivity.ADVENTURE_FILE)
            name = intent.getStringExtra(LoadAdventureActivity.ADVENTURE_DIR)
            dir = File(File(filesDir, "ffgbutil"), name)

            savedGame.load(InputStreamReader(FileInputStream(File(dir, fileName)), "UTF-8"))
        } else if (intent.hasExtra(LoadAdventureActivity.ADVENTURE_SAVEGAME_CONTENT)) {
            val savegameContent = intent.getStringExtra(LoadAdventureActivity.ADVENTURE_SAVEGAME_CONTENT)
            savedGame.load(StringReader(savegameContent))
        }
        return savedGame
    }

    private fun loadStateFromSavedGame(savedGame: Properties): T {

        val gamebookS = savedGame.getProperty("gamebook")
        val name = savedGame.getProperty("name")
        val numbericGbs = gamebookS.toIntOrNull()
        val gamebook = if (numbericGbs != null)
            FightingFantasyGamebook.values()[numbericGbs]
        else
            FightingFantasyGamebook.valueOf(gamebookS)

        val initialSkill = Integer.valueOf(savedGame.getProperty("initialSkill"))
        val initialLuck = Integer.valueOf(savedGame.getProperty("initialLuck"))
        val initialStamina = Integer.valueOf(savedGame.getProperty("initialStamina"))
        val currentSkill = Integer.valueOf(savedGame.getProperty("currentSkill"))
        val currentLuck = Integer.valueOf(savedGame.getProperty("currentLuck"))
        val currentStamina = Integer.valueOf(savedGame.getProperty("currentStamina"))

        val equipmentS = String(savedGame.getProperty("equipment").toByteArray(java.nio.charset.Charset.forName("UTF-8")))
        val notesS = String(savedGame.getProperty("notes").toByteArray(java.nio.charset.Charset.forName("UTF-8")))
        val currentReference = Integer.valueOf(savedGame.getProperty("currentReference"))

        val equipment = AdventureState.stringToStringList(
            equipmentS
        )

        val notes = AdventureState.stringToStringList(notesS)

        val provisionsS = savedGame.getProperty("provisions")
        val provisions = if (provisionsS != null && provisionsS != "null") Integer.valueOf(provisionsS) else -1
        val provisionsValueS = savedGame.getProperty("provisionsValue")
        val provisionsValue = if (provisionsValueS != null && provisionsValueS != "null") Integer.valueOf(
            provisionsValueS
        ) else -1

        val standardPotionS = savedGame.getProperty("standardPotion")
        val standardPotion = if (standardPotionS != null && standardPotionS != "null") Integer.valueOf(standardPotionS) else -1
        val standardPotionValueS = savedGame.getProperty("standardPotionValue")
        val standardPotionValue = if (standardPotionValueS != null && standardPotionValueS != "null") Integer.valueOf(
            standardPotionValueS
        ) else -1

        val gold = Integer.valueOf(savedGame.getProperty("gold"))

        return loadAdventureSpecificStateFromSavedGame(
            AdventureState.toValueMap(
                initialSkill = initialSkill,
                name = name,
                gamebook = gamebook,
                initialLuck = initialLuck,
                initialStamina = initialStamina,
                currentSkill = currentSkill,
                currentLuck = currentLuck,
                currentStamina = currentStamina,
                equipment = equipment,
                notes = notes,
                provisions = provisions,
                provisionsValue = provisionsValue,
                currentReference = currentReference,
                gold = gold,
                standardPotion = standardPotion,
                standardPotionValue = standardPotionValue
            ), savedGame
        )
    }

    protected open fun loadAdventureSpecificStateFromSavedGame(
        values: Map<out StateKey, Any>,
        savedGame: Properties
    ): T {
        return kClass.primaryConstructor!!.call(values)
    }

    fun performTestSkill(fragment: AdventureFragment) {
        val message = if (state.checkSkill()) R.string.success else R.string.failed
        showAlert(message, this)
    }

    fun performTestLuck(fragment: AdventureFragment) {
        val message = if (state.checkLuck()) R.string.success else R.string.failed
        state = state.decreaseLuck()
        showAlert(message, this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.

        menuInflater.inflate(R.menu.adventure, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.rolld6 -> {
                displayRollXD6(1)
            }
            R.id.roll2d6 -> {
                displayRollXD6(2)
            }
            R.id.roll3d6 -> {
                displayRollXD6(3)
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun displayRollXD6(diceNumber: Int): Boolean {

        val d1 = DiceRoller.rollD6()
        var d2 = 0
        var d3 = 0
        if (diceNumber > 1) {
            d2 = DiceRoller.rollD6()
        }
        if (diceNumber > 2) {
            d3 = DiceRoller.rollD6()
        }

        val toastView = layoutInflater.inflate(R.layout.d3toast, findViewById(R.id.d3ToastLayout))
        val imageViewD1 = toastView.findViewById<ImageView>(R.id.d1)
        var imageViewD2: ImageView? = null
        var imageViewD3: ImageView? = null

        if (diceNumber > 1) {
            imageViewD2 = toastView.findViewById(R.id.d2)
        }

        if (diceNumber > 2) {
            imageViewD3 = toastView.findViewById(R.id.d3)
        }

        val resultView = toastView.findViewById<TextView>(R.id.diceResult)

        val d1Id = resources.getIdentifier("d6$d1", "drawable", this.packageName)

        var d2Id: Int? = null
        var d3Id: Int? = null

        if (diceNumber > 1) {
            d2Id = resources.getIdentifier("d6$d2", "drawable", this.packageName)
        }

        if (diceNumber > 2) {
            d3Id = resources.getIdentifier("d6$d3", "drawable", this.packageName)
        }

        imageViewD1.setImageResource(d1Id)

        if (diceNumber > 1) {
            imageViewD2!!.setImageResource(d2Id!!)
        }

        if (diceNumber > 2) {
            imageViewD3!!.setImageResource(d3Id!!)
        }

        val sum = d1 + d2 + d3

        resultView.text = " = $sum"

        showAlert(toastView, this)

        return true
    }

    fun performSavegame(fragment: AdventureFragment) {
        val alert = AlertDialog.Builder(this)

        alert.setTitle(R.string.currentReference)

        // Set an EditText view to get user input
        val input = EditText(this)
        val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
        input.inputType = InputType.TYPE_CLASS_PHONE
        input.requestFocus()
        alert.setView(input)

        alert.setPositiveButton(R.string.ok) { _, _ ->
            try {
                imm.hideSoftInputFromWindow(input.windowToken, 0)
                val ref = input.text.toString()
                val file = File(dir, "$ref.xml")

                val bw = BufferedWriter(OutputStreamWriter(FileOutputStream(file), "UTF-8"))

                bw.write(state.toSavegameString())
                storeNotesForRestart(dir)

                bw.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        alert.setNegativeButton(R.string.cancel) { _, _ ->
            imm.hideSoftInputFromWindow(
                input.windowToken,
                0
            )
        }

        alert.show()
    }

    @Throws(IOException::class)
    private fun readFile(file: File): String {
        val reader = BufferedReader(FileReader(file))
        var line: String?
        val stringBuilder = StringBuilder()
        val ls = System.getProperty("line.separator")

        while (true) {
            line = reader.readLine()
            if (line == null) break
            stringBuilder.append(line)
            stringBuilder.append(ls)
        }

        reader.close()
        return stringBuilder.toString()
    }

    @Throws(IOException::class)
    private fun storeNotesForRestart(dir: File?) {

        var initialContent = readFile(File(dir, "initial.xml"))
        initialContent = initialContent.replace("notes=", "notes=${state.notesAsString()}")

        val fileWriter = FileWriter(File(dir, "initial_full_notes.xml"))
        val bw = BufferedWriter(fileWriter)
        bw.write(initialContent)

        bw.close()
        fileWriter.close()
    }

    fun performUsePotion(fragment: AdventureFragment) {
        var message = -1
        when (state.standardPotion) {
            0 -> {
                message = R.string.potionSkillReplenish
                state = state
                    .resetSkill()
                    .decreasePotion()
            }
            1 -> {
                message = R.string.potionStaminaReplenish
                state = state
                    .resetStamina()
                    .decreasePotion()
            }
            2 -> {
                message = R.string.potionLuckReplenish
                state = state
                    .changeInitialLuck(state.initialLuck + 1)
                    .resetLuck()
                    .decreasePotion()
            }
        }
        showAlert(message, this)
        fragment.refreshScreen()
    }

    fun performAddNote(fragment: AdventureFragment, note: String) {
        state = state.addNote(note)
        fragment.refreshScreen()
    }

    private fun performAddEquipment(fragment: AdventureFragment, equipment: String) {
        state = state.addEquipment(equipment)
        fragment.refreshScreen()
    }

    private fun performRemoveEquipment(fragment: AdventureFragment, equipment: String) {
        state = state.removeEquipment(equipment)
        fragment.refreshScreen()
    }

    fun performRemoveNote(fragment: AdventureFragment, note: String) {
        state = state.removeNote(note)
        fragment.refreshScreen()
    }

    fun performIncreaseGold(fragment: AdventureFragment, value: Int = 1) {
        state = state.increaseGold(value)
        fragment.refreshScreen()
    }

    fun performDecreaseGold(fragment: AdventureFragment, value: Int = 1) {
        state = state.decreaseGold(value)
        fragment.refreshScreen()
    }

    private fun performChangeGold(fragment: AdventureFragment, value: Int) {
        state = state.changeGold(value)
        fragment.refreshScreen()
    }

    fun performConsumeProvisions(fragment: AdventureFragment) {
        when {
            state.provisions == 0 -> showAlert(R.string.noProvisionsLeft, this)
            state.isMaxStamina() -> showAlert(R.string.provisionsMaxStamina, this)
            else -> {
                val staminaGain = Math.min(state.provisionsValue, state.initialStamina - state.currentStamina)
                state = state
                    .decreaseProvisions()
                    .increaseStamina(staminaGain)
                showAlert(resources.getString(R.string.provisionsStaminaGain, staminaGain), this)
                fragment.refreshScreen()
            }
        }
    }

    fun performRemoveEquipmentWithAlert(fragment: AdventureFragment, index: Int): Boolean {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.deleteEquipmentQuestion).setCancelable(false)
            .setNegativeButton(R.string.close) { dialog, _ -> dialog.cancel() }
        builder.setPositiveButton(R.string.ok) { _, _ ->
            performRemoveEquipment(fragment, state.equipment[index])
        }

        val alert = builder.create()
        alert.show()
        return true
    }

    fun performAddEquipmentWithAlert(fragment: AdventureFragment) {
        val alert = AlertDialog.Builder(this)

        alert.setTitle(R.string.equipment2)

        // Set an EditText view to get user input
        val input = EditText(this)
        input.id = R.id.alert_editText_field
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(input, InputMethodManager.SHOW_IMPLICIT)
        input.requestFocus()
        alert.setView(input)

        alert.setPositiveButton(
            R.string.ok
        ) { _, _ ->
            val value = input.text.toString()
            if (value.isEmpty())
                return@setPositiveButton
            this.performAddEquipment(fragment, value.trim { it <= ' ' })
        }

        alert.setNegativeButton(
            R.string.cancel
        ) { _, _ ->
            // Canceled.
        }

        alert.show()
    }

    fun performChangeGoldWithAlert(fragment: AdventureFragment) {
        val alert = AlertDialog.Builder(this)

        alert.setTitle(R.string.setValue)

        // Set an EditText view to get user input
        val input = EditText(this)
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(
            InputMethodManager.SHOW_FORCED,
            InputMethodManager.HIDE_IMPLICIT_ONLY
        )
        input.inputType = InputType.TYPE_CLASS_NUMBER
        input.requestFocus()
        alert.setView(input)

        alert.setPositiveButton(R.string.ok) { _, _ ->
            val value = Integer.parseInt(input.text.toString())
            performChangeGold(fragment, value)
        }

        alert.setNegativeButton(
            R.string.cancel
        ) { _, _ -> imm.hideSoftInputFromWindow(input.windowToken, 0) }

        alert.show()
    }

    fun performRemoveNoteWithAlert(fragment: AdventureFragment, position: Int): Boolean {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.deleteNote)
            .setCancelable(false)
            .setNegativeButton(
                R.string.close
            ) { dialog, _ -> dialog.cancel() }
        builder.setPositiveButton(R.string.ok) { _, _ ->
            if (this.state.notes.size > position) {
                this.performRemoveNote(fragment, this.state.notes[position])
            }
        }

        val alert = builder.create()
        alert.show()
        return true
    }

    fun performAddNoteWithAlert(fragment: AdventureFragment) {
        val alert = AlertDialog.Builder(this)

        alert.setTitle(R.string.note)

        // Set an EditText view to get user input
        val input = EditText(this)
        input.id = R.id.alert_editText_field
        val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(input, InputMethodManager.SHOW_IMPLICIT)
        input.requestFocus()
        alert.setView(input)

        alert.setPositiveButton(
            R.string.ok
        ) { _, _ ->
            val value = input.text.toString()
            if (value.isEmpty())
                return@setPositiveButton
            this.performAddNote(fragment, value.trim { it <= ' ' })
        }

        alert.setNegativeButton(
            R.string.cancel
        ) { _, _ ->
            // Canceled.
        }

        alert.show()
    }

    override fun onPause() {
        super.onPause()

        pause()
    }

    private fun pause() {
        try {
            val file = File(dir, "temp" + ".xml")

            val bw = BufferedWriter(OutputStreamWriter(FileOutputStream(file), "UTF-8"))

            bw.write(state.toSavegameString())

            bw.close()
        } catch (e: IOException) {
            try {
                val fw = FileWriter(File(dir, "exception_" + Date() + ".txt"), true)
                val pw = PrintWriter(fw)
                e.printStackTrace(pw)
                pw.close()
                fw.close()
            } catch (e1: IOException) {
            }
        }
    }

    override fun onResume() {
        super.onResume()
        resume()
    }

    private fun resume() {
        dir?.let {
            try {

                val f = File(dir, "temp.xml")

                if (!f.exists())
                    return

                val savedGame = Properties()
                savedGame.load(InputStreamReader(FileInputStream(File(dir, "temp.xml")), "UTF-8"))

                loadSavegame(savedGame)

                refreshScreens()
            } catch (e: Exception) {
                try {
                    val fw = FileWriter(File(dir, "exception_" + Date() + ".txt"), true)
                    val pw = PrintWriter(fw)
                    e.printStackTrace(pw)
                    pw.close()
                    fw.close()
                } catch (e1: IOException) {
                    e1.printStackTrace()
                }
            }
        }
    }

    fun loadSavegame(savedGame: Properties) {
        state = loadStateFromSavedGame(savedGame)
    }

    fun testResume() {
        resume()
    }

    fun testPause() {
        pause()
    }

    fun refreshScreens() {
        fragmentConfiguration
            .map { getFragment(it.fragment) as AdventureFragment? }
            .forEach { it?.refreshScreen() }
    }

    protected fun stringToArray(_string: String?): List<String> {

        val elements = ArrayList<String>()

        if (_string != null) {
            val list = Arrays.asList(*_string.split("#".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray())

            list.filterNotTo(elements) { it.isEmpty() }
        }

        return elements
    }

    protected fun stringToSet(_string: String?): Set<String> {

        val elements = HashSet<String>()

        if (_string != null) {
            val list = Arrays.asList(*_string.split("#".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray())
            list.filterNotTo(elements) { it.isEmpty() }
        }

        return elements
    }

    fun fullRefresh() {
        fragmentConfiguration
            .map { it.fragment }
            .forEach { (getFragment(it) as AdventureFragment?)?.refreshScreen() }
    }

    private fun closeKeyboard(view: View) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
        view.clearFocus()
    }

    fun notifyPagerAdapterChanged() {
        mViewPager?.adapter?.notifyDataSetChanged()
    }

    fun performIncreaseStamina(fragment: AdventureFragment) {
        state = state.increaseStamina()
        fragment.refreshScreen()
    }

    fun performIncreaseSkill(fragment: AdventureFragment) {
        state = state.increaseSkill()
        fragment.refreshScreen()
    }

    fun performIncreaseLuck(fragment: AdventureFragment) {
        state = state.increaseLuck()
        fragment.refreshScreen()
    }

    fun performIncreaseProvisions(fragment: AdventureFragment) {
        state = state.increaseProvisions()
        fragment.refreshScreen()
    }

    fun performDecreaseStamina(fragment: AdventureFragment, value: Int = 1) {
        state = state.decreaseStamina(value)
        fragment.refreshScreen()
    }

    fun performDecreaseSkill(fragment: AdventureFragment) {
        state = state.decreaseSkill()
        fragment.refreshScreen()
    }

    fun performDecreaseLuck(fragment: AdventureFragment) {
        state = state.decreaseLuck()
        fragment.refreshScreen()
    }

    fun performDecreaseProvisions(fragment: AdventureFragment) {
        state = state.decreaseProvisions()
        fragment.refreshScreen()
    }

    fun performSetInitialStamina(fragment: AdventureFragment) {
        val alert = createAlertForInitialStatModification(R.string.setInitialStamina, { dialog, _ ->

            val value = getValueFromAlertTextField(fragment.view!!, dialog as AlertDialog)

            state = state.changeInitialStamina(value)
        })

        alert.show()
        fragment.refreshScreen()
    }

    fun performSetInitialSkill(fragment: AdventureFragment) {
        val alert = createAlertForInitialStatModification(R.string.setInitialSkill, { dialog, _ ->

            val value = getValueFromAlertTextField(fragment.view!!, dialog as AlertDialog)

            state = state.changeInitialSkill(value)
        })

        alert.show()
        fragment.refreshScreen()
    }

    fun performSetInitialLuck(fragment: AdventureFragment) {
        val alert = createAlertForInitialStatModification(R.string.setInitialLuck, { dialog, _ ->

            val value = getValueFromAlertTextField(fragment.view!!, dialog as AlertDialog)

            state = state.changeInitialLuck(value)
        })

        alert.show()
        fragment.refreshScreen()
    }



    private fun getValueFromAlertTextField(view: View, dialog: AlertDialog): Int {
        val input = dialog.findViewById<EditText>(R.id.alert_editText_field)

        val value = Integer.parseInt(input.text.toString())

        this.closeKeyboard(view)

        return value
    }

    private fun createAlertForInitialStatModification(
        dialogTitle: Int,
        positiveButtonListener: (DialogInterface, Int) -> Unit
    ): AlertDialog.Builder {
        val alert = AlertDialog.Builder(this)

        alert.setTitle(dialogTitle)

        // Set an EditText view to get user input
        val input = EditText(this)
        input.id = R.id.alert_editText_field

        val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(
            InputMethodManager.SHOW_FORCED,
            InputMethodManager.HIDE_IMPLICIT_ONLY
        )
        input.inputType = InputType.TYPE_CLASS_PHONE
        input.keyListener = DigitsKeyListener.getInstance("0123456789")
        input.requestFocus()
        alert.setView(input)

        alert.setNegativeButton(
            R.string.cancel
        ) { _, _ -> imm.hideSoftInputFromWindow(input.windowToken, 0) }

        alert.setPositiveButton(R.string.ok, positiveButtonListener)

        alert.setOnDismissListener {
            val view = this.currentFocus
            if (view != null) {
                val inputManager = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputManager.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
            }
        }

        return alert
    }

    fun T.increaseStamina(value: Int = 1) = copy(
        DefaultStateKey.CURRENT_STAMINA,
        Math.min(currentStamina + value, initialStamina)
    )

    fun T.increaseSkill(value: Int = 1) = copy(
        DefaultStateKey.CURRENT_SKILL,
        Math.min(currentSkill + value, initialSkill)
    )

    fun T.increaseLuck(value: Int = 1) = copy(DefaultStateKey.CURRENT_LUCK, Math.min(currentLuck + value, initialLuck))

    fun T.increaseProvisions(value: Int = 1) = copy(DefaultStateKey.PROVISIONS, provisions + value)

    fun T.increaseGold(value: Int = 1) = copy(DefaultStateKey.GOLD, gold + value)

    fun T.decreaseStamina(value: Int = 1) = copy(DefaultStateKey.CURRENT_STAMINA, Math.max(currentStamina - value, 0))

    fun T.decreaseSkill(value: Int = 1) = copy(DefaultStateKey.CURRENT_SKILL, Math.max(currentSkill - value, 0))

    fun T.decreaseLuck(value: Int = 1) = copy(DefaultStateKey.CURRENT_LUCK, Math.max(currentLuck - value, 0))

    fun T.decreaseProvisions(value: Int = 1) = copy(DefaultStateKey.PROVISIONS, Math.max(provisions - value, 0))

    fun T.decreasePotion(value: Int = 1) = copy(
        DefaultStateKey.STANDARD_POTION_VALUE,
        Math.max(standardPotionValue - value, 0)
    )

    fun T.decreaseGold(value: Int = 1) = copy(DefaultStateKey.GOLD, Math.max(gold - value, 0))

    fun T.resetStamina() = copy(DefaultStateKey.CURRENT_STAMINA, initialStamina)

    fun T.resetSkill() = copy(DefaultStateKey.CURRENT_SKILL, initialSkill)

    fun T.resetLuck() = copy(DefaultStateKey.CURRENT_LUCK, initialLuck)

    fun T.changeInitialStamina(value: Int = 1) = copy(DefaultStateKey.INITIAL_STAMINA, Math.max(0, value))

    fun T.changeInitialSkill(value: Int = 1) = copy(DefaultStateKey.INITIAL_SKILL, Math.max(0, value))

    fun T.changeInitialLuck(value: Int = 1) = copy(DefaultStateKey.INITIAL_LUCK, Math.max(0, value))

    fun T.changeGold(value: Int) = copy(DefaultStateKey.GOLD, Math.max(value, 0))

    fun T.addEquipment(item: String) = if (item.isNotBlank()) {
        copy(DefaultStateKey.EQUIPMENT, equipment.plus(item))
    } else this

    fun T.addNote(note: String) = if (note.isNotBlank()) {
        copy(DefaultStateKey.NOTES, notes.plus(note))
    } else this

    fun T.removeEquipment(item: String) = copy(DefaultStateKey.EQUIPMENT, equipment.minus(item))

    fun T.removeNote(note: String) = copy(DefaultStateKey.NOTES, notes.minus(note))

    open fun T.copy(
        key: StateKey, value: Any
    ) = kClass.primaryConstructor!!.call(
        values.toMap().plus(key to value)
    )

    companion object {
        fun showAlert(title: Int, message: Int, context: Context) {
            showAlert(title, context.getString(message), context)
        }

        fun showAlert(
            title: Int? = null,
            message: String,
            context: Context,
            extraActionTextId: Int? = null,
            extraActionCallback: () -> Unit = {}
        ) {
            val builder = AlertDialog.Builder(context)
            builder.setTitle(if (title != null && title > 0) title else R.string.result).setMessage(message).setCancelable(
                false
            ).setNegativeButton(
                R.string.close
            ) { dialog, _ -> dialog.cancel() }

            if (extraActionTextId != null && extraActionCallback != {}) {
                builder.setPositiveButton(extraActionTextId) { dialog, _ ->
                    extraActionCallback()
                    dialog.cancel()
                }
            }
            val alert = builder.create()
            alert.show()
        }

        fun showConfirmation(
            title: Int,
            message: Int,
            context: Context,
            confirmOnClickListener: DialogInterface.OnClickListener
        ) {
            val builder = AlertDialog.Builder(context)
            builder.setTitle(if (title > 0) title else R.string.result).setMessage(message).setCancelable(false).setNegativeButton(
                R.string.close
            ) { dialog, _ -> dialog.cancel() }.setPositiveButton(R.string.ok, confirmOnClickListener)
            val alert = builder.create()
            alert.show()
        }

        fun showErrorAlert(message: Int, context: Context) {
            val builder = AlertDialog.Builder(context)
            builder.setTitle(R.string.error).setMessage(message).setCancelable(false).setIcon(R.drawable.error_icon).setNegativeButton(
                R.string.close
            ) { dialog, _ -> dialog.cancel() }
            val alert = builder.create()
            alert.show()
        }

        fun showInfoAlert(message: Int, context: Context) {
            val builder = AlertDialog.Builder(context)
            builder.setTitle(R.string.info).setMessage(message).setCancelable(false).setIcon(R.drawable.info_icon).setNegativeButton(
                R.string.close
            ) { dialog, _ -> dialog.cancel() }
            val alert = builder.create()
            alert.show()
        }

        fun showSuccessAlert(message: Int, context: Context) {
            val builder = AlertDialog.Builder(context)
            builder.setTitle(R.string.done).setMessage(message).setCancelable(false).setIcon(R.drawable.success_icon).setNegativeButton(
                R.string.close
            ) { dialog, _ -> dialog.cancel() }
            val alert = builder.create()
            alert.show()
        }

        fun showAlert(view: View, context: Context) {
            val builder = AlertDialog.Builder(context)
            builder.setTitle(R.string.result).setView(view).setCancelable(false).setNegativeButton(R.string.close) { dialog, _ -> dialog.cancel() }
            val alert = builder.create()
            alert.show()
        }

        fun showAlert(message: Int, context: Context) {
            showAlert(-1, message, context)
        }

        fun showAlert(message: String, context: Context) {
            showAlert(-1, message, context)
        }

        fun getResId(context: Context, resName: String, type: String): Int {
            return context.resources.getIdentifier(resName, type, context.packageName)
        }
    }
}
