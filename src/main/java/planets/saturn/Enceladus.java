package planets.saturn;

import com.jme3.asset.AssetManager;
import planets.Satellite;

public class Enceladus extends Satellite {
    public Enceladus(AssetManager assetManager) {
        super("Enceladus",
                0.05f,   // Scaled radius (relative to Earth; actual ~252.1 km)
                0.238f,  // Semi-major axis in AU (adjusted; actual ~237,948 km)
                1.37f,   // Orbital period (days)
                3.0f);   // Orbital inclination (degrees, scaled for representation)
        applyMaterial(assetManager, "Textures/Planets/Satellite/Saturn/enceladus_texture.jpeg");
    }
}
