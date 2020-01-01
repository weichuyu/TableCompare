# TableCompare
一、比较两个一样表结构库的所有表，并生成excel文件说明 所有表对比下的 新增记录 删除记录 修改记录。 
本代码适用于熟悉新接手项目中各个功能修改了那些表，以及在自己测试中更清楚的查看各个表的修改情况是否符合预期。
非常适合写报表sql时熟悉表结构，并且你不想自己看项目代码也找不到开发问表结构的情况。
比如下面这两个库：  
![image](https://github.com/weichuyu/TableCompare/blob/master/sample/img/table.PNG)  
建议使用方式：
（一）剔除历史数据，在本地建一份干净的数据库环境。  
（二）启动项目做操作前，备份整个数据库。  
（三）每做一部操作，就执行一次本项目main方法，生成每一步操作对数据库的修改情况excel。  
（五）这样持续下去，你就可以在不看代码的情况下，了解每一个操作修改了数据库的哪些表了。  
sample文件夹下面有导出的excel示例可以下载。  

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
（三）备份数据库往往是个耗时的操作，不过与其像无头苍蝇一样去翻代码（有些老代码你还不一定看的懂，开发的人可能都不在了），  
不如多花点备份的时间，使用本项目进行不断比较，这样更简单无脑。  

