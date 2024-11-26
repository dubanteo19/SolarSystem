package planets.uranus;

import com.jme3.asset.AssetManager;
import planets.Satellite;


// Titania class, updated values based on Earth ratios
public class Titania extends Satellite {
    public Titania(AssetManager assetManager) {
        super("Titania",
                0.14f,   // Scaled radius (relative to Earth; actual ~788.4 km)
                0.292f,  // Semi-major axis in AU (scaled from ~436,300 km)
                8.71f,   // Orbital period (days, scaled)
                2.8f);   // Orbital inclination (degrees, scaled)
        applyMaterial(assetManager, "Textures/Planets/Satellite/Uranus/titania_text_ture.jpeg");
    }
}