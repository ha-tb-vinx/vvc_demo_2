@echo off
rem =======================================
rem MSTB904-120 DWH用IF仕入先マスタ作成
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MSTB904-120 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL%
