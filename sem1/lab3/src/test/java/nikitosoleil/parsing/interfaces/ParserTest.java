package nikitosoleil.parsing.interfaces;

import nikitosoleil.concert.Dance;
import nikitosoleil.parsing.implementations.handlers.DanceHandler;
import nikitosoleil.parsing.implementations.parsers.ParserDOM;
import nikitosoleil.parsing.implementations.parsers.parserSAX;
import nikitosoleil.parsing.implementations.parsers.parserStAX;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class ParserTest {
    private ParserDOM<Dance> parserDOM;
    private parserStAX<Dance> parserStAX;
    private parserSAX<Dance> parserSax;

    private String xsdPath = "resources/dance.xsd";
    private List<String> correctXMLs = List.of(
            "resources/example_3_dancers.xml",
            "resources/example_2_dancers.xml",
            "resources/example_1_dancer.xml");
    private List<String> incorrectXMLs = List.of(
            "resources/example_unclosed.xml",
            "resources/example_0_dancers.xml");

    private Pair<String> correctRepresentation = new Pair<>(
            "resources/example_3_dancers.xml",
            "Id: 000\n" +
                    "Type: folk\n" +
                    "Scene: street\n" +
                    "Number Of Dancers: group\n" +
                    "Music: live_music\n" +
                    "Dancers: \n" +
                    " ________\n" +
                    " -Name: Igor\n" +
                    " -Age: 8\n" +
                    " -Experience: 1\n" +
                    " ________\n" +
                    " -Name: Nikita\n" +
                    " -Age: 19\n" +
                    " -Experience: 3\n" +
                    " ________\n" +
                    " -Name: Valera\n" +
                    " -Age: 55\n" +
                    " -Experience: 1\n" +
                    "Number: 1\n");


    @Before
    public void setParserDOM() {
        parserDOM = new ParserDOM<>(new DanceHandler());
    }

    @Before
    public void setParserStAX() {
        parserStAX = new parserStAX<>(new DanceHandler());
    }

    @Before
    public void setParserSax() {
        parserSax = new parserSAX<>(new DanceHandler());
    }

    private void runTestOnCorrect(String xmlPath, String xsdPath, Parser<Dance> parser) {
        Assert.assertNotNull(parser.parse(xmlPath, xsdPath));
    }

    private void runCorrectXMLs(Parser<Dance> parser) {
        for (String xmlPath : correctXMLs) {
            runTestOnCorrect(xmlPath, xsdPath, parser);
        }
    }

    @Test
    public void testParserNotNullDOM() {
        runCorrectXMLs(parserDOM);
    }


    @Test
    public void testParserNotNullSAX() {
        runCorrectXMLs(parserSax);
    }

    @Test
    public void testParserNotNullStAX() {
        runCorrectXMLs(parserStAX);
    }

    private void runTestOnIncorrect(String xmlPath, String xsdPath, Parser<Dance> parser) {
        Assert.assertNull(parser.parse(xmlPath, xsdPath));
    }

    private void runIncorrectXMLs(Parser<Dance> parser) {
        for (String xmlPath : incorrectXMLs) {
            runTestOnIncorrect(xmlPath, xsdPath, parser);
        }
    }

    @Test
    public void testParserNullDOM() {
        runIncorrectXMLs(parserDOM);
    }

    @Test
    public void testParserNullSAX() {
        runIncorrectXMLs(parserSax);
    }

    @Test
    public void testParserNullStAX() {
        runIncorrectXMLs(parserStAX);
    }


    @Test
    public void testParserRepresentationDOM() {
        Assert.assertEquals(correctRepresentation.y,
                parserDOM.parse(correctRepresentation.x, xsdPath).toString());
    }

    @Test
    public void testParserRepresentationSAX() {
        Assert.assertEquals(correctRepresentation.y,
                parserSax.parse(correctRepresentation.x, xsdPath).toString());
    }

    @Test
    public void testParserRepresentationStAX() {
        Assert.assertEquals(correctRepresentation.y,
                parserStAX.parse(correctRepresentation.x, xsdPath).toString());
    }
}