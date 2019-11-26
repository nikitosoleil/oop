package nikitosoleil.parsing.realisations.parsers;

import nikitosoleil.parsing.interfaces.Handler;
import nikitosoleil.parsing.interfaces.Parser;
import nikitosoleil.validation.ValidatorProxy;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.logging.Level;

import static javax.xml.stream.XMLStreamConstants.*;
import static javax.xml.stream.XMLStreamConstants.END_ELEMENT;

public class parserStAX<T> implements Parser<T> {

    private Handler<T> handler;


    public parserStAX(Handler<T> concreteHandler) {
        handler = concreteHandler;
    }

    @Override
    public T parse(String xmlPath, String xsdPath) throws IllegalArgumentException {

        if (xsdPath != null) {
            if (!ValidatorProxy.validate(xmlPath, xsdPath)) {
                return null;
            }
        }

        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        XMLStreamReader xmlStreamReader;

        xmlStreamReader = getXmlStreamReader(xmlPath, xmlInputFactory);
        processStream(xmlStreamReader);

        return handler.getParseResult();
    }

    private void processStream(XMLStreamReader xmlStreamReader) {
        try {
            while (xmlStreamReader.hasNext()) {
                int event = xmlStreamReader.next();

                if (event == END_DOCUMENT) {
                    break;
                }
                if (event == START_ELEMENT) {

                    handler.onTagStart(xmlStreamReader.getLocalName());

                    for (int i = 0; i < xmlStreamReader.getAttributeCount(); i++) {
                        String value = xmlStreamReader.getAttributeValue(i);
                        String name = xmlStreamReader.getAttributeName(i).toString();
                        handler.setAttribute(name, value);
                    }
                }
                if (event == CHARACTERS) {
                    String information = xmlStreamReader.getText();
                    information = information.replace("\n", "").trim();

                    handler.setTag(information);
                }
                if (event == END_ELEMENT) {
                    handler.onTagEnd(xmlStreamReader.getLocalName());
                }
            }

        } catch (XMLStreamException e) {
            parserLogger.log(Level.ALL, Arrays.toString(e.getStackTrace()));
            throw new IllegalArgumentException("Error " + e.getMessage());
        }
    }

    private XMLStreamReader getXmlStreamReader(String xmlPath, XMLInputFactory xmlInputFactory) {
        XMLStreamReader xmlStreamReader;
        try {
            xmlStreamReader = xmlInputFactory.createXMLStreamReader(new FileInputStream(xmlPath));
        } catch (FileNotFoundException | XMLStreamException e) {
            parserLogger.log(Level.ALL, Arrays.toString(e.getStackTrace()));
            throw new IllegalArgumentException("Error " + e.getMessage());
        }
        return xmlStreamReader;
    }
}