@echo off
rem =======================================
rem MSTB904-050 DWH用商品マスタバックアップ
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MSTB904-050 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL%
