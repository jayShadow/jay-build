##mybatisPlus 配置项目

###1.默认数据库配置

* 本地3306端口 
* security_demo数据库 
* 编码utf-8 
* 时区Asia/Shanghai 
* 用户名root 
* 密码abc=123

###2.修改数据库配置项
* datasource.url
* datasource.username
* datasource.password

###3.默认mybatisPlus配置
* List字符串 使用','字符拼接后存入相应字段 (配置类:cn.jay.mybatis.type.StringListTypeHandler)
* Set字符串 使用','字符拼接后存入相应字段 (配置类:cn.jay.mybatis.type.StringSetTypeHandler)
