@echo off

REM ============================================================================
REM タイトル：環境変数設定用バッチ 2014/09/18
REM ============================================================================

set  JAVA_HOME="C:\jdk1.6.0_35"
set  CLS_HOME=%~dp0\..\..

set  JRECLASSPATH="

set  JRECLASSPATH=%JRECLASSPATH%%CLS_HOME%\mkh-masterbatch.jar;
set  JRECLASSPATH=%JRECLASSPATH%%CLS_HOME%\lib\activation.jar;
set  JRECLASSPATH=%JRECLASSPATH%%CLS_HOME%\lib\AlarmCustApi.jar;
set  JRECLASSPATH=%JRECLASSPATH%%CLS_HOME%\lib\ant.jar;
set  JRECLASSPATH=%JRECLASSPATH%%CLS_HOME%\lib\commons-io-1.4.jar;
set  JRECLASSPATH=%JRECLASSPATH%%CLS_HOME%\lib\commons-lang-2.1.jar;
set  JRECLASSPATH=%JRECLASSPATH%%CLS_HOME%\lib\commons-net-1.4.1.jar;
set  JRECLASSPATH=%JRECLASSPATH%%CLS_HOME%\lib\CVS;
set  JRECLASSPATH=%JRECLASSPATH%%CLS_HOME%\lib\jcifs-1.3.12.jar;
set  JRECLASSPATH=%JRECLASSPATH%%CLS_HOME%\lib\jt400.jar;
set  JRECLASSPATH=%JRECLASSPATH%%CLS_HOME%\lib\log4j-1.2.7.jar;
set  JRECLASSPATH=%JRECLASSPATH%%CLS_HOME%\lib\mail.jar;
set  JRECLASSPATH=%JRECLASSPATH%%CLS_HOME%\lib\mdw-languageUtil-1.0.4.jar;
set  JRECLASSPATH=%JRECLASSPATH%%CLS_HOME%\lib\mdw-lawcommons-1.0.6.jar;
set  JRECLASSPATH=%JRECLASSPATH%%CLS_HOME%\lib\mdw-lawstccommons.jar;
set  JRECLASSPATH=%JRECLASSPATH%%CLS_HOME%\lib\mdw-mkpdf-1.0.0.jar;
set  JRECLASSPATH=%JRECLASSPATH%%CLS_HOME%\lib\ojdbc14.jar;
set  JRECLASSPATH=%JRECLASSPATH%%CLS_HOME%\lib\svf.jar;
set  JRECLASSPATH=%JRECLASSPATH%%CLS_HOME%\lib\tools.jar;
set  JRECLASSPATH=%JRECLASSPATH%%CLS_HOME%\lib\utils.jar;
set  JRECLASSPATH=%JRECLASSPATH%%JAVA_HOME%\jre\lib\charsets.jar;
set  JRECLASSPATH=%JRECLASSPATH%%JAVA_HOME%\jre\lib\ext\dnsns.jar;
set  JRECLASSPATH=%JRECLASSPATH%%JAVA_HOME%\jre\lib\ext\ldapsec.jar;
set  JRECLASSPATH=%JRECLASSPATH%%JAVA_HOME%\jre\lib\ext\localedata.jar;
set  JRECLASSPATH=%JRECLASSPATH%%JAVA_HOME%\jre\lib\ext\sunjce_provider.jar;
set  JRECLASSPATH=%JRECLASSPATH%%JAVA_HOME%\jre\lib\jaws.jar;
set  JRECLASSPATH=%JRECLASSPATH%%JAVA_HOME%\jre\lib\jce.jar;
set  JRECLASSPATH=%JRECLASSPATH%%JAVA_HOME%\jre\lib\jsse.jar;
set  JRECLASSPATH=%JRECLASSPATH%%JAVA_HOME%\jre\lib\rt.jar;
set  JRECLASSPATH=%JRECLASSPATH%%JAVA_HOME%\jre\lib\sunrsasign.jar;
set  JRECLASSPATH=%JRECLASSPATH%%JAVA_HOME%\lib\dt.jar;
set  JRECLASSPATH=%JRECLASSPATH%%JAVA_HOME%\lib\htmlconverter.jar;
set  JRECLASSPATH=%JRECLASSPATH%%JAVA_HOME%\lib\tools.jar;
set  JRECLASSPATH=%JRECLASSPATH%%CLS_HOME%\lib\commons-net-1.4.1.jar;
set  JRECLASSPATH=%JRECLASSPATH%%CLS_HOME%\lib\commons-lang-2.1.jar;
set  JRECLASSPATH=%JRECLASSPATH%%CLS_HOME%\lib\commons-io-1.4.jar;
set  JRECLASSPATH=%JRECLASSPATH%%CLS_HOME%\lib\jcifs-1.3.12.jar;
set  JRECLASSPATH=%JRECLASSPATH%"
set  EXECJAVA=%JAVA_HOME%\bin\java -Xms192m -Xmx512m -classpath %JRECLASSPATH%

