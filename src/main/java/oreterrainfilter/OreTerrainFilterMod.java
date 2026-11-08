package oreterrainfilter;

import arc.func.Prov;
import mindustry.maps.Maps;
import mindustry.maps.filters.GenerateFilter;
import mindustry.mod.Mod;

import java.util.Arrays;

public class OreTerrainFilterMod extends Mod{
    @Override
    public void init(){
        for(Prov<GenerateFilter> provider : Maps.allFilterTypes){
            if(provider.get().getClass() == OreTerrainFilter.class){
                return;
            }
        }

        Prov<GenerateFilter>[] updated = Arrays.copyOf(Maps.allFilterTypes, Maps.allFilterTypes.length + 1);
        updated[updated.length - 1] = OreTerrainFilter::new;
        Maps.allFilterTypes = updated;
    }
}
