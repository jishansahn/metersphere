<template>
  <div>
    <div class="assertion-item-editing regex" v-if="assertions.regex.length > 0">
      <div>
        {{$t("api_test.request.assertions.regex")}}
      </div>
      <div class="regex-item" v-for="(regex, index) in assertions.regex" :key="index">
        <ms-api-assertion-regex :is-read-only="isReadOnly" :list="assertions.regex" :regex="regex" :edit="true" :index="index"/>
      </div>
    </div>
    <div class="assertion-item-editing text" v-if="assertions.text.length > 0">
      <div>
        {{$t("api_test.request.assertions.text")}}
      </div>
      <div class="regex-item" v-for="(te, index) in assertions.text" :key="index">
        <ms-api-assertion-text :is-read-only="isReadOnly" :list="assertions.text" :text="te" :edit="true" :index="index"/>
      </div>
    </div>

    <div class="assertion-item-editing json_path" v-if="assertions.jsonPath.length > 0">
      <div>
        {{'JSONPath'}}
      </div>
      <div class="regex-item" v-for="(jsonPath, index) in assertions.jsonPath" :key="index">
        <ms-api-assertion-json-path :is-read-only="isReadOnly" :list="assertions.jsonPath" :json-path="jsonPath" :edit="true" :index="index"/>
      </div>
    </div>
    <div class="assertion-item-editing jsr223" v-if="assertions.jsr223.length > 0">
      <div>
        {{'JSR223'}}
      </div>
      <div class="regex-item" v-for="(jsr223, index) in assertions.jsr223" :key="index">
        <ms-api-assertion-jsr223 :is-read-only="isReadOnly" :list="assertions.jsr223" :jsr223="jsr223" :edit="true" :index="index"/>
      </div>
    </div>
    <div class="assertion-item-editing response-time" v-if="isShow">
      <div>
        {{$t("api_test.request.assertions.response_time")}}
      </div>
      <ms-api-assertion-duration :is-read-only="isReadOnly" v-model="assertions.duration.value" :duration="assertions.duration" :edit="true"/>
    </div>
  </div>

</template>

<script>
  import MsApiAssertionRegex from "./ApiAssertionRegex";
  import MsApiAssertionText from "./ApiAssertionText";
  import MsApiAssertionDuration from "./ApiAssertionDuration";
  import {Assertions} from "../../model/ScenarioModel";
  import MsApiAssertionJsonPath from "./ApiAssertionJsonPath";
  import MsApiAssertionJsr223 from "./ApiAssertionJsr223"

  export default {
    name: "MsApiAssertionsEdit",

    components: {MsApiAssertionJsr223,MsApiAssertionJsonPath, MsApiAssertionDuration, MsApiAssertionRegex,MsApiAssertionText},

    props: {
      assertions: Assertions,
      isReadOnly: {
        type: Boolean,
        default: false
      }
    },

    computed: {
      isShow() {
        let rt = this.assertions.duration;
        return rt.value !== undefined;
      }
    }
  }
</script>

<style scoped>
  .assertion-item-editing {
    padding-left: 10px;
    margin-top: 10px;
  }

  .assertion-item-editing.regex {
    border-left: 2px solid #7B0274;
  }
  .assertion-item-editing.text {
    border-left: 2px solid #7B0274;
  }
  .assertion-item-editing.jsr223 {
    border-left: 2px solid #7B0274;
  }
  .assertion-item-editing.json_path {
    border-left: 2px solid #44B3D2;
  }

  .assertion-item-editing.response-time {
    border-left: 2px solid #DD0240;
  }

  .regex-item {
    margin-top: 10px;
  }


</style>
