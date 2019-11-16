package com.nikitosoleil;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

public class Creator {
    private BulletAppState bulletAppState;
    private Node rootNode;
    private Material material;

    public Creator(AssetManager assetManager, BulletAppState bulletAppState, Node rootNode) {
        this.bulletAppState = bulletAppState;
        this.rootNode = rootNode;
        material = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
    }

    private void register(RigidBody body) {
        rootNode.attachChild(body.geo);
        bulletAppState.getPhysicsSpace().add(body.control);
    }

    public Wall createWall(Vector3f dims, Vector3f pos) {
        Wall wall = new Wall(material, dims, pos);
        register(wall);
        return wall;
    }

    public Ball createBall() {
        Ball ball = new Ball(material);
        register(ball);
        return ball;
    }
}
