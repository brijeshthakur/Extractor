package extractor;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import extractor.Extractor;
import extractor.LinkExtractor;
import extractor.exception.ExtractException;
import extractor.model.Links;


import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
@Config(manifest=Config.NONE)
public class LinkExtractorTest {

    private Extractor<Links> linkExtractor = new LinkExtractor();

    @Test
    public void testSingleLink() throws ExtractException {
        String singleLink = "Click here https://www.google.com";
        Links links = linkExtractor.extract(singleLink);
        assertTrue(links.get() != null);
        assertEquals("https://www.google.com", links.getItemAt(0).getUrl());
    }

    @Test
    public void testMultipleLinks() throws ExtractException {
        String multipleLink = "Click here https://www.google.com or http://google.com";
        Links links = linkExtractor.extract(multipleLink);
        assertFalse(links.get().isEmpty());
        assertEquals("https://www.google.com", links.getItemAt(0).getUrl());
        assertEquals("http://google.com", links.getItemAt(1).getUrl());
    }

    @Test
    public void testDifferentLinks() throws ExtractException {
        String multipleLinks = "Click here https://www.google.com or http://google.com";
        Links links = linkExtractor.extract(multipleLinks);
        assertFalse(links.get().isEmpty());
        assertEquals("https://www.google.com", links.getItemAt(0).getUrl());
        assertEquals("http://google.com", links.getItemAt(1).getUrl());
    }

    @Test
    public void testComplexLinks() throws ExtractException {
        String complexLink = "https://www.google.co.in/?gws_rd=ssl#q=android+%2B+slp+download+_+_win" +
                "_mac-chips%26hardware%27andsoftware";
        Links links = linkExtractor.extract(complexLink);
        assertFalse(links.get().isEmpty());
        assertEquals(complexLink, links.getItemAt(0).getUrl());
    }

    @Test
    public void testRandomLinks() throws ExtractException {
        String randomLink = "output.setVisibility(View.GONE);";
        Links links = linkExtractor.extract(randomLink);
        assertTrue(links.get().isEmpty());
    }
}
