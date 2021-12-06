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
        val loadPickUps = mapper.readValue<Array<PickUp>>(File("data/pickups.json"))
        pickUps = loadPickUps
    }

}

