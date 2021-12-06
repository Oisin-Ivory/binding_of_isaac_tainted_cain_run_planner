package binding_of_isaac_tainted_cain_run_planner.manager

import binding_of_isaac_tainted_cain_run_planner.models.Item
import binding_of_isaac_tainted_cain_run_planner.models.Run
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.io.File
import java.nio.file.Paths

class RunManager : Manager {
    private var runs : MutableList<Run> = emptyList<Run>().toMutableList()
    val mapper = jacksonObjectMapper()

    fun getRuns(): MutableList<Run> {
        return runs;
    }
    fun getRun(index : Int): Run {
        return runs[index]
    }

    fun setRun(index : Int): Run {
        return runs[index]
    }
    fun removeRun(index : Int){
        runs.removeAt(index)
    }
    fun add(run : Run){
        runs.add(run)
    }

    fun searchRuns(searchTerms : List<String>):List<Run>{
        val returnList = emptyList<Run>().toMutableList()

        for (run in runs){
            var addToList = true;
            for(term in searchTerms){
                if(!run.toString().lowercase().contains(term.lowercase())){
                    addToList = false
                }
            }
            if(addToList) returnList.add(run)
        }
        return returnList
    }

    override fun save() {
        mapper.writeValue(Paths.get("data/runs.json").toFile(), runs);
    }

    override fun load() {
        val loadRuns = mapper.readValue<MutableList<Run>>(File("data/runs.json"))
        runs = loadRuns
    }

}