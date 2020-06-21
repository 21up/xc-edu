<template>
  <div>
    <!--查询表单-->
    <el-form :model="params">
      <el-select v-model="params.siteId" placeholder="请选择站点">
        <el-option v-for="item in siteList" :key="item.siteId" :label="item.siteName" :value="item.siteId"></el-option>
      </el-select>
      页面别名：
      <el-input v-model="params.pageAliase" style="width: 100px"></el-input>
      页面名称：
      <el-input v-model="params.pageName" style="width: 100px"></el-input>
      <el-form-item label="页面类型">
        <el-radio-group v-model="params.pageType">
          <el-radio class="radio" label="0">静态</el-radio>
          <el-radio class="radio" label="1">动态</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-button type="primary" v-on:click="query" size="small">查询</el-button>
      <router-link class="mui-tab-item" :to="{path:'/cms/page/add',query:{
      page:this.params.page,siteId:this.params.siteId}}">
        <el-button type="primary" size="small">新增页面</el-button>
      </router-link>
    </el-form>
    <el-table
      :data="list"
      style="width: 100%">
      <el-table-column type="index" width="60"></el-table-column>
      <el-table-column prop="pageName" label="页面名称" width="120"></el-table-column>
      <el-table-column prop="pageAliase" label="别名" width="120"></el-table-column>
      <el-table-column prop="pageType" label="页面类型" width="150"></el-table-column>
      <el-table-column prop="pageWebPath" label="访问路径" width="250"></el-table-column>
      <el-table-column prop="pagePhysicalPath" label="物理路径" width="250"></el-table-column>
      <el-table-column prop="pageCreateTime" label="创建时间" width="180"></el-table-column>
      <el-table-column label="操作" width="80">
        <template slot-scope="page">
          <el-button size="small" type="text" @click="edit(page.row.pageId)">编辑</el-button>
          <el-button size="small" type="text" @click="del(page.row.pageId)">删除</el-button>
          <el-button size="small" type="text" @click="preview(page.row.pageId)">页面预览</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      layout="prev, pager, next"
      :page-size="this.params.size"
      v-on:current-change="changePage"
      :total="total"
      :current-page="this.params.page"
      style="float:right;">
    </el-pagination>
  </div>
</template>
<script>
  import * as cmsApi from '../api/cms'

  export default {
    data() {
      return {
        list: [],
        siteList: [],//站点列表
        total: 50,
        params: {
          page: 1,
          size: 10,
          pageAliase: '',
          siteId: '',
          pageName:'',
          pageType:''
        }
      }
    },
    mounted() {
      this.query();
      this.siteList = this.querySiteList();
      this.params.page = Number.parseInt(this.$route.query.page || 1);
      this.params.siteId = this.$route.query.siteId || '';
    },
    methods: {
      //分页查询
      changePage(page) {
        this.params.page = page;
        this.query();
      },
      query: function () {
        cmsApi.page_list(this.params.page, this.params.size, this.params).then((res) => {
          this.list = res.queryResult.list;
          this.total = res.queryResult.total;
        })
      },
      querySiteList: function () {
        cmsApi.site_list().then((res) => {
          this.siteList = res
        })
      },
      edit: function (pageId) {
        this.$router.push({
          path: '/cms/page/edit/' + pageId, query: {
            page: this.params.page, siteId: this.params.siteId
          }
        })
      },
      del: function (pageId) {
        this.$confirm('确认删除此页面吗？', '提示', {}).then(() => {
          cmsApi.page_del(pageId).then((res) => {
            if (res.success) {
                this.$message({
                  type:'success',
                  message:'删除成功！'
                });
                this.query();
            }else{
              this.$message({
                type:'error',
                message:'删除失败'
              })
            }
          })
        })
      },
      preview:function (pageId) {
        window.open("http://www.xuecheng.com/cms/preview/"+pageId)
      }
    }
  }
</script>
