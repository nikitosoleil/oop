package com.nikitosoleil;

import com.jme3.light.Light;
import com.jme3.post.Filter;

public class Switch {
    static void light(Light l) {
        if (l.isEnabled())
            l.setEnabled(false);
        else
            l.setEnabled(true);
    }

    static void filter(Filter f) {
        if (f.isEnabled())
            f.setEnabled(false);
        else
            f.setEnabled(true);
    }
}
