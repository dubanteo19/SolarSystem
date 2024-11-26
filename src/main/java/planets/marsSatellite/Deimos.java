package planets.marsSatellite;

import com.jme3.asset.AssetManager;
import planets.Satellite;

public class Deimos extends Satellite {
    public Deimos(AssetManager assetManager) {
        super("Deimos", 0.006F, 0.03F, 0.002F, 4.5F); // Tùy chỉnh kích thước, tốc độ quay, khoảng cách
        this.applyMaterial(assetManager, "Textures/Planets/Satellite/Mars/deimos_texture.jpeg"); // Đường dẫn texture
    }
}
