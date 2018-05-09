package com.shawnsofo.gilbarco.time.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import java.util.Optional;
import java.util.function.Supplier;

import com.shawnsofo.gilbarco.time.entity.Timestamp;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CurrentTimeServiceTests {

    @Autowired
    private TestRestTemplate restApi;

    @LocalServerPort
    private int port;

    private String getUrl() {
        return "http://localhost:" + this.port + "/now/est";
    }

    @Test
    public void testReturnTimestampInstance() throws JSONException {

        assertThatCode(() -> {
            this.restApi.getForEntity(this.getUrl(), Timestamp.class);
        }).doesNotThrowAnyException();

        JSONObject json = new JSONObject(this.restApi.getForObject(this.getUrl(), String.class));
        // expects a timestamp field that is not empty
        assertThat(json.getString("timestamp")).isNotEmpty();
        // expects a calls field greater than 0
        assertThat(json.getLong("calls")).isNotNull().isGreaterThan(0L);
    }

    @Test
    public void testStatusMustBeOK() {
        ResponseEntity<String> resp = this.restApi.getForEntity(this.getUrl(), String.class);
        assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void testMustIncrementNumberOfCalls() {
        Supplier<Long> numberOfCalls = () -> this.restApi.getForObject(this.getUrl(), Timestamp.class).getCalls();
        long firstCall = numberOfCalls.get();
        long secondCall = numberOfCalls.get();
        assertThat(secondCall).isEqualTo(firstCall + 1);
    }

}