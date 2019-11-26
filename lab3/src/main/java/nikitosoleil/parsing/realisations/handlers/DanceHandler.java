package nikitosoleil.parsing.realisations.handlers;

import nikitosoleil.concert.*;
import nikitosoleil.parsing.interfaces.Handler;

import java.util.ArrayList;
import java.util.List;

public class DanceHandler implements Handler<Dance> {
    protected Dance dance;
    protected List<Dancer> dancerList;
    protected Dancer currentDancer;

    FieldNames currentState;

    public DanceHandler() {
        dance = new Dance();
        dancerList = new ArrayList<>();
        dance.setDancers(dancerList);

        currentState = FieldNames.None;
    }

    @Override
    public void onTagStart(String tag) {
        switch (tag) {
            case "name":
                currentState = FieldNames.Name;
                break;
            case "type":
                currentState = FieldNames.Type;
                break;
            case "scene":
                currentState = FieldNames.Scene;
                break;
            case "number-of-dancers":
                currentState = FieldNames.NumberOfDancers;
                break;
            case "music":
                currentState = FieldNames.Music;
                break;
            case "dancers-root":
                break;
            case "dancer":
                currentDancer = new Dancer();
                break;
            case "age":
                currentState = FieldNames.Age;
                break;
            case "experience":
                currentState = FieldNames.Experience;
                break;
            case "number":
                currentState = FieldNames.Number;
                break;
            default:
                currentState = FieldNames.None;
        }
    }

    @Override
    public void onTagEnd(String tag) {
        if (tag.equals("dancer")) {
            dancerList.add(currentDancer);
        }
    }

    @Override
    public void setAttribute(String attributeName, String value) {
        if (value == null) value = "";

        if (attributeName.equals("id")) dance.setId(value);
    }

    @Override
    public void setTag(String information) {
        switch (currentState) {
            case Type:
                dance.setType(Type.valueOf(information));
                break;
            case Scene:
                dance.setScene(Scene.valueOf(information));
                break;
            case NumberOfDancers:
                dance.setNumberOfDancers(NumberOfDancers.valueOf(information));
                break;
            case Music:
                dance.setMusic(Music.valueOf(information));
                break;
            case Name:
                currentDancer.setName(information);
                break;
            case Age:
                currentDancer.setAge(Integer.valueOf(information));
                break;
            case Experience:
                currentDancer.setExperience(Integer.valueOf(information));
                break;
            case Number:
                dance.setNumber(Integer.valueOf(information));
                break;
            default:
                break;
        }

        currentState = FieldNames.None;
    }

    @Override
    public Dance getParseResult() {
        return dance;
    }
}