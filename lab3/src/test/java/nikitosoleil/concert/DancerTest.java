package nikitosoleil.concert;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class DancerTest {

    @Test
    public void testToString() {
        Dancer dancer = new Dancer("Igor", 22, 2);
        Assert.assertEquals(" -Name: Igor\n -Age: 22\n -Experience: 2\n",
                dancer.toString());
    }
}