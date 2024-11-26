package animations;

import com.jme3.animation.LoopMode;
import com.jme3.asset.AssetManager;
import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;
import com.jme3.util.TangentBinormalGenerator;

// Animation Control for Sun

public class SunAnimationControl extends AbstractControl {
    private float elapsedTime = 0;
    private final float scaleTime = 4f; // Time to scale up
    private final float moveTime = 5f; // Time to drop
    private boolean exploded = false;

    @Override
    protected void controlUpdate(float tpf) {
        elapsedTime += tpf;

        if (elapsedTime < scaleTime) {
            // Scale the sun over time
            float scaleFactor = 1 + (100f * (elapsedTime / scaleTime));
            spatial.setLocalScale(scaleFactor);
        } else if (elapsedTime < scaleTime + moveTime) {
            // Move the sun down to the terrain
            float progress = (elapsedTime - scaleTime) / moveTime;
            Vector3f targetPosition = new Vector3f(0, -300, 0); // Adjust terrain level
            Vector3f startPosition = spatial.getLocalTranslation();
            spatial.setLocalTranslation(startPosition.interpolateLocal(targetPosition, progress));
        } else if (!exploded) {
        }
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        // Not needed for this control
    }

    private void createExplosion(Vector3f position, AssetManager assetManager) {
        // Create a particle emitter for the explosion
        ParticleEmitter explosion = new ParticleEmitter("Explosion", ParticleMesh.Type.Triangle, 30);
        explosion.setMaterial(new Material(assetManager, "Common/MatDefs/Misc/Particle.j3md"));
        explosion.getMaterial().setTexture("Texture", assetManager.loadTexture("Textures/Effects/explosion.png"));
        explosion.setImagesX(2); // Number of texture columns
        explosion.setImagesY(2); // Number of texture rows
        explosion.setStartColor(new ColorRGBA(1f, 0.8f, 0.4f, 1f));
        explosion.setEndColor(new ColorRGBA(1f, 0f, 0f, 0.1f));
        explosion.setStartSize(10f);
        explosion.setEndSize(0.1f);
        explosion.setGravity(0, -1, 0);
        explosion.setLowLife(1f);
        explosion.setHighLife(3f);
        explosion.setLocalTranslation(position);
        TangentBinormalGenerator.generate(explosion.getMesh());
//        rootNode.attachChild(explosion);
        explosion.emitAllParticles();
    }
}
