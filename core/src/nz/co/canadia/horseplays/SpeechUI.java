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

    public SpeechUI(Table table, PlayScript playScript) {
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
        speechButton = lineButton();
        table.add(speechButton).width(Constants.APP_WIDTH / 2);
        table.pad(10);
        table.align(Align.left + Align.bottom);
    }

    public void speak (ScriptLine scriptLine) {
        speechButton.setText(scriptLine.getText());
        table.clearChildren();
        table.add(speechButton).width(Constants.APP_WIDTH / 2);
    }

    public void speak(Array<ScriptChoice> choices, PlayScript playScript) {
        table.clearChildren();
        for (ScriptChoice choice : choices) {
            TextButton button = choiceButton(choice, playScript);
            table.add(button).width(Constants.APP_WIDTH / 2);
        }
    }

    private TextButton lineButton() {
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
                super.touchUp(event, x, y, pointer, button);
            }
        });
        return speechButton;
    }

    private TextButton choiceButton(final ScriptChoice choice, final PlayScript playScript) {
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
                    playScript.setCurrentKnot(choice.getDivert());
                    playScript.addBomb(choice.getBomb());
                    super.touchUp(event, x, y, pointer, button);
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
