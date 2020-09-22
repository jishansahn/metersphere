package io.metersphere.api.dto.parse.jmx;

import org.json.JSONObject;
import org.json.XML;

public class test {
    public static String getJsonString() {
        String res = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<jmeterTestPlan version=\"1.2\" properties=\"5.0\" jmeter=\"5.2.1\">\n" +
                "  <hashTree>\n" +
                "    <TestPlan guiclass=\"TestPlanGui\" testclass=\"TestPlan\" testname=\"3次\" enabled=\"true\">\n" +
                "      <boolProp name=\"TestPlan.functional_mode\">false</boolProp>\n" +
                "      <boolProp name=\"TestPlan.serialize_threadgroups\">true</boolProp>\n" +
                "      <boolProp name=\"TestPlan.tearDown_on_shutdown\">true</boolProp>\n" +
                "      <stringProp name=\"TestPlan.comments\"/>\n" +
                "      <stringProp name=\"TestPlan.user_define_classpath\"/>\n" +
                "      <elementProp name=\"TestPlan.user_defined_variables\" elementType=\"Arguments\" guiclass=\"ArgumentsPanel\" testclass=\"Arguments\" testname=\"User Defined Variables\" enabled=\"true\">\n" +
                "        <collectionProp name=\"Arguments.arguments\"/>\n" +
                "      </elementProp>\n" +
                "    </TestPlan>\n" +
                "    <hashTree>\n" +
                "      <ThreadGroup guiclass=\"ThreadGroupGui\" testclass=\"ThreadGroup\" testname=\"登录\" enabled=\"true\">\n" +
                "        <intProp name=\"ThreadGroup.num_threads\">1</intProp>\n" +
                "        <intProp name=\"ThreadGroup.ramp_time\">1</intProp>\n" +
                "        <longProp name=\"ThreadGroup.delay\">0</longProp>\n" +
                "        <longProp name=\"ThreadGroup.duration\">0</longProp>\n" +
                "        <stringProp name=\"ThreadGroup.on_sample_error\">continue</stringProp>\n" +
                "        <boolProp name=\"ThreadGroup.scheduler\">false</boolProp>\n" +
                "        <elementProp name=\"ThreadGroup.main_controller\" elementType=\"LoopController\" guiclass=\"LoopControlPanel\" testclass=\"LoopController\" testname=\"Loop Controller\" enabled=\"true\">\n" +
                "          <boolProp name=\"LoopController.continue_forever\">false</boolProp>\n" +
                "          <stringProp name=\"LoopController.loops\">1</stringProp>\n" +
                "        </elementProp>\n" +
                "      </ThreadGroup>\n" +
                "      <hashTree>\n" +
                "        <Arguments guiclass=\"ArgumentsPanel\" testclass=\"Arguments\" testname=\"登录 Variables\" enabled=\"true\">\n" +
                "          <collectionProp name=\"Arguments.arguments\">\n" +
                "            <elementProp name=\"login_name\" elementType=\"Argument\">\n" +
                "              <stringProp name=\"Argument.name\">login_name</stringProp>\n" +
                "              <stringProp name=\"Argument.value\">jishanshan</stringProp>\n" +
                "              <stringProp name=\"Argument.desc\"/>\n" +
                "              <stringProp name=\"Argument.metadata\">=</stringProp>\n" +
                "            </elementProp>\n" +
                "            <elementProp name=\"password\" elementType=\"Argument\">\n" +
                "              <stringProp name=\"Argument.name\">password</stringProp>\n" +
                "              <stringProp name=\"Argument.value\">jishanshan</stringProp>\n" +
                "              <stringProp name=\"Argument.desc\"/>\n" +
                "              <stringProp name=\"Argument.metadata\">=</stringProp>\n" +
                "            </elementProp>\n" +
                "            <elementProp name=\"projectId\" elementType=\"Argument\">\n" +
                "              <stringProp name=\"Argument.name\">projectId</stringProp>\n" +
                "              <stringProp name=\"Argument.value\">23</stringProp>\n" +
                "              <stringProp name=\"Argument.desc\"/>\n" +
                "              <stringProp name=\"Argument.metadata\">=</stringProp>\n" +
                "            </elementProp>\n" +
                "          </collectionProp>\n" +
                "        </Arguments>\n" +
                "        <hashTree/>\n" +
                "        <CookieManager guiclass=\"CookiePanel\" testclass=\"CookieManager\" testname=\"登录\" enabled=\"true\">\n" +
                "          <collectionProp name=\"CookieManager.cookies\"/>\n" +
                "          <boolProp name=\"CookieManager.clearEachIteration\">false</boolProp>\n" +
                "          <boolProp name=\"CookieManager.controlledByThreadGroup\">false</boolProp>\n" +
                "        </CookieManager>\n" +
                "        <hashTree/>\n" +
                "        <JDBCDataSource guiclass=\"TestBeanGUI\" testclass=\"JDBCDataSource\" testname=\"mysqlJDBCDataSource\" enabled=\"true\">\n" +
                "          <boolProp name=\"autocommit\">true</boolProp>\n" +
                "          <boolProp name=\"keepAlive\">true</boolProp>\n" +
                "          <boolProp name=\"preinit\">false</boolProp>\n" +
                "          <stringProp name=\"dataSource\">mysql</stringProp>\n" +
                "          <stringProp name=\"dbUrl\">jdbc:mysql:replication://address=(protocol=tcp)(type=master)(host=ta3)(port=6446),address=(protocol=tcp)(type=master)(host=ta1)(port=6446),address=(protocol=tcp)(type=master)(host=ta2)(port=6446)/ta?autoReconnect=true&amp;useUnicode=true&amp;useSSL=false&amp;socketTimeout=60000&amp;retriesAllDown=3&amp;connectTimeout=10000</stringProp>\n" +
                "          <stringProp name=\"driver\">com.mysql.jdbc.Driver</stringProp>\n" +
                "          <stringProp name=\"username\">ta</stringProp>\n" +
                "          <stringProp name=\"password\">ADe47iLoFtR4eERE</stringProp>\n" +
                "          <stringProp name=\"poolMax\">10</stringProp>\n" +
                "          <stringProp name=\"timeout\">10000</stringProp>\n" +
                "          <stringProp name=\"connectionAge\">5000</stringProp>\n" +
                "          <stringProp name=\"trimInterval\">60000</stringProp>\n" +
                "          <stringProp name=\"transactionIsolation\">DEFAULT</stringProp>\n" +
                "          <stringProp name=\"checkQuery\"/>\n" +
                "          <stringProp name=\"initQuery\"/>\n" +
                "          <stringProp name=\"connectionProperties\"/>\n" +
                "        </JDBCDataSource>\n" +
                "        <hashTree/>\n" +
                "        <JDBCDataSource guiclass=\"TestBeanGUI\" testclass=\"JDBCDataSource\" testname=\"mysql_localhostJDBCDataSource\" enabled=\"true\">\n" +
                "          <boolProp name=\"autocommit\">true</boolProp>\n" +
                "          <boolProp name=\"keepAlive\">true</boolProp>\n" +
                "          <boolProp name=\"preinit\">false</boolProp>\n" +
                "          <stringProp name=\"dataSource\">mysql_localhost</stringProp>\n" +
                "          <stringProp name=\"dbUrl\">jdbc:mysql://localhost:3306?useUnicode=true&amp;characterEncoding=utf8&amp;allowMultiQueries=true&amp;serverTimezone=UTC</stringProp>\n" +
                "          <stringProp name=\"driver\">com.mysql.jdbc.Driver</stringProp>\n" +
                "          <stringProp name=\"username\">root</stringProp>\n" +
                "          <stringProp name=\"password\">123456</stringProp>\n" +
                "          <stringProp name=\"poolMax\">10</stringProp>\n" +
                "          <stringProp name=\"timeout\">10000</stringProp>\n" +
                "          <stringProp name=\"connectionAge\">5000</stringProp>\n" +
                "          <stringProp name=\"trimInterval\">60000</stringProp>\n" +
                "          <stringProp name=\"transactionIsolation\">DEFAULT</stringProp>\n" +
                "          <stringProp name=\"checkQuery\"/>\n" +
                "          <stringProp name=\"initQuery\"/>\n" +
                "          <stringProp name=\"connectionProperties\"/>\n" +
                "        </JDBCDataSource>\n" +
                "        <hashTree/>\n" +
                "        <HTTPSamplerProxy guiclass=\"HttpTestSampleGui\" testclass=\"HTTPSamplerProxy\" testname=\"登录\" enabled=\"true\">\n" +
                "          <stringProp name=\"HTTPSampler.domain\">ta_test.thinkingdata.cn</stringProp>\n" +
                "          <stringProp name=\"HTTPSampler.protocol\">https</stringProp>\n" +
                "          <stringProp name=\"HTTPSampler.path\">//v1/login/auth?login_name=${login_name}&amp;password=${password}</stringProp>\n" +
                "          <stringProp name=\"HTTPSampler.method\">POST</stringProp>\n" +
                "          <stringProp name=\"HTTPSampler.contentEncoding\">UTF-8</stringProp>\n" +
                "          <stringProp name=\"HTTPSampler.port\"/>\n" +
                "          <stringProp name=\"HTTPSampler.connect_timeout\">60000</stringProp>\n" +
                "          <boolProp name=\"HTTPSampler.use_keepalive\">true</boolProp>\n" +
                "          <elementProp name=\"HTTPsampler.Arguments\" elementType=\"Arguments\" guiclass=\"HTTPArgumentsPanel\" testclass=\"Arguments\" enabled=\"true\">\n" +
                "            <collectionProp name=\"Arguments.arguments\">\n" +
                "              <elementProp name=\"login_name\" elementType=\"HTTPArgument\">\n" +
                "                <boolProp name=\"HTTPArgument.always_encode\">true</boolProp>\n" +
                "                <boolProp name=\"HTTPArgument.use_equals\">true</boolProp>\n" +
                "                <stringProp name=\"Argument.name\">login_name</stringProp>\n" +
                "                <stringProp name=\"Argument.value\">${login_name}</stringProp>\n" +
                "                <stringProp name=\"Argument.metadata\">=</stringProp>\n" +
                "              </elementProp>\n" +
                "              <elementProp name=\"password\" elementType=\"HTTPArgument\">\n" +
                "                <boolProp name=\"HTTPArgument.always_encode\">true</boolProp>\n" +
                "                <boolProp name=\"HTTPArgument.use_equals\">true</boolProp>\n" +
                "                <stringProp name=\"Argument.name\">password</stringProp>\n" +
                "                <stringProp name=\"Argument.value\">${password}</stringProp>\n" +
                "                <stringProp name=\"Argument.metadata\">=</stringProp>\n" +
                "              </elementProp>\n" +
                "            </collectionProp>\n" +
                "          </elementProp>\n" +
                "          <boolProp name=\"HTTPSampler.postBodyRaw\">true</boolProp>\n" +
                "          <elementProp name=\"HTTPsampler.Arguments\" elementType=\"Arguments\" guiclass=\"HTTPArgumentsPanel\" testclass=\"Arguments\" enabled=\"true\">\n" +
                "            <collectionProp name=\"Arguments.arguments\">\n" +
                "              <elementProp name=\"\" elementType=\"HTTPArgument\">\n" +
                "                <boolProp name=\"HTTPArgument.always_encode\">false</boolProp>\n" +
                "                <boolProp name=\"HTTPArgument.use_equals\">true</boolProp>\n" +
                "                <stringProp name=\"Argument.value\"/>\n" +
                "                <stringProp name=\"Argument.metadata\">=</stringProp>\n" +
                "              </elementProp>\n" +
                "            </collectionProp>\n" +
                "          </elementProp>\n" +
                "        </HTTPSamplerProxy>\n" +
                "        <hashTree>\n" +
                "          <HeaderManager guiclass=\"HeaderPanel\" testclass=\"HeaderManager\" testname=\"登录 Headers\" enabled=\"true\">\n" +
                "            <collectionProp name=\"HeaderManager.headers\">\n" +
                "              <elementProp name=\"\" elementType=\"Header\">\n" +
                "                <stringProp name=\"Header.name\">Content-Type</stringProp>\n" +
                "                <stringProp name=\"Header.value\">application/x-www-form-urlencoded</stringProp>\n" +
                "              </elementProp>\n" +
                "            </collectionProp>\n" +
                "          </HeaderManager>\n" +
                "          <hashTree/>\n" +
                "          <ResponseAssertion guiclass=\"AssertionGui\" testclass=\"ResponseAssertion\" testname=\"Response Data contains: &quot;return_message&quot;:&quot;success&quot;\" enabled=\"true\">\n" +
                "            <stringProp name=\"Assertion.test_field\">Assertion.response_data</stringProp>\n" +
                "            <boolProp name=\"Assertion.assume_success\">false</boolProp>\n" +
                "            <intProp name=\"Assertion.test_type\">2</intProp>\n" +
                "            <stringProp name=\"Assertion.custom_message\"/>\n" +
                "            <collectionProp name=\"Asserion.test_strings\">\n" +
                "              <stringProp name=\"7639\">.*&quot;return_message&quot;:&quot;success&quot;.*</stringProp>\n" +
                "            </collectionProp>\n" +
                "          </ResponseAssertion>\n" +
                "          <hashTree/>\n" +
                "        </hashTree>\n" +
                "        <HTTPSamplerProxy guiclass=\"HttpTestSampleGui\" testclass=\"HTTPSamplerProxy\" testname=\"事件list\" enabled=\"true\">\n" +
                "          <stringProp name=\"HTTPSampler.domain\">ta_test.thinkingdata.cn</stringProp>\n" +
                "          <stringProp name=\"HTTPSampler.protocol\">https</stringProp>\n" +
                "          <stringProp name=\"HTTPSampler.path\">//v1/ta/event/catalog/listEvent</stringProp>\n" +
                "          <stringProp name=\"HTTPSampler.method\">GET</stringProp>\n" +
                "          <stringProp name=\"HTTPSampler.contentEncoding\">UTF-8</stringProp>\n" +
                "          <stringProp name=\"HTTPSampler.port\"/>\n" +
                "          <stringProp name=\"HTTPSampler.connect_timeout\">60000</stringProp>\n" +
                "          <boolProp name=\"HTTPSampler.use_keepalive\">true</boolProp>\n" +
                "          <elementProp name=\"HTTPsampler.Arguments\" elementType=\"Arguments\" guiclass=\"HTTPArgumentsPanel\" testclass=\"Arguments\" enabled=\"true\">\n" +
                "            <collectionProp name=\"Arguments.arguments\">\n" +
                "              <elementProp name=\"projectId\" elementType=\"HTTPArgument\">\n" +
                "                <boolProp name=\"HTTPArgument.always_encode\">true</boolProp>\n" +
                "                <boolProp name=\"HTTPArgument.use_equals\">true</boolProp>\n" +
                "                <stringProp name=\"Argument.name\">projectId</stringProp>\n" +
                "                <stringProp name=\"Argument.value\">${projectId}</stringProp>\n" +
                "                <stringProp name=\"Argument.metadata\">=</stringProp>\n" +
                "              </elementProp>\n" +
                "            </collectionProp>\n" +
                "          </elementProp>\n" +
                "          <elementProp name=\"HTTPsampler.Files\" elementType=\"HTTPFileArgs\">\n" +
                "            <collectionProp name=\"HTTPFileArgs.files\"/>\n" +
                "          </elementProp>\n" +
                "        </HTTPSamplerProxy>\n" +
                "        <hashTree>\n" +
                "          <JSONPathAssertion guiclass=\"JSONPathAssertionGui\" testclass=\"JSONPathAssertion\" testname=\"$.return_message expect: success\" enabled=\"true\">\n" +
                "            <stringProp name=\"JSON_PATH\">$.return_message</stringProp>\n" +
                "            <stringProp name=\"EXPECTED_VALUE\">success</stringProp>\n" +
                "            <boolProp name=\"JSONVALIDATION\">true</boolProp>\n" +
                "            <boolProp name=\"EXPECT_NULL\">false</boolProp>\n" +
                "            <boolProp name=\"INVERT\">false</boolProp>\n" +
                "            <boolProp name=\"ISREGEX\">true</boolProp>\n" +
                "          </JSONPathAssertion>\n" +
                "          <hashTree/>\n" +
                "        </hashTree>\n" +
                "        <eu.luminis.jmeter.wssampler.RequestResponseWebSocketSampler guiclass=\"eu.luminis.jmeter.wssampler.RequestResponseWebSocketSamplerGui\" testclass=\"eu.luminis.jmeter.wssampler.RequestResponseWebSocketSampler\" testname=\"SSSSS\" enabled=\"true\">\n" +
                "          <boolProp name=\"createNewConnection\">true</boolProp>\n" +
                "          <boolProp name=\"TLS\">true</boolProp>\n" +
                "          <stringProp name=\"server\">ta_test.thinkingdata.cn</stringProp>\n" +
                "          <stringProp name=\"port\">443</stringProp>\n" +
                "          <stringProp name=\"path\">/websocket/query</stringProp>\n" +
                "          <stringProp name=\"connectTimeout\">20000</stringProp>\n" +
                "          <boolProp name=\"binaryPayload\">false</boolProp>\n" +
                "          <stringProp name=\"requestData\">[&quot;data&quot;,{&quot;requestId&quot;:&quot;WS_SCATTER@@rTRWNcXT&quot;,&quot;projectId&quot;:319,&quot;eventModel&quot;:7,&quot;qp&quot;:{&quot;events&quot;:[{&quot;analysis&quot;:&quot;&quot;,&quot;analysisDesc&quot;:&quot;&quot;,&quot;customEvent&quot;:&quot;EnterActivities.iswin.A110+EnterActivities.iswin.A112&quot;,&quot;customFilters&quot;:[],&quot;formulation&quot;:{&quot;formulationDeps&quot;:[{&quot;event&quot;:{&quot;eventDesc&quot;:&quot;参与活动&quot;,&quot;eventName&quot;:&quot;EnterActivities&quot;,&quot;eventType&quot;:&quot;event&quot;},&quot;property&quot;:{&quot;columnName&quot;:&quot;iswin&quot;,&quot;columnDesc&quot;:&quot;是否胜利&quot;,&quot;tableType&quot;:&quot;0&quot;},&quot;quota&quot;:{&quot;quotaName&quot;:&quot;A110&quot;,&quot;quotaDesc&quot;:&quot;为假数&quot;}},{&quot;event&quot;:{&quot;eventDesc&quot;:&quot;参与活动&quot;,&quot;eventName&quot;:&quot;EnterActivities&quot;,&quot;eventType&quot;:&quot;event&quot;},&quot;property&quot;:{&quot;columnName&quot;:&quot;iswin&quot;,&quot;columnDesc&quot;:&quot;是否胜利&quot;,&quot;tableType&quot;:&quot;0&quot;},&quot;quota&quot;:{&quot;quotaName&quot;:&quot;A112&quot;,&quot;quotaDesc&quot;:&quot;为空数&quot;}}]},&quot;eventId&quot;:&quot;&quot;,&quot;eventName&quot;:&quot;test&quot;,&quot;eventDesc&quot;:&quot;undefined.EnterActivities.参与活动.iswin.是否胜利.A110.为假数,undefined.EnterActivities.参与活动.iswin.是否胜利.A112.为空数&quot;,&quot;eventType&quot;:&quot;event,event&quot;,&quot;eventNameDisplay&quot;:&quot;&quot;,&quot;filts&quot;:[],&quot;quota&quot;:&quot;&quot;,&quot;quotaIntervalArr&quot;:&quot;&quot;,&quot;intervalType&quot;:&quot;def&quot;,&quot;relation&quot;:1,&quot;type&quot;:1}],&quot;eventView&quot;:{&quot;projectId&quot;:319,&quot;startTime&quot;:&quot;2020-08-01 00:00:00&quot;,&quot;timeParticleSize&quot;:&quot;T1&quot;,&quot;endTime&quot;:&quot;2020-08-31 23:59:59&quot;,&quot;graphShape&quot;:&quot;L4&quot;,&quot;recentDay&quot;:&quot;&quot;,&quot;groupBy&quot;:[],&quot;comparedStartTime&quot;:&quot;&quot;,&quot;comparedEndTime&quot;:&quot;&quot;,&quot;comparedRecentDay&quot;:&quot;&quot;,&quot;comparedByTime&quot;:false,&quot;startToNow&quot;:-1,&quot;comparedType&quot;:&quot;&quot;,&quot;compareStartToNow&quot;:-1,&quot;uiCommonConfig&quot;:{&quot;startToNow&quot;:-1,&quot;compareStartToNow&quot;:-1}}},&quot;useCache&quot;:false,&quot;querySource&quot;:&quot;module&quot;}]</stringProp>\n" +
                "          <stringProp name=\"readTimeout\">6000</stringProp>\n" +
                "          <boolProp name=\"loadDataFromFile\">false</boolProp>\n" +
                "          <stringProp name=\"dataFile\"/>\n" +
                "        </eu.luminis.jmeter.wssampler.RequestResponseWebSocketSampler>\n" +
                "        <hashTree/>\n" +
                "        <JDBCSampler guiclass=\"TestBeanGUI\" testclass=\"JDBCSampler\" testname=\"ss\" enabled=\"true\">\n" +
                "          <stringProp name=\"dataSource\">mysql</stringProp>\n" +
                "          <stringProp name=\"query\">SELECT open_id from ta.auth_user where login_name=&apos;jishanshan&apos;</stringProp>\n" +
                "          <stringProp name=\"queryTimeout\">60000</stringProp>\n" +
                "          <stringProp name=\"resultVariable\">open_id2</stringProp>\n" +
                "          <stringProp name=\"variableNames\">open_id</stringProp>\n" +
                "          <stringProp name=\"queryArguments\"/>\n" +
                "          <stringProp name=\"queryArgumentsTypes\"/>\n" +
                "          <stringProp name=\"resultSetMaxRows\"/>\n" +
                "          <stringProp name=\"resultSetHandler\">Store as String</stringProp>\n" +
                "          <stringProp name=\"queryType\">Callable Statement</stringProp>\n" +
                "        </JDBCSampler>\n" +
                "        <hashTree/>\n" +
                "        <JDBCSampler guiclass=\"TestBeanGUI\" testclass=\"JDBCSampler\" testname=\"sss\" enabled=\"true\">\n" +
                "          <stringProp name=\"dataSource\">mysql_localhost</stringProp>\n" +
                "          <stringProp name=\"query\">select name from api_test.api_test_project</stringProp>\n" +
                "          <stringProp name=\"queryTimeout\">60000</stringProp>\n" +
                "          <stringProp name=\"resultVariable\">name2</stringProp>\n" +
                "          <stringProp name=\"variableNames\">name</stringProp>\n" +
                "          <stringProp name=\"queryArguments\"/>\n" +
                "          <stringProp name=\"queryArgumentsTypes\"/>\n" +
                "          <stringProp name=\"resultSetMaxRows\"/>\n" +
                "          <stringProp name=\"resultSetHandler\">Store as String</stringProp>\n" +
                "          <stringProp name=\"queryType\">Callable Statement</stringProp>\n" +
                "        </JDBCSampler>\n" +
                "        <hashTree/>\n" +
                "      </hashTree>\n" +
                "    </hashTree>\n" +
                "  </hashTree>\n" +
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
