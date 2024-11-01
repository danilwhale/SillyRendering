package danilwhale.sillyrendering;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.fabricmc.api.ClientModInitializer;

public class SillyRendering implements ClientModInitializer {
    public static SillyRenderingConfig config;

    public static float getRandomValue() {
        float value = (float) (config.useClassicRandom ? Math.random() : (Math.random() - Math.random()));
        return value * config.intensity;
    }

    @Override
    public void onInitializeClient() {
        AutoConfig.register(SillyRenderingConfig.class, Toml4jConfigSerializer::new);
        config = AutoConfig.getConfigHolder(SillyRenderingConfig.class).getConfig();
    }
}
