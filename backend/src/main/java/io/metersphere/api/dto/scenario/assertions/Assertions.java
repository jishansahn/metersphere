package io.metersphere.api.dto.scenario.assertions;

import lombok.Data;

import java.util.List;

@Data
public class Assertions {
    private List<AssertionRegex> regex;
    private List<AssertionText> text;
    private List<AssertionJsonPath> jsonPath;
    private List<AssertionJSR223> jsr223;
    private AssertionDuration duration;
}
