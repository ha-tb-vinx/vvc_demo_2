@echo off
rem =======================================
rem MSTB904-040 DWH用IF前日商品マスタ作成
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MSTB904-040 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL%
