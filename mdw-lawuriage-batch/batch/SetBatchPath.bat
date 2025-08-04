@echo off
REM ============================================================================
REM タイトル：環境変数設定用バッチ
REM version 1.00 2009/02/01 H.Yasuda:作成
REM version 1.00 2009/06/23 S.Hamaguchi:修正
REM version 3.00 2013/08/05 M.Ayukawa:ランドローム様対応
REM version 3.01 2013/10/25 T.Ooshiro:ランドローム様対応
REM version 4.00 2016/03/07 M.Kanno:FIVI様対応
REM ============================================================================

set  JAVA_HOME="C:\jdk1.6.0_35"
set  BATCH_HOME=@filter.shellscript.baseDirectory.value@

set  JRECLASSPATH="
set  JRECLASSPATH=%JRECLASSPATH%%BATCH_HOME%\@artifactId@.jar;
set  JRECLASSPATH=%JRECLASSPATH%%BATCH_HOME%\lib\antlr-2.7.2.jar;
set  JRECLASSPATH=%JRECLASSPATH%%BATCH_HOME%\lib\bsf-2.3.0.jar;
set  JRECLASSPATH=%JRECLASSPATH%%BATCH_HOME%\lib\commons-beanutils-1.7.0.jar;
set  JRECLASSPATH=%JRECLASSPATH%%BATCH_HOME%\lib\commons-chain-1.1.jar;
set  JRECLASSPATH=%JRECLASSPATH%%BATCH_HOME%\lib\commons-codec-1.3.jar;
set  JRECLASSPATH=%JRECLASSPATH%%BATCH_HOME%\lib\commons-collections-3.1.jar;
set  JRECLASSPATH=%JRECLASSPATH%%BATCH_HOME%\lib\commons-digester-1.8.jar;
set  JRECLASSPATH=%JRECLASSPATH%%BATCH_HOME%\lib\commons-el-1.0.jar;
set  JRECLASSPATH=%JRECLASSPATH%%BATCH_HOME%\lib\commons-fileupload-1.1.1.jar;
set  JRECLASSPATH=%JRECLASSPATH%%BATCH_HOME%\lib\commons-httpclient-2.0.2.jar;
set  JRECLASSPATH=%JRECLASSPATH%%BATCH_HOME%\lib\commons-io-1.1.jar;
set  JRECLASSPATH=%JRECLASSPATH%%BATCH_HOME%\lib\commons-lang-2.1.jar;
set  JRECLASSPATH=%JRECLASSPATH%%BATCH_HOME%\lib\commons-logging-1.0.4.jar;
set  JRECLASSPATH=%JRECLASSPATH%%BATCH_HOME%\lib\commons-net-1.4.1.jar;
set  JRECLASSPATH=%JRECLASSPATH%%BATCH_HOME%\lib\commons-validator-1.3.1.jar;
set  JRECLASSPATH=%JRECLASSPATH%%BATCH_HOME%\lib\dbunit-2.2.jar;
set  JRECLASSPATH=%JRECLASSPATH%%BATCH_HOME%\lib\jcifs-1.3.12.jar;
set  JRECLASSPATH=%JRECLASSPATH%%BATCH_HOME%\lib\jdom-1.0.jar;
set  JRECLASSPATH=%JRECLASSPATH%%BATCH_HOME%\lib\jstl-1.0.2.jar;
set  JRECLASSPATH=%JRECLASSPATH%%BATCH_HOME%\lib\junit-3.8.1.jar;
set  JRECLASSPATH=%JRECLASSPATH%%BATCH_HOME%\lib\junit-addons-1.4.jar;
set  JRECLASSPATH=%JRECLASSPATH%%BATCH_HOME%\lib\log4j-1.2.9.jar;
set  JRECLASSPATH=%JRECLASSPATH%%BATCH_HOME%\lib\mdw-lawcommons-1.0.6.jar;
set  JRECLASSPATH=%JRECLASSPATH%%BATCH_HOME%\lib\mdw-lawuriage-commons-1.0.0.jar;
set  JRECLASSPATH=%JRECLASSPATH%%BATCH_HOME%\lib\mdware-common-utils-1.0.6.jar;
set  JRECLASSPATH=%JRECLASSPATH%%BATCH_HOME%\lib\mdware-dao-1.0.9.jar;
set  JRECLASSPATH=%JRECLASSPATH%%BATCH_HOME%\lib\myfaces-api-1.1.0.jar;
set  JRECLASSPATH=%JRECLASSPATH%%BATCH_HOME%\lib\ojdbc-14.jar;
set  JRECLASSPATH=%JRECLASSPATH%%BATCH_HOME%\lib\oro-2.0.8.jar;
set  JRECLASSPATH=%JRECLASSPATH%%BATCH_HOME%\lib\poi-3.0.1-FINAL.jar;
set  JRECLASSPATH=%JRECLASSPATH%%BATCH_HOME%\lib\portlet-api-1.0.jar;
set  JRECLASSPATH=%JRECLASSPATH%%BATCH_HOME%\lib\ScStruts-1.0.7.2.jar;
set  JRECLASSPATH=%JRECLASSPATH%%BATCH_HOME%\lib\sso-client-1.0.8.jar;
set  JRECLASSPATH=%JRECLASSPATH%%BATCH_HOME%\lib\standard-1.0.2.jar;
set  JRECLASSPATH=%JRECLASSPATH%%BATCH_HOME%\lib\stclib-mdware-1.0.3.jar;
set  JRECLASSPATH=%JRECLASSPATH%%BATCH_HOME%\lib\struts-core-1.3.8.jar;
set  JRECLASSPATH=%JRECLASSPATH%%BATCH_HOME%\lib\struts-el-1.3.8.jar;
set  JRECLASSPATH=%JRECLASSPATH%%BATCH_HOME%\lib\struts-extras-1.3.8.jar;
set  JRECLASSPATH=%JRECLASSPATH%%BATCH_HOME%\lib\struts-faces-1.3.8.jar;
set  JRECLASSPATH=%JRECLASSPATH%%BATCH_HOME%\lib\struts-mailreader-dao-1.3.8.jar;
set  JRECLASSPATH=%JRECLASSPATH%%BATCH_HOME%\lib\struts-scripting-1.3.8.jar;
set  JRECLASSPATH=%JRECLASSPATH%%BATCH_HOME%\lib\struts-taglib-1.3.8.jar;
set  JRECLASSPATH=%JRECLASSPATH%%BATCH_HOME%\lib\struts-tiles-1.3.8.jar;
set  JRECLASSPATH=%JRECLASSPATH%%BATCH_HOME%\lib\xercesImpl-2.6.2.jar;
set  JRECLASSPATH=%JRECLASSPATH%%BATCH_HOME%\lib\xmlParserAPIs-2.6.2.jar;
set  JRECLASSPATH=%JRECLASSPATH%%BATCH_HOME%\lib\mdw-languageUtil-1.0.4.jar;
set  JRECLASSPATH=%JRECLASSPATH%%JAVA_HOME%\jre\lib\ext\dnsns.jar;
set  JRECLASSPATH=%JRECLASSPATH%%JAVA_HOME%\jre\lib\ext\localedata.jar;
set  JRECLASSPATH=%JRECLASSPATH%%JAVA_HOME%\jre\lib\ext\sunjce_provider.jar;
set  JRECLASSPATH=%JRECLASSPATH%%JAVA_HOME%\jre\lib\ext\sunpkcs11.jar;
set  JRECLASSPATH=%JRECLASSPATH%%JAVA_HOME%\jre\lib\charsets.jar;
set  JRECLASSPATH=%JRECLASSPATH%%JAVA_HOME%\jre\lib\deploy.jar;
set  JRECLASSPATH=%JRECLASSPATH%%JAVA_HOME%\jre\lib\javaws.jar;
set  JRECLASSPATH=%JRECLASSPATH%%JAVA_HOME%\jre\lib\jce.jar;
set  JRECLASSPATH=%JRECLASSPATH%%JAVA_HOME%\jre\lib\jsse.jar;
set  JRECLASSPATH=%JRECLASSPATH%%JAVA_HOME%\jre\lib\plugin.jar;
set  JRECLASSPATH=%JRECLASSPATH%%JAVA_HOME%\jre\lib\rt.jar;
set  JRECLASSPATH=%JRECLASSPATH%%JAVA_HOME%\lib\dt.jar;
set  JRECLASSPATH=%JRECLASSPATH%%JAVA_HOME%\lib\htmlconverter.jar;
set  JRECLASSPATH=%JRECLASSPATH%%JAVA_HOME%\lib\tools.jar;
set  JRECLASSPATH=%JRECLASSPATH%%JAVA_HOME%\lib\jconsole.jar
set  JRECLASSPATH=%JRECLASSPATH%"
set  EXECJAVA="%JAVA_HOME%\bin\java" -Xms192m -Xmx512m -classpath %JRECLASSPATH%

cd  /d %BATCH_HOME%
