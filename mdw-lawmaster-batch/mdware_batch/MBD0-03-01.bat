@echo off
rem =======================================
rem MBD0-03-01 AS400商品マスタＣＳＶ出力
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MBD0-03-01 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL% 
