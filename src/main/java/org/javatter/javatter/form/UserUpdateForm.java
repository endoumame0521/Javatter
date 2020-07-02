package org.javatter.javatter.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.URL;
import org.springframework.web.multipart.MultipartFile;

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
    @URL
    private String webSite;

    private MultipartFile profImgFile;

    private String profileName;

    private MultipartFile bgImgFile;

    private String backgroundName;

    @NotNull
    private Boolean status;
}
