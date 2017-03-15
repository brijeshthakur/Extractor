package example.extractor;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import example.extractor.model.Emoticons;
import example.extractor.exception.ExtractException;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
@Config(manifest=Config.NONE)
public class EmoticonsExtractorTest {

    private Extractor emoticonsExtractor = new EmoticonsExtractor();

    @Test
    public void testSingleEmoticon() throws ExtractException {
        String singleEmoticons = "I'm done (smile)";
        Emoticons emoticons = (Emoticons) emoticonsExtractor.extract(singleEmoticons);
        assertNotNull(emoticons);
        assertEquals("smile", emoticons.getItemAt(0));
    }

    @Test
    public void testMultipleEmoticons() throws ExtractException {
        String singleEmoticons = "I'm done (smile) (wink) (laugh)";
        Emoticons emoticons = (Emoticons) emoticonsExtractor.extract(singleEmoticons);
        assertNotNull(emoticons);
        assertFalse(emoticons.get().isEmpty());
        assertEquals("smile", emoticons.getItemAt(0));
        assertEquals("wink", emoticons.getItemAt(1));
        assertEquals("laugh", emoticons.getItemAt(2));
    }

    @Test
    public void testInvalidEmoticons() throws ExtractException {
        String singleEmoticons = "I'm done (smile)(wink)(laugh)(success";
        Emoticons emoticons = (Emoticons) emoticonsExtractor.extract(singleEmoticons);
        assertNotNull(emoticons);
        assertTrue(emoticons.get().size() == 3);
        assertEquals("smile", emoticons.getItemAt(0));
        assertEquals("wink", emoticons.getItemAt(1));
        assertEquals("laugh", emoticons.getItemAt(2));
    }

    @Test
    public void testEmoticonsLength() throws ExtractException {
        String lengthyEmoticons = "(smile) (smilesmilesmilesmilesmilesmile)";
        Emoticons emoticons = (Emoticons) emoticonsExtractor.extract(lengthyEmoticons);
        assertNotNull(emoticons);
        assertTrue(emoticons.get().size() == 1);
        assertEquals("smile", emoticons.getItemAt(0));
    }
}
