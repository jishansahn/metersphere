<template>
  <div>
    <el-row :gutter="10" type="flex" justify="space-between" align="middle">
      <el-col class="kv-checkbox">
        <input type="checkbox" :disabled="isReadOnly" v-model="duration.enable" v-if="edit" />
      </el-col>
      <el-col>
        <el-input :disabled="isReadOnly" :value="value" v-bind="$attrs" step="100" size="small" type="number" @change="change" @input="input"
                  :placeholder="$t('api_test.request.assertions.response_in_time')"/>
      </el-col>
      <el-col class="assertion-btn">
        <el-button :disabled="isReadOnly" type="danger" size="mini" icon="el-icon-delete" circle @click="remove" v-if="edit"/>
        <el-button :disabled="isReadOnly" type="primary" size="small" @click="add" v-else>Add</el-button>
      </el-col>
    </el-row>
  </div>
</template>

<script>

  import {Duration} from "../../model/ScenarioModel";

  export default {
    name: "MsApiAssertionDuration",

    props: {
      duration: Duration,
      value: [Number, String],
      edit: Boolean,
      callback: Function,
      isReadOnly: {
        type: Boolean,
        default: false
      }
    },

    methods: {
      add() {
        this.duration.value = this.value;
        this.callback();
      },
      remove() {
        this.duration.value = undefined;
      },
      change(value) {
        this.$emit('change', value);
      },
      input(value) {
        this.$emit('input', value);
      }
    }
  }
</script>

<style scoped>
  .assertion-btn {
    text-align: center;
    width: 60px;
  }
  .kv-checkbox {
    width: 20px;
    margin-right: 10px;
  }
</style>
