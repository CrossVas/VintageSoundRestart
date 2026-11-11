package vinatge.sound.restart;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.relauncher.Side;

import java.util.logging.Logger;

@Mod(modid = VintageSoundRestart.MODID, useMetadata = true)
public class VintageSoundRestart {
    public static final String MODID = "vintagesoundrestart";

    public static final Logger LOGGER = Logger.getLogger(MODID);

    public VintageSoundRestart() {
        LOGGER.setParent(FMLLog.getLogger());
    }

    @Mod.Init
    public void init(FMLInitializationEvent event) {
        if (event.getSide() == Side.CLIENT) {
            VintageSoundRestartKeyHandler.register();
            LOGGER.info("Vintage: Sound Restart initialized.");
        }
    }
}
