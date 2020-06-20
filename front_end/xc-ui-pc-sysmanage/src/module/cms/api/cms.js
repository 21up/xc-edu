import http from "./../../../base/api/public"
import querystring from "querystring"
let sysConfig = require('@/../config/sysConfig')
let apiUrl = sysConfig.xcApiUrlPre;
//页面列表查询
export const page_list=(page,size,params)=>{
  //将json对象转成key/value对
  let query=querystring.stringify(params)
  return http.requestQuickGet(apiUrl+'/cms/page/list/'+page+'/'+size+'/?'+query)
}
//站点查询
export  const site_list=()=>{
  return http.requestQuickGet(apiUrl+'/cms/site/list')
}
//模板查询
export const template_list=()=>{
  return http.requestQuickGet(apiUrl+'/cms/template/list')
}
//添加页面
export  const page_add=params=>{
  return http.requestPost(apiUrl+'/cms/page/add',params)
}
//根据页面ID查询
export const  page_get=id=>{
  return http.requestQuickGet(apiUrl+'/cms/page/get/'+id)
}
//修改页面
export const  page_edit=(id,params)=>{
  return http.requestPut(apiUrl+'/cms/page/edit/'+id,params)
}
//删除页面
export const page_del=id=>{
  return http.requestDelete(apiUrl+"/cms/page/del/"+id)
}
