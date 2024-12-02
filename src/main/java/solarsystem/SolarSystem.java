package solarsystem;

import com.jme3.app.SimpleApplication;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.light.PointLight;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.filters.BloomFilter;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Quad;
import com.jme3.scene.shape.Sphere;
import com.jme3.system.AppSettings;
import com.jme3.texture.Texture;
import com.jme3.util.SkyFactory;
import planets.*;


public class SolarSystem extends SimpleApplication {

    private Node planetsNode = new Node("Planets");
    private Geometry sun;
    private Planet earth, mercury, venus, mars, jupiter, saturn, uranus, neptune;
    private boolean simulationPaused = false;
    private Node dialogNode = new Node("Dialog Node");
    private BitmapText speedText;

    public SolarSystem() {
        new Thread(PlanetInfo::initData).start();
        AppSettings appSettings = new AppSettings(true);
        appSettings.setTitle("Solar System");
        appSettings.setFrameRate(60);
        appSettings.setFullscreen(true);
        appSettings.setResolution(1280, 720);
        this.setSettings(appSettings);
    }



    private void initGuide() {
        setDisplayStatView(false);
        BitmapFont font = assetManager.loadFont("Interface/Fonts/Default.fnt");

        // Create a BitmapText with the font
        String guide = """
                Nhan 't' de tang toc do gia lap
                Nhan 'y' de giam toc do gia lap
                """;
        BitmapText text = new BitmapText(font, false);
        speedText = new BitmapText(font, false);
        speedText.setText("Toc do hien tai: %f".formatted(speed));
        speedText.setColor(ColorRGBA.Yellow);
        // Set the text content
        text.setText(guide); // Add your frame rate or other text here

        // Set the location of the text at the top left
        text.setLocalTranslation(10, settings.getHeight() - 10, 0);
        speedText.setLocalTranslation(10, settings.getHeight() - 50, 0);

        // Attach the text to the GUI node
        guiNode.attachChild(text);
        guiNode.attachChild(speedText);
    }

    @Override
    public void simpleInitApp() {
        // Handle light resource
        flyCam.setMoveSpeed(30);
        cam.setLocation(new Vector3f(50f, 50f, 20f));
        initGuide();
        initCrossHairs();
        initKey();
        setSpeed(getSpeed() / 40);
        createSun();
        makePlanets();
        rootNode.attachChild(planetsNode);
        setupSky();
        setUpBloom();
    }

    private void initCrossHairs() {
        setDisplayStatView(false);
        guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
        BitmapText ch = new BitmapText(guiFont);
        ch.setSize(guiFont.getCharSet().getRenderedSize() * 2);
        ch.setText("+");
        ch.setLocalTranslation(
                settings.getWidth() / 2 - ch.getLineWidth() / 2, settings.getHeight() / 2 + ch.getLineHeight() / 2, 0
        );
        guiNode.attachChild(ch);
    }

    private void initKey() {
        inputManager.deleteMapping("SIMPLEAPP_Exit");
        inputManager.addMapping("SpeedUp", new KeyTrigger(KeyInput.KEY_T));
        inputManager.addMapping("SpeedDown", new KeyTrigger(KeyInput.KEY_Y));
        inputManager.addMapping("pick", new MouseButtonTrigger(MouseInput.BUTTON_LEFT), new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addListener(new ActionListener() {
            @Override
            public void onAction(String s, boolean b, float v) {
                if (s.equals("pick") && !b) {
                    Ray ray = new Ray(cam.getLocation(), cam.getDirection());
                    CollisionResults results = new CollisionResults();
                    planetsNode.collideWith(ray, results);
                    if (results.size() > 0) {
                        System.out.println("Pick");
                        CollisionResult closest = results.getClosestCollision();
                        var clickedGeometry = closest.getGeometry();
                        String name = clickedGeometry.getUserData("name");
                        if (name != null) {
                            displayPlanetDialog(name);
                        }
                    }
                }
            }
        }, "pick");
        inputManager.addListener(new ActionListener() {
            @Override
            public void onAction(String s, boolean b, float v) {
                if (b) {
                    if (s.equals("SpeedUp")) {
                        setSpeed(getSpeed() + 0.1f);
                    }
                    if (s.equals("SpeedDown")) {
                        setSpeed(getSpeed() - 0.1f);
                    }
                }
            }
        }, "SpeedDown", "SpeedUp");
    }

    // Display planet information in a dialog
    private void displayPlanetDialog(String name) {
        simulationPaused = true; // Pause the simulation
        guiNode.attachChild(dialogNode); // Attach dialog to the GUI node
        BitmapFont font = assetManager.loadFont("Interface/Fonts/Default.fnt");
        // Background for dialog
        Geometry bg = new Geometry("Dialog Background", new Quad(700, 500));
        Material bgMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        bgMat.setColor("Color", new ColorRGBA(0, 0, 0, 0.7f)); // Black with 70% opacity
        bgMat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha); // Enable transparency
        bg.setMaterial(bgMat);
        bg.setLocalTranslation(cam.getWidth() / 2 - 250, cam.getHeight() / 2 - 300, 0);

// Border for the dialog
        Geometry border = new Geometry("Dialog Border", new Quad(710, 510)); // Slightly larger
        Material borderMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        borderMat.setColor("Color", ColorRGBA.Gray); // Gray border color
        borderMat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha); // Enable transparency
        border.setMaterial(borderMat);
        border.setLocalTranslation(cam.getWidth() / 2 - 255, cam.getHeight() / 2 - 305, -0.01f); // Behind the background
        dialogNode.attachChild(border);
        dialogNode.attachChild(bg);
        // Display planet information
        String info = PlanetInfo.getInfo(name.toLowerCase());
        System.out.println(info);

        BitmapText text = new BitmapText(font, false);
        text.setSize(font.getCharSet().getRenderedSize() + 5);
        text.setText(info);
        text.setLocalTranslation(cam.getWidth() / 2 - 200, cam.getHeight() / 2 + 150, 0);
        BitmapText textClose = new BitmapText(font, false);
        textClose.setSize(font.getCharSet().getRenderedSize() + 10);
        textClose.setText("Nhan nut ESC de tat cua so");
        textClose.setColor(ColorRGBA.Red);
        textClose.setLocalTranslation(cam.getWidth() / 2 + 50, cam.getHeight() / 2 + 180, 0);

        dialogNode.attachChild(textClose);
        dialogNode.attachChild(text);

        // Add a "Close" button
        inputManager.addMapping("closeDialog", new KeyTrigger(KeyInput.KEY_ESCAPE));
        inputManager.addListener(new ActionListener() {
            @Override
            public void onAction(String name, boolean isPressed, float tpf) {
                if (name.equals("closeDialog") && !isPressed) {
                    closeDialog();
                }
            }
        }, "closeDialog");
    }

    // Close the dialog and resume simulation
    private void closeDialog() {
        dialogNode.detachAllChildren(); // Remove dialog content
        guiNode.detachChild(dialogNode); // Detach dialog from GUI node
        simulationPaused = false; // Resume simulation
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
        Satellite phobos = new Satellite("Phobos", 0.011F, 0.07F, 0.005F, 1.46F); // Tùy chỉnh kích thước, tốc độ quay, khoảng cách
        phobos.applyMaterial(assetManager, "Textures/Planets/Satellite/Mars/phobos_texture.jpeg"); // Đường dẫn texture
        Satellite deimos = new Satellite("Deimos", 0.006F, 0.03F, 0.002F, 3.67F); // Tùy chỉnh kích thước, tốc độ quay, khoảng cách
        deimos.applyMaterial(assetManager, "Textures/Planets/Satellite/Mars/deimos_texture.jpeg"); // Đường dẫn texture
        mars.addSatellite(phobos);
        mars.addSatellite(deimos);

// Jupiter
        jupiter = new Planet("Jupiter", 11.19f, 0.13f, 0.041f, 120.0f);
        jupiter.applyMaterial(assetManager, "Textures/Planets/jupiter_texture.jpg");
        jupiter.createJupiterSatellite(assetManager);
        Ring jupiterRing = jupiter.createPlanetRings(assetManager,
                4f, 1.6f, 0.05f);  // Cập nhật thông số cho vành đai Jupiter
        // Điều chỉnh góc nghiêng cho vành đai Jupiter (góc nghiêng rất nhỏ, khoảng 0.05 độ)
        jupiterRing.adjustOrientation(0.05f, 0, 0);
        // Điều chỉnh độ sáng của vành đai Jupiter
        jupiterRing.adjustBrightness(0.85f);
        jupiter.attachChild(jupiterRing);
// Saturn
        saturn = new Planet("Saturn", 9.45f, 0.09f, 0.034f, 200.0f);
        saturn.applyMaterial(assetManager, "Textures/Planets/saturn_texture.jpg");
        saturn.createSaturnSatellite(saturn, assetManager);
        // Cập nhật vành đai Saturn với thông số thực tế và góc nghiêng
        Ring saturnRing = saturn.createPlanetRings(assetManager,
                3f, 0.74f, 0.1f);
        // Điều chỉnh góc nghiêng và góc quay của vành đai Saturn
        // Góc nghiêng của vành đai Saturn là khoảng 26.7 độ
        saturnRing.adjustOrientation(26.7f, 0, 0);  // Xoay theo trục X với góc 26.7 độ
        saturn.attachChild(saturnRing);
// Uranus
        uranus = new Planet("Uranus", 4.00f, 0.06f, 0.022f, 300.0f);
        uranus.applyMaterial(assetManager, "Textures/Planets/uranus_texture.jpg");
        // Cập nhật vành đai Uranus với thông số thực tế và góc nghiêng (98 độ)
        Ring uranusRing = uranus.createPlanetRings(assetManager,
                2f, 0.5f, 0.1f);
        // Điều chỉnh góc nghiêng cho vành đai Uranus (98 độ)
        uranusRing.adjustOrientation(98, 0, 0);
//        uranusRing.adjustBrightness(0.1f);
        uranus.attachChild(uranusRing);
        Satellite miranda = new Satellite("Miranda",
                0.06f,
                0.174f,
                1.413f,
                20.29f);
        miranda.applyMaterial(assetManager, "Textures/Planets/Satellite/Uranus/miranda_diffuse.png");
        Satellite ariel = new Satellite("Ariel",
                0.08f,
                0.287f,
                2.52f,
                30f);
        ariel.applyMaterial(assetManager, "Textures/Planets/Satellite/Uranus/ariel_texture.jpeg");
        Satellite oberon = new Satellite("Oberon",
                0.12f,
                0.391f,
                13.46f,
                91.56f);
        oberon.applyMaterial(assetManager, "Textures/Planets/Satellite/Uranus/oberon_text_ture.jpeg");
        Satellite titania = new Satellite("Titania",
                0.14f,
                0.292f,
                8.71f,
                68.44f);
        titania.applyMaterial(assetManager, "Textures/Planets/Satellite/Uranus/titania_text_ture.jpeg");
        uranus.addSatellite(miranda);
        uranus.addSatellite(ariel);
        uranus.addSatellite(oberon);
        uranus.addSatellite(titania);

// Neptune
        neptune = new Planet("Neptune", 3.88f, 0.03f, 0.016f, 400.0f);
        neptune.applyMaterial(assetManager, "Textures/Planets/neptune_texture.jpg");

        Ring neptuneRing = neptune.createPlanetRings(assetManager,
                5f, 0.8f, 0.02f);  // Cập nhật thông số cho vành đai Neptune, bán kính và độ sáng thấp
        // Điều chỉnh góc nghiêng cho vành đai Neptune (khoảng 1 độ)
        neptuneRing.adjustOrientation(1.0f, 0, 0);
        // Điều chỉnh độ sáng của vành đai Neptune (giảm độ sáng vì vành đai Neptune khá mờ)
        neptuneRing.adjustBrightness(0.05f);
        neptune.attachChild(neptuneRing);
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
        sunLight.setRadius(5000f);  // This is an arbitrary value, adjust as needed

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
        speedText.setText("Toc do hien tai: %f".formatted(speed));
        if (simulationPaused) {
            return;
        }
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
