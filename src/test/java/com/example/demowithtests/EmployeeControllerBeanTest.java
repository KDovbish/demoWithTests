package com.example.demowithtests;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.dto.EmployeeCreateDto;
import com.example.demowithtests.dto.EmployeeDto;
import com.example.demowithtests.repository.EmployeeRepository;
import com.example.demowithtests.service.EmployeeServiceBean;
import com.example.demowithtests.util.config.EmployeeMapStructMapper;
import com.example.demowithtests.util.config.PassportMapper;
import com.example.demowithtests.web.ControllerBean;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = ControllerBean.class)
public class EmployeeControllerBeanTest {

    @Autowired
    ObjectMapper mapper;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    EmployeeServiceBean employeeService;

    @MockBean
    EmployeeMapStructMapper employeeMapStructMapper;

    @MockBean
    PassportMapper passportMapper;


    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("POST /api/users")
    public void saveEmployee() throws Exception {

        //  Входное DTO
        EmployeeCreateDto employeeCreateDto = new EmployeeCreateDto();
        employeeCreateDto.name = "Ivan Ivanov";
        employeeCreateDto.country = "USA";

        //  Выходное DTO
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.name = employeeCreateDto.name;

        //  Заглушка на вызов метода, который отдает результат работы метода контроллера
        when(employeeMapStructMapper.employeeToEmployeeDto(any(Employee.class))).thenReturn(employeeDto);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(employeeCreateDto));

        mockMvc.perform(mockRequest)
                .andExpect(status().isCreated());
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("POST /api/users/generate")
    public void generateEmployee() throws Exception {

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .post("/api/users/generate/1");

        //  Выполняем запрос и ждем заданный статус ответа
        mockMvc.perform(mockRequest)
                .andExpect(status().isCreated());

        //  Проверяем, что метод генерации запускался с нужными параметрами
        verify(employeeService).generate(1, false);

    }

}
