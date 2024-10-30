```markdown
## 项目简介
- 这是一个基于SpringBoot的安全教育脚手架，按照教程操作可以快速部署并运行。
- **目标**：提供一站式的安全能力评估平台。

## 部署指南（以阿里云服务器为例）
- 按照以下步骤部署，预计耗时约10分钟。

### LINUX环境下的操作
```bash
# 安装基础环境和依赖
yum install git maven -y
wget https://mirrors.estointernet.in/apache/maven/maven-3/3.6.3/binaries/apache-maven-3.6.3-bin.tar.gz
tar -xvf apache-maven-3.6.3-bin.tar.gz
mv apache-maven-3.6.3 /opt/
wget https://download.oracle.com/java/17/latest/jdk-17_linux-x64_bin.tar.gz
tar xf jdk-17_linux-x64_bin.tar.gz
mv jdk-17.0.7/ /usr/lib/jvm

# 更新配置
rm -rf /opt/apache-maven-3.6.3/conf/settings.xml
vi /opt/apache-maven-3.6.3/conf/settings.xml
# 参考“settings.xml”节配置源

vi /etc/profile
# 设置Java和Maven环境
export M2_HOME='/opt/apache-maven-3.6.3'
export JAVA_HOME=/usr/lib/jvm/jdk-17.0.7
export CLASSPATH=$JAVA_HOME/lib/tools.jar:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib
export PATH=$M2_HOME/bin:$JAVA_HOME/bin:$PATH

source /etc/profile

# 下载项目文件
git clone https://github.com/zgimszhd61/java-sec-code-plus.git
cd java-sec-code-plus

# 编译和运行
mvn install
mvn package
```

### 启动应用
```bash
mvn spring-boot:run
```

### 注意事项
- 默认监听8080端口，若使用阿里云，请在安全组中允许8080端口访问。
- 若要使用80端口，请在 `pom.xml` 文件中调整配置。

### settings.xml 配置示例
```xml
<?xml version="1.0" encoding="UTF-8"?>
<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0 http://maven.apache.org/xsd/settings-1.0.0.xsd">

  <mirrors>
    <mirror>
     <id>aliyunmaven</id>
     <mirrorOf>*</mirrorOf>
     <name>阿里云公共仓库</name>
     <url>https://maven.aliyun.com/repository/public</url>
    </mirror>
    <!-- 其他阿里云仓库配置同理 -->
  </mirrors>
</settings>
```

## 如果80或8080端口被占用
```bash
# 查找占用进程并结束
lsof -i:8080 
kill -9 [PID]
```

## 漏洞验证接口
- 下面是一些用于安全测试的API接口示例：

```bash
# XSS攻击测试
GET http://localhost:80/api/xss/bad?name=<script>alert(1)</script>

# SSRF攻击测试
GET http://localhost:80/api/ssrf/bad?url=http://localhost:80/api/xss/bad?name=<script>alert(1)</script>

# 远程代码执行测试
GET http://localhost:80/api/rce/bad02?cmd=ls

# XXE攻击测试
GET http://localhost:80/api/xxe/bad01?xml=<!DOCTYPE doc [<!ENTITY xxe SYSTEM \"http://127.0.0.1:1664\">]><doc>&xxe;</doc>

# JSONP攻击测试
GET http://localhost:80/api/jsonp/bad01?callback=<script>alert(1)</script>

# SPEL攻击测试
GET http://localhost:80/api/spel/bad01?cmd=vulnhere
```

## 应用场景
- **安全防护产品测试**：
  - IDE插件测试
  - WAF能力测试
  - 白盒、黑盒、IAST扫描测试
  - 供应链安全产品测试
- **人工代码审计学习**

## 更新日志
- **2023-06**：项目初始化，创建ssrf、rce、xss接口及健康检查。
- **2023-07-01**：新增反序列化和Ognl。
- **2023-07-04**：新增bsh、groovy、mvel、processbuilder、redos、jndi。

## 参与共建
- 联系微信：7908300 领取任务
- 或直接在项目中提出issue。

## 下一步计划
- [ ] 异常信息回显至页面
- [ ] 无频率限制的短信验证码
- [ ] 邮箱和手机号信息的枚举攻击
- [ ] 开源许可证风险模拟
- [ ] 高星CVEs漏洞复现
- [ ] 不安全的加密算法（如静态盐）
- [ ] 创建其他分支：Android、Python、Bun、iOS等应用漏洞Benchmark
```