package oreterrainfilter;

import arc.struct.Seq;
import mindustry.content.Blocks;
import mindustry.maps.filters.FilterOption;
import mindustry.maps.filters.GenerateFilter;
import mindustry.world.blocks.environment.OreBlock;

public class OreTerrainFilter extends GenerateFilter{
    /** Names of floors whose ore overlays get removed. */
    public Seq<String> excludedFloors = new Seq<>();

    @Override
    public FilterOption[] options(){
        return new FilterOption[]{ new FloorFilterOption(this) };
    }

    @Override
    public String name(){
        return "Ore Remover";
    }

    public boolean excludes(String floorName){
        return excludedFloors.contains(floorName);
    }

    @Override
    public void apply(GenerateInput in){
        if(in.overlay instanceof OreBlock && excludes(in.floor.name)){
            in.overlay = Blocks.air;
        }
    }
}
