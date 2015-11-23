package de.pincservices;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.cbor.CBORFactory;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;

import java.nio.charset.StandardCharsets;

public class CborHttpMessageConverter extends AbstractJackson2HttpMessageConverter {

    public CborHttpMessageConverter() {
        super(new ObjectMapper(new CBORFactory()), new MediaType("application", "cbor", StandardCharsets.UTF_8));

    }

}
