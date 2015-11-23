package de.pincservices;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.cbor.CBORFactory;
import com.fasterxml.jackson.dataformat.cbor.CBORGenerator;
import com.fasterxml.jackson.dataformat.cbor.CBORParser;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class CborHttpMessageConverter extends AbstractHttpMessageConverter<Object> {

    private CBORFactory cborFactory;

    public CborHttpMessageConverter() {
        super(new MediaType("application", "cbor", StandardCharsets.UTF_8));
        cborFactory = new CBORFactory(new ObjectMapper());
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    protected Object readInternal(Class<?> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        final CBORParser parser = cborFactory.createParser(inputMessage.getBody());
        return parser.readValueAs(clazz);
    }

    @Override
    protected void writeInternal(Object o, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        final CBORGenerator generator = cborFactory.createGenerator(outputMessage.getBody());
        generator.writeObject(o);
    }
}
