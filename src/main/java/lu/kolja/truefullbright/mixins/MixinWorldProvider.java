package lu.kolja.truefullbright.mixins;

import net.minecraft.world.WorldProvider;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import lu.kolja.truefullbright.FullBright;

@Mixin(WorldProvider.class)
public abstract class MixinWorldProvider {

    @Inject(method = "getVoidFogYFactor", at = @At("HEAD"), cancellable = true)
    private void tfb$getVoidFogYFactor(CallbackInfoReturnable<Double> cir) {
        if (FullBright.shouldRemoveVoidFog()) {
            cir.setReturnValue(1.0D);
        }
    }
}
