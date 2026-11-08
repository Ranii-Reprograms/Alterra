package oreterrainfilter;

import arc.scene.ui.CheckBox;
import arc.scene.ui.layout.Table;
import mindustry.Vars;
import mindustry.maps.filters.FilterOption;
import mindustry.world.Block;
import mindustry.world.blocks.environment.Floor;
import mindustry.world.blocks.environment.OverlayFloor;

import java.util.ArrayList;
import java.util.List;

public class FloorFilterOption extends FilterOption{
    private final OreTerrainFilter filter;
    private final List<String> excludedFloors = new ArrayList<>();

    public FloorFilterOption(OreTerrainFilter filter){
        this.filter = filter;
    }

    public boolean excludes(String floorName){
        return excludedFloors.contains(floorName);
    }

    @Override
    public void build(Table table){
        table.add("Remove ore overlays from these floors").left().colspan(2).wrap().row();

        for(Block block : Vars.content.blocks()){
            if(!(block instanceof Floor) || block instanceof OverlayFloor || !block.inEditor) continue;

            CheckBox box = new CheckBox(block.localizedName);
            box.setChecked(excludedFloors.contains(block.name));
            box.changed(() -> {
                if(box.isChecked()){
                    if(!excludedFloors.contains(block.name)){
                        excludedFloors.add(block.name);
                    }
                }else{
                    excludedFloors.remove(block.name);
                }
                changed.run();
            });

            table.add(box).left().growX().row();
        }
    }
}
