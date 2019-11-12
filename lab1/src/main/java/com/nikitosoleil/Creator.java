package com.nikitosoleil;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Sphere;

public class Creator {
    protected static final Sphere sphere = new Sphere(64, 64, 0.5f, true, false);
    private Material default_mat;
    private AssetManager assetManager;

    public Creator(AssetManager assetManager) {
        this.assetManager = assetManager;
        default_mat = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
    }

    public RigidBodyControl extendPhys(Geometry geo, float m) {
        RigidBodyControl phy = new RigidBodyControl(m);
        geo.addControl(phy);
        return phy;
    }

    public Geometry createWallGeo(Vector3f dims, Vector3f pos) {
        Box wall_box = new Box(dims.x, dims.y, dims.z);
        Geometry wall_geo = new Geometry("Wall", wall_box);
        wall_geo.setMaterial(default_mat);
        wall_geo.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);
        wall_geo.setLocalTranslation(pos);
        return wall_geo;
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
        ball_geo.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);
        return ball_geo;
    }
}
