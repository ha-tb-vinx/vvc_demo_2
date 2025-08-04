@echo off
rem =======================================
rem MSTB903-020 POP用IF商品マスタ作成
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MSTB903-020 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL%
