package com.nikitosoleil;

import com.jme3.asset.AssetManager;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
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

    // VARIABLES

    private AssetManager assetManager;
    private Node rootNode;
    private ViewPort viewPort;

    private AmbientLight ambientLight;
    private DirectionalLight directionalLight;
    private DirectionalLightShadowFilter dlsFilter;

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

    public Enlighter(AssetManager assetManager, Node rootNode, ViewPort viewPort) {
        this.assetManager = assetManager;
        this.rootNode = rootNode;
        this.viewPort = viewPort;
    }

    public void createDirectionalLight() {
        directionalLight = new DirectionalLight(light_direction, directional_light_color);
        rootNode.addLight(directionalLight);
    }

    public void createAmbientLight() {
        ambientLight = new AmbientLight(ambient_light_color);
        rootNode.addLight(ambientLight);
    }

    public static DirectionalLightShadowFilter buildDlsFilter(AssetManager assetManager) {
        DirectionalLightShadowFilter dlsf = new DirectionalLightShadowFilter(assetManager, shadowmap_size, nb_splits);
        dlsf.setLambda(shadow_lambda);
        dlsf.setShadowIntensity(shadow_intensity);
        dlsf.setEdgeFilteringMode(EdgeFilteringMode.Nearest);
        return dlsf;
    }

    public void createShadows() {
        dlsFilter = buildDlsFilter(assetManager);
        FilterPostProcessor fpp = new FilterPostProcessor(assetManager);
        dlsFilter.setLight(directionalLight);
        fpp.addFilter(dlsFilter);
        viewPort.addProcessor(fpp);
    }

    public void createAll() {
        createAmbientLight();
        createDirectionalLight();
        createShadows();
    }
}
