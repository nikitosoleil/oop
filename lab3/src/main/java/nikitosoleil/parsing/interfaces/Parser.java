package nikitosoleil.parsing.interfaces;

import java.util.logging.Logger;

public interface Parser<T> {
    Logger parserLogger = Logger.getLogger("ParserLogger");
    T parse(String xmlPath, String xsdPath);
}
