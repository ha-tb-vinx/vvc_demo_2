@echo off

REM ============================================================================
REM （ローカルテスト用）バッチ実行用ファイル 2009/05/07
REM バッチ用クラスパス設定ファイル
REM 引数：なし
REM ============================================================================

rem  == 各実行環境にインストールされているJavaのPathを記述してください。 ==
set  JAVA_HOME="C:\PROGRA~1\Java\IBM_JAVA_5.0"
set  CLS_HOME=%~dp0\..\defaultroot\WEB-INF

set  JRECLASSPATH="

set  JRECLASSPATH=%JRECLASSPATH%%CLS_HOME%\mm-masterbatch.jar;
set  JRECLASSPATH=%JRECLASSPATH%%CLS_HOME%\lib\activation.jar;
set  JRECLASSPATH=%JRECLASSPATH%%CLS_HOME%\lib\jt400.jar;
set  JRECLASSPATH=%JRECLASSPATH%%CLS_HOME%\lib\log4j-1.2.7.jar;
set  JRECLASSPATH=%JRECLASSPATH%%CLS_HOME%\lib\mail.jar;
set  JRECLASSPATH=%JRECLASSPATH%%CLS_HOME%\lib\mdw-mmstccommons.jar;
set  JRECLASSPATH=%JRECLASSPATH%%CLS_HOME%\lib\ojdbc14.jar;
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
set  JRECLASSPATH=%JRECLASSPATH%%JAVA_HOME%\lib\tools.jar
set  JRECLASSPATH=%JRECLASSPATH%"
set  EXECJAVA=%JAVA_HOME%\bin\java -Xms192m -Xmx512m -classpath %JRECLASSPATH%
