package uz.ollobergan.appsubscriber;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import uz.ollobergan.appsubscriber.dto.RawMessageDto;
import uz.ollobergan.appsubscriber.helper.CompressHelper;
import static org.assertj.core.api.Assertions.assertThat;

public class CompressHelperTest   {

    @Test
    void testDecode() throws JsonProcessingException {
        String msg = "{\"messageType\":\"report\",\"object\":\"H4sIAAAAAAAA/6tWykvMTVWyUvJKTM5WqgUAHayzxg8AAAA=\"}";
        RawMessageDto rawMessageDto = CompressHelper.DecodeToDto(msg, RawMessageDto.class);
        assertThat(rawMessageDto).isNotNull();
    }

    @Test
    void testDecompress() throws Exception {
        String s1 = "Hello World";
        byte[] objStr = CompressHelper.Compress(s1);
        String s2 = CompressHelper.Decompress(objStr);
        assertThat(s1.equals(s2)).isTrue();
    }
}
