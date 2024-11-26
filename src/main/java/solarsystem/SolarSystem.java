package solarsystem;

import com.jme3.app.SimpleApplication;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.DirectionalLight;
import com.jme3.light.PointLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.filters.BloomFilter;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Sphere;
import com.jme3.system.AppSettings;
import com.jme3.texture.Texture;
import com.jme3.util.SkyFactory;
import planets.Moon;
import planets.Planet;
import planets.Satellite;


public class SolarSystem extends SimpleApplication {

    private Node planetsNode = new Node("Planets");
    private Geometry sun;
    private Planet earth, mercury, venus, mars, jupiter, saturn, uranus, neptune;

    public SolarSystem() {
        AppSettings appSettings = new AppSettings(true);
        appSettings.setTitle("Solar System");
        appSettings.setFrameRate(60);
        appSettings.setFullscreen(true);
        appSettings.setResolution(1280, 720);
        this.setSettings(appSettings);
    }

    public static void main(String[] args) {
        SolarSystem app = new SolarSystem();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        // Handle light resource
        flyCam.setMoveSpeed(30);
        initKey();
        setSpeed(getSpeed());
        createSun();
        makePlanets();
        rootNode.attachChild(planetsNode);
        setupSky();
        setUpBloom();
    }

    private void initKey() {
        inputManager.addMapping("SpeedUp", new KeyTrigger(KeyInput.KEY_1));
        inputManager.addMapping("SpeedDown", new KeyTrigger(KeyInput.KEY_2));
        inputManager.addListener(new ActionListener() {
            @Override
            public void onAction(String s, boolean b, float v) {
                if (b) {
                    if (s.equals("SpeedUp")) {
                        setSpeed(getSpeed() + 0.1f);
                    }
                    if (s.equals("SpeedDown")) {
                        System.out.println(speed);
                        setSpeed(getSpeed() - 0.1f);
                    }
                }
            }
        }, "SpeedDown", "SpeedUp");
    }


    private void setupSky() {
        Spatial sky = SkyFactory.createSky(assetManager, assetManager.loadTexture("Textures/Sky/bkg1_right.png"),  // Positive X
                assetManager.loadTexture("Textures/Sky/bkg1_left.png"),   // Negative X
                assetManager.loadTexture("Textures/Sky/bkg1_back.png"),    // Negative Z
                assetManager.loadTexture("Textures/Sky/bkg1_front.png"),  // Positive Z
                assetManager.loadTexture("Textures/Sky/bkg1_top.png"),    // Positive Y
                assetManager.loadTexture("Textures/Sky/bkg1_bot.png")    // Negative Y
        );
        rootNode.attachChild(sky);
    }

    private void makePlanets() {
        // Earth
        earth = new Planet("Earth", 1.00f, 1.00f, 0.167f, 40.0f);
        earth.applyMaterial(assetManager, "Textures/Planets/earth_texture.jpg");
        Satellite moon = new Moon(assetManager);
        earth.addSatellite(moon);
        // Mercury
        mercury = new Planet("Mercury", 0.38f, 1.59f, 0.240f, 20.0f);
        mercury.applyMaterial(assetManager, "Textures/Planets/mercury_texture.jpg");

// Venus
        venus = new Planet("Venus", 0.95f, 1.18f, 0.092f, 30.0f);
        venus.applyMaterial(assetManager, "Textures/Planets/venus_texture.jpg");
// Mars
        mars = new Planet("Mars", 0.53f, 0.80f, 0.093f, 60.0f);
        mars.applyMaterial(assetManager, "Textures/Planets/mars_texture.jpg");

// Jupiter
        jupiter = new Planet("Jupiter", 11.19f, 0.13f, 0.041f, 120.0f);
        jupiter.applyMaterial(assetManager, "Textures/Planets/jupiter_texture.jpg");

// Saturn
        saturn = new Planet("Saturn", 9.45f, 0.09f, 0.034f, 200.0f);
        saturn.applyMaterial(assetManager, "Textures/Planets/saturn_texture.jpg");

// Uranus
        uranus = new Planet("Uranus", 4.00f, 0.06f, 0.022f, 300.0f);
        uranus.applyMaterial(assetManager, "Textures/Planets/uranus_texture.jpg");

// Neptune
        neptune = new Planet("Neptune", 3.88f, 0.03f, 0.016f, 400.0f);
        neptune.applyMaterial(assetManager, "Textures/Planets/neptune_texture.jpg");

        planetsNode.attachChild(earth);
        planetsNode.attachChild(neptune);
        planetsNode.attachChild(uranus);
        planetsNode.attachChild(saturn);
        planetsNode.attachChild(mars);
        planetsNode.attachChild(jupiter);
        planetsNode.attachChild(venus);
        planetsNode.attachChild(mercury);
    }

    private void createSun() {
        Sphere sunSphere = new Sphere(32, 32, 15f);
        this.sun = new Geometry("sun", sunSphere);
        this.sun.setLocalTranslation(0, 0, 0);
        Material sunMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        Texture sunTexture = assetManager.loadTexture("Textures/sun_texture.jpg");
        sunMat.setTexture("ColorMap", sunTexture);
        sunMat.setColor("GlowColor", new ColorRGBA(1.5f, 0.9f, 0.2f, 1f));
        sun.setMaterial(sunMat);
        rootNode.attachChild(sun);
        // Create a PointLight to act as the Sun's light source
        PointLight sunLight = new PointLight();
        sunLight.setPosition(sun.getLocalTranslation());  // Set the position of the light to the sun's position
        sunLight.setColor(new ColorRGBA(6f, 3.6f, 1.2f, 1f));  // Sun's light color

        // Set the range of the light (this will determine how far the light can reach)
        sunLight.setRadius(1000f);  // This is an arbitrary value, adjust as needed

        // Add the PointLight to the root node so it casts light
        rootNode.addLight(sunLight);

        viewPort.setClearFlags(true, true, true);
    }

    private void setUpBloom() {
        FilterPostProcessor fpp = new FilterPostProcessor(assetManager);
        BloomFilter bloomFilter = new BloomFilter(BloomFilter.GlowMode.Objects);
        bloomFilter.setExposurePower(5.0f);   // Stronger exposure for more intense glow
        bloomFilter.setBlurScale(3.0f);       // Increase blur for a larger, softer glow
        bloomFilter.setBloomIntensity(5.5f);
        fpp.addFilter(bloomFilter);
        viewPort.addProcessor(fpp);
    }

    @Override
    public void simpleUpdate(float tpf) {
        for (Spatial spatial : planetsNode.getChildren()) {
            if (spatial instanceof Planet planet) {
                planet.update(tpf);
            }
        }
    }

    @Override
    public void simpleRender(RenderManager rm) {
    }
}