package planets.uranus;

import com.jme3.asset.AssetManager;
import planets.Satellite;

// Miranda class, updated values based on Earth ratios
public class Miranda extends Satellite {
    public Miranda(AssetManager assetManager) {
        super("Miranda",
                0.06f,   // Scaled radius (relative to Earth; actual ~235.8 km)
                0.174f,  // Semi-major axis in AU (scaled from ~129,900 km)
                1.413f,  // Orbital period (days, scaled)
                1.8f);   // Orbital inclination (degrees, scaled)
        applyMaterial(assetManager, "Textures/Planets/Satellite/Uranus/miranda_diffuse.png");
    }
}

