# db2j-ce
Generating java files from database tables.

download [jar.zip](https://github.com/SShnoodles/db2j-ce/releases)

**Idea plugin!!!** [Idea Plugin](https://github.com/SShnoodles/db2j-ce-idea-plugin)

# Support database
- oracle
- postgresql
- mysql

# Two config properties
1. first load outside "app.properties"
2. second load resources "app.properties"

# Configuration file
```
# oracle or postgresql or mysql
url=jdbc:postgresql://localhost:5432/postgres
username=postgres
password=123456
out.path=./tmp
# jpa,dto,pojo,repository,controller，default pojo
templates=pojo
# default system username
author=
# overwrite files default false
overwrite.files=false
# single table name
single.table.name=
# rename single table
single.table.rename=
# custom template
custom.template=
```

# Templates
Jpa、Dto、Pojo、Repository

# Quick start
```java
// load config
Config config = FileUtil.PROPERTIES;
// create factory
DbFactory dbFactory = new DbFactoryImpl();
// generate template
dbFactory.create(config);
```