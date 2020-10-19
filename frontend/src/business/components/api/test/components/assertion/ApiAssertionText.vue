<template>
  <div>
    <el-row :gutter="10" type="flex" justify="space-between" align="middle">
      <el-col class="kv-checkbox">
        <input type="checkbox" :disabled="isReadOnly" v-model="text.enable" v-if="edit"/>
      </el-col>
      <el-col class="assertion-select">
        <el-select :disabled="isReadOnly" class="assertion-item" v-model="text.subject" size="small"
                   :placeholder="$t('api_test.request.assertions.select_subject')">
          <el-option label="Response Code" :value="subjects.RESPONSE_CODE"/>
          <el-option label="Response Headers" :value="subjects.RESPONSE_HEADERS"/>
          <el-option label="Response Data" :value="subjects.RESPONSE_DATA"/>
        </el-select>
      </el-col>
      <el-col class="assertion-select">
        <el-select :disabled="isReadOnly" class="assertion-item" v-model="text.condition" size="small"
                   :placeholder="$t('api_test.request.assertions.select_condition')">
          <el-option :label="'contains'" value="CONTAINS"/>
          <el-option :label="'substring'" value="SUBSTRING"/>
          <el-option :label="'equals'" value="EQUALS"/>
        </el-select>
      </el-col>
      <el-col>
        <el-input :disabled="isReadOnly" v-model="text.value" maxlength="1000" size="small" show-word-limit
                  :placeholder="$t('api_test.request.assertions.value')"/>
      </el-col>
      <el-col class="assertion-btn">
        <el-button :disabled="isReadOnly" type="danger" size="mini" icon="el-icon-delete" circle @click="remove"
                   v-if="edit"/>
        <el-button :disabled="isReadOnly" type="primary" size="small" @click="add" v-else>Add</el-button>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import {Text, ASSERTION_REGEX_SUBJECT} from "../../model/ScenarioModel";

export default {
  name: "MsApiAssertionText",

  props: {
    text: {
      type: Text,
      default: () => {
        return new Text();
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
      subjects: ASSERTION_REGEX_SUBJECT,
      // subject: "",
      // condition: "",
      // value: "",
      // enable:true
    }
  },

  methods: {
    add: function () {
      this.list.push(this.toText());
      this.callback();
    },
    toText: function () {
      let expression = this.value;
      let description = this.subject;
      switch (this.condition) {
        case "CONTAINS":
          // expression = ".*" + this.value + ".*";
          description += " contains: " + this.value;
          break;
        case "SUBSTRING":
          // expression = "(?s)^((?!" + this.value + ").)*$";
          description += " SUBSTRING: " + this.value;
          break;
        case "EQUALS":
          // expression = "^" + this.value + "$";
          description += " equals: " + this.value;
          break;
        case "START_WITH":
          // expression = "^" + this.value;
          description += " start with: " + this.value;
          break;
        case "END_WITH":
          // expression = this.value + "$";
          description += " end with: " + this.value;
          break;
      }
      this.text.description = description;
      return this.text;
      // return new Text({
      //   subject: this.subject,
      //   condition:this.condition,
      //   expression: expression,
      //   description: description,
      //   enable:this.enable
      // }
      // );
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
  width: 60px;
}

.kv-checkbox {
  width: 20px;
  margin-right: 10px;
}
</style>
