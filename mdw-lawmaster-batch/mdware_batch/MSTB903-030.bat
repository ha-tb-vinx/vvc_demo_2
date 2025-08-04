@echo off
rem =======================================
rem MSTB903-030 POP用商品マスタバックアップ
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MSTB903-030 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL%
