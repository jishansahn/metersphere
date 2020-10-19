package io.metersphere.api.dto.scenario.assertions;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class AssertionText extends AssertionType {
    private String subject;
    private String value;
    private String description;
    private String condition;

    public AssertionText() {
        setType(AssertionType.TEXT);
    }
}
