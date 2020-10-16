<template>
  <div>
    <el-row :gutter="10" type="flex" justify="space-between" align="middle">
      <el-col class="kv-checkbox">
        <input type="checkbox" :disabled="isReadOnly" v-model="jsr223.enable" v-if="edit" />
      </el-col>
      <el-col class="assertion-item">
        <el-row>
          <el-col :span="20" class="script-content">
            <ms-code-edit v-if="isCodeEditAlive" :mode="codeEditModeMap[jsr223.language]" :read-only="isReadOnly"
                          :data.sync="jsr223.script" theme="eclipse" :modes="['java','python']" ref="codeEdit"/>
          </el-col>
          <el-col :span="4" class="script-index">
            <ms-dropdown :default-command="jsr223.language" :commands="languages" @command="languageChange"/>
            <div class="template-title">{{ $t('api_test.request.processor.code_template') }}</div>
            <div v-for="(template, index) in codeTemplates" :key="index" class="code-template">
              <el-link :disabled="template.disabled" @click="addTemplate(template)">{{ template.title }}</el-link>
            </div>
            <div class="document-url">
              <el-link href="https://jmeter.apache.org/usermanual/component_reference.html#BeanShell_PostProcessor"
                       type="primary">{{ $t('commons.reference_documentation') }}
              </el-link>
              <ms-instructions-icon :content="$t('api_test.request.processor.bean_shell_processor_tip')"/>
            </div>
          </el-col>
        </el-row>
      </el-col>
      <el-col class="assertion-btn">
        <el-button :disabled="isReadOnly" type="danger" size="mini" icon="el-icon-delete" circle @click="remove" v-if="edit"/>
        <el-button :disabled="isReadOnly" type="primary" size="small" @click="add" v-else>Add</el-button>
      </el-col>

    </el-row>

  </div>
</template>

<script>
import {ASSERTION_REGEX_SUBJECT, JSR223, Regex} from "@/business/components/api/test/model/ScenarioModel";
import MsDropdown from "@/business/components/common/components/MsDropdown";
import MsInstructionsIcon from "@/business/components/common/components/MsInstructionsIcon";
import MsCodeEdit from "@/business/components/common/components/MsCodeEdit";

export default {
  name: "ApiAssertionJsr223",
  components: {MsDropdown, MsInstructionsIcon, MsCodeEdit},
  props: {
    jsr223: {
      type: JSR223,
      default: () => {
        return new JSR223();
      }
    },
    edit: {
      type: Boolean,
      default: false
    },
    index: Number,
    list: Array,
    callback: Function,
    isReadOnly: {
      type: Boolean,
      default: false
    }
  },

  data() {
    return {
      codeTemplates: [
        {
          title: this.$t('api_test.request.processor.code_template_get_variable'),
          value: 'vars.get("variable_name");',
        },
        {
          title: this.$t('api_test.request.processor.code_template_set_variable'),
          value: 'vars.put("variable_name", "variable_value");',
        },
        // {
        //   title: this.$t('api_test.request.processor.code_template_get_response_header'),
        //   value: 'ResponseHeaders',
        //   disabled: this.isPreProcessor
        // },
        // {
        //   title: this.$t('api_test.request.processor.code_template_get_response_code'),
        //   value: 'ResponseCode',
        //   disabled: this.isPreProcessor
        // },
        // {
        //   title: this.$t('api_test.request.processor.code_template_get_response_result'),
        //   value: 'prev.getResponseDataAsString();',
        //   disabled: this.isPreProcessor
        // }
      ],
      isCodeEditAlive: true,
      languages: [
        'beanshell', "python"
      ],
      codeEditModeMap: {
        beanshell: 'java',
        python: 'python'
      }

    }
  },

  methods: {
    add: function () {
      this.list.push(this.getJSR223());
      this.callback();
    },
    remove: function () {
      this.list.splice(this.index, 1);
    },
    getJSR223() {
      let jsr223 = new JSR223(this.jsr223);
      // jsr223.description = regex.subject + " has: " + regex.expression;
      return jsr223;
    },
    addTemplate(template) {
      if (!this.jsr223.script) {
        this.jsr223.script = "";
      }
      this.jsr223.script += template.value;
      this.reload();
    },
    reload() {
      this.isCodeEditAlive = false;
      this.$nextTick(() => (this.isCodeEditAlive = true));
    },
    languageChange(language) {
      this.jsr223.language = language;
    }
  }
  // setRegexDescription() {
  //   this.regex.description = this.regex.subject + " has: " + this.regex.expression;
  // }


}
</script>

<style scoped>
.script-content {
  height: calc(100vh - 570px);
}
.assertion-select {
  width: 250px;
}

.assertion-item {
  width: 100%;
}

.assertion-btn {
  text-align: center;
  width: 60px;
}

.kv-checkbox {
  width: 20px;
  margin-right: 10px;
}
</style>
