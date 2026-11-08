package oreterrainfilter;

import mindustry.content.Blocks;
import mindustry.maps.filters.FilterOption;
import mindustry.maps.filters.GenerateFilter;
import mindustry.world.blocks.environment.OreBlock;

public class OreTerrainFilter extends GenerateFilter{
    public FloorFilterOption floorOption = new FloorFilterOption(this);

    @Override
    public FilterOption[] options(){
        return new FilterOption[]{floorOption};
    }

    @Override
    public String name(){
        return "Ore Terrain Filter";
    }

    @Override
    public void apply(GenerateInput input){
        if(input.overlay instanceof OreBlock && input.floor != null && floorOption.excludes(input.floor.name)){
            input.overlay = Blocks.air;
        }
    }
}
