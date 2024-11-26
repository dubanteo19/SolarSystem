package planets;

import com.jme3.math.Vector3f;

public class Satellite extends CelestialBody {
    Planet parentPlanet;

    public Satellite(String name, float radius, float orbitSpeed, float rotateSpeed, float distance) {
        super(name, radius, orbitSpeed, rotateSpeed, distance);
    }

    public void setParentPlanet(Planet parentPlanet) {
        this.parentPlanet = parentPlanet;
    }

    @Override
    public void update(float tpf) {
        angle += orbitSpeed * tpf;
        //neu quay quanh 1 vong thi reset lai goc
        if (angle > Math.PI * 2) {
            angle -= Math.PI * 2;
        }
        var parentLocation = parentPlanet.geometry.getLocalTranslation();
        float x = parentLocation.x + (float) Math.cos(angle) * distance;
        float z = parentLocation.z + (float) Math.sin(angle) * distance;
        geometry.rotate(0, rotationSpeed * tpf, 0);
        this.geometry.setLocalTranslation(x, 0, z);
    }
}
