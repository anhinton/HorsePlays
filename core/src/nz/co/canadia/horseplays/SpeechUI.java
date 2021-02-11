package nz.co.canadia.horseplays;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;

import nz.co.canadia.horseplays.script.ScriptChoice;
import nz.co.canadia.horseplays.script.ScriptLine;
import nz.co.canadia.horseplays.util.Constants;

/**
 * This class puts our horse speech on screen
 */

public class SpeechUI extends Table {
    private final NinePatchDrawable speechNinePatch01;
    private final NinePatchDrawable choiceNinePatch01;
    private final BitmapFont smallFont;
    private final BitmapFont bigFont;
    private final Theatre theatre;
    private final int buttonPad;
    private final int speechButtonWidth;
    boolean hasChoices;
    boolean buttonAdvanceOnly;

    public SpeechUI(Theatre theatre, int uiWidth, int uiHeight) {
        this.setFillParent(true);

        this.theatre = theatre;

        buttonPad = MathUtils.round((float) Constants.BUTTON_PAD / Constants.APP_HEIGHT * uiHeight);
        speechButtonWidth = MathUtils.round((float) Constants.SPEECH_BUTTON_WIDTH / Constants.APP_WIDTH * uiWidth);

        buttonAdvanceOnly = true;
        hasChoices = false;

        Texture speechTexture01 = theatre.manager.get("ui/redBubble.png");
        speechNinePatch01 = new NinePatchDrawable(
                new NinePatch(speechTexture01,20, 20, 20, 20)
        );
        Texture choiceTexture01 = theatre.manager.get("ui/greyBubble.png");
        choiceNinePatch01 = new NinePatchDrawable(
                new NinePatch(choiceTexture01,20, 20, 20, 20)
        );
        smallFont = theatre.fontLoader.getSmallFont(theatre.manager);
        bigFont = theatre.fontLoader.getBigFont(theatre.manager);
    }

    public void showTitle(String title) {
        TextButton titleButton = new TextButton(
                "",
                new TextButton.TextButtonStyle(
                        choiceNinePatch01, choiceNinePatch01,
                        choiceNinePatch01, bigFont
                )
        );
        titleButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                theatre.advance();
            }
        });
        titleButton.setText(title);
        titleButton.getLabel().setWrap(true);
        this.clearChildren();
        this.add(titleButton).pad(buttonPad)
                .width(speechButtonWidth);
    }

    public void showOutcome(boolean hasBombed) {
        TextButton outcomeButton = new TextButton(
                "",
                new TextButton.TextButtonStyle(
                        choiceNinePatch01, choiceNinePatch01,
                        choiceNinePatch01, bigFont
                )
        );
        outcomeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                theatre.advance();
            }
        });
        if (hasBombed) {
            outcomeButton.setText("YOU LOSE...");
        } else {
            outcomeButton.setText("YOU WIN!");
        }
        outcomeButton.getLabel().setWrap(true);
        this.clearChildren();
        this.center();
        this.add(outcomeButton).pad(buttonPad).width(speechButtonWidth);

    }

    public void speak(ScriptLine scriptLine) {
        buttonAdvanceOnly = false;
        hasChoices = false;
        TextButton speechButton = lineButton();
        String character = scriptLine.getCharacter();
        Array<String> characters = theatre.getCharacters();
        speechButton.setText(character + ":\n" + scriptLine.getText());
        this.clearChildren();
        if (speechButton.getText().length() > Constants.LINE_LENGTH) {
            speechButton.getLabel().setWrap(true);
            this.add(speechButton).pad(buttonPad)
                    .width(speechButtonWidth);
        } else {
            this.add(speechButton).pad(buttonPad);
        }
        this.align(getAlign(character, characters));
        theatre.setCurrentHorse(character, characters);
    }

    public void speak(Array<ScriptChoice> choices) {
        buttonAdvanceOnly = true;
        hasChoices = true;
        this.clearChildren();
        int maxChars = 0;
        String character = choices.get(0).getCharacter();
        Array<String> characters = theatre.getCharacters();
        int align = getAlign(character, characters);

        // create array of choice buttons
        Array<TextButton> buttonArray = new Array<TextButton>();
        for(int i = 0; i < choices.size; i++) {
            TextButton choiceButton = choiceButton(choices.get(i), i + 1);
            buttonArray.add(choiceButton);
            maxChars = Math.max(maxChars, choiceButton.getText().length());
        }
        // add character name to top of choices
        this.add(authorLabel(character)).align(Constants.BUTTON_ALIGN)
                .pad(0, 0, buttonPad, buttonPad);
        this.row();

        // add choice buttons to table
        for (TextButton button : buttonArray) {
            if (maxChars > Constants.LINE_LENGTH) {
                button.getLabel().setWrap(true);
                this.add(button)
                        .pad(0, 0, buttonPad,
                                buttonPad)
                        .align(Constants.BUTTON_ALIGN)
                        .width(speechButtonWidth);
            } else {
                this.add(button)
                        .pad(0, 0, buttonPad,
                                buttonPad)
                        .align(Constants.BUTTON_ALIGN);
            }
            this.row();
        }
        this.align(align);
        theatre.setCurrentHorse(character, characters);
    }

    private Label authorLabel(String character) {
        return new Label(
                character + ":",
                new Label.LabelStyle(
                        smallFont, Color.WHITE
                ));
    }

    // return TextButton for a regular line
    private TextButton lineButton() {
        TextButton speechButton = new TextButton(
                "",
                new TextButton.TextButtonStyle(
                        speechNinePatch01, speechNinePatch01,
                        speechNinePatch01, smallFont
                )
        );
        speechButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                theatre.advance();
            }
        });
        speechButton.getLabel().setAlignment(Constants.BUTTON_ALIGN);
        return speechButton;
    }

    // return a choice-type TextButton
    private TextButton choiceButton(final ScriptChoice choice, int position) {
        final TextButton choiceButton = new TextButton(
                "",
                new TextButton.TextButtonStyle(
                        choiceNinePatch01, speechNinePatch01,
                        choiceNinePatch01, smallFont
                )
        );
        choiceButton.setText(position + ". " + choice.getText());
        choiceButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (choice.getDivert().equals(Constants.END_KNOT)) {
                        end();
                    } else {
                        theatre.addBomb(choice.getBomb());
                        theatre.setCurrentKnot(choice.getDivert());
                        theatre.advance();
                    }
                }
            });
        choiceButton.getLabel().setAlignment(Constants.BUTTON_ALIGN);
        return choiceButton;
    }

    // when the PlayScript is over
    void end() {
        clearChildren();
    }

    // return character alignment
    private int getAlign(String character, Array<String> characters) {
        int align = 0;
        if (character.equals(characters.get(0))) {
            align = Align.bottomRight;
        } else if (character.equals(characters.get(1))) {
            align = Align.bottomLeft;
        }
        return align;
    }

    public void dispose() {
    }
}
