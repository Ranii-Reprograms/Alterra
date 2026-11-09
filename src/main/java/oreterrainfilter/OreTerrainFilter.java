package oreterrainfilter;

import arc.struct.ObjectSet;
import arc.struct.Seq;
import arc.util.Log;
import mindustry.content.Blocks;
import mindustry.maps.filters.FilterOption;
import mindustry.maps.filters.GenerateFilter;
import mindustry.world.blocks.environment.OreBlock;

public class OreTerrainFilter extends GenerateFilter{
    /** Names of floors whose ore overlays get removed. */
    public Seq<String> excludedFloors = new Seq<>();

    // ---- debug only: remove once the Apply problem is solved ----
    private static int removedCount = 0;
    private static final ObjectSet<String> seenKept = new ObjectSet<>();

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
        if(in.overlay == Blocks.air || !excludes(in.floor.name)) return;

        if(in.overlay instanceof OreBlock){
            String oreName = in.overlay.name;
            in.overlay = Blocks.air;
            if(removedCount++ % 2000 == 0){
                Log.info("[Alterra] removed ore #@ (@ on @)", removedCount, oreName, in.floor.name);
            }
        }else if(seenKept.add(in.overlay.name + "|" + in.overlay.getClass().getName())){
            //an overlay on a ticked floor that is NOT an OreBlock, so it was left alone
            Log.info("[Alterra] kept overlay @ (class @) on ticked floor @",
                in.overlay.name, in.overlay.getClass().getName(), in.floor.name);
        }
    }
}
