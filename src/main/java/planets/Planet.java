package planets;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Sphere;
import com.jme3.texture.Texture;

import java.util.ArrayList;
import java.util.List;

public class Planet extends CelestialBody {
    private List<Satellite> satellies;

    public Planet(String name, float radius, float orbitSpeed, float rotationSpeed, float distance) {
        super(name, radius, orbitSpeed, rotationSpeed, distance);
        this.rotationSpeed = rotationSpeed;
        this.satellies = new ArrayList<>();
        this.geometry.setLocalTranslation(this.distance, 0, 0);
    }

    public Geometry getGeometry() {
        return this.geometry;
    }

    //Them ve tinh vao danh sach ve tinh cua hanh tinh
    public void addSatellite(Satellite satellite) {
        this.satellies.add(satellite);
        satellite.setParentPlanet(this);
        this.attachChild(satellite.geometry);
    }

    @Override
    public void update(float tpf) {
        // quay quanh quy dao
        angle += orbitSpeed * tpf;
        if (angle > Math.PI * 2) {
            angle -= (float) (Math.PI * 2);
        }
        // Assuming the sun is at (0, 0, 0), we calculate the new position using orbit equations.
        float x = (float) Math.cos(angle) * distance;
        float z = (float) Math.sin(angle) * distance;
        // Set the new position (we assume the Sun is at (0, 0, 0))
        geometry.setLocalTranslation(x, 0, z); // This moves the planet in a circle around the origin (0,0,0)
        //Tu quay quanh chinh no
        geometry.rotate(0, rotationSpeed * tpf, 0);

        // update danh sach ve tinh cua hanh tinh
        for (Satellite satellite : satellies) {
            satellite.update(tpf);
        }

    }
}
