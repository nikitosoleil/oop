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
    private static MyApplication app;
    private static Enlighter enlighter;
    private String[] actions = new String[]{"shoot", "ambientLight", "directionalLight", "shadowFilter"};

    @Test
    public void test00_initApp() {
        Thread appThread = new Thread(() -> {
            app = new MyApplication();
            app.setShowSettings(false);
            app.start(JmeContext.Type.Headless, true);
        });
        appThread.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignore) {
        }
        enlighter = app.getEnlighter();
    }

    @Test
    public void test01_initCam() {
        Assert.assertEquals(MyApplication.cam_pos, app.getCamera().getLocation());
        Vector3f dir = MyApplication.cam_look.subtract(MyApplication.cam_pos).normalize();
        Assert.assertEquals(0.f, app.getCamera().getDirection().distance(dir), delta);
        Assert.assertEquals(MyApplication.cam_speed, app.getFlyByCamera().getMoveSpeed(), delta);
    }

    @Test
    public void test03_initLights() {
        DirectionalLight dl = enlighter.getDirectionalLight();
        Assert.assertEquals(dl.getDirection(), Enlighter.light_direction.normalize());
        Assert.assertEquals(dl.getColor(), Enlighter.directional_light_color);

        AmbientLight al = enlighter.getAmbientLight();
        Assert.assertEquals(al.getColor(), Enlighter.ambient_light_color);

        Node root = app.getRootNode();
        LightList ll = root.getLocalLightList();
        Assert.assertEquals(2, ll.size());
    }

    @Test
    public void test04_constructDlsf() {
        DirectionalLightShadowFilter dlsf = Enlighter.buildDlsFilter(app.getAssetManager());
        Assert.assertEquals(Enlighter.shadow_lambda, dlsf.getLambda(), delta);
        Assert.assertEquals(Enlighter.shadow_intensity, dlsf.getShadowIntensity(), delta);
    }

    @Test
    public void test05_initFilters() {
        Assert.assertEquals(enlighter.getDirectionalLight(), enlighter.getDlsFilter().getLight());
        Assert.assertEquals(1, app.getViewPort().getProcessors().size());
    }

    @Test
    public void test09_initWalls() {
        Assert.assertEquals(6, app.getWalls().size());
    }

    @Test
    public void test10_initMappings() {
        for (String action : actions) {
            Assert.assertTrue(app.getInputManager().hasMapping(action));
        }
    }

    @Test
    public void test11_ambient_light_switch() {
        Assert.assertTrue(enlighter.getAmbientLight().isEnabled());
        app.getActionListener().onAction("ambientLight", false, 0.0f);
        Assert.assertFalse(enlighter.getAmbientLight().isEnabled());
        app.getActionListener().onAction("ambientLight", false, 0.0f);
        Assert.assertTrue(enlighter.getAmbientLight().isEnabled());
        app.getActionListener().onAction("ambientLight", true, 0.0f);
        Assert.assertTrue(enlighter.getAmbientLight().isEnabled());
    }

    @Test
    public void test12_directional_light_switch() {
        Assert.assertTrue(enlighter.getDirectionalLight().isEnabled());
        app.getActionListener().onAction("directionalLight", false, 0.0f);
        Assert.assertFalse(enlighter.getDirectionalLight().isEnabled());
        app.getActionListener().onAction("directionalLight", false, 0.0f);
        Assert.assertTrue(enlighter.getDirectionalLight().isEnabled());
        app.getActionListener().onAction("directionalLight", true, 0.0f);
        Assert.assertTrue(enlighter.getDirectionalLight().isEnabled());
    }

    @Test
    public void test13_shadow_switch() {
        Assert.assertTrue(enlighter.getDlsFilter().isEnabled());
        app.getActionListener().onAction("shadowFilter", false, 0.0f);
        Assert.assertFalse(enlighter.getDlsFilter().isEnabled());
        app.getActionListener().onAction("shadowFilter", false, 0.0f);
        Assert.assertTrue(enlighter.getDlsFilter().isEnabled());
        app.getActionListener().onAction("shadowFilter", true, 0.0f);
        Assert.assertTrue(enlighter.getDlsFilter().isEnabled());
    }

    @Test
    public void test14_shoot() {
        Assert.assertEquals(0, app.getBalls().size());
        app.getActionListener().onAction("shoot", false, 0.0f);
        Assert.assertEquals(1, app.getBalls().size());
        app.getActionListener().onAction("shoot", true, 0.0f);
        Assert.assertEquals(1, app.getBalls().size());
    }

    @Test
    public void test15_demo() {
        app.demo(100.0f);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignore) {
        }
        Assert.assertEquals(1, app.getCollisionListener().getCount());
    }
}