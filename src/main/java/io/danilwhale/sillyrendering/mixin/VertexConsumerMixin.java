package io.danilwhale.sillyrendering.mixin;

import net.minecraft.client.render.VertexConsumer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(VertexConsumer.class)
public interface VertexConsumerMixin {
    @ModifyArgs(method = "vertex(FFFFFFFFFIIFFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/VertexConsumer;vertex(DDD)Lnet/minecraft/client/render/VertexConsumer;"))
    private void randomizeVertexF(Args args) {
        args.set(0, (double)args.get(0) + Math.random());
        args.set(1, (double)args.get(1) + Math.random());
        args.set(2, (double)args.get(2) + Math.random());
    }

    @ModifyArgs(method = "vertex(Lorg/joml/Matrix4f;FFF)Lnet/minecraft/client/render/VertexConsumer;", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/VertexConsumer;vertex(DDD)Lnet/minecraft/client/render/VertexConsumer;"))
    private void randomizeVertexMatF(Args args) {
        args.set(0, (double)args.get(0) + Math.random());
        args.set(1, (double)args.get(1) + Math.random());
        args.set(2, (double)args.get(2) + Math.random());
    }
}
