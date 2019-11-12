package com.nikitosoleil;

import com.jme3.app.SimpleApplication;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.Trigger;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.light.DirectionalLight;
import com.jme3.light.AmbientLight;
import com.jme3.shadow.DirectionalLightShadowFilter;
import com.jme3.input.MouseInput;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.scene.Geometry;

import java.util.Vector;

public class MyApplication extends SimpleApplication {
    // BALL CONFIGS
    protected static final float shoot_distance = 3.0f;
    protected static final float shoot_speed = 25.0f;
    protected static final float ball_weight = 2.0f;

    // CAMERA CONFIGS
    protected static final Vector3f cam_pos = new Vector3f(0, 8f, 14f);
    protected static final Vector3f cam_look = new Vector3f(0, 2, 0);
    protected static final float cam_speed = 5.0f;

    // VARIABLES

    private BulletAppState bulletAppState;

    private Vector<Geometry> walls = new Vector<Geometry>();
    private Vector<Geometry> balls = new Vector<Geometry>();

    private AmbientLight ambientLight;
    private DirectionalLight directionalLight;
    private DirectionalLightShadowFilter dlsFilter;

    private MyActionListener actionListener;
    private Creator creator;
    private Enlighter enlighter;

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

    public MyActionListener getActionListener() {
        return actionListener;
    }

    public Enlighter getEnlighter() {
        return enlighter;
    }

    // IMPLEMENTATION

    @Override
    public void simpleInitApp() {
        bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);

        creator = new Creator(assetManager);
        enlighter = new Enlighter(assetManager);
        actionListener = new MyActionListener(this);

        initCam();
        initLights();
        initFilters();
        initWalls();
        initMappings();
    }

    public void initCam() {
        cam.setLocation(cam_pos);
        cam.lookAt(cam_look, Vector3f.UNIT_Y);
        flyCam.setMoveSpeed(cam_speed);
    }

    public void initLights() {
        directionalLight = enlighter.constructDirectionalLight();
        rootNode.addLight(directionalLight);

        ambientLight = enlighter.constructAmbientLight();
        rootNode.addLight(ambientLight);
    }

    public void initFilters() {
        dlsFilter = enlighter.constructDlsFilter();
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

    public void initWalls() {
        newWall(new Vector3f(10f, 2f, 0.1f), new Vector3f(0f, 2f, 10f));
        newWall(new Vector3f(10f, 2f, 0.1f), new Vector3f(0f, 2f, -10f));
        newWall(new Vector3f(0.1f, 2f, 10f), new Vector3f(-10f, 2f, 0f));
        newWall(new Vector3f(0.1f, 2f, 10f), new Vector3f(10f, 2f, 0f));
        newWall(new Vector3f(5f, 0.1f, 5f), new Vector3f(0f, 2f, 0f));
        newWall(new Vector3f(10f, 0.1f, 10f), new Vector3f(0f, 0f, 0f));
    }

    public void newWall(Vector3f dims, Vector3f pos) {
        Geometry wallGeo = creator.createWallGeo(dims, pos);
        rootNode.attachChild(wallGeo);
        RigidBodyControl wallPhy = creator.extendPhys(wallGeo, 0.0f);
        bulletAppState.getPhysicsSpace().add(wallPhy);
        walls.add(wallGeo);
    }

    public void newBall() {
        Geometry ballGeo = creator.createBallGeo();
        rootNode.attachChild(ballGeo);
        ballGeo.setLocalTranslation(cam.getLocation().add(cam.getDirection().mult(shoot_distance)));
        RigidBodyControl ballPhy = creator.extendPhys(ballGeo, ball_weight);
        bulletAppState.getPhysicsSpace().add(ballPhy);
        ballPhy.setLinearVelocity(cam.getDirection().mult(shoot_speed));
        balls.add(ballGeo);
    }
}