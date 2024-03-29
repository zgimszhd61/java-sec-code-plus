## 项目介绍
- 一个SpringBoot的脚手架，按照教程走下去直接部署到可运行状态.
- A SpringBoot scaffolding for security education purposes.
- 目标：
  - 一站式安全能力评估.

## 以阿里云服务器部署为例
- 按照下面教程部署，总共重新用时10分钟.

### LINUX下运行
```
## 下载基本环境和依赖:

yum install git maven -y
wget https://mirrors.estointernet.in/apache/maven/maven-3/3.6.3/binaries/apache-maven-3.6.3-bin.tar.gz
tar -xvf apache-maven-3.6.3-bin.tar.gz
mv apache-maven-3.6.3 /opt/
wget https://download.oracle.com/java/17/latest/jdk-17_linux-x64_bin.tar.gz
tar xf jdk-17_linux-x64_bin.tar.gz
mv jdk-17.0.7/ /usr/lib/jvm


## 更新配置

rm -rf /opt/apache-maven-3.6.3/conf/settings.xml
vi /opt/apache-maven-3.6.3/conf/settings.xml
更新settings.xml源，参考settings这个标题

vi /etc/profile
#set java environment(保存下面信息到/etc/profile末尾)
export M2_HOME='/opt/apache-maven-3.6.3'
export JAVA_HOME=/usr/lib/jvm/jdk-17.0.7
export CLASSPATH=$JAVA_HOME/lib/tools.jar:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib
export PATH=$M2_HOME/bin:$JAVA_HOME/bin:$PATH

source /etc/profile

## 下载文件包
git clone https://github.com/zgimszhd61/java-code-simple.git
cd java-code-simple

## 试编译运行:
mvn install
mvn package

```

### USAGE
```

mvn spring-boot:run

```

### 注意
```

打开的是8080端口，如果用的是阿里云，需要注意安全组允许8080端口被访问
如果想要启动的是80端口，那么pom.xml的配置需改一下

```

### settings.xml
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
     <mirror>
     <id>aliyunmaven</id>
     <mirrorOf>*</mirrorOf>
     <name>阿里云谷歌仓库</name>
     <url>https://maven.aliyun.com/repository/google</url>
    </mirror>
    <mirror>
     <id>aliyunmaven</id>
     <mirrorOf>*</mirrorOf>
     <name>阿里云阿帕奇仓库</name>
     <url>https://maven.aliyun.com/repository/apache-snapshots</url>
    </mirror>
    <mirror>
     <id>aliyunmaven</id>
     <mirrorOf>*</mirrorOf>
     <name>阿里云spring仓库</name>
     <url>https://maven.aliyun.com/repository/spring</url>
    </mirror>
    <mirror>
     <id>aliyunmaven</id>
     <mirrorOf>*</mirrorOf>
     <name>阿里云spring插件仓库</name>
     <url>https://maven.aliyun.com/repository/spring-plugin</url>
    </mirror>
   </mirrors>
</settings>
```

## 如果80或8080端口被占用
```

运行 lsof -i:8080 ， 得到PID进程号
kill -9 PID进程号 ，杀掉进程
然后重新运行

```

## 攻击验证API
```agsl

###
GET http://localhost:80/api/xss/bad?name=<script>alert(1)</script>

###
GET http://localhost:80/api/ssrf/bad?url=http://localhost:80/api/xss/bad?name=<script>alert(1)</script>

###
GET http://localhost:80/api/rce/bad02?cmd=ls

###
GET http://localhost:80/api/xxe/bad01?xml=<!DOCTYPE doc [<!ENTITY xxe SYSTEM \"http://127.0.0.1:1664\">]><doc>&xxe;</doc>

###
GET http://localhost:80/api/jsonp/bad01?callback=<script>alert(1)</script>

###
GET http://localhost:80/api/spel/bad01?cmd=vulnhere


```

## 应用场景
- 安全防护产品测试.
  - IDE插件能力测试.
  - WAF能力测试.
  - 白盒扫描能力测试.
  - 黑盒扫描能力测试.
  - IAST扫描能力测试.
  - 供应链安全产品能力测试.
- 人工代码审计学习.


## 更新日志
- 2023-06: 项目初始化，创建ssrf、rce、xss、Helloworld健康检查接口.创建README教程.
- 2023-07-01: 增加反序列化，增加Ognl;
- 2023-07-04: 增加bsh,groovy,mvel,processbuilder,redos,jndi；


## 如何加入共建
- 联系wx:7908300 ，领取任务.
- 或者直接本项目提出issue.

## 下一步计划
- [] 异常报错回显到页面.
- [] 短信验证码无频率控制.
- [] 账号之中邮箱和手机号信息可通过1+1+1枚举.
- [] 开源许可证违规风险还原.
- [] 常用高Star供应链CVE漏洞还原.
- [] 不安全的加密算法（如静态盐salt).
- [] 创建其他分支:AndroidApp常见漏洞benchmark,pythonApp常见漏洞benchmark,BunApp常见漏洞benchmark,IosApp常见漏洞Benchmark，并提供索引.
