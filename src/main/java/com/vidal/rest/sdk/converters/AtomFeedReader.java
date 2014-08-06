package com.vidal.rest.sdk.converters;

import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;

import java.io.StringReader;

public class AtomFeedReader {

    public SyndFeed buildFeed(String contents) throws FeedException {
        return new SyndFeedInput().build(new StringReader(contents));
    }
}
