package planets;

import com.jme3.asset.AssetManager;

public class Moon extends Satellite {
    public Moon(AssetManager assetManager) {
        super("Moon", 0.27f, 0.034f, 0.034f, 5.0f);
        applyMaterial(assetManager, "Textures/Planets/moon_texture.jpg");
    }
}
