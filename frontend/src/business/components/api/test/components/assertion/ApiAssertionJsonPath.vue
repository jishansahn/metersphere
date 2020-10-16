<template>
  <div>
    <el-row :gutter="10" type="flex" justify="space-between" align="middle">
      <el-col class="kv-checkbox">
        <input type="checkbox" :disabled="isReadOnly" v-model="jsonPath.enable" v-if="edit" />
      </el-col>
      <el-col>
        <el-input :disabled="isReadOnly" v-model="jsonPath.expression" maxlength="200" size="small" show-word-limit
                  :placeholder="$t('api_test.request.extract.json_path_expression')"/>
      </el-col>
      <el-col class="assertion-select">
        <el-select :disabled="isReadOnly" class="assertion-item" v-model="jsonPath.condition" size="small"
                   :placeholder="$t('api_test.request.assertions.select_condition')" >
          <el-option :label="$t('api_test.request.assertions.equals')" value="EQUALS"/>
          <el-option :label="$t('api_test.request.assertions.exists')" value="EXISTS"/>
          <el-option :label="$t('api_test.request.assertions.regex')" value="REGEX"/>
        </el-select>
      </el-col>
      <el-col class="kv-checkbox2">
        取反<input type="checkbox" :disabled="isReadOnly" v-model="jsonPath.invert" />
      </el-col>
      <el-col>
        <el-input :disabled="isReadOnly" v-model="jsonPath.expect" size="small" show-word-limit
                  :placeholder="$t('api_test.request.assertions.expect')" v-if="jsonPath.condition!='EXISTS'"/>
      </el-col>
      <el-col class="assertion-btn">
        <el-button :disabled="isReadOnly" type="danger" size="mini" icon="el-icon-delete" circle @click="remove" v-if="edit"/>
        <el-button :disabled="isReadOnly" type="primary" size="small" @click="add" v-else>Add</el-button>
      </el-col>

    </el-row>
  </div>
</template>

<script>
  import {JSONPath} from "../../model/ScenarioModel";

  export default {
    name: "MsApiAssertionJsonPath",

    props: {
      jsonPath: {
        type: JSONPath,
        default: () => {
          return new JSONPath();
        }
      },
      edit: {
        type: Boolean,
        default: false
      },
      hasValue:{
        type: Boolean,
        default: true
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
      }
    },

    watch: {
      'jsonPath.expect'() {
        this.setJSONPathDescription();
      },
      'jsonPath.expression'() {
        this.setJSONPathDescription();
      }
    },

    methods: {
      conditionChange:function (){
        if(this.jsonPath.condition==="EXISTS"){
          this.hasValue=false;
        }else {
          this.hasValue=true;
        }
      },
      add: function () {
        this.list.push(this.getJSONPath());
        this.callback();
      },
      remove: function () {
        this.list.splice(this.index, 1);
      },
      getJSONPath() {
        let jsonPath = new JSONPath(this.jsonPath);
        jsonPath.description = jsonPath.expression + jsonPath.condition + (jsonPath.expect ? jsonPath.expect : '') + "取反"+jsonPath.invert;
        return jsonPath;
      },
      setJSONPathDescription() {
        this.jsonPath.description = this.jsonPath.expression + " expect: " + (this.jsonPath.expect ? this.jsonPath.expect : '');
      }
    }
  }
</script>

<style scoped>
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
  .kv-checkbox2 {
    width: 90px;
    margin-right: 10px;
  }
</style>
