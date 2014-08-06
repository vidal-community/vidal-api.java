package com.vidal.rest.sdk.converters;

import com.sun.syndication.io.FeedException;
import com.vidal.rest.sdk.entities.Product;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import retrofit.converter.ConversionException;

import static com.vidal.rest.sdk.entities.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.isA;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductDeserializerTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Mock
    AtomFeedReader reader;

    @InjectMocks
    ProductDeserializer deserializer;

    @Test
    public void deserializes_product_from_feed() throws ConversionException, FeedException {
        when(reader.buildFeed(anyString())).thenCallRealMethod();
        String feed = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<feed xmlns=\"http://www.w3.org/2005/Atom\">" +
                "   <entry>" +
                "       <id>vidal://product/42</id>" +
                "       <title>Batman</title>" +
                "   </entry>" +
                "</feed>";

        Product product = deserializer.deserializeOne(feed);

        assertThat(product)
                .hasId("vidal://product/42")
                .hasName("Batman");
    }

    @Test
    public void feed_errors_are_wrapped_as_conversion_exceptions() throws FeedException, ConversionException {
        when(reader.buildFeed(anyString())).thenThrow(FeedException.class);
        exception.expect(ConversionException.class);
        exception.expectCause(isA(FeedException.class));

        deserializer.deserializeOne("");
    }

    @Test
    public void does_not_deserialize_collection_of_products_yet() throws ConversionException {
        exception.expect(UnsupportedOperationException.class);

        deserializer.deserializeAll("");
    }

}