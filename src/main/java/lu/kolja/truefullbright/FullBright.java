package lu.kolja.truefullbright;

import java.io.File;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.config.Configuration;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;

public final class FullBright {

    public static boolean enabled = true;
    public static boolean disableAmbientOcclusion = true;
    public static boolean removeVoidFog = true;

    private static Configuration config;
    private static KeyBinding toggleKey;

    private FullBright() {}

    public static void init(File configFile) {
        config = new Configuration(configFile);
        enabled = config.getBoolean("enabled", Configuration.CATEGORY_GENERAL, enabled, "Whether fullbright is active");
        disableAmbientOcclusion = config.getBoolean(
            "disableAmbientOcclusion",
            Configuration.CATEGORY_GENERAL,
            disableAmbientOcclusion,
            "Disable smooth lighting shadows in corners and next to walls while fullbright is active");
        removeVoidFog = config.getBoolean(
            "removeVoidFog",
            Configuration.CATEGORY_GENERAL,
            removeVoidFog,
            "Stop the fog from turning black at low Y levels while fullbright is active");
        if (config.hasChanged()) {
            config.save();
        }

        toggleKey = new KeyBinding("key.truefullbright.toggle", Keyboard.KEY_NONE, "key.categories.misc");
        ClientRegistry.registerKeyBinding(toggleKey);
        FMLCommonHandler.instance()
            .bus()
            .register(new FullBright());
    }

    public static boolean shouldDisableAmbientOcclusion() {
        return enabled && disableAmbientOcclusion;
    }

    public static boolean shouldRemoveVoidFog() {
        return enabled && removeVoidFog;
    }

    public static void toggle() {
        enabled = !enabled;
        config.get(Configuration.CATEGORY_GENERAL, "enabled", true)
            .set(enabled);
        config.save();

        var mc = Minecraft.getMinecraft();
        if (disableAmbientOcclusion && mc.gameSettings.ambientOcclusion != 0 && mc.renderGlobal != null) {
            mc.renderGlobal.loadRenderers();
        }
        var state = enabled ? EnumChatFormatting.GREEN + I18n.format("truefullbright.enabled")
            : EnumChatFormatting.RED + I18n.format("truefullbright.disabled");
        var message = EnumChatFormatting.GOLD.toString() + EnumChatFormatting.BOLD
            + I18n.format("truefullbright.name")
            + EnumChatFormatting.RESET
            + EnumChatFormatting.DARK_GRAY
            + " \u00BB "
            + state;
        mc.ingameGUI.func_110326_a(message, false);
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (toggleKey.isPressed()) {
            toggle();
        }
    }
}
