package org.wit.boitcrp.managers

import android.content.Context
import com.google.gson.reflect.TypeToken
import org.wit.boitcrp.models.PickUp
import org.wit.placemark.helpers.exists
import org.wit.placemark.helpers.gsonBuilder
import org.wit.placemark.helpers.read
import org.wit.placemark.helpers.write
import java.lang.reflect.Type


class PickUpManager(private val context: Context) {

    private var pickUps : MutableList<PickUp> = emptyList<PickUp>().toMutableList()
    val JSON_FILE = "pickups.json"
    val listType: Type = object : TypeToken<ArrayList<PickUp>>() {}.type

    init {
        if (exists(context, JSON_FILE)) {
            deserialize()
        }else{
            initPickups()
            serialize()
        }
    }
    fun getPickUps(): List<PickUp> {
        return pickUps
    }

    fun getPickUp(index : Int): PickUp {
        return pickUps[index]
    }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(pickUps, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        pickUps = gsonBuilder.fromJson(jsonString, listType)
    }

    fun findAll(): List<PickUp> {
        return pickUps
    }

    fun findAllNames(): List<String>{
        val nameArr: ArrayList<String> = ArrayList()
        for(pickup in pickUps){
            pickup.pickUpName?.let { nameArr.add(it) }
        }
        return nameArr
    }

    fun findPickupName(name:String): PickUp? {
        for (pickup in pickUps){
            if (pickup.pickUpName == name){
                return pickup
            }
        }
        return null
    }

    fun getPickUpIndex(pickUp: PickUp): Int{
        for(i in 0 .. pickUps.size){
            if(pickUps[i] == pickUp){
                return i
            }
        }
        return -1
    }

    fun initPickups() {
        //Initialize default pickups
        var locationList = listOf<String>()
        val empty = PickUp("Empty", "empty.png", locationList)

        locationList = listOf("Room Completion", "Champions", "Chests", "Secret Rooms", "Curse Rooms")
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

        locationList = listOf("Secret Rooms", "Shop", "Room Completion","Chests")
        val goldenPenny = PickUp("Golden Penny", "goldenpenny.png", locationList)

        locationList = listOf("Secret Rooms", "Shop", "Room Completion")
        val goldenPill = PickUp("Golden Pill", "goldenpill.png", locationList)

        locationList = listOf("Secret Rooms")
        val goldenBattery = PickUp("Golden Battery", "goldenbattery.png", locationList)

        locationList = listOf("Tainted Blue Baby")
        val poopNugget = PickUp("Poop Nugget", "poopnugget.png", locationList)

        locationList = listOf("Unknown")
        val unknown = PickUp("Unknown", "unknown.png", locationList)


        val items = listOf(
                empty,
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
                crackedKey,
                goldenPenny,
                goldenPill,
                goldenBattery,
                poopNugget,
                unknown
        )
        pickUps = items as MutableList<PickUp>
    }
}

