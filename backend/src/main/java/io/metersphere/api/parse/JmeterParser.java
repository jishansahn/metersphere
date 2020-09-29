package io.metersphere.api.parse;

import io.metersphere.api.dto.ApiTestImportRequest;
import io.metersphere.api.dto.parse.ApiImport;
import io.metersphere.api.dto.scenario.KeyValue;
import io.metersphere.api.dto.scenario.Scenario;
import io.metersphere.api.dto.scenario.assertions.AssertionJsonPath;
import io.metersphere.api.dto.scenario.assertions.AssertionRegex;
import io.metersphere.api.dto.scenario.assertions.Assertions;
import io.metersphere.api.dto.scenario.controller.IfController;
import io.metersphere.api.dto.scenario.processor.JSR223PostProcessor;
import io.metersphere.api.dto.scenario.processor.JSR223PreProcessor;
import io.metersphere.api.dto.scenario.processor.JSR223Processor;
import io.metersphere.api.dto.scenario.request.HttpRequest;
import io.metersphere.api.dto.scenario.request.Request;
import io.metersphere.api.dto.scenario.request.WebsocketRequest;
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
        parseSamplerHashTree(element, hashTree, scenario);
    }

    private void parseSamplerHashTree(Element element, Element hashTree, Scenario scenario) {
        List<KeyValue> variables = scenario.getVariables();
        List<KeyValue> headers = scenario.getHeaders();
        List<Request> requests = scenario.getRequests();

        List<Element> tags = hashTree.elements();
        for (int i = 0; i < tags.size(); i = i + 2) {
            String tagName = tags.get(i).getName();
            Element subHashTree = tags.get(i + 1);
            KeyValue kv = new KeyValue();
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
                    parseHttpRequest(tags.get(i), tags.get(i + 1), httpRequest,element);
                    requests.add(httpRequest);
                    break;
                case "eu.luminis.jmeter.wssampler.OpenWebSocketSampler":
                    WebsocketRequest websocketOpen = new WebsocketRequest();
                    break;
                case "eu.luminis.jmeter.wssampler.RequestResponseWebSocketSampler":
                    WebsocketRequest websocketRequest = new WebsocketRequest();
                    break;
                case "eu.luminis.jmeter.wssampler.CloseWebSocketSampler":
                    WebsocketRequest websocketClose = new WebsocketRequest();
                    break;
                case "GenericController":
                    parseSamplerHashTree(tags.get(i), subHashTree, scenario);
                case "IfController":
                    parseSamplerHashTree(tags.get(i), subHashTree, scenario);
                default:
                    System.out.println("未知tag");
                    break;
            }
        }
    }

    private void parseHeader(Element element, List<KeyValue> headers) {
        KeyValue kv = new KeyValue();
        kv.setEnable(Boolean.parseBoolean(element.attributeValue("enabled")));
        headers.add(kv);
        parseArguments(element, headers, "Header.name", "Header.value");
    }

    private void parseHttpRequest(Element element, Element hasTree, HttpRequest httpRequest,Element parent) {
        List<Element> tags = element.elements();
        String testname = element.attributeValue("testname");
        if ("GenericController".equals(parent.getName())) {
            testname = testname + element.attributeValue("testname");
        }
        httpRequest.setName(testname);
        if("IfController".equals(parent.getName())) {
            IfController ifController=new IfController();
//            ${__jexl3(${233}&gt;s)}
//            ${__groovy(&quot;${status}&quot;==&quot;running&quot;,)}
            ifController.setEnable(Boolean.parseBoolean(parent.attributeValue("enabled")));
            String condition=parent.element("IfController.condition").getText();

        }
        httpRequest.setMethod(getElementTextByAttribute(tags, "name", "HTTPSampler.method"));
        httpRequest.setPath(getElementTextByAttribute(tags, "name", "HTTPSampler.path"));
        httpRequest.setEnable(Boolean.parseBoolean(element.attributeValue("enabled")));


        httpRequest.setUseEnvironment(Boolean.TRUE);
        List<KeyValue> parameters = new ArrayList<>();
        Element paramTag = getElementByAttribute(tags, "name", "HTTPsampler.Arguments");
        if (paramTag != null)
            parseArguments(paramTag, parameters, "Argument.name", "Argument.value");
        httpRequest.setParameters(parameters);

        List<Element> subTags = hasTree.elements();
        Assertions assertions = new Assertions();
        List<AssertionRegex> regex = new ArrayList<>();
        List<AssertionJsonPath> jsonPath = new ArrayList<>();
        List<KeyValue> headers = new ArrayList<>();
        JSR223PreProcessor jsr223PreProcessor = new JSR223PreProcessor();
        JSR223PostProcessor jsr223PostProcessor = new JSR223PostProcessor();
        for (int i = 0; i < subTags.size(); i = i + 2) {
            String tagName = subTags.get(i).getName();
            Element subTag = subTags.get(i);
            switch (tagName) {
                case "ResponseAssertion":
                    parseResponseAssertion(subTag, regex);
                    break;
                case "HeaderManager":
                    parseHeader(subTag, headers);
                    break;
//                case "JDBCPreProcessor":
//                    System.out.println("s");
//                    break;
//                case "JDBCPostProcessor":
//                    System.out.println("ss");
//                    break;
                case "JSONPathAssertion":
                    parseJsonPathAssertion(subTag, jsonPath);
                    break;
                case "BeanShellAssertion":
                    break;
                case "BeanShellPreProcessor":
                    parseBeanShellProcessor(subTag, jsr223PreProcessor);
                    break;
                case "BeanShellPostProcessor":
                    parseBeanShellProcessor(subTag, jsr223PostProcessor);
                    break;
                default:
                    System.out.println("未知tag");
                    break;
            }
        }
        assertions.setRegex(regex);
        assertions.setJsonPath(jsonPath);
        httpRequest.setAssertions(assertions);
        httpRequest.setJsr223PreProcessor(jsr223PreProcessor);
        httpRequest.setJsr223PostProcessor(jsr223PostProcessor);
    }

    private void parseBeanShellProcessor(Element element, JSR223Processor jsr223Processor) {
        String testname = element.attributeValue("testname");
        List<Element> tags = element.elements();
        String script = "//" + testname + "\n" + getElementTextByAttribute(tags, "name", "script");
        jsr223Processor.setScript(script);
        jsr223Processor.setLanguage("beanshell");

    }

    private void parseJsonPathAssertion(Element element, List<AssertionJsonPath> jsonPath) {
        List<Element> tags = element.elements();
        AssertionJsonPath json = new AssertionJsonPath();
        String json_path = getElementTextByAttribute(tags, "name", "JSON_PATH");
        String expect_value = getElementTextByAttribute(tags, "name", "EXPECTED_VALUE");
        json.setExpression(json_path);
        json.setExpect(expect_value);
        json.setDescription(json_path + " expect: " + expect_value);
        jsonPath.add(json);

    }

    private void parseResponseAssertion(Element element, List<AssertionRegex> regex) {
        List<Element> tags = element.elements();
        String test_field = getElementTextByAttribute(tags, "name", "Assertion.test_field");
        String test_type = getElementTextByAttribute(tags, "name", "Assertion.test_type");
        if (!"2".equals(test_type) && !"16".equals(test_type)) {
            return;
        }
        HashMap<String, String> feild_list = new HashMap<String, String>();
        feild_list.put("Assertion.response_data", "Response Data");
        feild_list.put("Assertion.response_code", "Response Code");
        feild_list.put("Assertion.response_headers", "Response Headers");
        List<Element> testStrings = getElementByAttribute(tags, "name", "Asserion.test_strings").elements();
        for (int i = 0; i < testStrings.size(); i++) {
            AssertionRegex reg = new AssertionRegex();
            String expressions = ".*".concat(testStrings.get(i).getText()).concat(".*");
            reg.setExpression(expressions);
            reg.setType("Regex");
            reg.setSubject(feild_list.get(test_field));
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
        List<Element> tags = element.element("collectionProp").elements("elementProp");

        for (int i = 0; i < tags.size(); i = i + 1) {
            String tagName = tags.get(i).getName();
            Element tag = tags.get(i);
            switch (tagName) {
                case "elementProp":
                    addArgument(tag, variables, name, value);
                    break;
                default:
                    System.out.println("未知tag");
                    break;
            }
        }
    }

    private void addArgument(Element tag, List<KeyValue> variables, String name, String val) {
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
        kv.setEnable(Boolean.TRUE);
        kv.setType(type);
        variables.add(kv);
    }

    private void parseUserParameters(Element element, Element hasTree, List<KeyValue> variables) {
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
                kv.setEnable(Boolean.TRUE);
                variables.add(kv);
            }

        }
    }

    private String getElementTextByAttribute(List<Element> list, String key, String value) {
        String text = "";
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
