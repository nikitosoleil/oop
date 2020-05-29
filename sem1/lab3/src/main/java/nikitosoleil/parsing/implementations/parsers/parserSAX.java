package nikitosoleil.parsing.implementations.parsers;

import nikitosoleil.parsing.interfaces.Handler;
import nikitosoleil.parsing.interfaces.Parser;
import nikitosoleil.validation.ValidatorProxy;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.SAXParser;
import java.io.File;
import java.util.Arrays;
import java.util.logging.Level;

public class parserSAX<T> implements Parser<T> {
    private Handler<T> handler;

    public parserSAX(Handler<T> concreteHandler) {
        handler = concreteHandler;
    }



    @Override
    public T parse(String xmlPath, String xsdPath) throws IllegalArgumentException {
        if (xsdPath != null) {
            if (!ValidatorProxy.validate(xmlPath, xsdPath)) {
                return null;
            }
        }

        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        T parseResult;
        try {
            SAXParser saxParser = saxParserFactory.newSAXParser();
            SAXHandlerProxy<T> saxHandler = new SAXHandlerProxy<T>(handler);
            saxParser.parse(new File(xmlPath), saxHandler);
            parseResult = handler.getParseResult();
        } catch (Exception e) {
            parserLogger.log(Level.ALL, Arrays.toString(e.getStackTrace()));
            throw new IllegalArgumentException("Error: " + e.getMessage());
        }
        return parseResult;
    }

    private static class SAXHandlerProxy<T> extends DefaultHandler {
        private T parseResult;
        private Handler<T> handler;

        public SAXHandlerProxy(Handler<T> newHandler) {
            handler = newHandler;
        }

        @Override
        public void endDocument() {
            parseResult = handler.getParseResult();
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) {

            handler.onTagStart(qName);
            for (int iter = 0; iter < attributes.getLength(); iter++) {
                handler.setAttribute(attributes.getQName(iter), attributes.getValue(iter));
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) {
            handler.onTagEnd(qName);
        }

        @Override
        public void characters(char[] ch, int start, int length) {
            String data = new String(ch, start, length);
            data = data.replace("\n", "").trim();
            handler.setTag(data);
        }
    }
}