@echo off
rem =======================================
rem MBD0-04-01 AS400商品マスタＦＴＰ送信
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MBD0-04-01 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL% 
