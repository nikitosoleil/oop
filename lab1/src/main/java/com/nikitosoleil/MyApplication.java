package com.nikitosoleil;

import com.jme3.app.SimpleApplication;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.Trigger;
import com.jme3.math.ColorRGBA;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.light.DirectionalLight;
import com.jme3.light.AmbientLight;
import com.jme3.shadow.DirectionalLightShadowFilter;
import com.jme3.shadow.EdgeFilteringMode;
import com.jme3.input.MouseInput;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.post.FilterPostProcessor;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Sphere;

import java.util.Vector;

public class MyApplication extends SimpleApplication {
    // BALL CONFIGS
    protected static final Sphere sphere = new Sphere(64, 64, 0.5f, true, false);
    protected static final float shoot_distance = 3.0f;
    protected static final float shoot_speed = 25.0f;
    protected static final float ball_weight = 2.0f;

    // CAMERA CONFIGS
    protected static final Vector3f cam_pos = new Vector3f(0, 8f, 14f);
    protected static final Vector3f cam_look = new Vector3f(0, 2, 0);
    protected static final float cam_speed = 5.0f;

    // LIGHTING CONFIGS
    protected static final Vector3f light_direction = new Vector3f(-1.0f, -2.0f, -1.5f);
    protected static final ColorRGBA directional_light_color = ColorRGBA.White;
    protected static final ColorRGBA ambient_light_color = new ColorRGBA(0.1f, 0.1f, 0.1f, 0.f);

    // FILTERS CONFIGS
    protected static final int shadowmap_size = 4096;
    protected static final int nb_splits = 4;
    protected static final float shadow_lambda = 0.6f;
    protected static final float shadow_intensity = 0.5f;

    private BulletAppState bulletAppState;
    private Material default_mat;

    private Vector<Geometry> walls = new Vector<Geometry>();
    private Vector<Geometry> balls = new Vector<Geometry>();

    private AmbientLight ambientLight;
    private DirectionalLight directionalLight;
    private DirectionalLightShadowFilter dlsFilter;

    private boolean noInit = false;

    // GETTERS

    public AmbientLight getAmbientLight() {
        return ambientLight;
    }

    public DirectionalLight getDirectionalLight() {
        return directionalLight;
    }

    public DirectionalLightShadowFilter getDlsFilter() {
        return dlsFilter;
    }

    public Vector<Geometry> getWalls() {
        return walls;
    }

    public Vector<Geometry> getBalls() {
        return balls;
    }

    // IMPLEMENTATION

    @Override
    public void simpleInitApp() {
        bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);

        default_mat = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");

        if (!noInit) {
            initCam();
            initLights();
            initFilters();
            initWalls();
            initMappings();
        }
    }

    public void parentInit() {
        noInit = true;
        initialize();
        noInit = false;
    }

    public void initCam() {
        cam.setLocation(cam_pos);
        cam.lookAt(cam_look, Vector3f.UNIT_Y);
        flyCam.setMoveSpeed(cam_speed);
    }

    public void initLights() {
        directionalLight = new DirectionalLight(light_direction, directional_light_color);
        rootNode.addLight(directionalLight);

        ambientLight = new AmbientLight(ambient_light_color);
        rootNode.addLight(ambientLight);
    }

    public DirectionalLightShadowFilter constructDlsFilter() {
        DirectionalLightShadowFilter dlsf = new DirectionalLightShadowFilter(assetManager, shadowmap_size, nb_splits);
        dlsf.setLambda(shadow_lambda);
        dlsf.setShadowIntensity(shadow_intensity);
        dlsf.setEdgeFilteringMode(EdgeFilteringMode.Nearest);
        return dlsf;
    }

    public void initFilters() {
        dlsFilter = constructDlsFilter();
        dlsFilter.setLight(directionalLight);
        FilterPostProcessor fpp = new FilterPostProcessor(assetManager);
        fpp.addFilter(dlsFilter);
        viewPort.addProcessor(fpp);
    }

    public void initMappings() {
        addMapping("shoot", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        addMapping("ambientLight", new KeyTrigger(KeyInput.KEY_1));
        addMapping("directionalLight", new KeyTrigger(KeyInput.KEY_2));
        addMapping("shadowFilter", new KeyTrigger(KeyInput.KEY_3));
    }

    public void addMapping(String name, Trigger trigger) {
        inputManager.addMapping(name, trigger);
        inputManager.addListener(actionListener, name);
    }

    protected ActionListener actionListener = new ActionListener() {
        public void onAction(String name, boolean keyPressed, float tpf) {
            if (name.equals("shoot") && !keyPressed) {
                newBall();
            } else if (name.equals("ambientLight") && !keyPressed) {
                Switch.light(ambientLight);
            } else if (name.equals("directionalLight") && !keyPressed) {
                Switch.light(directionalLight);
            } else if (name.equals("shadowFilter") && !keyPressed) {
                Switch.filter(dlsFilter);
            }
        }
    };

    public void initWalls() {
        addWall(new Vector3f(10f, 2f, 0.1f), new Vector3f(0f, 2f, 10f));
        addWall(new Vector3f(10f, 2f, 0.1f), new Vector3f(0f, 2f, -10f));
        addWall(new Vector3f(0.1f, 2f, 10f), new Vector3f(-10f, 2f, 0f));
        addWall(new Vector3f(0.1f, 2f, 10f), new Vector3f(10f, 2f, 0f));
        addWall(new Vector3f(5f, 0.1f, 5f), new Vector3f(0f, 2f, 0f));
        addWall(new Vector3f(10f, 0.1f, 10f), new Vector3f(0f, 0f, 0f));
    }

    public RigidBodyControl extendPhys(Geometry geo, float m) {
        RigidBodyControl phy = new RigidBodyControl(m);
        geo.addControl(phy);
        bulletAppState.getPhysicsSpace().add(phy);
        return phy;
    }

    public Geometry createWallGeo(Vector3f dims, Vector3f pos) {
        Box wall_box = new Box(dims.x, dims.y, dims.z);
        Geometry wall_geo = new Geometry("Wall", wall_box);
        wall_geo.setMaterial(default_mat);
        wall_geo.setShadowMode(ShadowMode.CastAndReceive);
        wall_geo.setLocalTranslation(pos);
        return wall_geo;
    }

    public void addWall(Vector3f dims, Vector3f pos) {
        Geometry wall_geo = createWallGeo(dims, pos);
        rootNode.attachChild(wall_geo);
        extendPhys(wall_geo, 0.0f);
        walls.add(wall_geo);
    }

    public Material getBallMat(ColorRGBA color, float shininess) {
        Material mat = default_mat.clone();
//        mat.setTexture("NormalMap", assetManager.loadTexture("Textures/Terrain/Rock/Rock_normal.png"));
        mat.setBoolean("UseMaterialColors", true);
        mat.setColor("Diffuse", color);
        mat.setColor("Specular", color);
        mat.setFloat("Shininess", shininess);
        return mat;
    }

    public Geometry createBallGeo() {
        Geometry ball_geo = new Geometry("Ball", sphere);
        Material mat = getBallMat(ColorRGBA.randomColor(), 64f);
        ball_geo.setMaterial(mat);
        ball_geo.setShadowMode(ShadowMode.CastAndReceive);
        return ball_geo;
    }

    public void newBall() {
        Geometry ball_geo = createBallGeo();
        rootNode.attachChild(ball_geo);
        ball_geo.setLocalTranslation(cam.getLocation().add(cam.getDirection().mult(shoot_distance)));
        RigidBodyControl ball_phy = extendPhys(ball_geo, ball_weight);
        ball_phy.setLinearVelocity(cam.getDirection().mult(shoot_speed));
        balls.add(ball_geo);
    }
}