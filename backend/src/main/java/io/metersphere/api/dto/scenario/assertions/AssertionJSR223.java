package io.metersphere.api.dto.scenario.assertions;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class AssertionJSR223 extends AssertionType {
    private String language;
    private String script;
    public AssertionJSR223() {
        setType(AssertionType.JSR223);
    }
}
