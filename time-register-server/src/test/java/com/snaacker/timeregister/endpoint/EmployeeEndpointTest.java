package com.snaacker.timeregister.endpoint;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.snaacker.timeregister.model.request.EmployeeRequest;
import com.snaacker.timeregister.model.response.EmployeeResponse;
import com.snaacker.timeregister.model.response.TimeRegisterGenericResponse;
import com.snaacker.timeregister.service.EmployeeService;
import java.util.List;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

@Disabled
public class EmployeeEndpointTest extends BaseEndpointTest {
    @MockBean private EmployeeService employeeService;

    @Test
    public void testCreateUserShouldSuccessfully() throws Exception {
        EmployeeResponse employeeResponse = new EmployeeResponse();
        employeeResponse.setUsername("TestUser");
        when(employeeService.createEmployee(any())).thenReturn(employeeResponse);
        mockMvc.perform(
                        put("/api/v1/user")
                                .content(objectMapper.writeValueAsString(new EmployeeRequest()))
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(content().string(contains("TestUser")));
    }

    @Test
    public void testGetAllEmployeeShouldSuccess() throws Exception {
        EmployeeResponse employeeResponse = new EmployeeResponse();
        employeeResponse.setUsername("TestUser");
        List<EmployeeResponse> listEmployee = List.of(employeeResponse);
        TimeRegisterGenericResponse<EmployeeResponse> returnResponse =
                new TimeRegisterGenericResponse<>(listEmployee, 1, 1);
        when(employeeService.getListEmployee(anyInt(), anyInt())).thenReturn(returnResponse);
        mockMvc.perform(get("/api/v1/user"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(content().string(contains("TestUser")));
    }
}
