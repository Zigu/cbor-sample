package de.pincservices;

import co.nstant.in.cbor.CborBuilder;
import co.nstant.in.cbor.CborEncoder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.cbor.CBORFactory;
import com.fasterxml.jackson.dataformat.cbor.CBORGenerator;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.ByteArrayOutputStream;

import static com.jayway.restassured.RestAssured.given;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringApplicationConfiguration(classes = SampleApplication.class)
@IntegrationTest("server.port:0")
public class SampleApplicationTest {

    @Value("${local.server.port}")
    private int port;


    @Before
    public void init() {
        RestAssured.port = port;
    }

    @Test
    public void test() throws Exception {
        given().when().body(sampleNotification()).contentType("application/cbor").post("/notifications").then().statusCode(200);
    }

    private byte[] sampleNotification() throws Exception {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final CBORFactory factory = new CBORFactory(new ObjectMapper());
        final CBORGenerator generator = factory.createGenerator(baos);

        final Notification notification = new Notification();
        notification.setId(1);
        notification.setText("Check 12");

        generator.writeObject(notification);
        generator.flush();
        generator.close();

        final byte[] bytes = baos.toByteArray();

        System.out.println("Converted " + notification + " to cbor. ("  + new String(bytes) + ")");

        return bytes;
    }

}