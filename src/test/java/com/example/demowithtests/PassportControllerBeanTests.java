package com.example.demowithtests;

import com.example.demowithtests.domain.Passport;
import com.example.demowithtests.dto.PassportRequestDto;
import com.example.demowithtests.dto.PassportResponseDto;
import com.example.demowithtests.service.PassportServiceBean;
import com.example.demowithtests.util.config.PassportMapper;
import com.example.demowithtests.web.PassportControllerBean;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = PassportControllerBean.class)
public class PassportControllerBeanTests {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PassportServiceBean passportService;

    @MockBean
    PassportMapper passportMapper;

    @Test
    @WithMockUser(roles = "ADMIN")
    public void removeById() throws Exception {

        //  Заглушка для выполнения сервисного метода
        doNothing().when(passportService).removeById(anyInt());

        //  Готовим запрос к контроллеру
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .patch("/api/passports/1/remove");

        //  Выполняем запрок к контроллеру и проверяем статус
        mockMvc.perform(mockRequest)
                .andExpect(status().isNoContent());

        //  Проверяем факт вызова сервиса с заданным в запросе параметром
        Mockito.verify(passportService).removeById(1);
    }

    @Test
    @WithMockUser(roles = "USER")
    public void getPassportById() throws Exception {

        PassportResponseDto passportResponseDto = new PassportResponseDto();
        passportResponseDto.id = 1;

        when(  passportService.getById(1)  ).thenReturn(new Passport());
        when(  passportMapper.passportToPassportResponseDto(any(Passport.class))  ).thenReturn(passportResponseDto);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/api/passports/1");

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1) ));

        verify(passportService).getById(1);
    }

}
