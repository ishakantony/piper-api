package app.piper.piper.setting;

import app.piper.piper.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public class BaseSetting extends BaseEntity {

    @Pattern(regexp = "^[a-z]+(\\.[a-z]+)*$")
    @Column(nullable = false, unique = true)
    private String name;

}
