package planets;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Spatial;
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

    //Tao cac ve tinh cua hanh tinh sao moc
    public void createJupiterSatellite(AssetManager assetManager) {
        // Io
        Satellite io = new Satellite("Io", 0.286F, 1.097F, 0.065F, 66F);
        io.applyMaterial(assetManager, "Textures/Planets/Satellite/Jupiter/io_texture.png");
        this.addSatellite(io);

        // Europa
        Satellite europa = new Satellite("Europa", 0.245F, 1.745F, 0.13F, 105.02F);
        europa.applyMaterial(assetManager, "Textures/Planets/Satellite/Jupiter/europa_texture.jpeg");
        this.addSatellite(europa);

        // Ganymede
        Satellite ganymede = new Satellite("Ganymede", 0.413F, 2.784F, 0.26F, 167.46F);
        ganymede.applyMaterial(assetManager, "Textures/Planets/Satellite/Jupiter/ganymede_texture.jpeg");
        this.addSatellite(ganymede);

        // Callisto
        Satellite callisto = new Satellite("Callisto", 0.378F, 4.899F, 0.611F, 294.45F);
        callisto.applyMaterial(assetManager, "Textures/Planets/Satellite/Jupiter/callisto_texture.jpeg");
        this.addSatellite(callisto);
    }

    //Tao cac ve tinh cua hanh tinh sao Tho
    public void createSaturnSatellite(Planet saturn, AssetManager assetManager) {
        Satellite enceladus = new Satellite("Enceladus",
                0.05f,
                0.238f,
                1.37f,
                37.33f);
        enceladus.applyMaterial(assetManager, "Textures/Planets/Satellite/Saturn/enceladus_texture.jpeg");
        saturn.addSatellite(enceladus);
        Satellite mimas = new Satellite("Mimas", 0.04f, 0.185f, 0.94f, 29.13f);
        mimas.applyMaterial(assetManager, "Textures/Planets/Satellite/Saturn/mimas_texture.png");
        saturn.addSatellite(mimas);

    }


    // Tạo vành đai cho hành tinh
    public Ring createPlanetRings(AssetManager assetManager, float innerOffset, float ringWidth, float orbitSpeed) {
        // Xác định bán kính trong và ngoài
        float innerRadius = this.radius + innerOffset;
        float outerRadius = innerRadius + ringWidth;
        String texturePath1 = "Textures/Planets/Satellite/Saturn/uranus2_A_diffuse.png";
        String texturePath2 = "Textures/Planets/Satellite/Saturn/uranus2_B_diffuse.png";
        String shadowTexturePath = "Textures/Planets/Satellite/Saturn/uranus2_A_specularGlossiness.png";
        // Tạo RingMesh
        Mesh ringMesh = new RingMesh(innerRadius, outerRadius, 64); // Tăng segments nếu cần mịn hơn

        // Tạo vật liệu cho vành đai
        Material ringMaterial = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");

        // Tải các texture cho vành đai
        Texture ringTexture1 = assetManager.loadTexture(texturePath1); // Texture cho vành đai (một phần)
        Texture ringTexture2 = assetManager.loadTexture(texturePath2); // Texture cho vành đai (một phần khác)
        Texture shadowTexture = assetManager.loadTexture(shadowTexturePath); // Texture cho bóng của vành đai

        // Đảm bảo chế độ texture được lặp
        ringTexture1.setWrap(Texture.WrapMode.Repeat);
        ringTexture2.setWrap(Texture.WrapMode.Repeat);
        shadowTexture.setWrap(Texture.WrapMode.Repeat);

        // Thiết lập bộ lọc cho các texture
        ringTexture1.setMinFilter(Texture.MinFilter.BilinearNearestMipMap);
        ringTexture1.setMagFilter(Texture.MagFilter.Bilinear);
        ringTexture2.setMinFilter(Texture.MinFilter.BilinearNearestMipMap);
        ringTexture2.setMagFilter(Texture.MagFilter.Bilinear);
        shadowTexture.setMinFilter(Texture.MinFilter.BilinearNearestMipMap);
        shadowTexture.setMagFilter(Texture.MagFilter.Bilinear);

        // Áp dụng các texture vào vật liệu
        ringMaterial.setTexture("ColorMap", ringTexture1); // Texture cho phần trong của vành đai
        ringMaterial.setTexture("ColorMap", ringTexture2); // Texture cho phần ngoài của vành đai
        ringMaterial.setTexture("ColorMap", shadowTexture); // Texture cho bóng của vành đai

        // Thiết lập các thông số bổ sung cho vật liệu
        ringMaterial.getAdditionalRenderState().setFaceCullMode(RenderState.FaceCullMode.Off);

        // Tạo Ring và thêm vào Planet
        return new Ring(this.name + "_Ring", ringMesh, ringMaterial, orbitSpeed);

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
        // Cập nhật các vành đai
        for (Spatial child : this.getChildren()) {
            if (child instanceof Ring) {
                ((Ring) child).update(tpf, this.geometry.getLocalTranslation());
            }
        }
    }
}
