@echo off
rem =======================================
rem MBD0-02-01 AS400商品マスタＩＦ作成
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MBD0-02-01 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL% 
