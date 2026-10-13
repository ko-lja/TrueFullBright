package lu.kolja.truefullbright.mixins;

import net.minecraft.client.Minecraft;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import lu.kolja.truefullbright.FullBright;

@Mixin(Minecraft.class)
public abstract class MixinMinecraft {

    @Inject(method = "isAmbientOcclusionEnabled", at = @At("HEAD"), cancellable = true)
    private static void tfb$isAmbientOcclusionEnabled(CallbackInfoReturnable<Boolean> cir) {
        if (FullBright.shouldDisableAmbientOcclusion()) {
            cir.setReturnValue(false);
        }
    }
}
