package com.example.demowithtests.dto;

import com.example.demowithtests.domain.Address;
import com.example.demowithtests.domain.Gender;
import com.example.demowithtests.validation.CountryCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@ToString
public class EmployeeDto {

    public Integer id;

    @NotNull(message = "Name may not be null")
    @Size(min = 2, max = 32, message = "Name must be between 2 and 32 characters long")
    @Schema(description = "Name of an employee.", example = "Billy", required = true)
    public String name;

    @CountryCode(message = "Country code requires A2 type format")
    @Schema(description = "Name of the country.", example = "England", required = true)
    public String country;

    @Email
    @NotNull
    @Schema(description = "Email address of an employee.", example = "billys@mail.com", required = true)
    public String email;

    public Set<AddressDto> addresses = new HashSet<>();

    public Gender gender;

    public Set<PhotoDto> photos = new HashSet<>();


}
