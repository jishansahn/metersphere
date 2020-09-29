<template>
  <el-form :model="request" :rules="rules" ref="request" label-width="100px">
    <el-form-item :label="$t('api_test.request.websocket.mode')" prop="mode">
      <el-radio-group v-model="request.mode" :label="$t('api_test.request.websocket.mode')" prop="mode">
        <el-radio :label="item.value" :key="item.value" v-for="item in requestMode">{{item.label}}</el-radio>
      </el-radio-group>
    </el-form-item>

    <el-form-item :label="$t('api_test.request.name')" prop="name">
      <el-input v-model="request.name" maxlength="300" show-word-limit/>
    </el-form-item>

    <el-form-item :label="$t('api_test.request.websocket.statusCode')" v-if="(request.mode===0)">
      <el-input type="number" v-model="request.statusCode" />
    </el-form-item>

    <el-form-item v-if="(request.mode===0)" :label="$t('api_test.request.websocket.read_timeout')" prop="readTimeout">
      <el-input-number :disabled="isReadOnly" size="mini" v-model="request.readTimeout" :placeholder="$t('commons.millisecond')" :max="1000*10000000" :min="0"/>
    </el-form-item>

    <el-form-item v-if="request.mode===2" label="创建新连接" >
      <el-radio-group v-model="request.createNewConnection" >
        <el-radio :label="item.value" :key="item.value" v-for="item in isCreateNewConnection">{{item.label}}</el-radio>
      </el-radio-group>
    </el-form-item>

    <el-form-item v-if="(request.mode===2 && request.createNewConnection) || (request.mode===1)" label="Connection" name="connection">
      <el-form-item :label="$t('api_test.request.websocket.protocol')" prop="protocol">
        <el-select v-model="request.protocol">
          <el-option label="wss://" :value="protocols.WSS"/>
          <el-option label="ws://" :value="protocols.WS"/>
        </el-select>
      </el-form-item>
      <el-form-item :label="$t('api_test.request.websocket.domain')" prop="server">
        <el-input :disabled="isReadOnly" v-model="request.server" maxlength="50"
                  :placeholder="$t('api_test.request.websocket.domain_description')" clearable>
        </el-input>
      </el-form-item>
      <el-form-item :label="$t('api_test.request.path')" prop="path">
        <el-input :disabled="isReadOnly" v-model="request.path" maxlength="500"
                  :placeholder="$t('api_test.request.path_description')" @change="pathChange" clearable>
        </el-input>
      </el-form-item>
      <el-form-item :label="$t('api_test.request.websocket.connection_timeout')" prop="connectionTimeout">
        <el-input-number :disabled="isReadOnly" size="mini" v-model="request.connectTimeout" :placeholder="$t('commons.millisecond')" :max="1000*10000000" :min="0"/>
      </el-form-item>
    </el-form-item>
    <el-form-item>
      <el-switch
        v-model="request.useEnvironment"
        :active-text="$t('api_test.request.refer_to_environment')">
      </el-switch>
    </el-form-item>

    <el-button :disabled="!request.enable || !scenario.enable || isReadOnly" class="debug-button" size="small" type="primary" @click="runDebug">{{$t('api_test.request.debug')}}</el-button>
    <el-tabs v-if="request.mode===2" v-model="activeName">
      <el-tab-pane :label="$t('api_test.request.body')" name="body" >
        <el-input :rows="12"
                  type="textarea"
                  v-model="request.requestData" @blur="bodyBlur"></el-input>

        <el-form-item :label="$t('api_test.request.websocket.read_timeout')" prop="readTimeout">
          <el-input-number :disabled="isReadOnly" size="mini" v-model="request.readTimeout" :placeholder="$t('commons.millisecond')" :max="1000*10000000" :min="0"/>
        </el-form-item>
      </el-tab-pane>
      <el-tab-pane :label="$t('api_test.request.assertions.label')" name="assertions">
        <ms-api-assertions :is-read-only="isReadOnly" :assertions="request.assertions"/>
      </el-tab-pane>
      <el-tab-pane :label="$t('api_test.request.extract.label')" name="extract">
        <ms-api-extract :is-read-only="isReadOnly" :extract="request.extract"/>
      </el-tab-pane>
      <el-tab-pane :label="$t('api_test.request.processor.pre_jdbc_script')" name="jdbcPreProcessor">
        <ms-jdbc-processor :is-read-only="isReadOnly" :jdbc-processor="request.jdbcPreProcessor" :scenario="scenario" :useEnvironment="request.useEnvironment"/>
      </el-tab-pane>
      <el-tab-pane :label="$t('api_test.request.processor.post_jdbc_script')" name="jdbcPostProcessor">
        <ms-jdbc-processor :is-read-only="isReadOnly" :jdbc-processor="request.jdbcPostProcessor" :scenario="scenario" :useEnvironment="request.useEnvironment"/>
      </el-tab-pane>
      <el-tab-pane :label="$t('api_test.request.processor.pre_exec_script')" name="beanShellPreProcessor">
        <ms-jsr233-processor :is-read-only="isReadOnly" :jsr223-processor="request.jsr223PreProcessor"/>
      </el-tab-pane>
      <el-tab-pane :label="$t('api_test.request.processor.post_exec_script')" name="beanShellPostProcessor">
        <ms-jsr233-processor :is-read-only="isReadOnly" :jsr223-processor="request.jsr223PostProcessor"/>
      </el-tab-pane>

    </el-tabs>

  </el-form>


</template>

<script>
// import MsApiAdvancedConfig from "@/business/components/api/test/components/ApiAdvancedConfig";
// import MsBeanShellProcessor from "@/business/components/api/test/components/processor/BeanShellProcessor";
import MsApiVariable from "@/business/components/api/test/components/ApiVariable";
import ApiRequestMethodSelect from "@/business/components/api/test/components/collapse/ApiRequestMethodSelect";
import MsApiExtract from "@/business/components/api/test/components/extract/ApiExtract";
import MsApiAssertions from "@/business/components/api/test/components/assertion/ApiAssertions";
import MsApiBodyTextOrBinary from "@/business/components/api/test/components/body/ApiBodyTextOrBinary";
import MsApiKeyValue from "@/business/components/api/test/components/ApiKeyValue";
import {DubboRequest, Scenario, WebsocketRequest} from "@/business/components/api/test/model/ScenarioModel";
import {REQUEST_HEADERS} from "@/common/js/constants";
import {convert} from "@/business/components/api/test/model/DataProc";
import MsJsr233Processor from "@/business/components/api/test/components/processor/Jsr233Processor";
import MsJdbcProcessor from "@/business/components/api/test/components/processor/JdbcProcessor";
import MsCodeEdit from "@/business/components/common/components/MsCodeEdit";

export default {
  name: "MsApiWebsocketRequestForm",
  components: {
    MsApiVariable, MsApiAssertions, ApiRequestMethodSelect,MsApiKeyValue,MsApiExtract,MsJsr233Processor,MsJdbcProcessor,MsCodeEdit},
  props: {
    request: WebsocketRequest,
    scenario: Scenario,
    isReadOnly: {
      type: Boolean,
      default: false
    },
  },
  data() {
    return {
      activeName: "body",
      protocols: WebsocketRequest.PROTOCOLS,
      rules: {
        name: [
          {max: 300, message: this.$t('commons.input_limit', [1, 300]), trigger: 'blur'}
        ],
        url: [
          {max: 500, required: true, message: this.$t('commons.input_limit', [1, 500]), trigger: 'blur'}
        ]
      },
      isCreateNewConnection: [
        { label: "是", value: true },
        { label: "否", value: false }
      ],
      requestMode: [
        { label: "只连接", value: 1 },
        { label: "request-response", value: 2 },
        { label: "关闭连接", value: 0 }
      ]
      // headerSuggestions: REQUEST_HEADERS
    }
  },
  methods: {
    bodyBlur(){
      console.log("onblur");
      let obj=JSON.parse(this.request.requestData);
      convert(obj);
      this.request.requestData=JSON.stringify(obj);
      console.log(this.request.requestData);
    },

    urlChange() {
      if (!this.request.url) return;
      let url = this.getURL(this.addProtocol(this.request.url));
      if (url) {
        this.request.url = decodeURIComponent(url.origin + url.pathname);
      }
    },
    pathChange() {
      if (!this.request.path) return;
      let url = this.getURL(this.displayUrl);
      let urlStr = url.origin + url.pathname;
      let envUrl = 'wss://' + this.request.environment.socket;
      this.request.path = decodeURIComponent(urlStr.substring(envUrl.length, urlStr.length));
    },
    useEnvironmentChange(value) {
      if (value && !this.request.environment) {
        this.$error(this.$t('api_test.request.please_add_environment_to_scenario'), 2000);
        this.request.useEnvironment = false;
      }
      this.$refs["request"].clearValidate();
    },


    addProtocol(url) {
      if (url) {
        if (!url.toLowerCase().startsWith("wss") && !url.toLowerCase().startsWith("ws")) {
          return "wss://" + url;
        }
      }
      return url;
    },
    runDebug() {
      this.$emit('runDebug');
    }
  },
  computed: {
    // displayUrl() {
    //   return this.request.environment ?  + 'wss://' + this.request.environment.socket + (this.request.path ? this.request.path : '') : '';
    // }
  }
}
</script>

<style scoped>
.el-tag {
  width: 100%;
  height: 40px;
  line-height: 40px;
}

.environment-display {
  font-size: 14px;
}

.environment-name {
  font-weight: bold;
  font-style: italic;
}

.adjust-margin-bottom {
  margin-bottom: 10px;
}

.environment-url-tip {
  color: #F56C6C;
}
.script-content {
  height: calc(100vh - 570px);
}

</style>
