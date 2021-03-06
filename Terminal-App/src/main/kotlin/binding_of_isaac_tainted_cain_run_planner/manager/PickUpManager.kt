package binding_of_isaac_tainted_cain_run_planner.manager
import binding_of_isaac_tainted_cain_run_planner.models.PickUp
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.io.File
import java.nio.file.Paths

class PickUpManager : Manager {

    val mapper = jacksonObjectMapper()
    private var pickUps : Array<PickUp> = emptyArray<PickUp>()

    fun getPickUps(): Array<PickUp> {
        return pickUps
    }

    fun getPickUp(index : Int): PickUp {
        return pickUps[index]
    }

    override fun save() {
        mapper.writeValue(Paths.get("data/pickups.json").toFile(), pickUps);
    }

    override fun load() {

        val pickUpsFile = File("data/pickups.json")
        if(!pickUpsFile.exists()) {
            pickUpsFile.createNewFile()
            initPickups()
            save()
            return
        }
        val loadPickUps = mapper.readValue<Array<PickUp>>(pickUpsFile)

        pickUps = loadPickUps
    }

    fun initPickups() {
        //Initialize default pickups
        var locationList = listOf("Room Completion", "Champions", "Chests", "Secret Rooms", "Curse Rooms")
        val redHeart = PickUp("Red Heart", "redheart.png", locationList)

        locationList = listOf("Tinted Rocks", "Room Completion", "The Hierophant", "Boss Drop", "Red Chest")
        val soulHeart = PickUp("Soul Heart", "soulheart.png", locationList)

        locationList = listOf("Devil Room", "Curse Rooms", "Room Completion")
        val blackHeart = PickUp("Black Heart", "blackheart.png", locationList)

        locationList = listOf("Secret Room", "Angle Room", "Boss Drop")
        val eternalHeart = PickUp("Eternal Heart", "eternalheart.png", locationList)

        locationList = listOf("Room Completion")
        val goldHeart = PickUp("Golden Heart", "goldheart.png", locationList)

        locationList = listOf("Secret Rooms", "Room Completion")
        val boneHeart = PickUp("Bone Heart", "boneheart.png", locationList)

        locationList = listOf("Room Completion")
        val rottenHeart = PickUp("Rotten Heart", "rottenheart.png", locationList)

        locationList = listOf("Room Clear", "Donation Machine", "Keeper's Soul", "Secret Rooms", "Pots", "Chests")
        val penny = PickUp("Penny", "penny.png", locationList)
        val nickle = PickUp("Nickle", "nickle.png", locationList)
        val dime = PickUp("Dime", "dime.png", locationList)
        val luckyPenny = PickUp("Lucky Penny", "luckypenny.png", locationList)

        locationList = listOf("Room Completion", "Champions", "Chests")
        val key = PickUp("Key", "key.png", locationList)

        locationList = listOf("Room Completion", "Secret Rooms")
        val goldenKey = PickUp("Golden Key", "goldenkey.png", locationList)

        locationList = listOf("Room Completion", "Secret Rooms")
        val chargedKey = PickUp("Charged Key", "chargedkey.png", locationList)

        locationList = listOf("Room Completion", "Champions", "Chests", "Secret Rooms")
        val bomb = PickUp("Bomb", "bomb.png", locationList)

        locationList = listOf("Secret Rooms", "Room Completion")
        val goldenBomb = PickUp("Golden Bomb", "goldenbomb.png", locationList)

        locationList = listOf("The Depths w/ Safety Scissors")
        val gigaBomb = PickUp("Giga Bomb", "gigabomb.png", locationList)

        locationList = listOf("Room Completion")
        val microBattery = PickUp("Micro Battery", "microbattery.png", locationList)

        locationList = listOf("Room Completion")
        val lilBattery = PickUp("Lil' Battery", "lilbattery.png", locationList)

        locationList = listOf("Secret Rooms")
        val megaBattery = PickUp("Mega Battery", "megabattery.png", locationList)

        locationList = listOf("Shop", "Room Completion")
        val card = PickUp("Card", "card.png", locationList)

        locationList = listOf("Shop", "Mushrooms", "Room Clear")
        val pill = PickUp("Pill", "pill.png", locationList)

        locationList = listOf("Shop", "Room Clear")
        val runeSoul = PickUp("Rune/Soul", "runesoul.png", locationList)

        locationList = listOf("Secret Rooms", "Shop", "Room Completion")
        val diceShard = PickUp("Dice Shard", "diceshard.png", locationList)

        locationList = listOf("Secret Rooms", "Shop", "Room Completion")
        val crackedKey = PickUp("Cracked Key", "crackedkey.png", locationList)

        val items = arrayOf(
                redHeart,
                soulHeart,
                blackHeart,
                eternalHeart,
                goldHeart,
                boneHeart,
                rottenHeart,
                penny,
                nickle,
                dime,
                luckyPenny,
                key,
                goldenKey,
                chargedKey,
                bomb,
                goldenBomb,
                gigaBomb,
                microBattery,
                lilBattery,
                megaBattery,
                card,
                pill,
                runeSoul,
                diceShard,
                crackedKey
        )
        pickUps = items
    }
}

