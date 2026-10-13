package lu.kolja.truefullbright.mixins;

import net.minecraft.client.gui.GuiIngame;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import lu.kolja.truefullbright.FullBright;

@Mixin(GuiIngame.class)
public abstract class MixinGuiIngame {

    @ModifyVariable(method = "renderVignette", at = @At("HEAD"), argsOnly = true, name = "p_73829_1_")
    private float tfb$renderVignette(float brightness) {
        return FullBright.enabled ? 1.0F : brightness;
    }
}
