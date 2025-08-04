@echo off
rem =======================================
rem MSTB904-080 DWH用IF店別商品マスタ作成
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MSTB904-080 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL%
