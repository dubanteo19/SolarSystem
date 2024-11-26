package planets;

import com.jme3.asset.AssetManager;

public class Moon extends Satellite {
    public Moon(AssetManager assetManager) {
        super("Moon", 0.27f, 0.368f, 0.022f, 5.0f);
        applyMaterial(assetManager, "Textures/Planets/moon_texture.jpg");
    }
}
