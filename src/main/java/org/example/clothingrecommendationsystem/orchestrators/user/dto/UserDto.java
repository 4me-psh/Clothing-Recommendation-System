package org.example.clothingrecommendationsystem.orchestrators.user.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.clothingrecommendationsystem.model.basemodel.BaseModel;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserDto extends BaseModel {
    private Long id;
    private String username;
    private String password;
    private String email;
}
