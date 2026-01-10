//package com.adilzhan.workload.component;
//
//import com.adilzhan.workload.config.TestSecurityConfig;
//import com.adilzhan.workload.repository.TrainerSummaryRepository;
//import com.adilzhan.workload.service.WorkloadService;
//import io.cucumber.java.Before;
//import io.cucumber.java.en.Then;
//import io.cucumber.java.en.When;
//import io.cucumber.spring.CucumberContextConfiguration;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.TestPropertySource;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.ResultActions;
//import org.springframework.test.web.servlet.ResultHandler;
//import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//
//@CucumberContextConfiguration
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@AutoConfigureMockMvc(addFilters = false)
//@TestPropertySource(properties = {
//        "spring.data.mongodb.database=workload_test"
//})
//public class WorkloadSteps {
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private WorkloadService workloadService;
//
//    @Autowired
//    private TrainerSummaryRepository trainerSummaryRepository;
//
//    private ResultActions result;
//
//    @Before
//    public void cleanDb() {
//        trainerSummaryRepository.deleteAll();
//    }
//
//    @When("I send ADD workload update for trainer {string}")
//    public void sendAddWorkload(String username) throws Exception {
//        String body = """
//        {
//          "trainerUsername": "%s",
//          "trainerFirstName": "Test",
//          "trainerLastName": "Trainer",
//          "active": true,
//          "trainingDate": "2025-11-01",
//          "trainingDuration": 50,
//          "actionType": "ADD"
//        }
//        """.formatted(username);
//
//        result = mockMvc.perform(
//                post("/api/v1/workload/update")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .characterEncoding("UTF-8")
//                        .content(body)
//        ).andDo(MockMvcResultHandlers.print());
//    }
//
//    @Then("trainer {string} should have workload {int} for year {int} month {int}")
//    public void checkWorkload(String username, int expected, int year, int month) {
//        int actual = workloadService.getMonthlyWorkload(username, year, month);
//        assertEquals(expected, actual);
//    }
//}
