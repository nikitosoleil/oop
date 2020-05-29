package com.nikitosoleil;

import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Sphere;

public class Ball extends RigidBody {
    protected static final Sphere sphere = new Sphere(64, 64, 0.5f, true, false);
    protected static float weight = 10.0f;
    protected final static float shininess = 64.0f;
    protected final ColorRGBA color = ColorRGBA.randomColor();

    public Ball(Material material) {
        super(material.clone());
        initMaterial();
        initGeo();
        addControl(weight);
    }

    public void initMaterial() {
//        mat.setTexture("NormalMap", assetManager.loadTexture("Textures/Terrain/Rock/Rock_normal.png"));
        material.setBoolean("UseMaterialColors", true);
        material.setColor("Diffuse", color);
        material.setColor("Specular", color);
        material.setFloat("Shininess", shininess);
    }

    public void initGeo() {
        geo = new Geometry("Ball", sphere);
        geo.setMaterial(material);
        geo.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);
    }

    public void launch(Vector3f pos, Vector3f dir) {
        control.setPhysicsLocation(pos);
        control.setLinearVelocity(dir);
    }
}
