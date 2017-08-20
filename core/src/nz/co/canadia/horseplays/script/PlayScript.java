package nz.co.canadia.horseplays.script;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.OrderedMap;
import com.badlogic.gdx.utils.XmlReader;

import java.io.IOException;

/**
 * The play script contains all the horse actor dialog and controls what happens on the Stage
 */

public class PlayScript {
    private OrderedMap<String, ScriptKnot> scriptKnots;
    ScriptKnot currentKnot;

    public PlayScript() {

        scriptKnots = new OrderedMap<String, ScriptKnot>();

        XmlReader xmlReader = new XmlReader();
        XmlReader.Element rootElement;
        Array<XmlReader.Element> knotElements;

        try {
            // get root element
            rootElement = xmlReader.parse(Gdx.files.internal("scripts/script.xml"));

            // get knot elements
            knotElements = rootElement.getChildrenByName("knot");

            for (XmlReader.Element knot : knotElements) {

                // get knot id
                String id = knot.getAttribute("id");
                // get knot divert
                String divert = knot.getAttribute("divert", "DONE");

                // get line elements and create array of ScriptLines
                Array<XmlReader.Element> lineElements = knot.getChildrenByName("line");
                Array<ScriptLine> scriptLines = new Array<ScriptLine>();
                for (XmlReader.Element line : lineElements) {
                    scriptLines.add(new ScriptLine(line.getAttribute("actor"), line.getText()));
                }

                // get scriptChoices and create array of ScriptChoices
                Array<ScriptChoice> scriptChoices = new Array<ScriptChoice>();
                XmlReader.Element choicesElement = knot.getChildByName("choices");
                if (choicesElement != null) {
                    String choiceActor = choicesElement.getAttribute("actor");

                    Array<XmlReader.Element> choiceElements = choicesElement.getChildrenByName("choice");
                    for (XmlReader.Element choice : choiceElements) {
                        scriptChoices.add(new ScriptChoice(choiceActor, choice.getText(),
                                choice.getAttribute("divert", divert)));
                    }
                }

                scriptKnots.put(id, new ScriptKnot(scriptLines, scriptChoices, id, divert));

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
