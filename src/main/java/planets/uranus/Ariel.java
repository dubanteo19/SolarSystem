package planets.uranus;

import com.jme3.asset.AssetManager;
import planets.Satellite;

public class Ariel extends Satellite {
    public Ariel(AssetManager assetManager) {
        super("Ariel",
                0.08f,   // Scaled radius (relative to Earth; actual ~578.9 km)
                0.287f,  // Semi-major axis in AU (scaled from ~191,000 km)
                2.52f,   // Orbital period (days, scaled)
                3.0f);   // Orbital inclination (degrees, scaled)
        applyMaterial(assetManager, "Textures/Planets/Satellite/Uranus/ariel_texture.jpeg");
    }
}
