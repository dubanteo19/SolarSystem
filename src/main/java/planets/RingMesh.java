package planets;

import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Mesh;
import com.jme3.scene.VertexBuffer;
import com.jme3.util.BufferUtils;

import java.util.ArrayList;
import java.util.List;

public class RingMesh extends Mesh {
    public RingMesh(float innerRadius, float outerRadius, int segments) {
        // Danh sách vertex, UV và chỉ mục (indices)
        List<Vector3f> vertices = new ArrayList<>();
        List<Vector2f> texCoords = new ArrayList<>();
        List<Integer> indices = new ArrayList<>();

        // Tạo vertex và tọa độ UV
        for (int i = 0; i <= segments; i++) {
            float angle = (float) (2 * Math.PI * i / segments);
            float cos = (float) Math.cos(angle);
            float sin = (float) Math.sin(angle);

            // Nội điểm (inner ring)
            vertices.add(new Vector3f(innerRadius * cos, 0, innerRadius * sin));
            texCoords.add(new Vector2f((float) i / segments, 0)); // Vùng nội

            // Ngoại điểm (outer ring)
            vertices.add(new Vector3f(outerRadius * cos, 0, outerRadius * sin));
            texCoords.add(new Vector2f((float) i / segments, 1)); // Vùng ngoại
        }

        // Tạo chỉ mục (indices) cho từng đoạn tam giác
        for (int i = 0; i < segments; i++) {
            int inner = i * 2;
            int outer = inner + 1;

            indices.add(inner);
            indices.add(outer);
            indices.add(inner + 2);

            indices.add(outer);
            indices.add(outer + 2);
            indices.add(inner + 2);
        }

        // Chuyển đổi sang mảng
        this.setBuffer(VertexBuffer.Type.Position, 3, BufferUtils.createFloatBuffer(vertices.toArray(new Vector3f[0])));
        this.setBuffer(VertexBuffer.Type.TexCoord, 2, BufferUtils.createFloatBuffer(texCoords.toArray(new Vector2f[0])));
        this.setBuffer(VertexBuffer.Type.Index, 3, BufferUtils.createIntBuffer(indices.stream().mapToInt(i -> i).toArray()));

        // Tạo bounding box
        this.updateBound();
    }
}
