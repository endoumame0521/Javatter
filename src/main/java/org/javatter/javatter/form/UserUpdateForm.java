package org.javatter.javatter.form;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserUpdateForm extends UserForm {
    private Long id;
    private String address;
    private String introduction;
    private String webSite;
    private String profileImage;
    private String backgroundImage;
    private Boolean status;
}
