package vintage.sound.restart;

import cpw.mods.fml.client.registry.KeyBindingRegistry;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.audio.SoundManager;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;
import paulscode.sound.SoundSystem;
import paulscode.sound.libraries.LibraryLWJGLOpenAL;

import java.util.EnumSet;

@SideOnly(Side.CLIENT)
public class VintageSoundRestartKeyHandler extends KeyBindingRegistry.KeyHandler {

    private static final KeyBinding RESTART_SOUND = new KeyBinding("Restart sound engine", Keyboard.KEY_F6);

    private static boolean restarting = false;
    private static boolean keyWasDown = false;

    public VintageSoundRestartKeyHandler() {
        super(new KeyBinding[] { RESTART_SOUND }, new boolean[] { false });
    }

    public static void register() {
        KeyBindingRegistry.registerKeyBinding(new VintageSoundRestartKeyHandler());
    }

    @Override
    public String getLabel() {
        return "SoundRestart";
    }

    @Override
    public void keyDown(EnumSet<TickType> types, KeyBinding key, boolean tickEnd, boolean isRepeat) {
        if (!tickEnd) {
            return;
        }

        if (!keyWasDown) {
            keyWasDown = true;
            restartSoundEngine();
        }
    }

    @Override
    public void keyUp(EnumSet<TickType> types, KeyBinding key, boolean tickEnd) {
        if (tickEnd) {
            keyWasDown = false;
        }
    }

    @Override
    public EnumSet<TickType> ticks() {
        return EnumSet.of(TickType.CLIENT);
    }

    private static void restartSoundEngine() {
        if (restarting) {
            return;
        }

        restarting = true;

        try {
            SoundSystem soundSystem = SoundManager.sndSystem;
            if (soundSystem == null) {
                VintageSoundRestart.LOGGER.info("Sound system is not initialized.");
                return;
            }
            VintageSoundRestart.LOGGER.info("Restarting OpenAL...");
            boolean success = soundSystem.switchLibrary(LibraryLWJGLOpenAL.class);
            if (success) {
                VintageSoundRestart.LOGGER.info("OpenAL restarted successfully.");
            } else {
                VintageSoundRestart.LOGGER.severe("Failed to restart OpenAL.");
            }
        } catch (Exception e) {
            VintageSoundRestart.LOGGER.severe("Error restarting OpenAL:");
            e.printStackTrace();
        } finally {
            restarting = false;
        }
    }
}

