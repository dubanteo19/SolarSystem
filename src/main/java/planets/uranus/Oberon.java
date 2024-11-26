package planets.uranus;

import com.jme3.asset.AssetManager;
import planets.Satellite;

// Oberon class, updated values based on Earth ratios
public class Oberon extends Satellite {
    public Oberon(AssetManager assetManager) {
        super("Oberon",
                0.12f,   // Scaled radius (relative to Earth; actual ~471.6 km)
                0.391f,  // Semi-major axis in AU (scaled from ~583,500 km)
                13.46f,  // Orbital period (days, scaled)
                2.5f);   // Orbital inclination (degrees, scaled)
        applyMaterial(assetManager, "Textures/Planets/Satellite/Uranus/oberon_text_ture.jpeg");
    }
}