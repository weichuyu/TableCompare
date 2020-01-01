# DataCompare
一、比较两个相似库的表，如果表存在差异，会生成excel文件说明 新增记录 删除记录 修改记录  
二、目前只支持mysql  
三、只支持三个以内的复合主键，复合主键如果字段超过三个会报错  
四、config.properties中配置数据源  
五、log4j.properties配置日志  
六、DataCompareApp启动时需要手动修改CompareDataBase.compareDateBase的两个参数schema和workspace  
七、没有考虑可执行jar，我是eclipse每次运行的  
