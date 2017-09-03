package nz.co.canadia.horseplays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;

import nz.co.canadia.horseplays.script.PlayScript;
import nz.co.canadia.horseplays.script.ScriptChoice;
import nz.co.canadia.horseplays.script.ScriptLine;
import nz.co.canadia.horseplays.util.Constants;

/**
 * This class puts our horse speech on screen
 */

public class SpeechUI {
    private Table table;
    private NinePatchDrawable speechNinePatch01;
    private NinePatchDrawable choiceNinePatch01;
    private BitmapFont speechFont;
    private TextButton speechButton;
    private PlayScript playScript;

    public SpeechUI(Table table, PlayScript playScript) {
        this.playScript = playScript;
        speechNinePatch01 = new NinePatchDrawable(
                new NinePatch(
                        new Texture(Gdx.files.internal("ui/speechBubble02.png")),
                        20, 20, 20, 20
                )
        );
        choiceNinePatch01 = new NinePatchDrawable(
                new NinePatch(
                        new Texture(Gdx.files.internal("ui/choiceBubble01.png")),
                        20, 20, 20, 20
                )
        );
        speechFont = new BitmapFont(Gdx.files.internal("fonts/TlwgMonoBold24.fnt"));
        this.table = table;
        table.pad(10);
        table.align(Align.left + Align.bottom);
    }

    public void speak () {
        if (playScript.hasLine()) {
            ScriptLine scriptLine = playScript.getCurrentLine();
            TextButton speechButton = lineButton(playScript);
            speechButton.setText(scriptLine.getText());
            table.clearChildren();
            table.add(speechButton).width(Constants.APP_WIDTH / 2);
        } else if (playScript.hasChoice()) {
            Array<ScriptChoice> choices = playScript.getCurrentChoices();
            table.clearChildren();
            for (ScriptChoice choice : choices) {
                TextButton button = choiceButton(choice, playScript);
                table.add(button).width(Constants.APP_WIDTH / 2);
            }
        }
    }

    private TextButton lineButton(final PlayScript playScript) {
        final SpeechUI speechUI = this;
        speechButton = new TextButton(
                "",
                new TextButton.TextButtonStyle(
                        speechNinePatch01, speechNinePatch01, speechNinePatch01, speechFont
                )
        );
        speechButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                playScript.next(speechUI);
            }
        });
        return speechButton;
    }

    private TextButton choiceButton(final ScriptChoice choice, final PlayScript playScript) {
        final SpeechUI speechUI = this;
        TextButton button = new TextButton(
                "",
                new TextButton.TextButtonStyle(
                        choiceNinePatch01, choiceNinePatch01, choiceNinePatch01, speechFont
                )
        );
        button.setText(choice.getText());
        button.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (choice.getDivert().equals(Constants.END_KNOT)) {
                    end();
                } else {
                    playScript.addBomb(choice.getBomb());
                    playScript.setCurrentKnot(choice.getDivert());
                    playScript.next(speechUI);
                }
            }
        });
        return button;
    }

    public void end() {
        speechButton.setText("IT'S OVER");
        table.clearChildren();
        table.add(speechButton).width(Constants.APP_WIDTH / 2);
    }
}
