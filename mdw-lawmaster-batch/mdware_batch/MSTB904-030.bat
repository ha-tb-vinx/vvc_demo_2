@echo off
rem =======================================
rem MSTB904-030 DWH用IF商品マスタ作成
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MSTB904-030 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL%
