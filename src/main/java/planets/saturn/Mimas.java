package planets.saturn;

import com.jme3.asset.AssetManager;
import planets.Satellite;

public class Mimas extends Satellite {
    public Mimas(AssetManager assetManager) {
        super("Mimas",
                0.04f,   // Scaled radius (relative to Earth; actual ~198.2 km)
                0.185f,  // Semi-major axis in AU (adjusted; actual ~185,539 km)
                0.94f,   // Orbital period (days)
                1.5f);   // Orbital inclination (degrees, scaled for representation)
        applyMaterial(assetManager, "Textures/Planets/Satellite/Saturn/mimas_texture.png");
    }
}