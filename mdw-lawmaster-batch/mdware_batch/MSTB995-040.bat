@echo off
rem =======================================
rem MSTB995-040 計量器用商品マスタバックアップ
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MSTB995-040 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL%
