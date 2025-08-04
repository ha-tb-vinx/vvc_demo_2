@echo off
rem =======================================
rem MSTB904-090 DWH用IF前日店別商品マスタ作成
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MSTB904-090 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL%
