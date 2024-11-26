package planets.marsSatellite;

import com.jme3.asset.AssetManager;
import planets.Satellite;

public class Phobos extends Satellite {
    public Phobos(AssetManager assetManager) {
        super("Phobos", 0.011F, 0.07F, 0.005F, 3.0F); // Tùy chỉnh kích thước, tốc độ quay, khoảng cách
        this.applyMaterial(assetManager, "Textures/Planets/Satellite/Mars/phobos_texture.jpeg"); // Đường dẫn texture
    }
}
