package nikitosoleil;

import nikitosoleil.concert.Dance;
import nikitosoleil.parsing.realisations.handlers.DanceHandler;
import nikitosoleil.parsing.realisations.parsers.ParserDOM;
import nikitosoleil.parsing.realisations.parsers.parserSAX;
import nikitosoleil.parsing.realisations.parsers.parserStAX;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) {
        Logger logger = Logger.getLogger("logger");

        ParserDOM<Dance> parserDOM = new ParserDOM<>(new DanceHandler());
        parserStAX<Dance> parserStAX = new parserStAX<>(new DanceHandler());
        parserSAX<Dance> parserSax = new parserSAX<>(new DanceHandler());

        Dance dance = parserDOM.parse("resources/example_3_dancers.xml",
                "resources/dance.xsd");
        String res = dance.toString();
        logger.log(Level.INFO, res);


        dance = parserStAX.parse("resources/example_2_dancers.xml",
                "resources/dance.xsd");
        res = dance.toString();
        logger.log(Level.INFO, res);


        dance = parserSax.parse("resources/example_1_dancer.xml",
                "resources/dance.xsd");
        res = dance.toString();
        logger.log(Level.INFO, res);
    }
}
