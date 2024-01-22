package io.danilwhale.sillyrendering.mixin;

import net.minecraft.client.render.BufferVertexConsumer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(BufferVertexConsumer.class)
public interface BufferVertexConsumerMixin {
    @ModifyArgs(method = "vertex", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/BufferVertexConsumer;putFloat(IF)V"))
    private void randomizeVertexFb(Args args) {
        args.set(1, (float)args.get(1) + (float)Math.random());
    }
}
