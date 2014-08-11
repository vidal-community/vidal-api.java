package com.vidal.rest.sdk;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import retrofit.RestAdapter;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ResourceFactoryTest {

    @Mock
    RestAdapter restAdapter;

    @InjectMocks
    ResourceFactory factory;

    @Test
    public void delegates_to_retrofit() {
        factory.fetching(String.class);

        verify(restAdapter).create(String.class);
    }

}