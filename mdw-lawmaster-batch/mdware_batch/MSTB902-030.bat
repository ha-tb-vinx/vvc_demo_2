@echo off
rem =======================================
rem MSTB902-030 店別POS商品マスタ作成
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MSTB902-030 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL%
