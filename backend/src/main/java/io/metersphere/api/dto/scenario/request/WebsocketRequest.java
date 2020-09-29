package io.metersphere.api.dto.scenario.request;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
import io.metersphere.api.dto.scenario.KeyValue;
import io.metersphere.api.dto.scenario.assertions.Assertions;
import io.metersphere.api.dto.scenario.extract.Extract;
import io.metersphere.api.dto.scenario.processor.JDBCPreProcessor;
import io.metersphere.api.dto.scenario.processor.JDBCPostProcessor;
import io.metersphere.api.dto.scenario.processor.JSR223PostProcessor;
import io.metersphere.api.dto.scenario.processor.JSR223PreProcessor;
import lombok.Data;

import java.util.List;

@Data
@JSONType(typeName = RequestType.WEBSOCKET)
public class WebsocketRequest implements Request {
    private String type = RequestType.WEBSOCKET;
    @JSONField(ordinal = 1)
    private String name;
    @JSONField(ordinal = 2)
    private String url;
    @JSONField(ordinal = 3)
    private String path;
    @JSONField(ordinal = 4)
    private Boolean useEnvironment;
    @JSONField(ordinal = 5)
    private Boolean createNewConnection;
    @JSONField(ordinal = 6)
    private List<KeyValue> headers;
    @JSONField(ordinal = 7)
    private Boolean TLS;
    @JSONField(ordinal = 8)
    private String server;
    @JSONField(ordinal = 9)
    private Integer port;
    @JSONField(ordinal = 10)
    private String requestData;
    @JSONField(ordinal = 11)
    private String protocol;
    @JSONField(ordinal = 12)
    private Assertions assertions;
    @JSONField(ordinal = 13)
    private Extract extract;
    @JSONField(ordinal = 14)
    private JSR223PreProcessor jsr223PreProcessor;
    @JSONField(ordinal = 15)
    private JSR223PostProcessor jsr223PostProcessor;
    @JSONField(ordinal = 16)
    private String id;
    @JSONField(ordinal = 17)
    private Long connectTimeout;
//    private Boolean binaryPayload;
    @JSONField(ordinal = 18)
    private Long readTimeout;
    @JSONField(ordinal = 19)
    private Integer mode;
    @JSONField(ordinal = 20)
    private Integer statusCode;
    @JSONField(ordinal = 14)
    private JDBCPreProcessor jdbcPreProcessor;
    @JSONField(ordinal = 15)
    private JDBCPostProcessor jdbcPostProcessor;
//    private Boolean loadDataFromFile;

    //            dataFile
//    assertions
//    private String  environment;

}
