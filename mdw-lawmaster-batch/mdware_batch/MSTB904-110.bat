@echo off
rem =======================================
rem MSTB904-110 DWH用IF店舗マスタ作成
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MSTB904-110 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL%
