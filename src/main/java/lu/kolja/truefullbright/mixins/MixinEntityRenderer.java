package lu.kolja.truefullbright.mixins;

import java.util.Arrays;

import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.texture.DynamicTexture;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import lu.kolja.truefullbright.FullBright;

@Mixin(value = EntityRenderer.class, priority = 2000)
public abstract class MixinEntityRenderer {

    @Shadow
    @Final
    private int[] lightmapColors;

    @Shadow
    @Final
    private DynamicTexture lightmapTexture;

    @Shadow
    private boolean lightmapUpdateNeeded;

    @Inject(method = "updateLightmap", at = @At("HEAD"), cancellable = true)
    private void tfb$updateLightmap(float partialTicks, CallbackInfo ci) {
        if (FullBright.enabled) {
            tfb$applyFullBright();
            lightmapUpdateNeeded = false;
            ci.cancel();
        }
    }

    @Inject(
        method = { "updateCameraAndRender", "renderWorld" },
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/EntityRenderer;updateLightmap(F)V",
            shift = At.Shift.AFTER),
        require = 0)
    private void tfb$afterUpdateLightmap(float partialTicks, CallbackInfo ci) {
        if (FullBright.enabled) {
            tfb$applyFullBright();
        }
    }

    @Unique
    private void tfb$applyFullBright() {
        for (var color : lightmapColors) {
            if (color != 0xFFFFFFFF) {
                Arrays.fill(lightmapColors, 0xFFFFFFFF);
                lightmapTexture.updateDynamicTexture();
                return;
            }
        }
    }
}
