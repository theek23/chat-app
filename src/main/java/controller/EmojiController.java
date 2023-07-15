package controller;

import javafx.scene.input.MouseEvent;

import java.nio.charset.Charset;

public class EmojiController {
    public void clickOnSend(MouseEvent mouseEvent) {
        byte[] emojiByteCode = new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x98, (byte) 0x81};
        String emoji = new String(emojiByteCode, Charset.forName("UTF-8"));
       emoji = ("\uD83D\uDE00");
    }
}
