package io.danilwhale.sillyrendering.mixin;

import net.minecraft.client.render.BufferBuilder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(BufferBuilder.class)
public abstract class BufferBuilderMixin {
    @Shadow private boolean canSkipElementChecks;

    @Shadow public abstract void putFloat(int index, float value);

    @ModifyArgs(method = "vertex", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/FixedColorVertexConsumer;vertex(FFFFFFFFFIIFFF)V"))
    private void randomizeVertexSuperCall(Args args) {
        args.set(0, (float)args.get(0) + (float)Math.random());
        args.set(1, (float)args.get(0) + (float)Math.random());
        args.set(2, (float)args.get(0) + (float)Math.random());
    }

    @Inject(method = "vertex", at = @At("TAIL"))
    private void randomizeVertexPutBuffer(float x, float y, float z, float red, float green, float blue, float alpha, float u, float v, int overlay, int light, float normalX, float normalY, float normalZ, CallbackInfo ci) {
        if (this.canSkipElementChecks) {
            this.putFloat(0, x);
            this.putFloat(4, y);
            this.putFloat(8, z);
        }
    }
}
