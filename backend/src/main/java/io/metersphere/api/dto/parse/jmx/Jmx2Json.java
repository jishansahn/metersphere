package io.metersphere.api.dto.parse.jmx;

import org.json.JSONObject;
import org.json.XML;

public class Jmx2Json {
    public static String getJsonString() {
        String res = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<jmeterTestPlan version=\"1.2\" properties=\"5.0\" jmeter=\"5.2.1\">\n" +
                "    <TestPlan guiclass=\"TestPlanGui\" testclass=\"TestPlan\" testname=\"3次\" enabled=\"true\">abc</TestPlan>\n" +
                "    <boolProp name=\"TestPlan.functional_mode\">false</boolProp>\n" +
                "</jmeterTestPlan>";
        // 将xml转为json
        JSONObject xmlJSONObj = XML.toJSONObject(res);
//        JSONObject jsonObject = xmlJSONObj.getJSONObject("jmeterTestPlan");
        // 设置缩进
        return xmlJSONObj.toString(4);
    }
    public static void main(String [] args){
        String str=Jmx2Json.getJsonString();
        System.out.println(str);
    }
}
