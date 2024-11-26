package planets;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Sphere;
import com.jme3.texture.Texture;

public abstract class CelestialBody extends Node {
    protected Geometry geometry;
    protected String name;
    protected float rotationSpeed;
    protected float radius;
    protected float orbitSpeed;
    protected float distance;
    protected float angle = 0f;

    public CelestialBody(String name, float radius, float orbitSpeed, float rotationSpeed, float distance) {
        this.name = name;
        this.radius = radius;
        this.orbitSpeed = orbitSpeed;
        this.distance = distance;
        this.rotationSpeed = rotationSpeed;
        Sphere sphere = new Sphere(32, 32, this.radius);
        this.geometry = new Geometry(this.name, sphere);
        this.attachChild(geometry);
    }

    public void applyMaterial(AssetManager assetManager, String textureName) {
        Material material = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        Texture texture = assetManager.loadTexture(textureName);
        material.setTexture("DiffuseMap", texture);
        this.geometry.setMaterial(material);
    }

    @Override
    public String toString() {
        return "CelestialBody{" +
                "geometry=" + geometry +
                ", name='" + name + '\'' +
                ", radius=" + radius +
                ", orbitSpeed=" + orbitSpeed +
                ", distance=" + distance +
                ", angle=" + angle +
                '}';
    }

    public abstract void update(float tpf);
}
