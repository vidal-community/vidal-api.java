/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 VIDAL France
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.vidal.rest.sdk.converters;

import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.assertj.core.api.Assertions.assertThat;

public class AtomFeedReaderTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    private final AtomFeedReader reader = new AtomFeedReader();

    @Test
    public void fails_on_malformed_feed() throws FeedException {
        exception.expect(FeedException.class);

        reader.buildFeed("not a feed");
    }

    @Test
    public void serializes_feed() throws FeedException {
        String contents = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<feed xmlns=\"http://www.w3.org/2005/Atom\">\n" +
                " \n" +
                " <title>Fil d'exemple</title>\n" +
                " <subtitle>Un titre secondaire.</subtitle>\n" +
                " <link href=\"http://example.org/\"/>\n" +
                " <updated>2010-05-13T18:30:02Z</updated>\n" +
                " <id>urn:uuid:60a76c80-d399-11d9-b91C-0003939e0af6</id>\n" +
                " \n" +
                " <entry>\n" +
                "   <title>Des robots propulsés par Atom deviennent fous</title>\n" +
                "   <link href=\"http://example.org/2003/12/13/atom03\"/>\n" +
                "   <id>urn:uuid:1225c695-cfb8-4ebb-aaaa-80da344efa6a</id>\n" +
                "   <updated>2010-04-01T18:30:02Z</updated>\n" +
                "   <summary>Poisson d'avril !</summary>\n" +
                " </entry>\n" +
                " \n" +
                "</feed>";

        SyndFeed feed = reader.buildFeed(contents);

        assertThat(feed.getTitle()).isEqualTo("Fil d'exemple");
        assertThat(feed.getEntries()).hasSize(1);
    }

}