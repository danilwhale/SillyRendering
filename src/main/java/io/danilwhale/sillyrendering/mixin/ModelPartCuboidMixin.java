package io.danilwhale.sillyrendering.mixin;

import net.minecraft.client.model.ModelPart;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(ModelPart.Cuboid.class)
public class ModelPartCuboidMixin {
    @ModifyArgs(method = "renderCuboid", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/VertexConsumer;vertex(FFFFFFFFFIIFFF)V"))
    private void randomizedRenderVertexCall(Args args) {
        args.set(0, (float)args.get(0) + (float)Math.random());
        args.set(1, (float)args.get(1) + (float)Math.random());
        args.set(2, (float)args.get(2) + (float)Math.random());
    }
}
