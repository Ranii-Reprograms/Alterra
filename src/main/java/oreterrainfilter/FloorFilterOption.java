package oreterrainfilter;

import arc.Core;
import arc.struct.Seq;
import arc.scene.ui.layout.Table;
import mindustry.Vars;
import mindustry.content.Blocks;
import mindustry.maps.filters.FilterOption;
import mindustry.world.Block;
import mindustry.world.blocks.environment.Floor;
import mindustry.world.blocks.environment.OverlayFloor;

public class FloorFilterOption extends FilterOption{
    private final OreTerrainFilter filter;
    private final Seq<Floor> floors = new Seq<>();
    private String query = "";
    private Table list;

    public FloorFilterOption(OreTerrainFilter filter){
        this.filter = filter;
    }

    @Override
    public void build(Table table){
        //collect every real floor (vanilla + modded), skipping air and ore overlays
        floors.clear();
        for(Block block : Vars.content.blocks()){
            if(block instanceof Floor && !(block instanceof OverlayFloor) && block != Blocks.air){
                floors.add((Floor)block);
            }
        }
        floors.sort((a, b) -> a.localizedName.compareToIgnoreCase(b.localizedName));

        table.left().defaults().left();
        table.add("Remove ore overlays from these floors").left().growX().wrap().row();

        table.field(query, text -> {
            query = text.trim().toLowerCase();
            rebuildList();
        }).growX().get().setMessageText("Search floors");
        table.row();

        table.table(buttons -> {
            buttons.defaults().growX().pad(2f);
            buttons.button("Select shown", () -> {
                for(Floor f : floors){
                    if(matches(f) && !filter.excludes(f.name)) filter.excludedFloors.add(f.name);
                }
                rebuildList();
                changed.run();
            });
            buttons.button("Clear", () -> {
                filter.excludedFloors.clear();
                rebuildList();
                changed.run();
            });
        }).growX().row();

        list = new Table();
        list.left().top();
        rebuildList();

        //list height scales with the screen, so it fits phones, tablets and desktop alike
        float height = Math.max(160f, Core.scene.getHeight() * 0.45f);
        table.pane(list).growX().height(height).get().setScrollingDisabled(true, false);
        table.row();
    }

    private boolean matches(Floor f){
        return query.isEmpty()
            || f.localizedName.toLowerCase().contains(query)
            || f.name.toLowerCase().contains(query);
    }

    private void rebuildList(){
        list.clearChildren();
        boolean any = false;
        for(Floor f : floors){
            if(!matches(f)) continue;
            any = true;
            list.table(row -> {
                row.left();
                row.image(f.uiIcon).size(Vars.iconMed).padRight(6f);
                row.check(f.localizedName, filter.excludes(f.name), checked -> {
                    if(checked){
                        if(!filter.excludes(f.name)) filter.excludedFloors.add(f.name);
                    }else{
                        filter.excludedFloors.remove(f.name);
                    }
                    changed.run();
                }).left().growX();
            }).growX().left().row();
        }
        if(!any) list.add("No floors found").left();
    }
}
