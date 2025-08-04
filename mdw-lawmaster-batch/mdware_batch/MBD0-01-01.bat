@echo off
rem =======================================
rem MBD0-01-01 AS400商品マスタ初期
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MBD0-01-01 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL% 
