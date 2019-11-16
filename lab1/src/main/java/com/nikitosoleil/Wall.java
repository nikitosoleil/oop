package com.nikitosoleil;

import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.material.Material;

public class Wall extends RigidBody {
    protected static float weight = 0.0f;
    private Vector3f dims;
    private Vector3f pos;
    private Box box;

    public Wall(Material material, Vector3f dims, Vector3f pos) {
        super(material);
        this.dims = dims;
        this.pos = pos;
        initGeo();
        addControl(weight);
    }

    protected void initGeo() {
        box = new Box(dims.x, dims.y, dims.z);
        geo = new Geometry("Wall", box);
        geo.setMaterial(material);
        geo.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);
        geo.setLocalTranslation(pos);
    }
}
