# TableCompare
一、比较两个一样表结构库的所有表，并生成excel文件说明 所有表对比下的 新增记录 删除记录 修改记录。  
比如下面这个库：  
![image](https://github.com/weichuyu/TableCompare/blob/master/sample/img/table.PNG)  


二、使用方法  
（一）修改config.properties  
![image](https://github.com/weichuyu/TableCompare/blob/master/sample/img/configproperties.PNG)
（二）修改log4j.properties  
![image](https://github.com/weichuyu/TableCompare/blob/master/sample/img/log4j.PNG)  
（三）运行DataCompareApp中main方法  
![image](https://github.com/weichuyu/TableCompare/blob/master/sample/img/console.PNG)
（五）查看导出结果    
![image](https://github.com/weichuyu/TableCompare/blob/master/sample/img/result1.PNG)
![image](https://github.com/weichuyu/TableCompare/blob/master/sample/img/result2.PNG)

三、注意事项  
（一）只支持三个以内的复合主键，复合主键如果字段超过三个会报错。  
（二）会将两个数据源的同一张表所有数据读到内存中做比较，不适用生产数据量较大的情况。  
