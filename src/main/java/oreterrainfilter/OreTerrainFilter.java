package oreterrainfilter;

import arc.struct.Seq;
import mindustry.content.Blocks;
import mindustry.maps.filters.FilterOption;
import mindustry.maps.filters.GenerateFilter;
import mindustry.world.blocks.environment.OreBlock;

public class OreTerrainFilter extends GenerateFilter{
    public Seq<String> excludedFloors = new Seq<>();

    @Override
    public FilterOption[] options(){
        return new FilterOption[]{new FloorFilterOption(this)};
    }

    @Override
    public String name(){
        return "Ore Terrain Filter";
    }

    public boolean excludes(String floorName){
        return excludedFloors.contains(floorName);
    }

    @Override
    public void apply(GenerateInput input){
        if(input.overlay instanceof OreBlock && input.floor != null && excludes(input.floor.name)){
            input.overlay = Blocks.air;
        }
    }
}
