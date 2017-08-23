package nz.co.canadia.horseplays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Align;

import nz.co.canadia.horseplays.util.Constants;

/**
 * This class puts our horse speech on screen
 */

public class SpeechUI {
    private Table table;
    private NinePatchDrawable speechNinePatch01;
    private BitmapFont speechFont;
    private TextButton speechButton;

    public SpeechUI(Table table) {
        this.table = table;
        speechNinePatch01 = new NinePatchDrawable(
                new NinePatch(
                        new Texture(Gdx.files.internal("ui/speechBubble02.png")),
                        20, 20, 20, 20
                )
        );
        speechFont = new BitmapFont(Gdx.files.internal("fonts/TlwgMonoBold24.fnt"));
        speechButton = new TextButton(
                "spicy cool Jeff",
                new TextButton.TextButtonStyle(
                        speechNinePatch01, speechNinePatch01, speechNinePatch01, speechFont
                )
        );
        table.add(speechButton).width(Constants.APP_WIDTH / 2);
        table.pad(10);
        table.align(Align.left + Align.bottom);
    }

    public void speak (String s) {
        speechButton.setText(s);
    }
}
