package com.nikitosoleil;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.scene.Geometry;

public abstract class RigidBody {
    protected Geometry geo;
    protected RigidBodyControl control;
    protected Material material;

    public RigidBody(Material material) {
        this.material = material;
    }

    abstract protected void initGeo();

    protected void addControl(float weight) {
        control = new RigidBodyControl(weight);
        geo.addControl(control);
    }
}
