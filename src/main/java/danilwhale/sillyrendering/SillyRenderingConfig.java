package danilwhale.sillyrendering;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name = "sillyrendering")
public class SillyRenderingConfig implements ConfigData {
    public float intensity = 0.1f;
    public boolean randomizeVertex = true;
    public boolean randomizeTextureOffset = false;
    public boolean randomizeNormal = false;
    public boolean randomizeColor = false;
    public boolean randomizeLight = false;
    public boolean useClassicRandom = false;
}
