package com.nikitosoleil;

import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.light.LightList;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.shadow.DirectionalLightShadowFilter;
import com.jme3.system.JmeContext;
import org.junit.Assert;
import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AppTest {
    private static final double delta = 1e-7;
    private static MyApplication app = new MyApplication();
    private String[] actions = new String[]{"shoot", "ambientLight", "directionalLight", "shadowFilter"};

    public AppTest() {
        app.start(JmeContext.Type.Headless, false);
        app.parentInit();
    }

    @Test
    public void test01_initCam() {
        app.initCam();
        Assert.assertEquals(MyApplication.cam_pos, app.getCamera().getLocation());
        Vector3f dir = MyApplication.cam_look.subtract(MyApplication.cam_pos).normalize();
        Assert.assertEquals(0.f, app.getCamera().getDirection().distance(dir), delta);
        Assert.assertEquals(MyApplication.cam_speed, app.getFlyByCamera().getMoveSpeed(), delta);
    }

    @Test
    public void test03_initLights() {
        app.initLights();

        DirectionalLight dl = app.getDirectionalLight();
        Assert.assertEquals(dl.getDirection(), MyApplication.light_direction.normalize());
        Assert.assertEquals(dl.getColor(), MyApplication.directional_light_color);

        AmbientLight al = app.getAmbientLight();
        Assert.assertEquals(al.getColor(), MyApplication.ambient_light_color);

        Node root = app.getRootNode();
        LightList ll = root.getLocalLightList();
        Assert.assertEquals(2, ll.size());
    }

    @Test
    public void test04_constructDlsf() {
        DirectionalLightShadowFilter dlsf = app.constructDlsFilter();
        Assert.assertEquals(MyApplication.shadow_lambda, dlsf.getLambda(), delta);
        Assert.assertEquals(MyApplication.shadow_intensity, dlsf.getShadowIntensity(), delta);
    }

    @Test
    public void test05_initFilters() {
        app.initFilters();
        Assert.assertEquals(app.getDirectionalLight(), app.getDlsFilter().getLight());
        Assert.assertEquals(1, app.getViewPort().getProcessors().size());
    }

    @Test
    public void test06_initWalls() {
        app.initWalls();
        Assert.assertEquals(6, app.getWalls().size());
    }

    @Test
    public void test10_initMappings() {
        app.initMappings();
        for (String action : actions) {
            Assert.assertTrue(app.getInputManager().hasMapping(action));
        }
    }

    @Test
    public void test11_ambient_light_switch() {
        Assert.assertTrue(app.getAmbientLight().isEnabled());
        app.actionListener.onAction("ambientLight", false, 0.0f);
        Assert.assertFalse(app.getAmbientLight().isEnabled());
        app.actionListener.onAction("ambientLight", false, 0.0f);
        Assert.assertTrue(app.getAmbientLight().isEnabled());
        app.actionListener.onAction("ambientLight", true, 0.0f);
        Assert.assertTrue(app.getAmbientLight().isEnabled());
    }

    @Test
    public void test12_directional_light_switch() {
        Assert.assertTrue(app.getDirectionalLight().isEnabled());
        app.actionListener.onAction("directionalLight", false, 0.0f);
        Assert.assertFalse(app.getDirectionalLight().isEnabled());
        app.actionListener.onAction("directionalLight", false, 0.0f);
        Assert.assertTrue(app.getDirectionalLight().isEnabled());
        app.actionListener.onAction("directionalLight", true, 0.0f);
        Assert.assertTrue(app.getDirectionalLight().isEnabled());
    }

    @Test
    public void test13_shadow_switch() {
        Assert.assertTrue(app.getDlsFilter().isEnabled());
        app.actionListener.onAction("shadowFilter", false, 0.0f);
        Assert.assertFalse(app.getDlsFilter().isEnabled());
        app.actionListener.onAction("shadowFilter", false, 0.0f);
        Assert.assertTrue(app.getDlsFilter().isEnabled());
        app.actionListener.onAction("shadowFilter", true, 0.0f);
        Assert.assertTrue(app.getDlsFilter().isEnabled());
    }

    @Test
    public void test100_shoot() {
        Assert.assertEquals(0, app.getBalls().size());
        app.actionListener.onAction("shoot", false, 0.0f);
        Assert.assertEquals(1, app.getBalls().size());
        app.actionListener.onAction("shoot", true, 0.0f);
        Assert.assertEquals(1, app.getBalls().size());
    }
}