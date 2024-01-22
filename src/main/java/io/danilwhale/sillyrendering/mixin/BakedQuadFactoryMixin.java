package io.danilwhale.sillyrendering.mixin;

import net.minecraft.client.render.model.BakedQuadFactory;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BakedQuadFactory.class)
public class BakedQuadFactoryMixin {
    @Inject(method = "getPositionMatrix", at = @At(value = "RETURN"), cancellable = true)
    private void randomizeGetPositionMatrix(Vector3f from, Vector3f _to, CallbackInfoReturnable<float[]> cir) {
        float[] arr = cir.getReturnValue();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = arr[i] + (float)Math.random();
        }
        cir.setReturnValue(arr);
    }
}
