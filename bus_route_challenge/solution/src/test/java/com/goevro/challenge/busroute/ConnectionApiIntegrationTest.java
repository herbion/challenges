package com.goevro.challenge.busroute;

import com.goevro.challenge.busroute.storage.RouteStorage;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest("app.import.routes.strategy=manual")
public class ConnectionApiIntegrationTest {

  private static final String ROUTE_DATA_3 = "route-data-3";

  @Autowired
  private MockMvc mvc;

  @Autowired
  private RouteStorage storage;

  @Before
  public void setup() {
    storage.load(TestCaseHelper.getResource(ROUTE_DATA_3));
  }

  @Test
  public void findDirectConnection() throws Exception {
    testFindDirectConnection(2, 6, false);
    testFindDirectConnection(3, 6, true);
    testFindDirectConnection(999, 1, false);
  }

  private void testFindDirectConnection(Integer department, Integer arrival, boolean expectedStatus) throws Exception {
    RequestBuilder apiRequest = get("/api/direct")
            .param("dep_sid", String.valueOf(department))
            .param("arr_sid", String.valueOf(arrival))
            .contentType(MediaType.APPLICATION_JSON);

    mvc.perform(apiRequest)
       .andExpect(status().isOk())
       .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
       .andExpect(jsonPath("$.dep_sid").value(department))
       .andExpect(jsonPath("$.arr_sid").value(arrival))
       .andExpect(jsonPath("$.direct_bus_route").value(expectedStatus));

  }
}