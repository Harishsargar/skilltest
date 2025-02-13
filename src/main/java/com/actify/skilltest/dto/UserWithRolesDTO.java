package com.actify.skilltest.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserWithRolesDTO {

    private String id;
    private String name;
    private String email;
    private List<String> roleList;

}
