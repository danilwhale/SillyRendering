package danilwhale.sillyrendering.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalFloatRef;
import com.llamalad7.mixinextras.sugar.ref.LocalIntRef;
import com.llamalad7.mixinextras.sugar.ref.LocalShortRef;
import danilwhale.sillyrendering.SillyRendering;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.util.math.ColorHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BufferBuilder.class)
public class BufferBuilderMixin {
    @Inject(method = "vertex(FFFIFFIIFFF)V", at = @At("HEAD"))
    private void randomizeBigVertexCall(
            CallbackInfo ci,
            @Local(ordinal = 0, argsOnly = true) LocalFloatRef x,
            @Local(ordinal = 1, argsOnly = true) LocalFloatRef y,
            @Local(ordinal = 2, argsOnly = true) LocalFloatRef z,
            @Local(ordinal = 3, argsOnly = true) LocalFloatRef u,
            @Local(ordinal = 4, argsOnly = true) LocalFloatRef v) {
        if (SillyRendering.config.randomizeVertex) {
            x.set(x.get() + SillyRendering.getRandomValue());
            y.set(y.get() + SillyRendering.getRandomValue());
            z.set(z.get() + SillyRendering.getRandomValue());
        }

        if (SillyRendering.config.randomizeTextureOffset) {
            u.set((u.get() + SillyRendering.getRandomValue()) % 1.0f);
            v.set((v.get() + SillyRendering.getRandomValue()) % 1.0f);
        }
    }

    @Inject(method = "vertex(FFF)Lnet/minecraft/client/render/VertexConsumer;", at = @At("HEAD"))
    private void randomizeVertexCall(
            CallbackInfoReturnable<VertexConsumer> ci,
            @Local(ordinal = 0, argsOnly = true) LocalFloatRef x,
            @Local(ordinal = 1, argsOnly = true) LocalFloatRef y,
            @Local(ordinal = 2, argsOnly = true) LocalFloatRef z) {
        if (!SillyRendering.config.randomizeVertex) {
            return;
        }

        x.set(x.get() + SillyRendering.getRandomValue());
        y.set(y.get() + SillyRendering.getRandomValue());
        z.set(z.get() + SillyRendering.getRandomValue());
    }

    @Inject(method = "texture", at = @At("HEAD"))
    private void randomizeTextureCall(
            CallbackInfoReturnable<VertexConsumer> ci,
            @Local(ordinal = 0, argsOnly = true) LocalFloatRef u,
            @Local(ordinal = 1, argsOnly = true) LocalFloatRef v) {
        if (!SillyRendering.config.randomizeTextureOffset) {
            return;
        }

        u.set((u.get() + SillyRendering.getRandomValue()) % 1.0f);
        v.set((v.get() + SillyRendering.getRandomValue()) % 1.0f);
    }

    @Inject(method = "putUv", at = @At("HEAD"))
    private void randomizePutUvCall(
            CallbackInfoReturnable<VertexConsumer> ci,
            @Local(ordinal = 0, argsOnly = true) LocalShortRef u,
            @Local(ordinal = 1, argsOnly = true) LocalShortRef v
    ) {
        if (!SillyRendering.config.randomizeTextureOffset) {
            return;
        }

        u.set((short) ((u.get() + SillyRendering.getRandomValue() * Short.MAX_VALUE) % Short.MAX_VALUE));
        v.set((short) ((v.get() + SillyRendering.getRandomValue() * Short.MAX_VALUE) % Short.MAX_VALUE));
    }

    @ModifyVariable(method = "putColor", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    private static int randomizePutColor(int argb) {
        if (!SillyRendering.config.randomizeColor) {
            return argb;
        }

        // extract color components
        int red = ColorHelper.Argb.getRed(argb);
        int green = ColorHelper.Argb.getGreen(argb);
        int blue = ColorHelper.Argb.getBlue(argb);
        int alpha = ColorHelper.Argb.getAlpha(argb);

        // add random value and modulate result values for cool effects because clamp is boring tbh
        red = (red + (int) (SillyRendering.getRandomValue() * 255.0f)) % 255;
        green = (green + (int) (SillyRendering.getRandomValue() * 255.0f)) % 255;
        blue = (blue + (int) (SillyRendering.getRandomValue() * 255.0f)) % 255;
        // it's a bad idea to modify alpha component

        // pack back to argb and return
        return ColorHelper.Argb.getArgb(red, green, blue, alpha);
    }

    @Inject(method = "color(IIII)Lnet/minecraft/client/render/VertexConsumer;", at = @At("HEAD"))
    private void randomizeColorCall(
            CallbackInfoReturnable<VertexConsumer> ci,
            @Local(ordinal = 0, argsOnly = true) LocalIntRef r,
            @Local(ordinal = 1, argsOnly = true) LocalIntRef g,
            @Local(ordinal = 2, argsOnly = true) LocalIntRef b
    ) {
        if (!SillyRendering.config.randomizeColor) {
            return;
        }

        r.set((int)((r.get() + SillyRendering.getRandomValue() * 255.0f) % 255.0f));
        g.set((int)((g.get() + SillyRendering.getRandomValue() * 255.0f) % 255.0f));
        b.set((int)((b.get() + SillyRendering.getRandomValue() * 255.0f) % 255.0f));
    }

    @ModifyVariable(method = "light(I)Lnet/minecraft/client/render/VertexConsumer;", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    private int randomizeLight(int uv) {
        if (!SillyRendering.config.randomizeLight) {
            return uv;
        }
        return (uv + (int) (SillyRendering.getRandomValue() * Integer.MAX_VALUE)) % Integer.MAX_VALUE;
    }

    @ModifyVariable(method = "floatToByte", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    private static float randomizeFloatToByte(float f) {
        if (!SillyRendering.config.randomizeNormal) {
            return f;
        }

        return (f + SillyRendering.getRandomValue()) % 1.0f;
    }
}
