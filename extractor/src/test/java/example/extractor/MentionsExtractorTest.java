package example.extractor;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import example.extractor.exception.ExtractException;
import example.extractor.model.Mentions;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(RobolectricTestRunner.class)
@Config(manifest=Config.NONE)
public class MentionsExtractorTest {

    private Extractor<Mentions> mentionExtractor = new MentionExtractor();

    @Test
    public void testSingleMention() throws ExtractException {
        String singleMention = "Hey @Brijesh";
        Mentions mentions = mentionExtractor.extract(singleMention);
        assertFalse(mentions.get().isEmpty());
        assertEquals("Brijesh", mentions.getItemAt(0));
    }

    @Test
    public void testMultipleMention() throws ExtractException {
        String multipleMention = "Hey @Brijesh !! How are you? Did you meet @Kushika yesterday?";
        Mentions mentions = mentionExtractor.extract(multipleMention);
        assertFalse(mentions.get().isEmpty());
        assertEquals("Brijesh", mentions.getItemAt(0));
        assertEquals("Kushika", mentions.getItemAt(1));
    }

    @Test
    public void testMentionsWithoutSpace() throws ExtractException {
        String multipleMention = "Hey @Brijesh@Kush@ika !! How are you doing?";
        Mentions mentions = mentionExtractor.extract(multipleMention);
        assertFalse(mentions.get().isEmpty());
        assertEquals("Brijesh", mentions.getItemAt(0));
        assertEquals("Kush", mentions.getItemAt(1));
        assertEquals("ika", mentions.getItemAt(2));
    }

    @Test
    public void testMiscMentions() throws ExtractException {
        String miscMention = "Hey @BrijeshSingh1, did you get this?";
        Mentions mentions = mentionExtractor.extract(miscMention);
        assertFalse(mentions.get().isEmpty());
        assertEquals("BrijeshSingh1", mentions.getItemAt(0));

        miscMention = "Hey @Brijesh_Singh_1, did you get this?";
        mentions = mentionExtractor.extract(miscMention);
        assertFalse(mentions.get().isEmpty());
        assertEquals("Brijesh_Singh_1", mentions.getItemAt(0));
    }

}
