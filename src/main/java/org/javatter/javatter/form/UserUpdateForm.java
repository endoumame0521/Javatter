package org.javatter.javatter.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserUpdateForm extends UserForm {
    @NotNull
    private Long id;

    @Size(max = 100)
    private String address;

    @Size(max = 500)
    private String introduction;

    @Size(max = 200)
    private String webSite;

    @Size(max = 200)
    private String profileImage;

    @Size(max = 200)
    private String backgroundImage;

    @NotNull
    private Boolean status;
}
