package com.nikitosoleil;

import com.jme3.asset.AssetManager;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.shadow.DirectionalLightShadowFilter;
import com.jme3.shadow.EdgeFilteringMode;

public class Enlighter {
    // LIGHTING CONFIGS
    protected static final Vector3f light_direction = new Vector3f(-1.0f, -2.0f, -1.5f);
    protected static final ColorRGBA directional_light_color = ColorRGBA.White;
    protected static final ColorRGBA ambient_light_color = new ColorRGBA(0.1f, 0.1f, 0.1f, 0.f);

    // FILTERS CONFIGS
    protected static final int shadowmap_size = 4096;
    protected static final int nb_splits = 4;
    protected static final float shadow_lambda = 0.6f;
    protected static final float shadow_intensity = 0.5f;

    private AssetManager assetManager;

    public Enlighter(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    public DirectionalLightShadowFilter constructDlsFilter() {
        DirectionalLightShadowFilter dlsf = new DirectionalLightShadowFilter(assetManager, shadowmap_size, nb_splits);
        dlsf.setLambda(shadow_lambda);
        dlsf.setShadowIntensity(shadow_intensity);
        dlsf.setEdgeFilteringMode(EdgeFilteringMode.Nearest);
        return dlsf;
    }

    public DirectionalLight constructDirectionalLight() {
        return new DirectionalLight(light_direction, directional_light_color);
    }

    public AmbientLight constructAmbientLight() {
        return new AmbientLight(ambient_light_color);
    }
}
