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
    private Theatre theatre;

    public SpeechUI(Table table, PlayScript playScript, Theatre theatre) {
        this.playScript = playScript;
        this.theatre = theatre;
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
    }

    // display the appropriate dialogue line or choices on the screen
    public void speak () {

        // check if bombThreshold has been exceeded
        playScript.checkBombCount();

        if (playScript.hasLine()) {
            // speak a line if it's available
            ScriptLine scriptLine = playScript.getCurrentLine();
            TextButton speechButton = lineButton(playScript);
            String actor = scriptLine.getActor();
            speechButton.setText(scriptLine.getText());
            table.clearChildren();
            if (speechButton.getText().length() > Constants.LINE_LENGTH) {
                speechButton.getLabel().setWrap(true);
                table.add(speechButton).pad(Constants.BUTTON_PAD)
                        .width(Constants.APP_WIDTH * 2 / 3);
            } else {
                table.add(speechButton).pad(Constants.BUTTON_PAD);
            }
            table.align(getAlign(actor));
            theatre.setCurrentHorse(actor);
        } else if (playScript.hasChoice()) {
            // display choices if we have them
            Array<ScriptChoice> choices = playScript.getCurrentChoices();
            table.clearChildren();
            int maxChars = 0;
            String actor = choices.get(0).getActor();
            int align = getAlign(actor);
            Array<TextButton> buttonArray = new Array<TextButton>();
            for (ScriptChoice choice : choices) {
                TextButton choiceButton = choiceButton(choice, playScript);
                buttonArray.add(choiceButton);
                maxChars = Math.max(maxChars, choiceButton.getText().length());
            }
            for (TextButton button : buttonArray) {
                if (maxChars > Constants.LINE_LENGTH) {
                    table.add(button).pad(Constants.BUTTON_PAD).align(align)
                            .width(Constants.APP_WIDTH * 2 / 3);
                } else {
                    table.add(button).pad(Constants.BUTTON_PAD).align(align);
                }
                table.row();
            }
            table.align(align);
            theatre.setCurrentHorse(actor);
        } else if (playScript.hasKnot()) {
            // go to next knot and start speaking
            playScript.nextKnot();
            this.speak();
        } else {
            // it must be all over
            end();
        }

    }

    // return TextButton for a regular line
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
                playScript.nextLine();
                speechUI.speak();
            }
        });
        return speechButton;
    }

    // return a choice-type TextButton
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
                    speechUI.speak();
                }
            }
        });
        return button;
    }

    // when the PlayScript is over
    private void end() {
        speechButton.setText("IT'S OVER");
        speechButton.clearListeners();
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
        table.clearChildren();
        table.align(Align.center);
        table.add(speechButton).pad(Constants.BUTTON_PAD);
    }

    // return actor alignment
    private int getAlign(String actor) {
        int align = 0;
        if (actor.equals("1")) {
            align = Align.bottomRight;
        } else if (actor.equals("2")) {
            align = Align.bottomLeft;
        }
        return align;
    }
}
