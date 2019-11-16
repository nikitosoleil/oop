package com.nikitosoleil;

import com.jme3.app.SimpleApplication;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.Trigger;
import com.jme3.bullet.BulletAppState;
import com.jme3.input.MouseInput;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.math.Vector3f;

import java.util.Vector;

public class MyApplication extends SimpleApplication {
    // BALL CONFIGS
    protected static final float shoot_distance = 3.0f;
    protected static final float shoot_speed = 30.0f;

    // CAMERA CONFIGS
    protected static final Vector3f cam_pos = new Vector3f(0, 8f, 14f);
    protected static final Vector3f cam_look = new Vector3f(0, 2, 0);
    protected static final float cam_speed = 5.0f;

    // VARIABLES

    private BulletAppState bulletAppState;

    private Vector<Wall> walls = new Vector<Wall>();
    private Vector<Ball> balls = new Vector<Ball>();

    private MyCollisionListener collisionListener;
    private MyActionListener actionListener;
    private Creator creator;
    private Enlighter enlighter;

    // GETTERS

    public Vector<Wall> getWalls() {
        return walls;
    }

    public Vector<Ball> getBalls() {
        return balls;
    }

    public MyCollisionListener getCollisionListener() {
        return collisionListener;
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

        collisionListener = new MyCollisionListener();
        bulletAppState.getPhysicsSpace().addCollisionListener(collisionListener);

        initCam();

        actionListener = new MyActionListener(this);
        initMappings();

        enlighter = new Enlighter(assetManager, rootNode, viewPort);
        enlighter.createAll();

        creator = new Creator(assetManager, bulletAppState, rootNode);
        initWalls();
    }

    public void demo(float speed) {
        Ball one = creator.createBall();
        Ball two = creator.createBall();
        one.launch(new Vector3f(-5.0f, 5.0f, -0.1f), new Vector3f(1.0f, 0.0f, 0.0f).mult(speed));
        two.launch(new Vector3f(5.0f, 5.0f, 0.1f), new Vector3f(-1.0f, 0.0f, 0.0f).mult(speed));
    }

    public void initCam() {
        cam.setLocation(cam_pos);
        cam.lookAt(cam_look, Vector3f.UNIT_Y);
        flyCam.setMoveSpeed(cam_speed);
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
        walls.add(creator.createWall(new Vector3f(10f, 2f, 0.1f), new Vector3f(0f, 2f, 10f)));
        walls.add(creator.createWall(new Vector3f(10f, 2f, 0.1f), new Vector3f(0f, 2f, -10f)));
        walls.add(creator.createWall(new Vector3f(0.1f, 2f, 10f), new Vector3f(-10f, 2f, 0f)));
        walls.add(creator.createWall(new Vector3f(0.1f, 2f, 10f), new Vector3f(10f, 2f, 0f)));
        walls.add(creator.createWall(new Vector3f(5f, 0.1f, 5f), new Vector3f(0f, 2f, 0f)));
        walls.add(creator.createWall(new Vector3f(10f, 0.1f, 10f), new Vector3f(0f, 0f, 0f)));
    }

    public void newBall() {
        Ball ball = creator.createBall();
        ball.launch(cam.getLocation().add(cam.getDirection().mult(shoot_distance)), cam.getDirection().mult(shoot_speed));
        balls.add(ball);
    }
}