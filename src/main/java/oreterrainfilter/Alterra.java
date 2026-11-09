package oreterrainfilter;

import arc.func.Prov;
import arc.util.Log;
import mindustry.maps.Maps;
import mindustry.maps.filters.GenerateFilter;
import mindustry.mod.Mod;

import java.util.Arrays;

public class Alterra extends Mod{
    @Override
    public void init(){
        Log.info("[Alterra] Alterra.init() started.");

        int filterCountBefore = Maps.allFilterTypes.length;
        Log.info("[Alterra] Filter providers before registration: @", filterCountBefore);

        for(Prov<GenerateFilter> provider : Maps.allFilterTypes){
            if(provider.get().getClass() == OreTerrainFilter.class){
                Log.info("[Alterra] Ore Terrain Filter is already registered.");
                return;
            }
        }

        Prov<GenerateFilter>[] updated = Arrays.copyOf(Maps.allFilterTypes, Maps.allFilterTypes.length + 1);
        updated[updated.length - 1] = OreTerrainFilter::new;
        Maps.allFilterTypes = updated;

        Log.info("[Alterra] Ore Terrain Filter registered. Filter providers after registration: @", Maps.allFilterTypes.length);
    }
}
