package nikitosoleil.validation;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class ValidatorProxyTest {

    @Test
    public void validate() {
        Assert.assertTrue(ValidatorProxy.validate("resources/example_3_dancers.xml","resources/dance.xsd"));
        Assert.assertTrue(ValidatorProxy.validate("resources/example_1_dancer.xml","resources/dance.xsd"));

        Assert.assertFalse(ValidatorProxy.validate("resources/example_0_dancers.xml","resources/dance.xsd"));
        Assert.assertFalse(ValidatorProxy.validate("resources/example_unclosed.xml","resources/dance.xsd"));
    }
}