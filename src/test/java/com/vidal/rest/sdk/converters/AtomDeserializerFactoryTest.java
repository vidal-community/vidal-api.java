package com.vidal.rest.sdk.converters;

import com.vidal.rest.sdk.entities.Product;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class AtomDeserializerFactoryTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Spy
    AtomFeedReader reader;

    @InjectMocks
    AtomDeserializerFactory factory;

    @Test
    public void returns_product_converter() {
        assertThat(factory.find(Product.class)).isInstanceOf(ProductDeserializer.class);
    }

    @Test
    public void fails_on_unsupported_type() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("java.lang.Object not supported");

        factory.find(Object.class);
    }
}