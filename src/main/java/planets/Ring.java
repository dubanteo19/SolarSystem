package planets;

import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.VertexBuffer;

import java.nio.FloatBuffer;

public class Ring extends Node {
    private Geometry ringGeometry;
    private float angle = 0f;
    private float orbitSpeed;

    public Ring(String name, Mesh mesh, Material material, float orbitSpeed) {
        this.orbitSpeed = orbitSpeed;
        this.ringGeometry = new Geometry(name, mesh);
        this.ringGeometry.setMaterial(material);

        // Xoay để phù hợp với trục X-Y
        this.ringGeometry.rotate((float) Math.toRadians(90), 0, 0);

        // Adjust texture coordinates based on the mesh's geometry
        adjustTextureCoordinates();

        this.attachChild(ringGeometry);
    }

    public void update(float tpf, Vector3f planetPosition) {
        // Quay quanh hành tinh
        angle += orbitSpeed * tpf;
        if (angle > Math.PI * 2) {
            angle -= Math.PI * 2;
        }

        // Tính vị trí mới
        float x = planetPosition.x + (float) Math.cos(angle);
        float z = planetPosition.z + (float) Math.sin(angle);
        this.setLocalTranslation(x, 0, z);
    }

    public void adjustOrientation(float xAngle, float yAngle, float zAngle) {
        this.ringGeometry.setLocalRotation(new Quaternion().fromAngles(
                (float) Math.toRadians(xAngle),
                (float) Math.toRadians(yAngle),
                (float) Math.toRadians(zAngle)
        ));
    }

    public void adjustBrightness(float brightness) {
        if (brightness < 0f || brightness > 1f) {
            throw new IllegalArgumentException("Brightness must be between 0 and 1");
        }

        // Lấy material và điều chỉnh màu sắc dựa trên brightness
        Material material = ringGeometry.getMaterial();
        if (material != null && material.getParam("Diffuse") != null) {
            Object colorObject = material.getParam("Diffuse").getValue();
            if (colorObject instanceof ColorRGBA) {
                ColorRGBA color = (ColorRGBA) colorObject;
                // Điều chỉnh độ sáng
                color = color.mult(brightness);
                material.setColor("Diffuse", color);
            }
        }
    }

    // Điều chỉnh tọa độ UV nếu cần
    private void adjustTextureCoordinates() {
        Mesh mesh = this.ringGeometry.getMesh();
        VertexBuffer texCoord = mesh.getBuffer(VertexBuffer.Type.TexCoord);
        if (texCoord != null) {
            FloatBuffer buffer = (FloatBuffer) texCoord.getData();
            float scaleU = 1.0f; // Tỷ lệ UV theo chiều U
            float scaleV = 1.0f; // Tỷ lệ UV theo chiều V

            // Áp dụng tỷ lệ vào các tọa độ UV
            for (int i = 0; i < buffer.limit(); i += 2) {
                float u = buffer.get(i);
                float v = buffer.get(i + 1);
                buffer.put(i, u * scaleU);   // Thay đổi hệ số UV cho chiều U
                buffer.put(i + 1, v * scaleV); // Thay đổi hệ số UV cho chiều V
            }
            texCoord.updateData(buffer);
        }
    }

}



