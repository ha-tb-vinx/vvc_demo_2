@echo off
rem =======================================
rem MSTB904-100 DWH用店別商品マスタバックアップ
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MSTB904-100 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL%
