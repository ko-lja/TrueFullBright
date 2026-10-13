package lu.kolja.truefullbright;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(
    modid = TrueFullBright.MODID,
    name = "TrueFullBright",
    version = Tags.VERSION,
    acceptedMinecraftVersions = "[1.7.10]",
    acceptableRemoteVersions = "*")
public class TrueFullBright {

    public static final String MODID = "tfb";

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        if (event.getSide()
            .isClient()) {
            FullBright.init(event.getSuggestedConfigurationFile());
        }
    }
}
