<template>
  <editor v-model="requestData" :lang="mode" />
</template>

<script>
import {WebsocketRequest,BODY_TYPE, Scenario} from "@/business/components/api/test/model/ScenarioModel";

export default {
  name: "MsApiBodyTextOrBinary",
  components: { editor: require('vue2-ace-editor')},
  props: {
    isReadOnly: {
      type: Boolean,
      default: false
    },
    mode: {
      type: String,
      default() {
        return 'text';
      }
    },
  },

  data() {
    return {
      // type: BODY_TYPE,
      // modes: ['text'],
      requestData:''
    };
  },
}
</script>

<style scoped>

</style>
