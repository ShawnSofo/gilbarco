/**
 * This test makes sure the first API call starts with 1
 */
package com.shawnsofo.gilbarco.time.api;

import static org.assertj.core.api.Assertions.assertThat;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS) // makes sure to restart the Spring boot application
                                                                  // after the test completes
public class FirstCallToServiceTests {
    @Autowired
    private TestRestTemplate restApi;

    @LocalServerPort
    private int port;

    private String getUrl() {
        return "http://localhost:" + this.port + "/now/est";
    }

    @Test()
    public void testCallsStartWithOne() throws JSONException {
        System.out.println(port);
        JSONObject json = new JSONObject(this.restApi.getForObject(this.getUrl(), String.class));
        assertThat(json.getLong("calls")).isOne();
    }
}