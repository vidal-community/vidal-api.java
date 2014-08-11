package com.vidal.rest.sdk.entities;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductTest {

    @Test
    public void finds_product_by_id_and_name() {
        Collection<Product> products = new ArrayList<>();
        products.add(new Product("id", "name"));

        assertThat(products).contains(new Product("id", "name"));
        assertThat(products).doesNotContain((Product) null);
        assertThat(products).doesNotContain(new Product("id1", "name"));
        assertThat(products).doesNotContain(new Product("id", "name") {}); //anonymous subclass ;)
    }

}