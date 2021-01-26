package nz.co.canadia.horseplays.script;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.OrderedMap;
import com.badlogic.gdx.utils.SerializationException;
import com.badlogic.gdx.utils.XmlReader;
import nz.co.canadia.horseplays.util.Constants;

/**
 * The play script contains all the horse character dialog and controls what
 * happens on the Stage
 */

public class PlayScript {
    private FileHandle playScriptXml;
    private String title;
    private OrderedMap<String, ScriptKnot> scriptKnots;
    private ScriptKnot currentKnot;
    public int bombThreshold;
    private Array<String> characters;

    public PlayScript(FileHandle playScriptXml) {
        characters = new Array<String>();

        scriptKnots = new OrderedMap<String, ScriptKnot>();

        // Set default bomb threshold for the case when no bomb knot exists
        bombThreshold = -1;

        XmlReader xmlReader = new XmlReader();
        XmlReader.Element rootElement;
        Array<XmlReader.Element> characterElements;
        Array<XmlReader.Element> knotElements;

        // get root element
        try {
            this.playScriptXml = playScriptXml;
            rootElement = xmlReader.parse(playScriptXml);

            //get title
            title = rootElement.getAttribute("title");

            // get character names
            XmlReader.Element charactersElement = rootElement.getChildByName("characters");
            characterElements = charactersElement.getChildrenByName("character");
            for (XmlReader.Element character : characterElements) {
                characters.add(character.getAttribute("name"));
                String player = character.getAttribute("player", null);
            }

            // get knot elements
            knotElements = rootElement.getChildrenByName("knot");

            for (XmlReader.Element knot : knotElements) {

                // get knot id
                String id = knot.getAttribute("id");
                // get bombThreshold from "bomb" knot
                if (id.equals("bomb")) {
                    bombThreshold = knot.getIntAttribute("threshold");
                }
                // get knot divert
                String divert = knot.getAttribute("divert", Constants.END_KNOT);

                // get line elements and create array of ScriptLines
                Array<XmlReader.Element> lineElements = knot.getChildrenByName("line");
                Array<ScriptLine> scriptLines = new Array<ScriptLine>();
                for (XmlReader.Element line : lineElements) {
                    scriptLines.add(new ScriptLine(line.getAttribute("character"), line.getText()));
                }

                // get scriptChoices and create array of ScriptChoices
                Array<ScriptChoice> scriptChoices = new Array<ScriptChoice>();
                XmlReader.Element choicesElement = knot.getChildByName("choices");
                if (choicesElement != null) {
                    String choiceCharacter = choicesElement.getAttribute("character");

                    Array<XmlReader.Element> choiceElements = choicesElement.getChildrenByName("choice");
                    for (XmlReader.Element choice : choiceElements) {
                        scriptChoices.add(new ScriptChoice(choiceCharacter, choice.getText(),
                                choice.getAttribute("divert", divert),
                                choice.getIntAttribute("bomb", 0)));
                    }
                }

                // create scriptKnot and add to array
                scriptKnots.put(id, new ScriptKnot(scriptLines, scriptChoices, id, divert));

            }
        } catch (SerializationException e) {
            e.getMessage();
            Gdx.app.exit();
        }

        currentKnot = scriptKnots.get("start");

    }

    public void setCurrentKnot(String knot) {
        this.currentKnot = scriptKnots.get(knot);
    }

    public ScriptLine getCurrentLine() {
        return currentKnot.getCurrentScriptLine();
    }

    public void nextLine() {
        currentKnot.nextLine();
    }

    public boolean hasLine() {
        return currentKnot.hasLine();
    }

    public boolean hasChoice() {
        return currentKnot.hasChoice();
    }

    public Array<ScriptChoice> getCurrentChoices() {
        return currentKnot.getChoices();
    }

    public void nextKnot() {
        currentKnot = scriptKnots.get(currentKnot.getDivert());
    }

    public boolean hasKnot() {
        return !currentKnot.getDivert().equals(Constants.END_KNOT);
    }

    public Array<String> getCharacters() {
        return characters;
    }

    public String getCurrentKnotId() {
        return currentKnot.getId();
    }

    public FileHandle getPlayScriptXml() {
        return playScriptXml;
    }

    public String getTitle() {
        return title;
    }
}
