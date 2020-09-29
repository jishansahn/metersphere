package io.metersphere.api.dto.scenario.processor;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class JDBCProcessor {
    @JSONField(ordinal = 1)
    private String dataSource;
    @JSONField(ordinal = 2)
    private String query;
    @JSONField(ordinal = 3)
    private long queryTimeout;
    @JSONField(ordinal = 4)
    private String resultVariable;
    @JSONField(ordinal = 5)
    private String variableNames;
}
