@echo off
rem =======================================
rem MSTB995-030 計量器用IF商品マスタ作成
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MSTB995-030 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL%
