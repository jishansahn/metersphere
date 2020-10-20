package io.metersphere.api.parse;

import io.metersphere.api.dto.ApiTestImportRequest;
import io.metersphere.api.dto.parse.ApiImport;
import io.metersphere.api.dto.scenario.Body;
import io.metersphere.api.dto.scenario.KeyValue;
import io.metersphere.api.dto.scenario.Scenario;
import io.metersphere.api.dto.scenario.assertions.*;
import io.metersphere.api.dto.scenario.controller.IfController;
import io.metersphere.api.dto.scenario.extract.Extract;
import io.metersphere.api.dto.scenario.extract.ExtractJSONPath;
import io.metersphere.api.dto.scenario.extract.ExtractRegex;
import io.metersphere.api.dto.scenario.extract.ExtractXPath;
import io.metersphere.api.dto.scenario.processor.*;
import io.metersphere.api.dto.scenario.request.*;
import io.metersphere.api.dto.scenario.timer.ConstantTimer;
import io.metersphere.commons.utils.LogUtil;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.DOMReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JmeterParser extends ApiImportAbstractParser {
//    private final static String HASH_TREE_ELEMENT = "hashTree";
//    private final static String TEST_PLAN = "TestPlan";
//    private final static String THREAD_GROUP = "";
//    private final static String USER_PARAM = "UserParameters";
//    private final static String Cookie_Manager = "CookieManager";
//    private final static String STRING_PROP = "stringProp";
//    private final static String ARGUMENTS = "Arguments";
//    private final static String COLLECTION_PROP = "collectionProp";
//    private final static String HTTP_SAMPLER_PROXY = "HTTPSamplerProxy";
//    private final static String ELEMENT_PROP = "elementProp";

//    private final static String DATA_SOURCE = "48e7c8cc-5161-9758-327b-0dbc8e3a9715";
    private final static String DATA_SOURCE="6c6ff387-99b6-4cc1-5f32-72d6ec2c9520";
//    private final static String ENV_ID = "0aa4bfbc-462f-4703-aebb-81ed97e7465d";
    private final static String ENV_ID = "8b154ca6-7c1c-43f3-a1f8-1ea8e15bba45";

    @Override
    public ApiImport parse(InputStream source, ApiTestImportRequest request) {
        ApiImport apiImport = new ApiImport();
        List<Scenario> scenarios = new ArrayList<>();
        apiImport.setScenarios(scenarios);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            org.w3c.dom.Document d = builder.parse(source);
            DOMReader reader = new DOMReader();
            Document document = reader.read(d);
            Element jmeterTestPlan = document.getRootElement();
            jmeterTestPlan.elements();
            Element plan = jmeterTestPlan.element("hashTree").element("testPlan");
            Element planTree = jmeterTestPlan.element("hashTree").element("hashTree");
            parseTestPlan(plan, planTree, scenarios);

        } catch (Exception e) {
            LogUtil.error(e);

        }
        return apiImport;
    }
//    public void parse(){
//        ApiImport apiImport = new ApiImport();
//        List<Scenario> scenarios = new ArrayList<>();
//        Scenario scenario = new Scenario();
//        SAXReader reader = new SAXReader();
//        JSONObject obj = new JSONObject();
//        try {
//            File file=new File("D:\\softdata\\py-project\\metersphere\\metersphere\\backend\\demo2.xml");
//
//            Document document = reader.read(file);
//            Element root = document.getRootElement();
//            System.out.println(root.getName());
//            int i=0;
//            List<Element> list = root.elements() ;
//            for (Element e:list){
//                i++;
//                System.out.println(e.elementText("Name"));
//                System.out.println(e.elementText("Value"));
//                obj.put(e.elementText("Name"), e.elementText("Value"));
//
//            }
//            System.out.println(i);
//            System.out.println(obj);
//
//        }catch(DocumentException e){
//            e.printStackTrace();
//        }
//    }

    public void parse2() {
        List<Scenario> scenarios = new ArrayList<>();
//        setScenarioByRequest
//        apiImport.setScenarios(parseRequests(swagger));
//        apiImport.getScenarios().forEach(scenario -> scenario.setEnvironmentId(request.getEnvironmentId()));
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            org.w3c.dom.Document d = builder.parse("backend\\权限管理.xml");
            DOMReader reader = new DOMReader();
            Document document = reader.read(d);
            Element jmeterTestPlan = document.getRootElement();
            jmeterTestPlan.elements();
            Element plan = jmeterTestPlan.element("hashTree").element("testPlan");
            Element planTree = jmeterTestPlan.element("hashTree").element("hashTree");
            parseTestPlan(plan, planTree, scenarios);

        } catch (Exception e) {
            LogUtil.error(e);

        }
    }

    private void parseTestPlan(Element testplan, Element hashTree, List<Scenario> scenarios) {
        List<Element> tags = hashTree.elements();
        for (int i = 0; i < tags.size(); i = i + 2) {
            String tagName = tags.get(i).getName();
            switch (tagName) {
                case "ThreadGroup":
                    parseThreadGroup(tags.get(i), tags.get(i + 1), scenarios);
                    break;
                default:
                    System.out.println("未知tag");
                    break;
            }

        }
    }

    private void parseThreadGroup(Element element, Element hashTree, List<Scenario> scenarios) {
        Scenario scenario = new Scenario();
        List<KeyValue> variables = new ArrayList<>();
        List<KeyValue> headers = new ArrayList<>();
        List<Request> requests = new ArrayList<>();

        scenario.setName(element.attributeValue("testname"));
        scenario.setEnable(Boolean.parseBoolean(element.attributeValue("enabled")));

        scenario.setVariables(variables);
        scenario.setHeaders(headers);
        scenario.setRequests(requests);
        scenarios.add(scenario);
//        "environmentId": "0aa4bfbc-462f-4703-aebb-81ed97e7465d"
        scenario.setEnvironmentId(ENV_ID);
        parseSamplerHashTree(element, hashTree, scenario);
        KeyValue falseKv = new KeyValue();
        falseKv.setEnable(false);
        headers.add(falseKv);
        KeyValue falseKv2 = new KeyValue();
        falseKv2.setEnable(false);
        variables.add(falseKv2);
    }

    private void parseSamplerHashTree(Element element, Element hashTree, Scenario scenario) {
        List<KeyValue> variables = scenario.getVariables();
        List<KeyValue> headers = scenario.getHeaders();
        List<Request> requests = scenario.getRequests();

        List<Element> tags = hashTree.elements();
        for (int i = 0; i < tags.size(); i = i + 2) {
            String tagName = tags.get(i).getName();
            Element subHashTree = tags.get(i + 1);
            switch (tagName) {
                case "Arguments":
//                        设置所有参数enable
//                    kv.setEnable(Boolean.parseBoolean(tags.get(i).attributeValue("enabled")));
//                    variables.add(kv);
                    parseArguments(tags.get(i), variables, "Argument.name", "Argument.value");
                    break;
                case "UserParameters":
//                        设置所有参数enable
//                    kv.setEnable(Boolean.parseBoolean(tags.get(i).attributeValue("enabled")));
//                    variables.add(kv);
                    parseUserParameters(tags.get(i), tags.get(i + 1), variables);
                    break;
                case "CookieManager":
                    scenario.setEnableCookieShare(Boolean.parseBoolean(tags.get(i).attributeValue("enabled")));
                    break;
                case "HeaderManager":
                    parseHeader(tags.get(i), headers);
                    break;
                case "HTTPSamplerProxy":
                    HttpRequest httpRequest = new HttpRequest();
                    parseHttpRequest(tags.get(i), tags.get(i + 1), httpRequest, element);
                    requests.add(httpRequest);
                    break;
                case "eu.luminis.jmeter.wssampler.OpenWebSocketSampler":
                    WebsocketRequest websocketOpen = new WebsocketRequest();
                    parseWebsocketRequest(tags.get(i), tags.get(i + 1), websocketOpen, element);
                    requests.add(websocketOpen);
                    break;
                case "eu.luminis.jmeter.wssampler.RequestResponseWebSocketSampler":
                    WebsocketRequest websocketRequest = new WebsocketRequest();
                    parseWebsocketRequest(tags.get(i), subHashTree, websocketRequest, element);
                    requests.add(websocketRequest);
                    break;
                case "eu.luminis.jmeter.wssampler.CloseWebSocketSampler":
                    WebsocketRequest websocketClose = new WebsocketRequest();
                    parseWebsocketRequest(tags.get(i), subHashTree, websocketClose, element);
                    requests.add(websocketClose);
                    break;
                case "JDBCSampler":
                    SqlRequest sqlRequest = new SqlRequest();
                    parseSqlRequest(tags.get(i), subHashTree, sqlRequest, element);
                    requests.add(sqlRequest);
                    break;
                case "GenericController":
                    parseSamplerHashTree(tags.get(i), subHashTree, scenario);
                    break;
                case "IfController":
                    parseSamplerHashTree(tags.get(i), subHashTree, scenario);
                    break;
                default:
                    System.out.println("未知tag");
                    break;
            }
        }


    }

    private void parseSqlRequest(Element element, Element hashTree, SqlRequest sqlRequest, Element parent) {
        List<Element> tags = element.elements();
        String tagName = element.getName();
        String testname = element.attributeValue("testname");
        if ("GenericController".equals(parent.getName())) {
            testname = testname + ">" + parent.attributeValue("testname");
        }
        sqlRequest.setName(testname);
        sqlRequest.setEnable(Boolean.parseBoolean(element.attributeValue("enabled")));
        sqlRequest.setUseEnvironment(Boolean.TRUE);

        if ("IfController".equals(parent.getName())) {
            IfController ifController = new IfController();
//            ${__jexl3(${233}&gt;s)}
//            ${__groovy(&quot;${status}&quot;==&quot;running&quot;,)}
            parseIfController(ifController,parent);
            sqlRequest.setController(ifController);
        }
//        9458b437-a5d9-a038-7f6c-9066fc483128
        sqlRequest.setDataSource(DATA_SOURCE);
        sqlRequest.setQuery(getElementTextByAttribute(tags, "name", "query"));
        sqlRequest.setVariableNames(getElementTextByAttribute(tags, "name", "variableNames"));
        sqlRequest.setResultVariable(getElementTextByAttribute(tags, "name", "resultSetHandler"));

        parseProcessHashTree(element, hashTree, sqlRequest);
    }

    private void parseIfController(IfController ifController,Element parent){
        ifController.setEnable(Boolean.parseBoolean(parent.attributeValue("enabled")));
        ifController.setType("If Controller");
        String condition = getElementTextByAttribute(parent.elements(),"name","IfController.condition");

//        ifController.setValue(condition.split("==")[0]);
        ifController.setVariable("variable");
        ifController.setOperator("==");
        ifController.setValue(condition);


//        if(condition.indexOf(">")>0){
//            ifController.setValue(condition.split(">")[0]);
//            ifController.setOperator(">");
//            ifController.setValue(condition.split(">")[1]);
//        }
    }

    private void parseWebsocketRequest(Element element, Element hashTree, WebsocketRequest websocketRequest, Element parent) {
        List<Element> tags = element.elements();
        String tagName = element.getName();
        String testname = element.attributeValue("testname");
        if ("GenericController".equals(parent.getName())) {
            testname = testname + ">" + parent.attributeValue("testname");
        }
        websocketRequest.setName(testname);
        websocketRequest.setEnable(Boolean.parseBoolean(element.attributeValue("enabled")));
        websocketRequest.setUseEnvironment(Boolean.TRUE);
        if ("IfController".equals(parent.getName())) {
            IfController ifController = new IfController();
//            ${__jexl3(${233}&gt;s)}
//            ${__groovy(&quot;${status}&quot;==&quot;running&quot;,)}
            parseIfController(ifController,parent);
            websocketRequest.setController(ifController);
        }
        int mode = 0;
        if (tagName.indexOf("OpenWebSocketSampler") > 0) {
            mode = 1;
        } else if (tagName.indexOf("RequestResponseWebSocketSampler") > 0) {
            mode = 2;
        } else {
            mode = 0;
        }
        websocketRequest.setMode(mode);
        if (mode == 1 || mode == 2) {
            websocketRequest.setServer(getElementTextByAttribute(tags, "name", "server"));
            websocketRequest.setPath(getElementTextByAttribute(tags, "name", "path"));
            String protocol = Boolean.parseBoolean(getElementTextByAttribute(tags, "name", "TLS")) ? "wss://" : "ws://";
            websocketRequest.setProtocol(protocol);
            websocketRequest.setConnectTimeout(Long.parseLong(getElementTextByAttribute(tags, "name", "connectTimeout")));

        }
        if (mode == 2) {
            websocketRequest.setCreateNewConnection(Boolean.parseBoolean(getElementTextByAttribute(tags, "name", "createNewConnection")));
            websocketRequest.setRequestData(getElementTextByAttribute(tags, "name", "requestData"));
        }
        if (mode == 0) {
            websocketRequest.setStatusCode(Integer.parseInt(getElementTextByAttribute(tags, "name", "statusCode")));
        }
        if (mode == 1 || mode == 2 || mode == 3) {
            websocketRequest.setReadTimeout(Long.parseLong(getElementTextByAttribute(tags, "name", "readTimeout")));
        }
        parseProcessHashTree(element, hashTree, websocketRequest);

    }


    private void parseHttpRequest(Element element, Element hashTree, HttpRequest httpRequest, Element parent) {
        List<Element> tags = element.elements();
        String testname = element.attributeValue("testname");
        if ("GenericController".equals(parent.getName())) {
            testname = testname + ">" + parent.attributeValue("testname");
        }
        httpRequest.setName(testname);
        if ("IfController".equals(parent.getName())) {
            IfController ifController = new IfController();
//            ${__jexl3(${233}&gt;s)}
//            ${__groovy(&quot;${status}&quot;==&quot;running&quot;,)}
            parseIfController(ifController,parent);
//            String expression="";
//            if(condition.startsWith("${__")){
//                int ld=condition.indexOf("(");
//                int rd=condition.indexOf(",)}");
//                expression=condition.substring(ld+1,rd-1);
//            }else{
//                expression=condition;
//            }
//            if(expression.indexOf("==")>0){
//                ifController.setOperator("==");
//                ifController.setVariable(expression.substring(0,));
//            }
            httpRequest.setController(ifController);

        }

        String method = getElementTextByAttribute(tags, "name", "HTTPSampler.method");
        httpRequest.setMethod(method);
        httpRequest.setPath(getElementTextByAttribute(tags, "name", "HTTPSampler.path"));
        httpRequest.setEnable(Boolean.parseBoolean(element.attributeValue("enabled")));
        httpRequest.setUseEnvironment(Boolean.TRUE);
        if (method.equals("GET")) {
            List<KeyValue> parameters = new ArrayList<>();
            Element paramTag = getElementByAttribute(tags, "name", "HTTPsampler.Arguments");
            if (paramTag != null) {
                parseArguments(paramTag, parameters, "Argument.name", "Argument.value");

            }
            KeyValue kv = new KeyValue();
            kv.setEnable(false);
            kv.setType("text");
            parameters.add(kv);
            httpRequest.setParameters(parameters);
        }
        if (method.equals("POST")) {
            Body body = new Body();
            List<KeyValue> kvs = new ArrayList<>();
            body.setKvs(kvs);
            httpRequest.setBody(body);
            int i = 0;
            while (i < tags.size()) {
                Element tag = tags.get(i);
                String tagAttribute = tag.attributeValue("name");
                if (tagAttribute.equals("HTTPSampler.postBodyRaw")) {
                    i = i + 1;
                    body.setType("Raw");
                    body.setFormat("Text");
                    String raw = getElementTextByAttribute(tags.get(i).element("collectionProp").element("elementProp").elements(),
                            "name", "Argument.value");
                    body.setRaw(raw);
                }
                if (tagAttribute.equals("HTTPsampler.Arguments")) {
                    body.setType("KeyValue");
                    body.setFormat("Text");
                    Element paramTag = getElementByAttribute(tags, "name", "HTTPsampler.Arguments");
                    if (paramTag != null)
                        parseArguments(paramTag, kvs, "Argument.name", "Argument.value");
                }
                if (tagAttribute.equals("HTTPsampler.Files")) {
                    List<Element> args = tag.elements("collectionProp");
                    for (int j = 0; j < args.size(); j++) {
                        KeyValue kv = new KeyValue();
                        kv.setEnable(Boolean.TRUE);
                        kv.setType("file");
                        kv.setName(getElementTextByAttribute(args.get(i).elements(), "name", "File.paramname"));
                        List<BodyFile> files = new ArrayList<>();
                        BodyFile bf = new BodyFile();
                        files.add(bf);
                        kv.setFiles(files);
                        kvs.add(kv);
                    }

                }
                i++;
            }
            KeyValue falseKv = new KeyValue();
            falseKv.setEnable(false);
            falseKv.setType("text");
            kvs.add(falseKv);
        }

        parseProcessHashTree(element, hashTree, httpRequest);

    }

    private void parseProcessHashTree(Element element, Element hashTree, Request request) {
        List<Element> subTags = hashTree.elements();
        Assertions assertions = new Assertions();
        List<AssertionText> rsp_text = new ArrayList<>();
        List<AssertionJsonPath> jsonPath = new ArrayList<>();
        List<AssertionJSR223> jsr223 = new ArrayList<>();
        List<KeyValue> headers = new ArrayList<>();
        Extract extract = new Extract();
        List<ExtractRegex> ex_regexs = new ArrayList<>();
        List<ExtractJSONPath> ex_jsons = new ArrayList<>();
        List<ExtractXPath> ex_xpaths = new ArrayList<>();
        ConstantTimer timer = new ConstantTimer();

        JDBCPreProcessor jdbcPreProcessor = new JDBCPreProcessor();
        JDBCPostProcessor jdbcPostProcessor = new JDBCPostProcessor();
        JSR223PreProcessor jsr223PreProcessor = new JSR223PreProcessor();
        JSR223PostProcessor jsr223PostProcessor = new JSR223PostProcessor();

        for (int i = 0; i < subTags.size(); i = i + 2) {
            String tagName = subTags.get(i).getName();
            Element subTag = subTags.get(i);
            switch (tagName) {
                case "HeaderManager":
                    parseHeader(subTag, headers);
                    break;
                case "ResponseAssertion":
                    parseResponseAssertion(subTag, rsp_text);
                    break;
                case "JSONPathAssertion":
                    parseJsonPathAssertion(subTag, jsonPath);
                    break;
                case "BeanShellAssertion":
                    parseJsr223Assertion(subTag, jsr223);
                    break;
                case "JSR223Assertion":
                    parseJsr223Assertion(subTag, jsr223);
                    break;
                case "JSONPostProcessor":
                    parseJSONPostProcessor(subTag, ex_jsons);
                    break;
                case "JDBCPreProcessor":
                    parseJDBCProcessor(subTag, jdbcPreProcessor);
                    break;
                case "JDBCPostProcessor":
                    parseJDBCProcessor(subTag, jdbcPostProcessor);
                    break;
                case "BeanShellPreProcessor":
                    parseBeanShellProcessor(subTag, jsr223PreProcessor);
                    break;
                case "BeanShellPostProcessor":
                    parseBeanShellProcessor(subTag, jsr223PostProcessor);
                    break;
                case "ConstantTimer":
                    parseConstantTimer(subTag, timer);
                default:
                    System.out.println("未知tag");
                    break;
            }
        }
        KeyValue falseKv = new KeyValue();
        falseKv.setEnable(false);
        headers.add(falseKv);
        assertions.setText(rsp_text);
        assertions.setJsonPath(jsonPath);
        assertions.setJsr223(jsr223);
        extract.setRegex(ex_regexs);
        extract.setJson(ex_jsons);
        extract.setXpath(ex_xpaths);

        if (request instanceof HttpRequest) {
            HttpRequest req = (HttpRequest) request;
            req.setAssertions(assertions);
            req.setHeaders(headers);
            req.setExtract(extract);
            req.setJdbcPreProcessor(jdbcPreProcessor);
            req.setJdbcPostProcessor(jdbcPostProcessor);
            req.setJsr223PreProcessor(jsr223PreProcessor);
            req.setJsr223PostProcessor(jsr223PostProcessor);
            req.setTimer(timer);
        }
        if (request instanceof WebsocketRequest) {
            WebsocketRequest req = (WebsocketRequest) request;
            req.setAssertions(assertions);
            req.setHeaders(headers);
            req.setExtract(extract);
            req.setJdbcPreProcessor(jdbcPreProcessor);
            req.setJdbcPostProcessor(jdbcPostProcessor);
            req.setJsr223PreProcessor(jsr223PreProcessor);
            req.setJsr223PostProcessor(jsr223PostProcessor);
        }
        if (request instanceof SqlRequest) {
            SqlRequest req = (SqlRequest) request;
            req.setAssertions(assertions);
            req.setExtract(extract);
            req.setJsr223PreProcessor(jsr223PreProcessor);
            req.setJsr223PostProcessor(jsr223PostProcessor);
        }
    }

    private void parseConstantTimer(Element element, ConstantTimer timer) {
        String delay = getElementTextByAttribute(element.elements(), "name", "ConstantTimer.delay");
        timer.setDelay(delay);
        timer.setEnable(Boolean.parseBoolean(element.attributeValue("enabled")));
    }

    private void parseHeader(Element element, List<KeyValue> headers) {

        parseArguments(element, headers, "Header.name", "Header.value");

    }


    private void parseJDBCProcessor(Element element, JDBCProcessor jdbcProcessor) {
        List<Element> tags = element.elements();
        jdbcProcessor.setDataSource(DATA_SOURCE);
        jdbcProcessor.setQuery(getElementTextByAttribute(tags, "name", "query"));
        jdbcProcessor.setVariableNames(getElementTextByAttribute(tags, "name", "variableNames"));
        jdbcProcessor.setResultVariable(getElementTextByAttribute(tags, "name", "resultSetHandler"));
        jdbcProcessor.setQueryTimeout(60000);
    }

    private void parseBeanShellProcessor(Element element, JSR223Processor jsr223Processor) {
        String testname = element.attributeValue("testname");
        List<Element> tags = element.elements();
        String script = "//" + testname + "\n" + getElementTextByAttribute(tags, "name", "script");
        jsr223Processor.setScript(script);
        jsr223Processor.setLanguage("beanshell");

    }

    private void parseJSONPostProcessor(Element element, List<ExtractJSONPath> ex_jsons) {
        List<Element> tags = element.elements();
        ExtractJSONPath jsonPath = new ExtractJSONPath();
        String name = getElementTextByAttribute(tags, "name", "JSONPostProcessor.referenceNames");
        String expression = getElementTextByAttribute(tags, "name", "JSONPostProcessor.jsonPathExprs");
        String matchNo = getElementTextByAttribute(tags, "name", "JSONPostProcessor.match_numbers");
        String defaultValues = getElementTextByAttribute(tags, "name", "JSONPostProcessor.defaultValues");
        jsonPath.setExpression(expression);
        jsonPath.setVariable(name);
        jsonPath.setMatch(matchNo);
        jsonPath.setDefaultValues(defaultValues);
        ex_jsons.add(jsonPath);
    }

    private void parseJsr223Assertion(Element element, List<AssertionJSR223> jsr223_list) {
        List<Element> tags = element.elements();
        String tagName = element.getName();
        String language = "";
        String script = "";
        if ("BeanShellAssertion".equals(tagName)) {
            language = "beanshell";
            script = getElementTextByAttribute(tags, "name", "BeanShellAssertion.query");
            //断言转换
            script = script.replace("Failure=true;", "AssertionResult.setFailure(true);");
            script = script.replace("Failure=false;", "AssertionResult.setFailure(false);");
            int index = script.indexOf("FailureMessage=");
            while (index > 0) {
                int msg_index = index + 15;
                int end_index = script.indexOf("\n", index) - 1;
                String old = "FailureMessage=" + script.substring(msg_index, end_index);
                String new_string = "AssertionResult.setFailureMessage(" + script.substring(msg_index, end_index) + ")";
                script = script.replace(old, new_string);
                index = script.indexOf("FailureMessage=", end_index);
            }

        } else {
            language = getElementTextByAttribute(tags, "name", "scriptLanguage");
            script = getElementTextByAttribute(tags, "name", "script");
        }
        Boolean enable = Boolean.parseBoolean(element.attributeValue("enabled"));

        AssertionJSR223 jsr223 = new AssertionJSR223();
        jsr223.setLanguage(language);
        jsr223.setScript(script);
        jsr223.setEnable(enable);
        jsr223_list.add(jsr223);
    }

    private void parseJsonPathAssertion(Element element, List<AssertionJsonPath> jsonPath) {
        List<Element> tags = element.elements();
        AssertionJsonPath json = new AssertionJsonPath();
        String json_path = getElementTextByAttribute(tags, "name", "JSON_PATH");
        String expect_value = getElementTextByAttribute(tags, "name", "EXPECTED_VALUE");
        Boolean json_validation = Boolean.parseBoolean(getElementTextByAttribute(tags, "name", "JSONVALIDATION"));
        Boolean invert = Boolean.parseBoolean(getElementTextByAttribute(tags, "name", "INVERT"));
        Boolean is_regex = Boolean.parseBoolean(getElementTextByAttribute(tags, "name", "ISREGEX"));
        if (!json_validation) {
            json.setCondition("EXISTS");
        } else if (is_regex) {
            json.setCondition("REGEX");
        } else {
            json.setCondition("EQUALS");
        }
        json.setInvert(invert);
        Boolean enable = Boolean.parseBoolean(element.attributeValue("enabled"));
        json.setEnable(enable);
        json.setExpression(json_path);
        json.setExpect(expect_value);
        jsonPath.add(json);
    }

    private void parseResponseAssertion(Element element, List<AssertionText> regex) {
        List<Element> tags = element.elements();
        String test_field = getElementTextByAttribute(tags, "name", "Assertion.test_field");
        String test_type = getElementTextByAttribute(tags, "name", "Assertion.test_type");
        String condition="SUBSTRING";
        if (!"2".equals(test_type) && !"16".equals(test_type) && !"8".equals(test_type)) {
            return;
        }
        if("2".equals(test_type)){
            condition="CONTAINS";
        }
        if("8".equals(test_type)){
            condition="EQUALS";
        }
        Boolean enable = Boolean.parseBoolean(element.attributeValue("enabled"));
        HashMap<String, String> feild_list = new HashMap<String, String>();
        feild_list.put("Assertion.response_data", "Response Data");
        feild_list.put("Assertion.response_code", "Response Code");
        feild_list.put("Assertion.response_headers", "Response Headers");
        List<Element> testStrings = getElementByAttribute(tags, "name", "Asserion.test_strings").elements();
        for (int i = 0; i < testStrings.size(); i++) {
            AssertionText reg = new AssertionText();
            reg.setCondition(condition);
            String expressions = testStrings.get(i).getText();
            reg.setValue(expressions);
            reg.setType("Text");
            reg.setSubject(feild_list.get(test_field));
            reg.setEnable(enable);
//            reg.setDescription(feild_list.get(test_field) + "" + expressions);
            regex.add(reg);
        }

    }

    /***
     * 解析样式
     * <collectionProp name="HeaderManager.headers">
     *             <elementProp name="" elementType="Header">
     *               <stringProp name="Header.name">Accept</stringProp>
     *               <stringProp name="Header.value">application/json</stringProp>
     *             </elementProp>
     *             <elementProp name="" elementType="Header">
     *               <stringProp name="Header.name">Accept-Encoding</stringProp>
     *               <stringProp name="Header.value">gzip, deflate,br</stringProp>
     *             </elementProp>
     * </collectionProp>
     * @param element
     * @param variables
     * @param name
     * @param value
     */
    private void parseArguments(Element element, List<KeyValue> variables, String name, String
            value) {
        Boolean enable = Boolean.parseBoolean(element.attributeValue("enabled"));
        List<Element> tags = element.element("collectionProp").elements("elementProp");

        for (int i = 0; i < tags.size(); i = i + 1) {
            String tagName = tags.get(i).getName();
            Element tag = tags.get(i);
            switch (tagName) {
                case "elementProp":
                    addArgument(tag, variables, name, value, enable);
                    break;
                default:
                    System.out.println("未知tag");
                    break;
            }
        }
    }

    private void addArgument(Element tag, List<KeyValue> variables, String name, String val, Boolean enable) {
        List<Element> subtags = tag.elements();
        String key = "";
        String value = "";
        String type = "text";
        for (int i = 0; i < subtags.size(); i++) {
            Element subtag = subtags.get(i);
            String attrName = subtag.attributeValue("name");
            if (attrName.equals(name)) {
                key = subtag.getText();
            }
            if (attrName.equals(val)) {
                value = subtag.getText();
            }
        }
        KeyValue kv = new KeyValue(key, value);
        kv.setEnable(enable);
        kv.setType(type);
        variables.add(kv);
    }

    private void parseUserParameters(Element element, Element hasTree, List<KeyValue> variables) {
        Boolean enable = Boolean.parseBoolean(element.attributeValue("enabled"));
        List<Element> tags = element.elements("collectionProp");
        Element nameTag = getElementByAttribute(tags, "name", "UserParameters.names");
        Element valueTag = getElementByAttribute(tags, "name", "UserParameters.thread_values");
        if (nameTag != null && valueTag != null) {
            List<Element> keys = nameTag.elements("stringProp");
            List<Element> values = valueTag.element("collectionProp").elements("stringProp");
            for (int i = 0; i < keys.size(); i++) {
                String key = keys.get(i).getText();
                String value = values.get(i).getText();
                KeyValue kv = new KeyValue(key, value);
                kv.setEnable(enable);
                variables.add(kv);
            }

        }
    }

    private String getElementTextByAttribute(List<Element> list, String key, String value) {
        String text = null;
        for (int i = 0; i < list.size(); i = i + 1) {
            Element ele = list.get(i);
            String v = ele.attributeValue(key);
            if (value.equals(v)) {
                text = ele.getText();
                break;
            }
        }
        return text;
    }

    private Element getElementByAttribute(List<Element> list, String key, String value) {
        for (int i = 0; i < list.size(); i = i + 1) {
            Element ele = list.get(i);
            String v = ele.attributeValue(key);
            if (value.equals(v)) {
                return ele;
            }
        }
        return null;
    }


    public static void main(String args[]) {
        JmeterParser jp = new JmeterParser();
        jp.parse2();
    }

}
