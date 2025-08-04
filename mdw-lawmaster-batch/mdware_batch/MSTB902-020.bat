@echo off
rem =======================================
rem MSTB902-020 日別店別POS商品マスタ作成
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MSTB902-020 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL%
