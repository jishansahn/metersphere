<template>
  <div>
    <el-form-item :label="$t('api_test.request.sql.dataSource')" prop="dataSource">
      <el-select v-model="jdbcProcessor.dataSource">
        <el-option v-for="(item, index) in databaseConfigsOptions" :key="index" :value="item.id" :label="item.name"/>
      </el-select>
    </el-form-item>

    <el-form-item :label="$t('api_test.request.sql.result_variable')" prop="resultVariable">
      <el-input v-model="jdbcProcessor.resultVariable" maxlength="300" show-word-limit/>
    </el-form-item>

    <el-form-item :label="$t('api_test.request.sql.variable_names')" prop="variableNames">
      <el-input v-model="jdbcProcessor.variableNames" maxlength="300" show-word-limit/>
    </el-form-item>

    <el-form-item :label="$t('api_test.request.sql.timeout')" prop="queryTimeout">
      <el-input-number :disabled="isReadOnly" size="mini" v-model="jdbcProcessor.queryTimeout"
                       :placeholder="$t('commons.millisecond')" :max="1000*10000000" :min="0"/>
    </el-form-item>

    <el-form-item>
      <el-switch
        v-model="useEnvironment"
        :active-text="$t('api_test.request.refer_to_environment')" @change="getDatabaseConfigsOptions" disabled>
      </el-switch>
    </el-form-item>
    <el-form-item :label="$t('api_test.request.sql.sql_script')" name="sql">
      <div class="script-content">
        <ms-code-edit mode="sql" :read-only="isReadOnly" :modes="['sql']" :data.sync="jdbcProcessor.query" theme="eclipse"
                      ref="codeEdit"/>
      </div>
    </el-form-item>
  </div>
</template>

<script>
import MsCodeEdit from "@/business/components/common/components/MsCodeEdit";

export default {
  name: "MsJdbcProcessor",
  components: {
    MsCodeEdit
  },
  props: {
    type: {
      type: String,
    },

    isReadOnly: {
      type: Boolean,
      default: false
    },
    jdbcProcessor: {
      type: Object,
    },
    scenario: {
      type: Object,
    },
    useEnvironment: {
      type: Boolean,
      default: false
    },
    isPreProcessor: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      activeName: "sql",
      databaseConfigsOptions: [],
      rules: {
        dataSource: [
          {required: true, message: this.$t('commons.input_name'), trigger: 'blur'},
        ],
      }
    }
  },
  methods: {
    getDatabaseConfigsOptions() {
      this.databaseConfigsOptions = [];
      let names = new Set();
      let ids = new Set();
      this.scenario.databaseConfigs.forEach(config => {
        this.databaseConfigsOptions.push(config);
        names.add(config.name);
        ids.add(config.id);
      });
      if (this.useEnvironment && this.scenario.environment) {
        this.scenario.environment.config.databaseConfigs.forEach(config => {
          if (!names.has(config.name)) {
            this.databaseConfigsOptions.push(config);
            ids.add(config.id);
          }
        });
      }
      if (!ids.has(this.jdbcProcessor.dataSource)) {
        this.jdbcProcessor.dataSource = undefined;
      }
    },
    runDebug() {
      this.$emit('runDebug');
    }
  },

  created() {
    this.getDatabaseConfigsOptions();
  },
  activated() {
    this.getDatabaseConfigsOptions();
  }

}
</script>

<style scoped>

.script-content {
  height: calc(100vh - 570px);
}


</style>
